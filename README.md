# Desafio

<p align="center">
  <img src="https://img.shields.io/badge/Java-000?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/PostgreSQL-black?style=for-the-badge&logo=postgresql&logoColor=blue"/>
  <img src="https://img.shields.io/badge/Python-black?style=for-the-badge&logo=python&logoColor=blue"/>
  <img src="https://img.shields.io/badge/Flask-000000?style=for-the-badge&logo=flask&logoColor=white"/>
    <img src="https://img.shields.io/badge/Docker-black?style=for-the-badge&logo=docker&logoColor=white"/>

</p>


## ⚙️ Como configurar para teste da aplicação
- Clone este repositório
### Web Scrapping + Transformação dados
1. Leia o README: [WebScrappingConverter/README.MD](https://github.com/apicela/just4fun/blob/main/WebScrappingConverter/README.MD)

### Teste banco de dados + frontend + backend - Utilizando Docker (recomendado) 
1. Execute o seguinte comando no terminal: ```docker-compose up --build```
2. Acesse o frontend pelo navegador: http://localhost:8080/
3. Faça requisições ao servidor Python em http://localhost:8000

# Documentação da API
## Postman collection:
- [/teste.postman_collection.json](https://github.com/apicela/just4fun/blob/main/teste.postman_collection.json)
## Base URL
```
http://localhost:8000
```

## Rotas

### 1. Operadoras

#### **Listar todas as operadoras**
**Endpoint:** `GET /operadoras`

**Descrição:** Retorna uma lista com até 10 operadoras cadastradas no banco de dados.

**Resposta:**
```json
[
    {
        "registro_ans": "123456",
        "razao_social": "Operadora X",
        "nome_fantasia": "Saúde X",
        ...
    },
    ...
]
```

---

#### **Listar as operadoras com maiores despesas no último ano**
**Endpoint:** `GET /operadoras/despesas/ano`

**Descrição:** Retorna as 10 operadoras com os maiores gastos relacionados a eventos/sinistros conhecidos no último ano.

**Resposta:**
```json
[
    {
        "razao_social": "Operadora A",
        "nome_fantasia": "Plano A",
        "total_despesa": 500000.00
    },
    ...
]
```

---

#### **Listar as operadoras com maiores despesas no último trimestre**
**Endpoint:** `GET /operadoras/despesas/trimestre`

**Descrição:** Retorna as 10 operadoras com os maiores gastos relacionados a eventos/sinistros conhecidos no último trimestre.

**Resposta:**
```json
[
    {
        "razao_social": "Operadora B",
        "nome_fantasia": "Plano B",
        "total_despesa": 200000.00
    },
    ...
]
```

---

#### **Buscar operadoras por nome ou razão social**
**Endpoint:** `GET /operadoras/busca`

**Parâmetros:**
- `termo` (query param) - Texto de busca (razao_social ou nome_fantasia)

**Exemplo de requisição:**
```
GET /operadoras/busca?termo=Saude
```

**Resposta:**
```json
[
    {
        "registro_ans": "789012",
        "razao_social": "Operadora Saúde",
        "nome_fantasia": "Saúde Total",
        ...
    },
    ...
]
```

---

### 2. Demonstrações Contábeis

#### **Listar todas as demonstrações contábeis filtradas**
**Endpoint:** `GET /demonstracoes`

**Descrição:** Retorna até 200 registros da tabela de demonstrações contábeis que possuem a descrição relacionada a eventos/sinistros conhecidos.

**Resposta:**
```json
[
    {
        "id": 1,
        "descricao": "EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR",
        "vl_saldo_inicial": 100000,
        "vl_saldo_final": 150000,
        "data": "2024-04-01"
    },
    ...
]
```

---

### 3. Health Check

#### **Verificar status do servidor**
**Endpoint:** `GET /health`

**Descrição:** Retorna um status simples indicando que o servidor está online.

**Resposta:**
```json
"server is online :)"
```

