package apiTest;

import org.testng.annotations.Test;
import io.restassured.response.Response;


public class GetTest {

    @Test
            public void testGet_getBookById(){
        Response responce = given()
                .baseUrl("http://booklibrarywebapidev.azurewebsites.net")
                .basePath("")
                .header("Authorization", )

    }


}
