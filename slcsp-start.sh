#!/usr/bin/env bash

echo "*************************************building and installing the  project **********************"

mvn clean install

mvn exec:java -Dexec.mainClass=com.adhoc.demo.CalculateRate
