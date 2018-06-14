package com.speechpro.biometric.platform.onepass.util;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by sadurtinova on 01.05.2018.
 */
public class DataConverter {

    public static final Logger LOGGER = Logger.getLogger("DataConverter");

    public static String convertFileToBase64(File file){
        byte [] fileBytes = new byte[0];
        try {
            fileBytes = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            LOGGER.error("Couldn't read file " + file.toString() + ": " + e.getMessage());
            e.printStackTrace();
        }
        String encodedFile = SoundSender.convertBytesToBase64String(fileBytes);
        return encodedFile;
    }

}
