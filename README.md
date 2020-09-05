# CVC Bank API

Aplicação para registro e controle de transações financeiras.

## Requisitos do projeto

* Criar transações
* Exibir transferências realizadas
* Calcular taxa com base na diferença entre a data de agêndamento e a data efetiva da transferência

### Requisitos adicionais aplicados

* Criação de conta - 
    - Somente usuários cadastrados podem realizar transferências
* Validação de conta e transferência
    - Transferências só podem ser realizadas por usuários que possuem saldo compatível, contemplando o valor da transferência e o adicional de taxa aplicada. Transferências não podem ser realizadas para uma mesma conta, ou para uma conta inválida.
* Controle de saldo das contas - 
    - Os saldos das contas devem ser atualizados após cada transferência com a finalidade de informar sempre o saldo atual do portador da conta.

### Próximas funcionalidades

* Exibição do extrato detalhado para do portador da conta
* Visualização do saldo, transações e taxas por parte do dono do banco.

## Documentação técnica

### Arquitetura

A Aplicação consiste em uma API com os serviços de transação e conta centralizados, com a finalidade de garantir uma estrutura de validação viável, mas estruturados lógicamente para uma possível separação em serviços destíntos garantindo a escalabilidade característica desse modelo.

#### Fluxo da Aplicação

Para acessar os recursos de conta e transferências é necessário realizar o processo de autenticação descrito no tópico API Docs.

![alt text](https://github.com/lucasmancan/cvc-bank-api/blob/master/cvc-api.png?raw=true)


### Tecnologias Aplicadas

* Java 11
* Spring Boot v2.3.3
* Mockito 
* JUnit 5 
* Docker
* Swagger

#### Banco de dados
* H2 

### Justificativa

#### Java 11

Ultima versão LTS do Java lançada, garantindo segurança e performance para a aplicação, inferencia de tipos e outros recursos característicos dessa versão como melhor suporte a containers.

#### Spring Boot

Conjunto de ferramentas para contrução de aplicações web extremamente adotados do merdado, com uma variedade de ferramentas de teste, cache e comunicação. Facilitando a manutenção da plataforma garantindo novas funcionalidades de forma simples através da utilização do ecossistema Spring. 

#### API Docs

A documentação oficial em formato OpenAPI 3.0 pode ser consultada através da implementação do Swagger, disponível em: https://cvc-bank-api.herokuapp.com/api-docs.

## Instruções para Devs :)

```bash

#Download do projeto
git clone https://github.com/lucasmancan/cvc-bank-api.git

cd /cvc-bank-api

#Rodando os testes
./mvnw test

# Gerando o arquivo executável 
./mvnw package

# Iniciando a aplicação
java -jar target/cvc-bank-api-0.0.1.jar

```

### Ou Docker

```bash

#Download do projeto
git clone https://github.com/lucasmancan/cvc-bank-api.git

cd /cvc-bank-api

# Criando a imagem
docker build -t lucasmancan/cvc-bank-api .

#Iniciando o container
docker run -d -p 8080:8080 lucasmancan/cvc-bank-api:latest 

```

## Licença
[MIT](https://choosealicense.com/licenses/mit/)

## Autor
[Lucas Frederico Mançan](https://www.linkedin.com/in/lucasmancan/)
