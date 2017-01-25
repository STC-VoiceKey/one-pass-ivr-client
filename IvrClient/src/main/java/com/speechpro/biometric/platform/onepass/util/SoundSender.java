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
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List fileItems = null;
        try {
            fileItems = upload.parseRequest(request);
        } catch (FileUploadException e) {
            LOGGER.info("!!!__Couldn't upload the file__!!!");
            e.printStackTrace();
        }
            Iterator iterator = fileItems.iterator();

            while(iterator.hasNext()) {
                FileItem item = (FileItem)iterator.next();
                audioBytes = item.get();
            }
        return audioBytes;
    }

    public static byte[] readAudioFromAvayaDD(final String sourcePath){
        URL sourceURL = null;
        try {
            sourceURL = new URL(sourcePath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection connection = null;
        try {
            connection = sourceURL.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            IOUtils.copy(connection.getInputStream(), baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] fileBytes = baos.toByteArray();
        return fileBytes;
    }

    public static String convertBytesToBase64String(byte[] soundBytes){
        byte[] encoded = Base64.encodeBase64(soundBytes);
        String encodedString = new String(encoded);
        System.out.println("Encoded bytes: " + encodedString);
        return encodedString;
    }

    public static void saveAudioFromURL(String sourcePath) {
        //String sourcePath = "http://localhost:8080/test4/data/temp/file_undefined_1872067389446421012.wav";
        String filename = "F:\\Avaya\\sounds\\";
        String pathName = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date().getTime());
        filename += pathName.substring(0, 4) + "/" + pathName.substring(4, 6) + "/" + pathName.substring(6, 8) + "/" + pathName + "_verify.wav";
        URL sourceURL = null;
        try {
            sourceURL = new URL(sourcePath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.print(sourceURL.toString());
        try {
            FileUtils.copyURLToFile(sourceURL, new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAudio(byte[] bytes){
        String filename = "F:\\Avaya\\sounds\\";
        String pathName = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date().getTime());
        filename += pathName.substring(0, 4) + "/" + pathName.substring(4, 6) + "/" + pathName.substring(6, 8) + "/" + pathName + "_verify.wav";
        try {
            FileUtils.writeByteArrayToFile(new File(filename), bytes);
            LOGGER.info("File saved to: " + filename);
        } catch (IOException e) {
            LOGGER.info("!!!__Couldn't write bytes into the file__!!!");
            e.printStackTrace();
        }
    }

}
