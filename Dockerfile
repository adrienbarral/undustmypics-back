FROM ubuntu:16.04

MAINTAINER Adrien BARRAL

RUN \
  apt-get update && \
  apt-get install -y openjdk-8-jre && \
  apt-get install -y libopencv-core-dev libopencv-highgui-dev libopencv-photo-dev && \
  rm -rf /var/lib/apt/lists/*
