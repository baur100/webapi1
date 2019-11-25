package apiTest;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected Map<String, String> headers;
    protected final String baseUrl = "http://booklibrarywebapidev.azurewebsites.net";

    public BaseTest() {
        this.headers = new HashMap<>();
        this.headers.put("Authorization", "Basic YWJjQHh5ei5jb206VGVzdHRlc3QxMjMh");
        this.headers.put("Content-Type", "application/JSON");

    }
}
