package apiTest;

import com.google.gson.Gson;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.Unirest;
import models.GetAllBooksResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import kong.unirest.HttpResponse;

public class UnirestTest {
    Gson gson=new Gson();
@Test
    public void getAllBooks(){
    String getAllBookUrl="http://booklibrarywebapidev.azurewebsites.net/api/books/all/";
    HttpResponse<JsonNode> response= Unirest.get(getAllBookUrl)
    .header("Authorization","Basic YWJjQHh5ei5jb206VGVzdHRlc3QxMjMh")
            .header("Content Type", "application/JSON")
            .asJson();
    Assert.assertEquals(response.getStatus(), 200);
String body = response.getBody().toString();
    GetAllBooksResponse allBooks = gson.fromJson(response.getBody(), GetAllBooksResponse.class);
Assert.assertTrue(allBooks.);
}
}
