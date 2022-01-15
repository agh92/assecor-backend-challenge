package com.example.persons.converter;

import com.example.persons.dto.ColorDto;
import org.springframework.core.convert.converter.Converter;


public class StringToColorDtoConverter implements Converter<String, ColorDto> {
    @Override
    public ColorDto convert(String source) {
        return ColorDto.valueOf(source.toUpperCase());
    }
}
