package homework.week4.FileUpload;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileStorage implements FileStorageService {
    @Override
    public String fileStore(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String uploadDir = "src/main/resources/static/UploadPhoto/ProfileImage";
            Path directoryPath = Paths.get(uploadDir).toAbsolutePath();
            Path savePath = directoryPath.resolve(file.getOriginalFilename());
            try {
                file.transferTo(savePath.toFile());
            } catch (IOException | IllegalStateException e) {
                throw new RuntimeException(e);
            }
            return savePath.toString();
        }
        return null;
    }
}
