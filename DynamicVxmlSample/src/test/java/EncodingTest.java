import org.apache.http.entity.StringEntity;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by sadurtinova on 09.09.2016.
 */
public class EncodingTest {

    @org.junit.Test
    public void testOfEncoding() throws Exception {
        String str = "hello мой друг";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("hello ");
        stringBuilder.append("мой друг");

        Assert.assertEquals(str, stringBuilder.toString());

        System.out.println(str);
    }

    @Test
    public void testOfStringEntity() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("hello ");
        stringBuilder.append("мой друг");

        StringEntity stringEntityDefault = new StringEntity(stringBuilder.toString());
        StringEntity stringEntity = new StringEntity(stringBuilder.toString(), Charset.forName("utf-8"));

        System.out.println(stringEntityDefault.toString());
        System.out.println(stringEntity.toString());

        BufferedReader readerDefault = new BufferedReader(new InputStreamReader(stringEntityDefault.getContent()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stringEntity.getContent()));

        System.out.println(readerDefault.readLine());
        System.out.println(reader.readLine());


    }
}
