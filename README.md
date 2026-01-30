# 📘 Blog API – Kotlin + Spring Boot + MongoDB

API REST desenvolvida em **Kotlin com Spring Boot**, integrando autenticação JWT, refresh token, verificação de usuário via email e CRUD de notas de blog com comentários.  
O projeto utiliza **MongoDB**, Spring Security e envio de emails via SMTP.

---

## ✨ Funcionalidades

**Autenticação Segura**: Fluxo completo de login com JWT (JSON Web Tokens).

**Gestão de Sessão**: Implementação de Refresh Token para manter o utilizador ligado com segurança.

**Verificação de Conta**: Registo de novos utilizadores com envio automático de e-mail de confirmação via SMTP.

**Eficiência de Dados**: Uso de TTL (Time To Live) Indexes no MongoDB para limpeza automática de tokens expirados.

**Gestão de Conteúdo**: CRUD completo de notas (posts) e sistema de comentários integrados.

---

## 🏛 Arquitetura do Projeto

<img width="266" height="555" alt="image" src="https://github.com/user-attachments/assets/8d59374d-6a33-4563-bdf9-e27e65a0ba76" />

## ⚙️ Configuração Local
1. Pré-requisitos
JDK 17 ou superior

2. MongoDB instalado ou via Docker

3. Conta SMTP (Gmail, SendGrid ou Mailtrap) para testes de e-mail

## Variáveis de Ambiente
Configure o seu application.yml ou application.properties com os dados necessários:
<img width="839" height="256" alt="image" src="https://github.com/user-attachments/assets/bda3e28a-5c09-4362-85ea-1d7d326dda42" />

## 🏛 Arquitetura
O projeto segue o padrão de Camadas (Layered Architecture), garantindo que a lógica de negócio (Service) esteja isolada da camada de exposição (Controller) e da persistência de dados (Repository).


## 💡Melhorias que estão sendo planejadas para o futuro proximo:
- Swagger/OpenAPI: Adicionar a dependência springdoc-openapi para gerar documentação interativa.

- Docker Compose: Criar um ficheiro docker-compose.yml que já suba a API e o MongoDB juntos.

- Testes: Implementar testes unitários com JUnit 5 e MockK.

## 🛠 Tecnologias Utilizadas

- **Kotlin**
- **Spring Boot**
- Spring Web  
- Spring Security (JWT)  
- Spring Data MongoDB  
- JavaMailSender  
- MongoDB com TTL Index  
- Gradle Kotlin DS

