package mate.academy;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class FileUtil {
    public static String encodeFileToBase64(String path) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        return Base64.getEncoder().encodeToString(bytes);
    }
}
