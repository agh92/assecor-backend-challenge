package com.example.persons.converter;

import com.example.persons.model.Color;
import org.springframework.core.convert.converter.Converter;


public class StringToColorConverter implements Converter<String, Color> {
    @Override
    public Color convert(String source) {
        return Color.valueOf(source.toUpperCase());
    }
}
