package tie.lehrlinge.lehrverwaltung.server;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tie.lehrlinge.lehrverwaltung.server.util.DockerJavaUtil;

@SpringBootApplication
public class ServerApplication implements ApplicationRunner {


  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      DockerJavaUtil.stopContainer();
    }));
  }
}
