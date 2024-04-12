FROM ubuntu:22.04

RUN apt-get update && apt-get -y install openjdk-17-jdk maven

WORKDIR /WebApp
