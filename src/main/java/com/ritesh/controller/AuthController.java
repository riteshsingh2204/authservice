package com.ritesh.controller;

import com.ritesh.constants.RestUrlConstants;
import com.ritesh.exception.AuthServiceException;
import com.ritesh.request.AuthRequest;
import com.ritesh.response.AuthResponse;
import com.ritesh.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestUrlConstants.AUTH_SERVICE)
public class AuthController {

  @Autowired private IAuthService authService;

  @GetMapping(RestUrlConstants.GENERATE_TOKEN)
  public ResponseEntity<Object> generateAuthToken(
      @RequestHeader(required = true, name = "userName") String userName,
      @RequestHeader(required = true, name = "password") String password,
      @RequestHeader(required = true, name = "configurableTime") Integer configurableTime) {
    AuthRequest request = new AuthRequest();
    request.setUserName(userName);
    request.setPassword(password);
    request.setConfigurableTime(configurableTime);
    AuthResponse response = new AuthResponse();
    String authToken = null;
    try {
      authToken = authService.generateAuthToke(request);
    } catch (AuthServiceException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    response.setAuthToken(authToken);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping(RestUrlConstants.REFRESH_TOKEN)
  public ResponseEntity<AuthResponse> refreshAuthToken(
      @RequestHeader(required = true, name = "authToken") String authToken) {
    AuthResponse response = new AuthResponse();
    String refreshToken = authService.refreshToken(authToken);
    response.setAuthToken(refreshToken);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/list")
  public ResponseEntity<Object> getFruitsList(
      @RequestHeader(required = true, name = "authToken") String authToken) {
    AuthResponse response = new AuthResponse();
    List<String> fruitsList = null;
    try {
      fruitsList = authService.getFruitsList(authToken);
    } catch (AuthServiceException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity<>(fruitsList, HttpStatus.OK);
  }
}
