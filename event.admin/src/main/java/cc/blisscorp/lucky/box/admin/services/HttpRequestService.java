/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.lucky.box.admin.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author anhlnt
 */
public class HttpRequestService {

    public JsonObject get(String url) throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        HttpResponse response = client.execute(request);

        return getResponseContent(response);
    }

    public JsonObject post(String url, String jsonRequest) throws IOException {

        HttpClient client = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(url);
        
        StringEntity entity = new StringEntity(jsonRequest, "UTF-8");
        entity.setContentType("application/json");

        post.setEntity(entity);

        HttpResponse response = client.execute(post);

        return getResponseContent(response);
    }

    protected JsonObject getResponseContent(HttpResponse res) throws IOException {
        InputStreamReader reader = null;

        reader = new InputStreamReader(res.getEntity().getContent(), "UTF-8");

        JsonParser parser = new JsonParser();
        return parser.parse(reader).getAsJsonObject();
    }

}
