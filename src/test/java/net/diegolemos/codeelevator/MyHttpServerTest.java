package net.diegolemos.codeelevator;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

@FixMethodOrder(NAME_ASCENDING)
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
    public void _1_should_respond_200_when_a_call_from_1st_floor_is_made() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/call?atFloor=1&to=UP").openConnection();

        int responseCode = connection.getResponseCode();

        assertThat(responseCode).isEqualTo(200);
    }

    @Test
    public void _2_should_respond_UP_and_go_to_the_1st_floor() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/nextCommand").openConnection();

        String message = convertStreamToString(connection.getInputStream());

        assertThat(message).isEqualTo("UP");
    }

    @Test
    public void _3_should_respond_OPEN_when_arrived_on_1st_floor() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/nextCommand").openConnection();

        String message = convertStreamToString(connection.getInputStream());

        assertThat(message).isEqualTo("OPEN");
    }

    @Test
    public void _4_should_respond_CLOSE_once_doors_were_opened() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/nextCommand").openConnection();

        String message = convertStreamToString(connection.getInputStream());

        assertThat(message).isEqualTo("CLOSE");
    }

    @Test
    public void _5_should_respond_NOTHING_for_nextCommand() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/nextCommand").openConnection();

        String message = convertStreamToString(connection.getInputStream());

        assertThat(message).isEqualTo("NOTHING");
    }

    @Test
    public void _6_should_respond_200_if_someone_inside_wants_to_go_to_the_3rd_floor() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/go?floorToGo=3").openConnection();

        int responseCode = connection.getResponseCode();

        assertThat(responseCode).isEqualTo(200);
    }

    @Test
    public void _7_should_respond_UP_and_go_to_the_2nd_floor() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/nextCommand").openConnection();

        String message = convertStreamToString(connection.getInputStream());

        assertThat(message).isEqualTo("UP");
    }

    @Test
    public void _8_should_respond_OPEN_once_in_the_3rd_floor() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/nextCommand").openConnection();

        String message = convertStreamToString(connection.getInputStream());

        assertThat(message).isEqualTo("UP");
    }

    @Test
    public void should_respond_200_on_reset() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/reset").openConnection();

        int responseCode = connection.getResponseCode();

        assertThat(responseCode).isEqualTo(200);
    }

    @Test
    public void should_respond_200_on_userHasExited() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/userHasExited").openConnection();

        int responseCode = connection.getResponseCode();

        assertThat(responseCode).isEqualTo(200);
    }

    @Test
    public void should_respond_200_on_userHasEntered() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9000/userHasEntered").openConnection();

        int responseCode = connection.getResponseCode();

        assertThat(responseCode).isEqualTo(200);
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
