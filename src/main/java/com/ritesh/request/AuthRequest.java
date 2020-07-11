package com.ritesh.request;

import lombok.Data;

@Data
public class AuthRequest {
  private String userName;
  private String password;
  private Integer configurableTime;
}
