package com.speechpro.biometric.platform.onepass.dto;


import com.speechpro.biometric.platform.onepass.util.JsonSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by sadurtinova on 15.09.2016.
 */
public class DtoHelper {

    public static <T> T create(InputStream sourceStream, Class<T> type){
        T result = null;

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(sourceStream))) {
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);

            result = JsonSerializer.deserialize(sb.toString(), type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
