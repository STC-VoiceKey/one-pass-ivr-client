package com.speechpro.biometric.platform.onepass.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sadurtinova on 06.09.2016.
 */
public class SoundSender {

    public static final Logger LOGGER = Logger.getLogger("SoundSender");
    public static byte [] readAudioFromRequest(HttpServletRequest request){
        LOGGER.info("Reading audio from request...");
        byte[] audioBytes = new byte[1024];
        //boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List fileItems = null;
        try {
            fileItems = upload.parseRequest(request);
        } catch (FileUploadException e) {
            LOGGER.error("Couldn't upload the file!");
            e.printStackTrace();
        }
            Iterator iterator = fileItems.iterator();

            while(iterator.hasNext()) {
                FileItem item = (FileItem)iterator.next();
                audioBytes = item.get();
            }
        return audioBytes;
    }

    private static String convertBytesToBase64String(byte[] soundBytes){
        byte[] encoded = Base64.encodeBase64(soundBytes);
        String encodedString = new String(encoded);
        System.out.println("Encoded bytes: " + encodedString);
        return encodedString;
    }
}
