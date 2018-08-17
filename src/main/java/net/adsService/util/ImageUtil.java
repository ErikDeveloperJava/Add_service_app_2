package net.adsService.util;

import lombok.Cleanup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ImageUtil {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private Environment env;
    private static final String[] IMAGE_FORMATS = {"image/jpeg", "image/png"};

    public boolean isValidFormat(String format) {
        for (String imageFormat : IMAGE_FORMATS) {
            if (imageFormat.equals(format)) {
                return true;
            }
        }
        return false;
    }

    public void save(String parent, String child, MultipartFile img) {
        File file = new File(env.getProperty("image.file.path"), parent);
        if (!file.exists()) {
            if (!file.mkdir()) {
                throw new RuntimeException("parent ' " + parent + " ' does not created");
            }
        }
        try {
            @Cleanup FileOutputStream out = new FileOutputStream(new File(file, child));
            out.write(img.getBytes());
            LOGGER.info("{} image, successfully saved", child);
        } catch (IOException e) {
            throw new RuntimeException("'" + child + "' failed save", e);
        }
    }

    public void delete(String filePath) {
        File file = new File(env.getProperty("image.file.path"), filePath);
        if (file.exists()) {
            if (file.delete()) {
                LOGGER.debug("'{}' file successfully deleted");
            } else {
                LOGGER.debug("'{}' file failed delete");
            }
        }
    }

    public void deleteAll(String parent) {
        File file = new File(env.getProperty("image.file.path"), parent);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    delete(f.getName());
                }
            }
            if (file.delete()) {
                LOGGER.debug("'{}' file successfully deleted");
            } else {
                LOGGER.debug("'{}' file failed delete");
            }
        }
    }
}
