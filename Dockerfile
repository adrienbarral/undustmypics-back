FROM ubuntu:16.04

MAINTAINER Adrien BARRAL

RUN \
  apt-get update && \
  apt-get install -y openjdk-8-jre && \
  apt-get install -y gcc-5 && \
  apt-get install -y libopencv-core-dev libopencv-highgui-dev libopencv-photo-dev libopencv-dev && \
  apt-get install -y cmake cmake-curses-gui && \
  rm -rf /var/lib/apt/lists/*

RUN apt-get update && \
    apt-get install -y libboost-all-dev && \
    rm -rf /var/lib/apt/lists/*

COPY undustpics-cv /tmp
RUN mkdir /tmp-build
RUN cd /tmp-build && \
    cmake ../tmp && \
    make && \
    cp ./undustpics-cv /


COPY build/libs/undustmypics-back-0.0.1-SNAPSHOT.jar /
