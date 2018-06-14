package com.speechpro.biometric.platform.onepass.rest;

/**
 * Created by sadurtinova on 09.09.2016.
 */
public class OnePassRestPaths {
    private static String session = "session";
    private static String person = "person/%s";                                  // %s - person ID
    private static String createPerson = "registration/person/%s";                     // %s - person ID
    private static String personVoiceDynamicFile = "registration/voice/dynamic/file";            // %s - person ID
    private static String personVoiceStaticFile = "registration/voice/static/file";             // %s - person ID
    private static String personPhotoFile = "registration/face/file";                     // %s - person ID
    private static String verificationVoiceDynamicFile = "verification/voice/dynamic/file";            // %s - session ID
    private static String verificationVoiceStaticFile = "verification/voice/static/file";             // %s - session ID
    private static String verificationStart = "verification/person/%s";                     // %s - person ID
    private static String verificationScoreCloseSessionFalse = "verification/score";                         // %s - session ID
    private static String closeVerification = "startVerification";                               // %s - session ID

    private String root;

    public OnePassRestPaths(String protocol, String host, String port, String applicationRoot) {
        if (port == null) {
            root = String.format("%s://%s/%s/", protocol, host, applicationRoot);
        } else {
            root = String.format("%s://%s:%s/%s/", protocol, host, port, applicationRoot);
        }
    }

    public String getPersonUri(String personId) {
        return getWithRoot(String.format(person, personId));
    }

    public String getStartSessionUri() {
        return getWithRoot(session);
    }

    public String getCreatePersonUri(String personId) {
        return getWithRoot(String.format(createPerson, personId));
    }

    public String getPersonVoiceDynamicFileUri(String personId) {
        return getWithRoot(String.format(personVoiceDynamicFile, personId));
    }

    public String getPersonVoiceStaticFileUri(String personId) {
        return getWithRoot(String.format(personVoiceStaticFile, personId));
    }

    public String getPersonPhotoFileUri() {
        return getWithRoot(personPhotoFile);
    }

    public String getVerificationVoiceDynamicFileUri(String sessionId) {
        return getWithRoot(String.format(verificationVoiceDynamicFile, sessionId));
    }

    public String getVerificationVoiceStaticFileUri(String sessionId) {
        return getWithRoot(String.format(verificationVoiceStaticFile, sessionId));
    }

    public String getVerificationStartUri(String personId) {
        return getWithRoot(String.format(verificationStart, personId));
    }

    public String getVerificationScoreCloseSessionFalseUri(String sessionId) {
        return getWithRoot(String.format(verificationScoreCloseSessionFalse, sessionId));
    }

    public String getCloseVerificationUri(String sessionId) {
        return getWithRoot(String.format(closeVerification, sessionId));
    }

    private String getWithRoot(String resourceUri) {
        return String.format("%s%s", root, resourceUri);
    }

}
