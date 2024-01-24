package tie.lehrlinge.lehrverwaltung.server.util;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.StreamType;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@UtilityClass
@Log4j2
public class DockerJavaUtil {

    DockerClientConfig config;

    // Verbindung mit Docker herstellen
    DockerHttpClient httpClient;

    public DockerClient dockerClient;

    HostConfig hostConfig;

    public CreateContainerResponse containerResponse;

    public static void prepare(String dockerHost) {
        log.debug("Für die VM werden 512 MB RAM und 512 CPU Shares zugewiesen");
        hostConfig = new HostConfig()
                .withMemory(512 * 1024 * 1024L)
                .withCpuShares(512);
        log.info("Verbindungsversuch mit dem Host: " + dockerHost);
        config = DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerHost(dockerHost).build();
        httpClient = new ApacheDockerHttpClient.Builder().dockerHost(config.getDockerHost()).build();
        dockerClient = DockerClientImpl.getInstance(config, httpClient);
    }

    // Images aus Docker holen
    public static List<Image> getImages() {
        return dockerClient.listImagesCmd().withShowAll(true).exec();
    }

    public static boolean checkIfImageIsInDocker(String imageName) {
        log.debug("Das Image wird gesucht");
        for (Image image : getImages()) {
            if (image.getRepoTags() != null) {
                if (image.getRepoTags()[0].equals(imageName)) {
                    log.debug("Image: " + imageName + " ist installiert");
                    return true;
                }
            }
        }
        log.debug("Das Image wurde nicht gefunden");
        return false;
    }

    public static void checkIfContainerExistsOrCreateNew(String containerName, String imageName) {
        while (true) {
            try {
                InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(containerName).exec();
                log.info("Altes Container wurde gefunden");
                if (inspectContainerResponse.getState().getRunning()) {
                    log.info("Altes Container ist bereits gestartet und wird gestoppt");
                    dockerClient.stopContainerCmd(containerName).exec();
                }
                log.info("Altes Container wurde gestoppt");
                dockerClient.removeContainerCmd(containerName).exec();
            } catch (NotFoundException e) {
                log.info("Container existiert nicht");
                break;
            }
        }
        containerResponse = dockerClient.createContainerCmd(imageName)
                .withName(containerName)
                .withHostConfig(hostConfig)
                .withTty(true)
                .withCmd("bash")
                .exec();

        log.info("Neues Container mit dem id " + containerResponse.getId() + " wurde erstellt");
        dockerClient.startContainerCmd(containerResponse.getId()).exec();
    }

    public static void checkIfImageExistsOrCreate(String imageName) {
        if (!checkIfImageIsInDocker(imageName)) {
            log.info("Image: " + imageName + " ist nicht installiert und wird gleich installiert");
            try {
                dockerClient.pullImageCmd(imageName).exec(new PullImageResultCallback()).awaitCompletion();
                log.info(imageName + " wurde installiert");
            } catch (InterruptedException interruptedException) {
                throw new RuntimeException(interruptedException);
            }
        }
        log.debug("GraalVM lauft auf dem " + imageName + " Imagestand");
    }

    public static void runInstallCommand(String command, String option, String argument, CountDownLatch latch) {
        log.info(argument + " wird installiert");
        ExecCreateCmdResponse execCreateCmdResponse = dockerClient
                .execCreateCmd(containerResponse.getId())
                .withAttachStdout(true)
                .withAttachStderr(true)
                .withCmd(command, option, argument)
                .exec();
        try {
            String execId = execCreateCmdResponse.getId();
            log.debug("Docker Logs from Container with id:" + execId + " ");
            dockerClient.execStartCmd(execId).exec(
                    new ResultCallback.Adapter<Frame>() {
                        @Override
                        public void onNext(Frame item) {
                            String logMessage = new String(item.getPayload());
                            if (item.getStreamType() == StreamType.STDOUT) {
                                log.debug("log: " + logMessage);
                            } else if (item.getStreamType() == StreamType.STDERR) {
                                log.debug("error: " + logMessage);
                            }
                        }
                    }).awaitCompletion();
            log.info(argument + " wurde installiert");
            latch.countDown();
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
        }
    }

    public static void stopContainer() {
        log.info("Container " + containerResponse.getId() + " wird gestoppt");
        dockerClient.stopContainerCmd(containerResponse.getId()).exec();
    }

    public static void transferFileToDocker(String fileName) {
        log.info("Datei wird auf Docker übertragen");
        dockerClient.copyArchiveToContainerCmd(containerResponse.getId())
                .withHostResource("F:/temp/" + fileName)
                .withRemotePath("/usr/src/")
                .exec();
    }
}

