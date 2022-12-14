package fr.cyu.rt.control.services.camera;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Aldric Vitali Silvestre
 */
@Service
public class CameraStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(CameraStorage.class);

    private static final DateTimeFormatter DATE_FILENAME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss_SSSS");

    @Autowired
    private CameraRegistry cameraRegistry;

    @Autowired
    private ImageWriter imageWriter;

    private Path rootPath;

    public CameraStorage(@Value("${camera.storage.root_path}") String rootPath) {
        this.rootPath = Path.of(rootPath);
        File file = this.rootPath.toFile();
        if (!file.exists() || !file.isDirectory()) {
            throw new IllegalArgumentException("The path for the camera images does not exist: " + rootPath);
        }
    }

    /**
     * Save the last image retrieved from the house to the content folder.
     *
     * @return the path of the image stored
     */
    public String registerLastImage() {
        LOGGER.trace("register last image");
        var currentTimestamp = LocalDateTime.now();
        String dateStr = currentTimestamp.format(DateTimeFormatter.ISO_LOCAL_DATE);
        Path folderPath = rootPath.resolve(dateStr);
        File folder = folderPath.toFile();
        if (!folder.exists()) {
            LOGGER.debug("Create folder \"{}\"in camera root path", folder.getName());
            folder.mkdir();
        }
        String imageFilename = currentTimestamp.format(DATE_FILENAME_FORMAT);
        File file = folderPath.resolve(imageFilename).toFile();
        if (file.exists()) {
            LOGGER.warn("File \"{}\" already exists ! Will be replaced", file.getPath());
        }
        byte[] data = cameraRegistry.getImageBinary();
        // TODO what is the format ?
        imageWriter.saveRawGreyscaleImage(400, 400, data, file);
        return file.toPath().toAbsolutePath().toString();
    }
}
