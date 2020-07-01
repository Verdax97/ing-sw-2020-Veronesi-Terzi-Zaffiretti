# Santorini - ing-sw-2020-07

Santorini is a accessible strategy game with simple and immediate rules, but a deep and entertaing gameplay.
Each turn consist in two steps, MOVE and BUILD, if none of your workers can do both, you have lost!

Project work for "085923 - PROVA FINALE (INGEGNERIA DEL SOFTWARE)" - Politecnico di Milano.

# Features
- Complete Rules
- CLI
- GUI
- Socket
- AF 1: Advanced Gods (Charon, Chrono, Hestia, Triton, Zeus)
- AF 2: Persistence

# Server
### Cmd line
To start the server use the following command:
```java -jar GC07-1.0-SNAPSHOT-jar-with-dependencies.JAR -server```

### Installer
Double click Santorini-server.

# Client
## CLI
### Cmd line
To start the cli use the following command:
```java -jar GC07-1.0-SNAPSHOT-jar-with-dependencies.JAR -cli```

### Installer
Double click Santorini-CLI.

### GUI
### Cmd line
To start the gui use the following command:
```java -jar GC07-1.0-SNAPSHOT-jar-with-dependencies.JAR```

### Installer
Double click Santorini-GUI.

# Coverage
| Package |Tested Class | Coverage(line) | Coverage (methods)|
|:-----------------------|:------------------|:----------------|:--------------------:|
| Controller | Controller | 76/82 (92%) | 100%
| Model | Match | 295/344 (85%) | 100%
| Model | GameSaver | 101/106 (95%) | 100%
| Model | Global Package | 990/1044 (94%) | 100%

# Miscellanea (other usefull info)
TODO


