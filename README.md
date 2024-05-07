# gestao_produtos
API de gestão de produtos.

# Especificações
Linguagem de programação: Java
Banco de Dados: MySQL

pre requisitos:
* openjdk version "17.0.2"
* docker

Como subir o projeto:
* `docker compose up -d` subir container mysql
* `./gradlew flywayMigrate` criar tabela

Links
* http://localhost:8080/swagger-ui/index.html


Observações: 
* No diretório postman_collections possui o arquivo para exportar no postman
