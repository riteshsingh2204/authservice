package com.ritesh.model;

import lombok.Data;

@Data
public class Token {
  private String id;
  private String userName;
  private String password;
  private long expirationTime;
  private long startTime;
  private long endTime;
}
