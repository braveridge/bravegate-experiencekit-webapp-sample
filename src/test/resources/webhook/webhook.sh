#!/bin/bash

SCRIPT_DIR=$(cd $(dirname $0); pwd)

if [ -z "$1" ]; then
   echo "usage: $SCRIPT_DIR/webhook.sh sensor_type\n"
   echo "sensor_type: e.g. lux, gps, openClose"
   exit
fi

curl -H 'Content-Type:application/json' -H 'X-Braveridge-Webhook-Token:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ1c2VyIiwibmFtZSI6ImJyYXZlLXRhcm8iLCJleHAiOjE2MTg3MzI2NzV9._SrYptaNJ4dGpQYD2SFRIDU1byRYVeLVqxS4OhiZrHQ'  -X POST http://localhost:8080/webhook/sensor -d @$SCRIPT_DIR/json/$1.json
