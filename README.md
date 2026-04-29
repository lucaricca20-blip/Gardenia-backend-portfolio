# Gardenia_Backend
Backend dell'applicazione **Gardenia**, un e-commerce per la vendita di piante, articoli da giardinaggio e arredo da giardino.

## Tecnologie utilizzate
- Java 25
- Spring Boot 4.0.3
- PostgreSQL
- JPA / Hibernate
- Spring Security
- Lombok
- SpringDoc OpenAPI (Swagger UI)
- JavaMailSender (Gmail SMTP)
- Maven
- Git

## Descrizione
Gardenia è un progetto sviluppato in team da 5 persone nell'ambito di un percorso formativo presso Betacom Group.
Il backend espone API RESTful consumate dal frontend Angular.

## Sicurezza
La configurazione di Spring Security è presente ma tutti gli endpoint sono aperti (`permitAll`) a scopo didattico.
In un contesto di produzione andrebbe configurata con autenticazione e ruoli per i singoli endpoint.

## Configurazione
Prima di avviare impostare le seguenti variabili d'ambiente:

- `DB_PASSWORD` — password del database PostgreSQL
- `MAIL_PASSWORD` — App Password Gmail per le email di conferma registrazione e reset password

**Windows (cmd):**
```cmd
set DB_PASSWORD=tua_password
set MAIL_PASSWORD=tua_app_password_gmail
```

**macOS / Linux:**
```bash
export DB_PASSWORD=tua_password
export MAIL_PASSWORD=tua_app_password_gmail
```

> **Nota Gmail:** genera un'App Password da [myaccount.google.com/apppasswords](https://myaccount.google.com/apppasswords) dopo aver abilitato la verifica in due passaggi. Non usare la password normale dell'account.

Creare il database prima di avviare:
```sql
CREATE DATABASE db_gardenia;
```

## Frontend
Disponibile qui: [Gardenia-frontend-portfolio](https://github.com/lucaricca20-blip/Gardenia-frontend-portfolio.git)