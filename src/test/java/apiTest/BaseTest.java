        package apiTest;

        import java.util.HashMap;
        import java.util.Map;

public class BaseTest {
    //        URIBuilder url = new URIBuilder();
//        url.setScheme("http")
//                .setHost("booklibrarywebapidev.azurewebsites.net")
//                .setPath("api/books/id/240")
//                .setPort(80)
//                .build();
//
    protected Map<String,String> headers;
    protected final String baseUrl = "http://booklibrarywebapidev.azurewebsites.net/";

    public BaseTest() {
        headers=new HashMap<>();
        headers.put("Authorization","Basic YWJjQHh5ei5jb206VGVzdHRlc3QxMjMh");
        headers.put("Content-Type","application/JSON");
    }
}