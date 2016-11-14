import com.speechpro.biometric.platform.onepass.dto.SendDynamicFileRequestDto;
import com.speechpro.biometric.platform.onepass.util.JsonSerializer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by sadurtinova on 09.09.2016.
 */
public class DtoTest {

    @Test
    public void testSerialization() throws Exception {

        String dataBase64 = "dfghdlfghdfhgdhfgdhlfjgldkjfhgldjgfkjdh";
        String password = "one two three four five";

        SendDynamicFileRequestDto request = new SendDynamicFileRequestDto(password, dataBase64);


        String serializedString = JsonSerializer.serialize(request);
        SendDynamicFileRequestDto deserialized = JsonSerializer.deserialize(serializedString, SendDynamicFileRequestDto.class);

        System.out.println(request.toString());

        Assert.assertEquals(request.toString(), deserialized.toString());
    }

}
