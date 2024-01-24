package tie.lehrlinge.lehrverwaltung.server.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import tie.lehrlinge.lehrverwaltung.server.model.entity.FileObject;

import java.io.FileOutputStream;
import java.io.IOException;

@Log4j2
@UtilityClass
public class FileUtil {
    public static void saveFile(String dir, FileObject codeFile) {
        log.info("Datei: {} wird unter den Verzeichnis", codeFile.getName());
        try (FileOutputStream fos = new FileOutputStream(dir + codeFile.getName())) {
            fos.write(codeFile.getData());
        } catch (IOException e) {
            log.error("Verzeichnis existiert nicht mehr");
            e.printStackTrace();
        }
    }
}
