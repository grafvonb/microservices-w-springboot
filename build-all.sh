#!/usr/bin/env zsh

alias d=docker

cd ./product-service
d build -t product-service:2.0.0 .
cd ..

cd ./review-service
d build -t review-service:2.0.0 .
cd ..

cd ./recommendation-service
d build -t recommendation-service:2.0.0 .
cd ..

cd ./product-composite-service
d build -t product-composite-service:2.0.0 .
cd ..
