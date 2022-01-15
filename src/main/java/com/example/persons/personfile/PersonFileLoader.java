package com.example.persons.personfile;

import java.io.IOException;
import java.io.InputStream;

public interface PersonFileLoader {
    InputStream loadFile() throws IOException;
}
