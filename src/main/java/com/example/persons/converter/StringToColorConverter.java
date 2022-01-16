package com.example.persons.converter;

import com.example.persons.model.Color;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class StringToColorConverter implements Converter<String, Color> {
    @Override
    public Color convert(String source) {
        try {
            return Color.valueOf(source.toUpperCase());
        } catch (Exception exception) {
            String reason = String.format("%s is not a valid color", source);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, reason);
        }
    }
}
