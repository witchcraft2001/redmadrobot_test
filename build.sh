#!/bin/sh

./mvnw package && docker build -t bootcamp-mikh .