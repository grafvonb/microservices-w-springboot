#!/usr/bin/env zsh

spring init \
--boot-version=2.5.2 \
--java-version=1.8 \
--packaging=jar \
--name=product-service \
--package-name=com.boczek.microservices.core.product \
--groupId=com.boczek.microservices.core.product \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
product-service

spring init \
--boot-version=2.5.2 \
--java-version=1.8 \
--packaging=jar \
--name=review-service \
--package-name=com.boczek.microservices.core.review \
--groupId=com.boczek.microservices.core.review \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
review-service

spring init \
--boot-version=2.5.2 \
--java-version=1.8 \
--packaging=jar \
--name=recommendation-service \
--package-name=com.boczek.microservices.core.recommendation \
--groupId=com.boczek.microservices.core.recommendation \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
recommendation-service

spring init \
--boot-version=2.5.2 \
--java-version=1.8 \
--packaging=jar \
--name=product-composite-service \
--package-name=com.boczek.microservices.composite.product \
--groupId=com.boczek.microservices.composite.product \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
product-composite-service
