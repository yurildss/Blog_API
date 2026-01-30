# 📘 Blog API – Kotlin + Spring Boot + MongoDB

API REST desenvolvida em **Kotlin com Spring Boot**, integrando autenticação JWT, refresh token, verificação de usuário via email e CRUD de notas de blog com comentários.  
O projeto utiliza **MongoDB**, Spring Security e envio de emails via SMTP.

---

## ✨ Funcionalidades

- ✔ Registro de usuário com **envio de email de verificação**  
- ✔ Login com geração de **access token + refresh token**  
- ✔ Rotas protegidas por JWT  
- ✔ CRUD de notas (Blog Notes)  
- ✔ Comentários associados às notas  
- ✔ Tokens de verificação e refresh token com expiração automática (TTL Index do MongoDB)  
- ✔ Arquitetura organizada seguindo boas práticas do Spring  

---

## 🏛 Arquitetura do Projeto

<img width="266" height="555" alt="image" src="https://github.com/user-attachments/assets/8d59374d-6a33-4563-bdf9-e27e65a0ba76" />

## 🛠 Tecnologias Utilizadas

- **Kotlin**
- **Spring Boot**
- Spring Web  
- Spring Security (JWT)  
- Spring Data MongoDB  
- JavaMailSender  
- MongoDB com TTL Index  
- Gradle Kotlin DS

