# project-portfolio-api

API de portfólio de projetos (multi-módulo) com Spring Boot, JPA, PostgreSQL, Spring Security (Basic Auth), Swagger/OpenAPI e OpenFeign para integração com o **Mock Members API**.

## Módulos
- **project-rest-api** – API principal (porta **8081**)
- **mock-members-api** – serviço mock para membros/ocupações/contratos (porta **8082**)

---

## Requisitos
- **Java 17**
- **Maven 3.9+**
- **PostgreSQL 14+** (ou Docker)
- Portas **8081** e **8082** livres

---

## Banco de Dados

### Docker (recomendado)
```bash
docker run --name postgres-portfolio -e POSTGRES_PASSWORD=postgres   -e POSTGRES_DB=portfolio_pm -p 5432:5432 -d postgres:17
```

> Caso use DB local, crie o banco `portfolio_pm` e ajuste usuário/senha no `application.yml`.

---

## Configuração (exemplo)

`project-rest-api/src/main/resources/application.yml`
```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/portfolio_pm
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: create-drop   # dev only
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

# Segurança (usuários/roles e whitelists)
app:
  security:
    whitelist:
      swagger: ["/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"]
      actuator: ["/actuator/health", "/actuator/info"]
      misc: ["/error"]
    users:
      - username: user
        password: user
        roles: [USER]
      - username: admin
        password: admin
        roles: [ADMIN]

# URL do serviço mock (OpenFeign)
clients:
  mock-members:
    url: http://localhost:8082
```

> Em produção, **não** use `ddl-auto: create-drop` e **não** deixe senhas em texto plano.

---

## Como subir

### Build completo (raiz do projeto)
```bash
mvn -U clean install -DskipTests
```

### Subir o serviço **mock** (porta 8082)
```bash
mvn spring-boot:run -pl mock-members-api
```

### Subir a **API principal** (porta 8081)
```bash
mvn spring-boot:run -pl project-rest-api
```

> Alternativa via IDE: executar `MockMembersApiApplication` e `ProjectRestApiApplication`.

---

## Autenticação & Swagger

A API usa **Basic Auth**.

- Usuários padrão:
  - **user / user** (ROLE_USER)
  - **admin / admin** (ROLE_ADMIN)
- Swagger (livre sem login):
  - UI: `http://localhost:8081/swagger-ui/index.html`
  - OpenAPI JSON: `http://localhost:8081/v3/api-docs`

### Teste rápido (cURL)
```bash
curl -u user:user http://localhost:8081/api/v1/members
```

Na UI do Swagger, clique em **Authorize**, selecione **basicAuth** e informe `user` / `user`.

---

## Integração (OpenFeign) — Endpoints do Mock
- `GET http://localhost:8082/api/members/{id}`
- `GET http://localhost:8082/api/occupations`
- `GET http://localhost:8082/api/employments-contract`

A URL é configurada em `clients.mock-members.url`.

---

## Troubleshooting
- **Swagger sem endpoints**: confirme `packagesToScan`/`paths` e se os controllers estão em `br.com.project.portfolio.rest.api`.
- **Erro de FK (UUID vs INTEGER)**: alinhe tipos de IDs entre entidades e FKs.
- **“Failed to load API definition”**: verifique se a API está rodando em **8081** e sem erros no log.
- **Compatibilidade Spring Boot/Cloud**: use versões compatíveis (ex.: Boot 3.4.x com Cloud 2024.x).

---

## Licença
Apache 2.0 (ajuste conforme necessário).
