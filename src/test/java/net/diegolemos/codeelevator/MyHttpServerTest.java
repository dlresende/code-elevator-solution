package net.diegolemos.codeelevator;

import org.junit.BeforeClass;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.fest.assertions.Assertions.assertThat;

public class MyHttpServerTest {
    @BeforeClass
    public static void startServer()
    {
        new MyHttpServer().run();
    }

    @Test
    public void server_should_be_up() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/test").openConnection();
        connection.setRequestMethod("HEAD");

        int responseCode = connection.getResponseCode();

        assertThat(responseCode).isEqualTo(200);
    }

    @Test
    public void should_answer_200_on_user_calls() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/call?atFloor=0&to=UP").openConnection();
        connection.setRequestMethod("HEAD");

        int responseCode = connection.getResponseCode();

        assertThat(responseCode).isEqualTo(200);
    }
}
