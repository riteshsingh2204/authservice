package com.ritesh.service;

import com.ritesh.exception.AuthServiceException;
import com.ritesh.request.AuthRequest;

import java.util.List;

public interface IAuthService {
  String generateAuthToke(AuthRequest request) throws AuthServiceException;

  String refreshToken(String authToken);

  List<String> getFruitsList(String authToken) throws AuthServiceException;
}
