# Backend Casa Bahia

Este projeto implementa um backend para operações relacionadas a vendedores e filiais da Casa Bahia.

## Funcionalidades

- **Vendedores:**
    - Criar, atualizar, obter e deletar vendedores.
    - Validações automáticas para dados de vendedores.

- **Filiais:**
    - Obter todas as filiais cadastradas.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Hibernate
- Swagger (OpenAPI) para documentação de API

## Estrutura do Projeto

- `com.backendcasabahia.controller`: Controladores REST para vendedores e filiais.
- `com.backendcasabahia.model`: Modelos de dados como VendedorEntity e FilialEntity.
- `com.backendcasabahia.repository`: Repositórios JPA para acesso a dados.
- `com.backendcasabahia.service`: Serviços para lógica de negócio.
- `com.backendcasabahia.service.impl`: Implementações dos serviços.

## Pré-requisitos

- JDK 17 ou superior
- Maven 3.x

## Documentação da API

- Acesse a documentação da API Swagger em http://localhost:8080/swagger-ui.html.