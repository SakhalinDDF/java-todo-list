package com.todo.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class QueryStringBuilder {
  private String result = "";
  private int count = 0;

  public QueryStringBuilder append(String key, String value) {
    try {
      if (count > 0) {
        result += "&";
      }

      result += URLEncoder.encode(key, StandardCharsets.UTF_8);
      result += "=";
      result += URLEncoder.encode(value, StandardCharsets.UTF_8);

      ++count;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return this;
  }

  public QueryStringBuilder append(String key, int value) {
    return append(key, value + "");
  }

  public QueryStringBuilder append(String key, float value) {
    return append(key, value + "");
  }

  public QueryStringBuilder append(String key, double value) {
    return append(key, value + "");
  }

  public QueryStringBuilder append(String key, char value) {
    return append(key, value + "");
  }

  public QueryStringBuilder append(String key, boolean value) {
    return append(key, value ? "1" : "0");
  }

  public String toString() {
    return result;
  }
}