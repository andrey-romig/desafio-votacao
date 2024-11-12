## Sobre

Esse projeto tem como objetivo implementar um sistema para gerenciar os associados e pautas para votação.

## Funcionalidades

- Cadastrar Pautas e Sessões de votação
- Abrir e fechar sessões de votação
- Registrar votos
- Visualizar o resultado da votação nas sessões de votação.
- Cadastrar Associados

## Tecnologias

- Java 21
- PostgreSQL
- Spring
- Swagger
- FeignClient 

## Pré-Requisitos

- Docker instalado na máquina.
- Java 21 instalado na máquina.

## Como executar

1. Para subir o PostgreSQL como contâiner no Docker, é necessário acessar a pasta docker/database e executar o seguinte comando:
`````
docker-compose up -d
`````

Obs.: É possível utilizar o pgAdmin para realizar as consultas no PostgreSQL. Com os dados

`````
host: http://localhost:5050/
user: admin@admin.com
senha: admin

host do server: postgres
user do server: admin
senha do server: admin123
`````


2. Para executar a API é necessário acessar o diretório do projeto e rodar o seguinte commando:
  `````
   mvnw clean package -Dmaven.test.skip
   `````
 - Após isso, será criado uma pasta /target, acessar essa pasta e rodar o seguinte comando:
 `````
  java -jar pauta-0.0.1-SNAPSHOT.jar
 `````


## Documentação da API

# [Swagger](http://localhost:8080/swagger-ui/index.html)
`````
http://localhost:8080/swagger-ui/index.html
`````

Obs.: Na raiz do projeto está em anexo uma collection do Postman contendo os Endpoints da API.

