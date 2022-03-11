package com.example.persons.bootstrap;

import java.io.IOException;
import java.io.InputStream;

/**
 * Loads the file/resource containing the data to be preloaded into the repository when the
 * application starts
 */
public interface PersonFileLoader {
  InputStream loadFile() throws IOException;
}
