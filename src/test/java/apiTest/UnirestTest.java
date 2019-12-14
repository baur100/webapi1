package apiTest;

import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import models.GetAllBooksResponse;
import org.testng.Assert;
import org.testng.annotations.Test;


public class UnirestTest {
    @Test
    public void getAllBooks(){
        Gson gson = new Gson();
        String getAllBooksUrl = "http://booklibrarywebapidev.azurewebsites.net/api/books/all/";
        HttpResponse<String> response = Unirest.get(getAllBooksUrl)
                .header("Authorization","Basic YWJjQHh5ei5jb206VGVzdHRlc3QxMjMh")
                .header("Content-Type","application/JSON")
                .asString();

        Assert.assertEquals(response.getStatus(),200);

        GetAllBooksResponse allBooks =  gson.fromJson(response.getBody(), GetAllBooksResponse.class);
        Assert.assertTrue(allBooks.getErrors().size()==0);
        Assert.assertTrue(allBooks.getValue().size()>0);

    }
}
