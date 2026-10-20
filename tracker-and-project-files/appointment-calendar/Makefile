.PHONY: test run infra up build
infra:
	docker compose up -d postgres keycloak mailpit
build:
	mvn -B -U -DskipTests package
run:
	mvn spring-boot:run -Dspring-boot.run.profiles=local
test:
	mvn -B verify
up: build
	docker build -t appointment-calendar:local .
