package com.speechpro.biometric.platform.onepass.rest;

/**
 * Created by sadurtinova on 09.09.2016.
 */
public class OnePassRestPaths {
    private static String person                                = "person/%s";                                  // %s - person ID
    private static String createPerson                          = "person";
    private static String personVoiceDynamicFile                = "person/%s/voice/dynamic/file";               // %s - person ID
    private static String personVoiceStaticFile                 = "person/%s/voice/static/file";                // %s - person ID
    private static String verificationVoiceDynamicFile          = "verification/%s/voice/dynamic/file";         // %s - session ID
    private static String verificationVoiceStaticFile           = "verification/%s/voice/static/file";          // %s - session ID
    private static String verificationStart                     = "verification/start/%s";                      // %s - person ID
    private static String verificationScoreCloseSessionFalse    = "verification/%s/score?close_session=false"; // %s - session ID
    private static String closeVerification                     = "verification/%s";                            // %s - session ID

    private String root             = null;
    private String applicationPath          = "%s://%s/%s/rest/v4/";
    private String applicationPathWithPort  = "%s://%s:%s/%s/rest/v4/";

    public OnePassRestPaths(String protocol, String host, String port, String applicationRoot) {
        if(port.equals(""))
            root = String.format(applicationPath, protocol, host, applicationRoot);
        else
            root = String.format(applicationPathWithPort, protocol, host, port, applicationRoot);
    }

    public String getPersonUri(String personId){
        return getWithRoot(String.format(person, personId));
    }

    public String getCreatePersonUri(){
        return getWithRoot(createPerson);
    }

    public String getPersonVoiceDynamicFileUri(String personId){
        return getWithRoot(String.format(personVoiceDynamicFile, personId));
    }

    public String getPersonVoiceStaticFileUri(String personId){
        return getWithRoot(String.format(personVoiceStaticFile, personId));
    }

    public String getVerificationVoiceDynamicFileUri(String sessionId){
        return getWithRoot(String.format(verificationVoiceDynamicFile, sessionId));
    }

    public String getVerificationVoiceStaticFileUri(String sessionId){
        return getWithRoot(String.format(verificationVoiceStaticFile, sessionId));
    }

    public String getVerificationStartUri (String personId){
        return getWithRoot(String.format(verificationStart, personId));
    }

    public String getVerificationScoreCloseSessionFalseUri(String sessionId){
        return getWithRoot(String.format(verificationScoreCloseSessionFalse, sessionId));
    }

    public String getCloseVerificationUri (String sessionId){
        return getWithRoot(String.format(closeVerification, sessionId));
    }

    private String getWithRoot(String resourceUri){
        return String.format("%s%s", root, resourceUri);
    }


}
