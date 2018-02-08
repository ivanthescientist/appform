all: build deploy
.PHONY: all

build:
	mvn clean
	mvn install
.PHONY: build

deploy:
	sudo docker-compose up -d --build
.PHONY: deploy