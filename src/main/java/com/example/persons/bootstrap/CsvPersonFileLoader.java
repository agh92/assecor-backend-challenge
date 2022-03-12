package com.example.persons.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class CsvPersonFileLoader implements PersonFileLoader {

  private final ResourceLoader resourceLoader;
  private final String filePath;

  public CsvPersonFileLoader(
      @Value("${persons.file}") String csvLocation, ResourceLoader resourceLoader) {
    this.filePath = csvLocation;
    this.resourceLoader = resourceLoader;
  }

  @Override
  public InputStream loadFile() throws IOException {
    Resource csvFileResource = resourceLoader.getResource(filePath);
    return csvFileResource.getInputStream();
  }
}
