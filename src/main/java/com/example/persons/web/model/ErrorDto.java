package com.example.persons.web.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorDto {
  private Long timestamp;
  private Integer status;
  private String error;
  private String reason;
  private String path;
}
