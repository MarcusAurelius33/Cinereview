# 🎬 CineReview API - Documentação do Projeto

## 📖 1. Descrição do Projeto

A **CineReview** é uma API RESTful desenvolvida para a gestão de reviews de filmes e criação de listas personalizadas. O projeto foca em **qualidade de código**, **segurança robusta** e **performance**. A aplicação atua como seu próprio Servidor de Autorização (Authorization Server), gerenciando identidades, permissões e emissão de tokens JWT, além de oferecer documentação interativa e automatizada pronta para consumo.

---

## 🧠 2. Conceitos Abordados

* **Autenticação e Autorização Avançada:** Implementação nativa do protocolo OAuth2 com fluxos de *Authorization Code* (com suporte a PKCE para *Public Clients*) e *Client Credentials*.
* **Semântica RESTful:** Utilização criteriosa de verbos HTTP, mapeamento semântico de status codes e manipulação inteligente de sub-recursos (ex: rotas aninhadas para itens de uma lista).
* **Segurança de Dados:** Hash de senhas, proteção contra elevação de privilégios e blindagem de rotas.
* **Tratamento Global de Exceções:** Interceptação centralizada de erros de negócio e de validação, garantindo respostas padronizadas e limpas para o Front-end.
* **Auditoria de Entidades:** Rastreamento automático de datas de criação e modificação via JPA Auditing (`@CreatedDate`, `@LastModifiedDate`).
* **Pesquisas Dinâmicas:** Uso de *Specifications* do Spring Data JPA para consultas complexas e paginadas.

---

## 🏗️ 3. Arquitetura e Tecnologias

O sistema utiliza uma **Arquitetura em Camadas** para garantir a separação de responsabilidades, testabilidade e facilitar a manutenção estrutural.

| Tecnologia | Versão | Finalidade |
| :--- | :--- | :--- |
| **Java** | 21 | Linguagem base com suporte a Records e features modernas. |
| **Spring Boot** | 4.0.2 | Framework principal da aplicação. |
| **PostgreSQL** | 16.3 | Banco de dados relacional para persistência de dados. |
| **Spring Security & OAuth2** | - | Authorization Server nativo e Resource Server. |
| **Springdoc OpenAPI** | 3.0.1 | Documentação interativa (Swagger UI) integrada com fluxo PKCE. |
| **MapStruct** | 1.6.3 | Mapeamento performático e *type-safe* entre DTOs e Entidades. |
| **Hypersistence Utils** | 3.15.1 | Mapeamento avançado de tipos complexos (Arrays nativos no Postgres). |

---

## 🔐 4. Segurança (RBAC e OAuth2)

A API implementa um controle de acesso rigoroso utilizando **Role-Based Access Control (RBAC)** e um **Authorization Server próprio**.

* **Criptografia:** Senhas de usuários e *secrets* de clientes OAuth2 são armazenados com hash **BCrypt**.
* **Gestão de Clientes OAuth2:** O sistema suporta múltiplos clientes registrados, separando responsabilidades:
    * Clientes confidenciais (ex: Postman) autenticam-se via *Basic Auth* tradicional.
    * Clientes públicos (ex: Swagger UI) utilizam o fluxo **PKCE** de forma transparente, mitigando a exposição do *client-secret*.
* **Social Login:** Preparação estrutural para login via provedores externos (Google).
* **Papeis (Roles):**
    * **ADMIN:** Possui privilégios totais. Pode criar, atualizar e deletar filmes, além de registrar novos aplicativos clientes na plataforma.
    * **USER:** Pode visualizar filmes, criar e gerenciar suas próprias reviews, e organizar listas personalizadas (com validação estrita de "dono do recurso").

---

## 📦 5. Modelo de Dados (JPA)

As entidades são mapeadas de forma otimizada, utilizando chaves primárias do tipo **UUID** para maior segurança contra enumeração.

* **Usuario:** Armazena credenciais, e-mail e permissões (roles). As permissões são salvas utilizando um array nativo do PostgreSQL (`ListArrayType`).
* **Filme:** Entidade central contendo os metadados do catálogo (Título, Sinopse, Ano, Gênero).
* **Review:** Relacionamento `OneToOne` com Filmes e `ManyToOne` com Usuários, contendo notas decimais precisas (`BigDecimal`).
* **Lista:** Relacionamento `ManyToMany` com Filmes através de uma tabela de junção gerenciada (`lista_filme`).
* **Client:** Entidade de segurança que armazena as credenciais de aplicativos autorizados a consumir a API.

---

## 🛣️ 6. Endpoints Principais

A documentação interativa completa, incluindo schemas JSON de requisição e resposta, pode ser explorada via Swagger UI na rota `/swagger-ui.html`.

### 📽️ Filmes (`/filmes`)
* `POST /filmes`: Cadastro de novos filmes (Apenas ADMIN).
* `GET /filmes`: Pesquisa dinâmica e paginada por Título, Gênero e Ano.
* `GET /filmes/{id}`: Detalhes completos de um filme.
* `PUT /filmes/{id}`: Atualização de dados de um filme.
* `DELETE /filmes/{id}`: Exclusão de um filme (Apenas ADMIN).

### 📂 Listas (`/listas`)
* `POST /listas`: Criação de uma lista de filmes vinculada ao usuário logado.
* `DELETE /listas/{id}`: Exclusão de uma lista inteira.
* `POST /listas/{idLista}/filmes/{idFilme}`: Adiciona um filme específico a uma lista.
* `DELETE /listas/{idLista}/filmes/{idFilme}`: Remove um filme de uma lista.

### 📝 Reviews (`/reviews`)
* `POST /reviews`: Publicação de avaliações (impede múltiplas reviews do mesmo usuário para o mesmo filme).
* `GET /reviews`: Busca paginada de reviews.
* `PUT /reviews/{id}` / `DELETE /reviews/{id}`: Gestão e exclusão de reviews pelo próprio autor.

### 👤 Gestão e Identidade (`/usuarios` e `/clients`)
* `POST /usuarios`: Cadastro de novos usuários na plataforma.
* `POST /clients`: Registro de novos aplicativos clientes OAuth2 (Apenas ADMIN).

---

## 🛠️ 7. Tratamento de Erros

A API intercepta exceções e devolve objetos JSON padronizados, facilitando a vida do desenvolvedor Front-end.

* **403 Forbidden:** Acesso negado por falta de permissões ou credenciais inválidas.
* **404 Not Found:** Recurso solicitado não localizado no banco de dados.
* **409 Conflict:** Violação de integridade ou regra de negócio (ex: registro duplicado, login/e-mail em uso, filme já existente na lista).
* **422 Unprocessable Content:** Falhas nas validações de payload (`@Valid`) detalhando exatamente quais campos falharam e os respectivos motivos.
* **500 Internal Server Error:** Falhas de infraestrutura.

---

## 🚀 8. Como Executar (Ambiente Local)

### 📌 Pré-requisitos
Certifique-se de ter as seguintes ferramentas instaladas:
* **[Git](https://git-scm.com/):** Para o clone do projeto.
* **[Java 21](https://jdk.java.net/21/):** JDK base utilizada no desenvolvimento.
* **[Docker](https://www.docker.com/):** Necessário para provisionar o banco de dados.
* **Maven:** Para compilação e execução (opcional, pode-se usar o *wrapper* embutido).

### 📥 Passo 1: Clonar o Repositório
Abra o seu terminal e execute os comandos abaixo para baixar o código-fonte:
```bash
git clone [https://github.com/seu-usuario/cinereview.git](https://github.com/MarcusAurelius33/Cinereview.git)
cd cinereview
```

### 🐳 Passo 2: Infraestrutura (Docker)
Crie a rede e suba os containers do PostgreSQL e do pgAdmin
```bash
docker create network cinereview-network

docker run --name cinereviewdb -p 5432:5432 --network cinereview-network -d \
  -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=cinereview postgres:16.3

docker run --name pgadmin4 -p 15432:80 --network cinereview-network -d \
  -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin dpage/pgadmin4:8.9
```

### 🗄️ Passo 3: Banco de Dados (SQL)
Acesse o seu gerenciador de banco de dados e execute a criação das tabelas estritamente nesta ordem para respeitar as chaves estrangeiras:
1. usuario, filme e client.
2. review e lista.
3. lista_filme.

### 🔐 Passo 4: Variáveis de Ambiente
Configure as seguintes variáveis de ambiente no seu sistema ou as injete na sua IDE antes de executar o projeto:

1. DB_PASSWORD: Senha do PostgreSQL (ex: postgres).

2. GOOGLE_CLIENT_ID: Pode preencher com `test-id`

3. GOOGLE_CLIENT_SECRET: Pode preencher com `test-secret`

*Nota: Ao utilizar credenciais fictícias do Google, o fluxo de login via banco de dados (JWT/Basic Auth) funcionará perfeitamente, apenas o botão de "Social Login" ficará inoperante no ambiente de desenvolvimento.*

### 💻 Passo 5: Rodando a Aplicação
```bash
mvn spring-boot:run
```

### 🧪 Passo 6: Acesso e Documentação Interativa
1. Acesse http://localhost:8080/swagger-ui.html.
2. Certifique-se de cadastrar previamente o cliente swagger-client via banco de dados ou Postman, configurando a Redirect URI para http://localhost:8080/swagger-ui/oauth2-redirect.html.
3. Clique em Authorize na interface do Swagger, realize o login seguro, e a documentação assumirá seu Token JWT nativamente para que você possa testar todos os endpoints.

Desenvolvido por: Marcus Aurelius Costa de Paiva.



