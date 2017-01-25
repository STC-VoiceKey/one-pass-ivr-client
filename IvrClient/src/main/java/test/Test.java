package test;

import com.speechpro.biometric.platform.onepass.api.OnePassApi;
import com.speechpro.biometric.platform.onepass.api.PersonApi;
import com.speechpro.biometric.platform.onepass.api.VerificationApi;

/**
 * Created by sadurtinova on 12.01.2017.
 */
public class Test {

    public static void main(String [] args){

        OnePassApi onePassApi = new OnePassApi("http", "192.168.2.137", "8080", "vkonepass");
        VerificationApi verificationApi = onePassApi.person("111").startVerification();
        String password  = verificationApi.getVerificationPassword();
        String sessionId = verificationApi.getSessionId();
        System.out.println(password);

    }
}
