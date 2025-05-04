# 🏥 - Smart Health System (FAC)

Este é um projeto acadêmico desenvolvido como MVP (Minimum Viable Product) para um sistema de gerenciamento hospitalar. Ele oferece funcionalidades essenciais para o controle de usuários, médicos, pacientes e agendamentos, servindo como base para evoluções futuras do sistema.

### 📌 Visão Geral
O objetivo deste projeto é fornecer uma API RESTful robusta e segura, utilizando autenticação JWT, documentação via Swagger e cobertura total de testes. A arquitetura é baseada em camadas bem definidas, promovendo organização e manutenibilidade do código.

### 🚀 Tecnologias Utilizadas
- Java
- Spring Boot
- Spring Security (com JWT)
- JPA / Hibernate
- H2 Database (ou equivalente)
- Swagger para documentação
- JUnit e Mockito para tes

### 📁 Estrutura do Projeto

- 📦 A arquitetura segue o padrão em camadas.

### 🔐 Autenticação

O sistema utiliza **JWT (JSON Web Token)** para autenticação. As rotas protegidas exigem um token válido, que é obtido ao autenticar o usuário via o endpoint `/auth`.

- **Entidade User**: Responsável por armazenar as credenciais de login e controlar o acesso ao sistema.
- Todas as rotas, com exceção das que começam com `/auth`, requerem autenticação JWT.

### 🧠 Entidades

__👤 User__
 - Responsável pelo controle de acesso e autenticação.
 - Utilizada para login e geração do token JWT.

__🧑‍⚕️ Doctor__
 - Representa um médico do sistema.
 - Possui relacionamento `ManyToOne` com os agendamentos (`Appointment`).

__🧑 Patient__
 - Representa um paciente do hospital.
 - Possui relacionamento `ManyToOne` com os agendamentos (`Appointment`).

__📅 Appointment__
 - Representa um agendamento médico.
 - Relacionamentos:
    - `ManyToOne` com `Patient`
    - `ManyToOne` com `Doctor`

### 🧪 Testes

 - ✅ **Cobertura de testes: 100% de cobertura nas regras de negócio.**
 - Todos os módulos da aplicação são cobertos por testes unitários e/ou de integração.
 - Ferramentas utilizadas:
    - **JUnit**
    - **Mockito**

### 📄 Documentação da API

A documentação está disponível via Swagger:

🔗 [Acessar Swagger](http://localhost:8080/api/v1/swagger-ui/index.html#/)

> Obs: A URL acima é acessível somente enquanto o projeto estiver em execução localmente.

### 🎓 Projeto Acadêmico
Este projeto foi desenvolvido como parte do curso de Análise e Desenvolvimento de Sistemas no Centro Universitário Internacional - UNINTER, com o objetivo de aplicar na prática os conceitos de:

 - Arquitetura de Software

 - Boas práticas com testes automatizados

 - Segurança com autenticação JWT

 - Documentação de APIs com Swagger