package tie.lehrlinge.lehrverwaltung.server.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import tie.lehrlinge.lehrverwaltung.server.util.DockerJavaUtil;

import java.util.concurrent.CountDownLatch;

@Component
@Log4j2
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${docker-java.host.url}")
    String dockerHost;

    @Value("${docker-java.image.name}")
    String imageName;

    @Value("${docker-java.container.name}")
    String containerName;

    private final CountDownLatch latch = new CountDownLatch(1);


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        DockerJavaUtil.prepare(dockerHost);
        DockerJavaUtil.checkIfImageExistsOrCreate(imageName);
        DockerJavaUtil.checkIfContainerExistsOrCreateNew(containerName, imageName);
        DockerJavaUtil.runInstallCommand("gu", "install", "nodejs", latch);
        this.stopLatch();
        DockerJavaUtil.runInstallCommand("npm", "install", "ts-node", latch);
        this.stopLatch();
    }

    private void stopLatch(){
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
