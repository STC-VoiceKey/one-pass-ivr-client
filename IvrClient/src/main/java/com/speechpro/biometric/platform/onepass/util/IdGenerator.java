package com.speechpro.biometric.platform.onepass.util;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by sadurtinova on 10.10.2016.
 */
public class IdGenerator {
    private static final Logger LOGGER = Logger.getLogger(IdGenerator.class);
    private String idFile;

    public IdGenerator(){
        ConfigReader conf = new ConfigReader();
        this.idFile = conf.readProperty("biometric.platform.id");
    }

    public int getId (){
        int id = 0;
        File file = new File(idFile);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            LOGGER.error("File with id not found; path is " + idFile);
            e.printStackTrace();
        }
        String line = null;
        try {
            line = br.readLine();
            br.close();
        } catch (IOException e) {
            LOGGER.error("Error during reading of id");
            e.printStackTrace();
        }

        id = Integer.parseInt(line);
        rewriteFile(id);
        return id;
    }

    private void rewriteFile(int id){
        File newFile = new File(idFile);
        try {
            FileWriter fw = new FileWriter(newFile, false);
            fw.write(String.valueOf(id+1));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
