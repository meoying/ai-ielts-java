DOCKER_COMPOSE_FILE=.script/docker-compose.yaml

.PHONY: compose_up
compose_up:
	@docker compose -f ${DOCKER_COMPOSE_FILE} up -d
.PHONY: compose_down
compose_down:
	@docker compose -f ${DOCKER_COMPOSE_FILE} down
.PHONY: install
install:
	@docker compose up -d
	@mvn install

.PHONY: fmt
fmt:
	@mvn formatter:format