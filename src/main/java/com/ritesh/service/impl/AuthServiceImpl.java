package com.ritesh.service.impl;

import com.ritesh.constants.CommonConstants;
import com.ritesh.exception.AuthServiceException;
import com.ritesh.model.Token;
import com.ritesh.request.AuthRequest;
import com.ritesh.service.IAuthService;
import com.ritesh.utils.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements IAuthService {
  private static final int default_expiration_time = 5;
  private static final long inMillis = 60 * 1000;
  private Map<String, String> tokenKeyMap = new HashMap();
  private Map<String, Token> keyTokenObjectMap = new HashMap();

  public String generateAuthToke(AuthRequest request) throws AuthServiceException {
    String finalKey = null;
    if (null == request.getUserName() || request.getUserName().isEmpty()) {
      throw new AuthServiceException("Invalid request.");
    }
    if (null == request.getPassword() || request.getPassword().isEmpty()) {
      throw new AuthServiceException("Invalid request.");
    }
    String key = request.getUserName() + request.getPassword();
    String encodedKey = CommonUtils.getBaseCode(request.getUserName(), request.getPassword());
    if (!tokenKeyMap.containsKey(encodedKey)) {
      Token token = new Token();
      token.setId(encodedKey);
      token.setPassword(request.getPassword());
      token.setUserName(request.getUserName());
      long expirationTime = default_expiration_time * inMillis;
      if (null != request.getConfigurableTime() && request.getConfigurableTime() > 0) {
        expirationTime = request.getConfigurableTime() * inMillis;
      }
      token.setExpirationTime(expirationTime);
      long startTime = System.currentTimeMillis();
      long endTime = startTime + expirationTime;
      token.setStartTime(startTime);
      token.setEndTime(endTime);
      if (!keyTokenObjectMap.containsKey(key)) {
        keyTokenObjectMap.put(key, token);
      }
      finalKey = encodedKey + System.currentTimeMillis();
      keyTokenObjectMap.get(key).setId(finalKey);
      tokenKeyMap.put(finalKey, key);
    }

    return finalKey;
  }

  @Override
  public String refreshToken(String authToken) {
    String finalToken = null;
    if (tokenKeyMap.containsKey(authToken)) {
      String key = tokenKeyMap.get(authToken);
      if (keyTokenObjectMap.containsKey(key)) {
        long expirationTime = keyTokenObjectMap.get(key).getExpirationTime();
        long startTime = System.currentTimeMillis();
        long endTime = startTime + expirationTime;
        keyTokenObjectMap.get(key).setStartTime(startTime);
        keyTokenObjectMap.get(key).setEndTime(endTime);
        finalToken = key + System.currentTimeMillis();
        keyTokenObjectMap.get(key).setId(finalToken);
      }
    }
    String encodedKey = tokenKeyMap.get(authToken);
    tokenKeyMap.remove(authToken);
    tokenKeyMap.put(finalToken, encodedKey);
    return finalToken;
  }

  @Override
  public List<String> getFruitsList(String authToken) throws AuthServiceException {
    validateToken(authToken);
    List<String> list = new ArrayList<>();
    list.add("Apple");
    list.add("Mango");
    return list;
  }

  private void validateToken(String authToken) throws AuthServiceException {
    if (tokenKeyMap.containsKey(authToken)) {
      String encodedKey = tokenKeyMap.get(authToken);
      if (keyTokenObjectMap.containsKey(encodedKey)) {
        Token token = keyTokenObjectMap.get(encodedKey);
        long endTime = token.getEndTime();
        long currentTime = System.currentTimeMillis();
        if (currentTime >= endTime) {
          throw new AuthServiceException("Token Expired");
        }
      } else {
        throw new AuthServiceException("Invalid Token");
      }
    } else {
      throw new AuthServiceException("Invalid Token");
    }
  }
}
