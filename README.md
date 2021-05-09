# BraveGATE experience kit sample web application

This is a sample web application that use BraveGATE and experience kit.

# What is BraveGATE?
"BraveGATE" is an IoT platform that facilitates control of IoT devices via a network and overwhelmingly accelerates the development of service application.
![bravegate](https://user-images.githubusercontent.com/81787022/118094300-87bdcb80-b409-11eb-9cf5-d03b0003a22c.png)
- [日本語](https://www.braveridge.com/product/archives/7)
- [English](https://www.braveridge.com/en/product/archives/6)

## BraveGATE Experience Kit
"BraveGATE Experience Kit" is a value kit that allows you to experience the powerful capabilities of BraveGATE platform, and to also use it for technical verification in the study and development of IoT services with BraveGATE.
![experiencekit](https://user-images.githubusercontent.com/81787022/118096572-7cb86a80-b40c-11eb-9683-3cb975cdfd8b.png)
- [日本語](https://www.braveridge.com/product/archives/4)
- [English](https://www.braveridge.com/en/product/archives/3)

### CONTENTS
* LTE-BLE router "BraveROUTE": 1 unit
* Sensor Device "BraveTETRA" (temperature, humidity, illuminance, acceleration sensor installed, all with waterproof enclosure): 4 units
* BraveGATE usage fee (including communication fee): 3 months

# Application Overview
This sample web application visualizes the data measured by TETRA.
![visualize](https://user-images.githubusercontent.com/81787022/118098722-344e7c00-b40f-11eb-8b17-2c1ea22fcbfc.png)

# Requirement
* docker desktop
* jdk14

# preparations

## clone

```bash
git clone https://github.com/braveridge/bravegate-experiencekit-webapp-sample.git
````

## modify the configuration file
Please set the "authkey-id" and "authkey-secret" according to your account.

./src/main/resources/config/application-common.yml

```yml
bravegate:
  api-url: https://api.braveridge.io/
  authkey-id: xxxxxxxxxxxxxxxxxxxx
  authkey-secret: xxxxxxxxxxxxxxxxxxxxx

spring:
  jackson:
    time-zone: Asia/Tokyo
```

## build
```bash
cd bravegate-experiencekit-webapp-sample
./gradlew build copyfile
```

# run
```bash
docker-compose build
docker-compose up -d
```
If application work on local host, access to http://localhost:8080

# stop
```bash
docker-compose down
```

# Usage
## register router
Register the router of BraveGATE Experience Kit.

Please enter router ID, name and registration code.
![register router](https://user-images.githubusercontent.com/81787022/118101715-da4fb580-b412-11eb-8357-aa2562b0eef4.png)

## register sensor devices
Register the sensor devices of BraveGATE Experience Kit.

Please enter sensor device ID, name and registration code.
![register device](https://user-images.githubusercontent.com/81787022/118136167-c752da80-b43e-11eb-94cb-04553eb95fc9.png)

## register application
Register the webhook URL to receive the sensor data from BraveGATE.

The webhook URL has to be a public endpoint.
![register application](https://user-images.githubusercontent.com/81787022/118143966-dccc0280-b446-11eb-83fb-75ce5ccf7a4c.png)

## Confirmation of sensor data receiving
Select the Sensor Log menu to display the received sensor data.
![sensor log](https://user-images.githubusercontent.com/81787022/118248300-1d775a80-b4df-11eb-83c6-f2d18f710ba7.png)