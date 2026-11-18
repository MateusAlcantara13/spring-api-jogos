# API de Jogos

API REST para catálogo de uma loja de jogos, com CRUD completo, validação e busca com filtros.

Projeto de estudo do curso de **Análise e Desenvolvimento de Sistemas** (SENAI-SP Vila Leopoldina), construído do zero como exercício de API REST.

## Stack

- Java 21
- Spring Boot 4 (Spring Web + Spring Data JPA)
- Bean Validation
- H2 (banco em memória)
- Lombok
- Maven

## Como rodar

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`. O console do banco fica em `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:gamesdb`, usuário `sa`, sem senha).

## Endpoints

| Método | Rota | Resposta |
|---|---|---|
| GET | `/api/games` | 200 + lista |
| GET | `/api/games/{id}` | 200 + jogo · 404 se não existir |
| POST | `/api/games` | 201 + header `Location` · 400 se inválido |
| PUT | `/api/games/{id}` | 200 + jogo atualizado · 404 se não existir |
| DELETE | `/api/games/{id}` | 204 sem corpo · 404 se não existir |
| GET | `/api/games/busca?titulo=` | 200 + lista filtrada (ignora maiúsculas) |
| GET | `/api/games/genero/{genero}` | 200 + lista do gênero |

### Exemplo de cadastro

`POST /api/games`

```json
{
  "titulo": "The Legend of Zelda",
  "genero": "ACAO",
  "valorGame": 299.90,
  "estoque": 15
}
```

Gêneros aceitos: `ACAO`, `TERROR`, `RPG`.

## Estrutura

```
src/main/java/br/edu/games/
├── model/Game.java             (entidade JPA + validações)
├── model/GeneroGame.java       (enum)
├── repository/GameRepository.java
└── controller/GameController.java
```

## O que este projeto exercita

- `@RestController` e serialização automática para JSON
- `@RequestBody` convertendo JSON em objeto
- `@PathVariable` (identificar) × `@RequestParam` (filtrar)
- `ResponseEntity` para controlar status HTTP e headers
- Status codes: 200, 201, 204, 400, 404
- Bean Validation com `@Valid` respondendo 400 automaticamente
- Query methods derivados do nome (`findByTituloContainingIgnoreCase`)
- `BigDecimal` para valores monetários