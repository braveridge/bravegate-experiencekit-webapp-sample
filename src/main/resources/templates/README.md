# Web app that visualize sensor data via BraveGATE

This web app is a sample of visualization of sensor data via BraveGATE based on Spring Boot (with Kotlin).

- What is [BraveGATE](https://www.braveridge.com/product/archives/7)
    - [日本語](https://www.braveridge.com/product/archives/7)
    - [English](https://www.braveridge.com/en/product/archives/6)

## Requirements
- JDK 11

## How to run on localhost

### Edit your auth key id and secret, device id of BraveGATE
```bash
vi src/main/resources/config/application.yml
```

### run
```bash
./gradlew bootRun
```

access to http://localhost:8080/sensor