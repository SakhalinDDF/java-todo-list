package com.todo;

import com.todo.util.QueryStringBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Properties;
import java.util.Random;

import org.json.JSONObject;

public class ApplicationTest {
  public String protocol;
  public String hostname;

  public static void main(String[] args) throws Exception {
    new ApplicationTest().test();
  }

  public void test() throws Exception {
    InputStream inputStream = new FileInputStream(".env");
    Properties properties = new Properties();

    properties.load(inputStream);

    protocol = properties.getProperty("WEB_PROTOCOL");
    hostname = properties.getProperty("WEB_HOSTNAME");

    String login = newLogin();

    JSONObject registerResponse = new JSONObject(doPost("/api/user", "login=" + login));

    String authToken = registerResponse.getString("authToken");

    createPost(authToken, "Hello world");
    createPost(authToken, "Something nasty");
    createPost(authToken, "Ouu shii");
    createPost(authToken, "Hello world 2");
    createPost(authToken, "Something nasty 2");
    createPost(authToken, "Ouu shii 2");
    createPost(authToken, "Hello world 3");
    createPost(authToken, "Something nasty 3");
    createPost(authToken, "Ouu shii 3");
    createPost(authToken, "Hello world 4");
    createPost(authToken, "Something nasty 4");
    createPost(authToken, "Ouu shii 4");

    getTaskListing(authToken, 2, "id,asc");
    getTaskListing(authToken, 5, "createdAt,desc");
  }

  public void createPost(String authToken, String name) throws Exception {
    QueryStringBuilder queryStringBuilder = new QueryStringBuilder();

    queryStringBuilder.append("authToken", authToken);
    queryStringBuilder.append("name", name);

    doPost("/api/task", queryStringBuilder.toString());
  }

  public void getTaskListing(String authToken, int size, String sort) throws Exception {
    int page = 0;

    while (true) {
      QueryStringBuilder queryStringBuilder = new QueryStringBuilder();

      queryStringBuilder.append("authToken", authToken);
      queryStringBuilder.append("size", size);
      queryStringBuilder.append("page", page);

      if (sort != null) {
        queryStringBuilder.append("sort", sort);
      }

      String taskListingResponse = doGet("/api/task/listing?" + queryStringBuilder.toString());
      JSONObject taskListingResponseJson = new JSONObject(taskListingResponse);

      taskListingResponseJson.getJSONArray("content").forEach(t -> {
        JSONObject task = (JSONObject) t;

        int id = task.getInt("id");
        String name = task.getString("name");
        String createdAtRaw = task.getString("createdAt");
        LocalDateTime createdAt = OffsetDateTime.parse(createdAtRaw).toLocalDateTime();

        System.out.println(
            "Task \""
                + name
                + "\", id: "
                + id
                + ", created at: "
                + createdAt.toString()
        );

        QueryStringBuilder updateQueryStringBuilder = new QueryStringBuilder();

        updateQueryStringBuilder.append("authToken", authToken);
        updateQueryStringBuilder.append("id", id);
        updateQueryStringBuilder.append("name", name + " [Updated]");
        updateQueryStringBuilder.append("status", "completed");

        try {
          doPut("/api/task", updateQueryStringBuilder.toString());
        } catch (Exception e) {
          e.printStackTrace();
          System.exit(1);
        }
      });

      boolean last = taskListingResponseJson.getBoolean("last");

      if (last) {
        break;
      }

      ++page;
    }
  }

  public String doPost(String uri, String body) throws Exception {
    return makeRequest("POST", uri, body);
  }

  public String doGet(String uri) throws Exception {
    return makeRequest("GET", uri, null);
  }

  public String doPut(String uri, String body) throws Exception {
//    hostname = "cryptogold.sakhalin.in";
//    uri = "/test.php";

    return makeRequest("PUT", uri, body);
  }

  public String makeRequest(String method, String uri, String body) throws Exception {
    URL url = new URL(protocol, hostname, 80, uri);

    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    connection.setDoOutput(true);
    connection.setRequestMethod(method);
    connection.setRequestProperty("Accept", "application/json; charset=utf8");

    if (body != null) {
      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      connection.setRequestProperty("Content-Length", body.length() + "");

      writeRequestBody(connection, body);
    }

    int responseCode = connection.getResponseCode();

    System.out.println("# " + responseCode + " " + method + " " + uri);

    String inputLine;
    StringBuilder stringBuilder = new StringBuilder();

    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

      while ((inputLine = in.readLine()) != null) {
        stringBuilder.append(inputLine);
        stringBuilder.append("\n");
      }

      in.close();

      return stringBuilder.toString();
    } catch (Exception e) {
      BufferedReader err = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

      while ((inputLine = err.readLine()) != null) {
        stringBuilder.append(inputLine);
        stringBuilder.append("\n");
      }

      System.err.println(stringBuilder.toString());

      err.close();

      throw e;
    }
  }

  public String newLogin() {
    Random random = new Random();

    return "new-login-" + random.nextInt(10000);
  }

  public void writeRequestBody(HttpURLConnection connection, String body) {
    try {
      OutputStream os = connection.getOutputStream();
      os.write(body.getBytes());
      os.flush();
      os.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
