package net.diegolemos.codeelevator;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.runners.MethodSorters.JVM;

@FixMethodOrder(JVM) // Caution: the order from the JVM may vary from run to run
public class MyHttpServerTest {

    @BeforeClass
    public static void startServer()
    {
        new MyHttpServer().run();
    }

    @Test
    public void server_should_be_up() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/test").openConnection();

        int responseCode = connection.getResponseCode();

        assertThat(responseCode).isEqualTo(200);
    }

    @Test
    public void should_respond_200_for_call() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/call?atFloor=1&to=UP").openConnection();

        int responseCode = connection.getResponseCode();

        assertThat(responseCode).isEqualTo(200);
    }

    @Test
    public void should_respond_up_for_nextCommand() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/nextCommand").openConnection();

        String message = convertStreamToString(connection.getInputStream());

        assertThat(message).isEqualTo("UP");
    }

    @Test
    public void should_respond_open_for_nextCommand() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/nextCommand").openConnection();

        String message = convertStreamToString(connection.getInputStream());

        assertThat(message).isEqualTo("OPEN");
    }

    @Test
    public void should_respond_close_for_nextCommand() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/nextCommand").openConnection();

        String message = convertStreamToString(connection.getInputStream());

        assertThat(message).isEqualTo("CLOSE");
    }

    @Test
    public void should_respond_nothing_for_nextCommand() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/nextCommand").openConnection();

        String message = convertStreamToString(connection.getInputStream());

        assertThat(message).isEqualTo("NOTHING");
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
