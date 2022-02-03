package hu.petrik.filmdb.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Response {
    int responseCode;
    String content;

    public Response(int responseCode, String content) {
        this.responseCode = responseCode;
        this.content = content;
    }
    public Response(int responseCode, InputStream is) throws IOException {
        this.responseCode = responseCode;
        StringBuilder boby = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = br.readLine();
        while (line != null){
            boby.append(line);
            line = br.readLine();
        }
        br.close();
        is.close();
        this.content = boby.toString();
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getContent() {
        return content;
    }
}
