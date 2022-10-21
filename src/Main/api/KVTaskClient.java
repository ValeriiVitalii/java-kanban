package api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    HttpClient client = HttpClient.newHttpClient();
    protected String url;
    protected String token;

    public KVTaskClient(String path) {
        this.url = path;
    }

    public void put(String key, String json)  {
        if (token == null) {
            System.out.println("Не получен токен!");
            return;
        }
        HttpRequest httprequest = HttpRequest.newBuilder()
                .uri(URI.create(url + "/save" + key + "?API_TOKEN=" + token))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(httprequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(httpResponse.statusCode());
        } catch (IOException | InterruptedException e) {
            System.out.println("Произошла ошибка во время запроса\n" + "Проверьте адрес и повторите попытку.");
        }
    }

    public String register() {
        URI url = URI.create(this.url + "/register");
        HttpRequest httprequest = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(httprequest, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                token = httpResponse.body();
                return token;
            } else System.out.println("Не получен токен!");
        } catch (IOException | InterruptedException e) {
            System.out.println("Произошла ошибка во время запроса\n" + "Проверьте адрес и повторите попытку.");
        }
        return "Неправильный токен";
    }

    public String load(String key) {
        if (token == null) {
            return "Не получен токен!";
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/load" + key + "?API_TOKEN=" + token))
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(httpResponse.statusCode());
            return httpResponse.body();
        } catch (IOException | InterruptedException e) {
            System.out.println("Произошла ошибка во время запроса\n" + "Проверьте адрес и повторите попытку.");
        }
        return "Ошибка получения запроса";
    }
}


