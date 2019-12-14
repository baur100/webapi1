package apiTest;

import com.google.gson.Gson;
import models.GetAllBooksResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpTest {
    @Test
    public void getAllBooks() throws IOException {
        String getAllBooksUrl = "http://booklibrarywebapidev.azurewebsites.net/api/books/all/";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getAllBooksUrl)
                .addHeader("Authorization","Basic YWJjQHh5ei5jb206VGVzdHRlc3QxMjMh")
                .addHeader("Content-Type","application/JSON")
                .build();
        Response response = client.newCall(request)
                .execute();

        Assert.assertEquals(response.code(),200);
        Gson gson = new Gson();

        String body = response.body().string();
        GetAllBooksResponse allBooks =  gson.fromJson(body, GetAllBooksResponse.class);

    }

}
