package com.example.persons.bootstrap;

import com.example.persons.domain.Color;
import com.example.persons.domain.Person;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

@Component
public class CsvPersonParser implements PersonParser {

  public List<Person> parse(InputStream inputStream) throws IOException {
    var reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    var csvParser = CSVFormat.DEFAULT.parse(reader);
    return parse(csvParser);
  }

  private List<Person> parse(CSVParser csvParser) {
    return csvParser.stream()
        .filter(record -> record.size() > 3) // ignore incomplete records
        .map(this::parse)
        .filter(Optional::isPresent) // include only records that were successfully parsed
        .map(Optional::get)
        .map(((personBuilder) -> personBuilder.id(csvParser.getRecordNumber()).build()))
        .collect(Collectors.toList());
  }

  private Optional<Person.PersonBuilder> parse(CSVRecord csvRecord) {
    try {
      var builder = parsePerson(csvRecord);
      return Optional.of(builder);
    } catch (Exception exception) {
      return Optional.empty();
    }
  }

  private Person.PersonBuilder parsePerson(CSVRecord record) {
    var colorId = Integer.parseInt(record.get(3).trim());
    var personColor = Color.values()[colorId - 1];

    return Person.builder()
        .lastName(record.get(0).trim())
        .name(record.get(1).trim())
        .address(record.get(2).trim())
        .color(personColor);
  }
}
