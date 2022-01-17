# Assecor Backend Challenge

Implementation
of [Assecor Assessment Backend](https://github.com/assecor-GmbH/assecor-assessment-backend#readme)

# Environment

The project was developed and tested in the following environment:

```
> java --verison
openjdk 16.0.1 2021-04-20
OpenJDK Runtime Environment (build 16.0.1+9-24)
OpenJDK 64-Bit Server VM (build 16.0.1+9-24, mixed mode, sharing)
```

# Test

`./gradlew test`

# Build

`./gradlew bootJar`

# Run

`java -jar build/libs/persons-1.0.0.jar`

## Override csv file location

To override the file path the provided configuration should contain the key `persons.file`.

`persons.file` should be a path to the file to load and use the format required by spring
meaning `file:path/to/file`