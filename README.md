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
src/main/kotlin/com/yurildss/Blog_API/
│
├── controller/
│   ├── AuthController.kt
│   ├── BlogNotesController.kt
│   └── VerificationController.kt
│
├── Email/
│   └── EmailService.kt
│
├── model/
│   ├── BlogNote.kt
│   ├── Comments.kt
│   ├── User.kt
│   ├── RefreshToken.kt
│   └── VerificationToken.kt
│
├── repository/
│   ├── UserRepository.kt
│   ├── NoteRepository.kt
│   └── VerifyUserRepository.kt
│
└── security/
    ├── AuthService.kt
    ├── JwtService.kt
    ├── JwtAuthFilter.kt
    ├── HashEncoder.kt
    └── SecurityConfig.kt

## 🛠 Tecnologias Utilizadas

- **Kotlin**
- **Spring Boot**
- Spring Web  
- Spring Security (JWT)  
- Spring Data MongoDB  
- JavaMailSender  
- MongoDB com TTL Index  
- Gradle Kotlin DS

