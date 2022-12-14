package fr.cyu.rt.control.services.camera;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;
import java.nio.file.Path;

/**
 * Responsible for creating images from various formats
 *
 * @author Aldric Vitali Silvestre
 */
@Service
public class ImageWriter {

    public void saveRawGreyscaleImage(int width, int height, byte[] data, File destination) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        byte[] array = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(data, 0, array, 0, array.length);
        try {
            ImageIO.write(image, "png", destination);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
