import com.speechpro.biometric.platform.onepass.rest.OnePassRestPaths;
import junit.framework.Assert;

/**
 * Created by sadurtinova on 09.11.2016.
 */
public class OnePassRestStaticPathsTest {

    private static final OnePassRestPaths REST_PATHS = new OnePassRestPaths("http", "192.168.29.169", "", "ivr_static");
    private static final String PERSON_ID = "01";
    private static final String SESSION_ID = "a00a0000-a00a-0000-00a0-000a0aaaa000";

    @org.junit.Test
    public void testGetPersonUri (){
        Assert.assertEquals("http://192.168.29.169/ivr_static/rest/v4/" +
                        "person/01",
                REST_PATHS.getPersonUri(PERSON_ID));
    }

    @org.junit.Test
    public void testGetCreatePersonUri (){
        Assert.assertEquals("http://192.168.29.169/ivr_static/rest/v4" +
                        "/person",
                REST_PATHS.getCreatePersonUri());
    }

    @org.junit.Test
    public void testGetPersonVoiceDynamicFileUri (){
        Assert.assertEquals("http://192.168.29.169/ivr_static/rest/v4/" +
                        "person/01/voice/dynamic/file",
                REST_PATHS.getPersonVoiceDynamicFileUri(PERSON_ID));
    }

    @org.junit.Test
    public void testGetPersonVoiceStaticFileUri (){
        Assert.assertEquals("http://192.168.29.169/ivr_static/rest/v4/" +
                        "person/01/voice/static/file",
                REST_PATHS.getPersonVoiceStaticFileUri(PERSON_ID));
    }

    @org.junit.Test
    public void testGetVerificationVoiceDynamicFileUri (){
        Assert.assertEquals("http://192.168.29.169/ivr_static/rest/v4/" +
                        "verification/01/voice/dynamic/file",
                REST_PATHS.getVerificationVoiceDynamicFileUri(PERSON_ID));
    }

    @org.junit.Test
    public void testGetVerificationVoiceStaticFileUri (){
        Assert.assertEquals("http://192.168.29.169/ivr_static/rest/v4/" +
                        "verification/01/voice/static/file",
                REST_PATHS.getVerificationVoiceStaticFileUri(PERSON_ID));
    }

    @org.junit.Test
    public void testGetVerificationStartUri () {
        Assert.assertEquals("http://192.168.29.169/ivr_static/rest/v4/" +
                        "verification/start/a00a0000-a00a-0000-00a0-000a0aaaa000",
                REST_PATHS.getVerificationStartUri(SESSION_ID));
    }

    @org.junit.Test
    public void testGetVerificationScoreCloseSessionTrueUri () {
        Assert.assertEquals("http://192.168.29.169/ivr_static/rest/v4/" +
                        "verification/a00a0000-a00a-0000-00a0-000a0aaaa000/score?close_session=false",
                REST_PATHS.getVerificationScoreCloseSessionFalseUri(SESSION_ID));
    }

    @org.junit.Test
    public void testCloseVerificationSessionUri(){
        Assert.assertEquals("http://192.168.29.169/ivr_static/rest/v4/" +
                        "verification/a00a0000-a00a-0000-00a0-000a0aaaa000",
                REST_PATHS.getCloseVerificationUri(SESSION_ID));
    }

}
