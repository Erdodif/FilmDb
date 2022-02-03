package hu.petrik.filmdb.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHandler {
    private RequestHandler(){}

    /**
     * @param urlString A kérés url hivatkozása
     * @return Visszatér egy alapértelmezés szerintei GET kapcsolattal a megadott url címre
     * @throws IOException Hibás kapcsolat esetén
     */
    private static HttpURLConnection setupConnection(String urlString) throws IOException {
        return  setupConnection(urlString,"GET");
    }
    /**
     * @param urlString A kérés url hivatkozása
     * @param method A kérés metódusa, GET,HEAD,PUT,PATCH,POST,DELETE
     * @return Visszatér a megadott metódusú kapcsolattal a megadott url címre
     * @throws IOException Hibás kapcsolat esetén
     */
    private static HttpURLConnection setupConnection(String urlString, String method) throws IOException {
        URL urlObj = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
        conn.setRequestProperty("Accept","application/json");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(9800);
        conn.setRequestMethod(method);
        return conn;
    }

    /**
     * @param conn A módosítandó Http kapcsolat
     * @param data JSON Standard-nek megfelelő String
     * @throws IOException Nem létező kapcsolat esetében
     */
    private static void addRequestBody(HttpURLConnection conn, String data) throws  IOException {
        conn.setRequestProperty("Content-Type","application/json");
        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        bw.write(data);
        bw.flush();
        bw.close();
        os.close();
    }

    /**
     * @param conn A Http kapcsolat
     * @return Http válasz objektum
     * @throws IOException Nem létező kapcsolat esetén
     */
    private static Response createResponse(HttpURLConnection conn) throws IOException {
        InputStream is;
        int code = conn.getResponseCode();
        if(code < 400){
            is = conn.getInputStream();
        }
        else{
            is = conn.getErrorStream();
        }
        Response response = new Response(code, is);
        return response;
    }

    public static Response get(String urlString) throws IOException {
        return createResponse(setupConnection(urlString));
    }
    public static Response head(String urlString) throws IOException {
        return createResponse(setupConnection(urlString,"HEAD"));
    }
    public static Response delete(String urlString) throws IOException {
        return createResponse(setupConnection(urlString,"DELETE"));
    }
    public static Response post(String urlString, String data) throws IOException {
        HttpURLConnection conn = setupConnection(urlString,"POST");
        addRequestBody(conn,data);
        return createResponse(conn);
    }
    public static Response put(String urlString, String data) throws IOException {
        HttpURLConnection conn = setupConnection(urlString,"PUT");
        addRequestBody(conn,data);
        return createResponse(conn);
    }
    public static Response patch(String urlString, String data) throws IOException {
        HttpURLConnection conn = setupConnection(urlString,"PATCH");
        addRequestBody(conn,data);
        return createResponse(conn);
    }
}
