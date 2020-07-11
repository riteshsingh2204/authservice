package com.ritesh.utils;

import com.ritesh.constants.CommonConstants;
import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

public class CommonUtils {

  public static String getBaseCode(String userName, String password) {
    String auth = userName + CommonConstants.DELIMETER + password;
    byte[] encodedBytes = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
    return new String(encodedBytes);
  }

  public static String getKey(String encodedString) {
    byte[] decodedBytes = Base64.encodeBase64(encodedString.getBytes(StandardCharsets.US_ASCII));
    return new String(decodedBytes);
  }
}
