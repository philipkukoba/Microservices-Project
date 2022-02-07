# Microservices project

Extended microservices group project for an (imaginary) medicine shop. 

- Large microservices ( `./Verzendingsdienst` (my work), `./bestellingen`, `./orderservice`, `./medicijnen`) written in Java Spring Boot
- Small microservices written in NodeJS 
- Gateway written in Java Spring Boot
- Small front-end for testing (`./nginx`)
- Containerised with Docker (every microservice has a Dockerfile)
- Deployed on a Kubernetes cluster (every microservice has a `.yaml` in `./kubernetes`)
- Microservices communicate with the help of Kafka
