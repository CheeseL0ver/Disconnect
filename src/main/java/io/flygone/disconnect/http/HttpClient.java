package io.flygone.disconnect.http;

import io.flygone.disconnect.handler.ConfigHandler;
import okhttp3.*;

import java.io.IOException;

public class HttpClient {

    private final OkHttpClient httpClient = new OkHttpClient();
    private final String channelID = ConfigHandler.discordChannelId;
    private final String botSecret = ConfigHandler.botSecret;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void sendPost(String message) throws Exception {

        String json = String.format("{\"content\": \"%s\",\"tts\": false}", message);
        RequestBody body = RequestBody.create(JSON, json);

        if (channelID.equals("") || botSecret.equals(""))
            return;

        Request request = new Request.Builder()
                .url(String.format("https://discordapp.com/api/channels/%s/messages", channelID))
                .addHeader("Authorization", String.format("Bot %s", botSecret))
                .addHeader("User-Agent", "DisconnectBot (https://flygone.io, v1)")
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
//            System.out.println(response.body().string());
        }

    }
}
