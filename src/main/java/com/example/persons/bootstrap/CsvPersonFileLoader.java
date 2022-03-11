package com.example.persons.bootstrap;

import com.example.persons.configuration.PropertiesConfiguration;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class CsvPersonFileLoader implements PersonFileLoader {

  private final ResourceLoader resourceLoader;
  private final String filePath;

  public CsvPersonFileLoader(PropertiesConfiguration configuration, ResourceLoader resourceLoader) {
    this.filePath = configuration.getCsvLocation();
    this.resourceLoader = resourceLoader;
  }

  @Override
  public InputStream loadFile() throws IOException {
    Resource csvFileResource = resourceLoader.getResource(filePath);
    return csvFileResource.getInputStream();
  }
}
