package com.example.persons.converter;

import com.example.persons.model.Color;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StringToColorConverter implements Converter<String, Color> {

  @Override
  public Color convert(String coloString) {
    try {
      return Color.valueOf(coloString.toUpperCase());
    } catch (Exception exception) {
      var reason = String.format("%s is not a valid color", coloString);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, reason);
    }
  }
}
