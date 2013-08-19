package net.diegolemos.codeelevator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static org.fest.assertions.Assertions.*;

public class MyHttpServerIT
{
    private static final int TEST_PORT = 8001;
    private static final String LOCALHOST = "http://localhost:" + TEST_PORT;
    private static MyHttpServer server = new MyHttpServer(TEST_PORT);

    @BeforeClass
    public static void startServer() throws Exception {
        server.run();
    }

    @Test
    public void server_should_be_up() throws Exception {
        assertThat(get("/test")).isEqualTo("Server is up!");
    }

    @Test
    public void should_go_to_1st_and_3rd_floor() throws Exception {
        assertThat(get("/call", "atFloor=1&to=UP")).isEqualTo("OK");
        assertThat(get("/nextCommand")).isEqualTo("UP");
        assertThat(get("/nextCommand")).isEqualTo("OPEN");
        assertThat(get("/nextCommand")).isEqualTo("CLOSE");
        assertThat(get("/nextCommand")).isEqualTo("NOTHING");
        assertThat(get("/go", "floorToGo=3")).isEqualTo("OK");
        assertThat(get("/nextCommand")).isEqualTo("UP");
        assertThat(get("/nextCommand")).isEqualTo("UP");
        assertThat(get("/reset")).isEqualTo("OK");
        assertThat(get("/userHasExited")).isEqualTo("OK");
        assertThat(get("/userHasEntered")).isEqualTo("OK");
    }

    private String get(String path) throws IOException {
        return get(path, "");
    }

    private String get(String path, String query) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(LOCALHOST + path + "?" + query).openConnection();
        connection.disconnect();
        return convertStreamToString(connection.getInputStream());
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    @AfterClass
    public static void stopServer() {
        server.shutdown();
    }
}
