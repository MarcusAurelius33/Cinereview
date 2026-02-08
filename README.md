# 🎬 CineReview API - Documentação do Projeto

A **CineReview** é uma API RESTful desenvolvida para a gestão de reviews de filmes e criação de listas personalizadas. O projeto foca em **qualidade de código**, **segurança robusta** e **performance** utilizando as versões mais recentes do ecossistema Java.

---

## 🏗️ 1. Arquitetura e Tecnologias

O sistema utiliza uma **Arquitetura em Camadas** para garantir a separação de responsabilidades e facilitar a manutenção.

| Tecnologia | Versão | Finalidade |
| :--- | :--- | :--- |
| **Java** | 21 | Linguagem base com suporte a Records e Virtual Threads |
| **Spring Boot** | 4.0.2 | Framework para agilidade no desenvolvimento |
| **PostgreSQL** | 16.3 | Banco de dados relacional para persistência |
| **MapStruct** | 1.6.3 | Mapeamento performático entre DTOs e Entidades |
| **Spring Security** | - | Controle de acesso e autenticação via Roles |
| **HikariCP** | - | Pool de conexões otimizado para o banco de dados |



---

## 🔐 2. Segurança (RBAC)

A API implementa o controle de acesso baseado em funções (**Role-Based Access Control**), utilizando criptografia **BCrypt** para proteção de credenciais.

* **ADMIN**: Possui privilégios para criar, atualizar e deletar filmes.
* **USER/USUARIO**: Pode visualizar filmes, criar suas próprias reviews e organizar listas personalizadas.
* **Auditoria**: Todas as ações de criação de recursos capturam automaticamente o usuário logado através do `SecurityService`.

---

## 📦 3. Modelo de Dados (JPA)

As entidades são mapeadas para o **PostgreSQL** com chaves primárias do tipo **UUID** e suporte a auditoria automática via `AuditingEntityListener`.

* **Filme**: Entidade central com metadados e validação de data de lançamento.
* **Review**: Relacionamento `OneToOne` com filmes e `ManyToOne` com usuários, incluindo notas em `BigDecimal`.
* **Lista**: Relacionamento `ManyToMany` com filmes através de uma tabela de junção (`lista_filme`).
* **Usuario**: Armazena logins, senhas criptografadas e permissões (Roles) em formato de array nativo.



---

## 🛣️ 4. Endpoints Principais

### 📽️ Filmes (`/filmes`)
* `POST /filmes`: Cadastro de novos filmes (Apenas ADMIN).
* `GET /filmes`: Pesquisa dinâmica paginada utilizando **Specifications** (Título, Gênero e Ano).
* `GET /filmes/{id}`: Detalhes completos de um filme específico.

### 📝 Reviews (`/reviews`)
* `POST /reviews`: Publicação de avaliações vinculadas ao usuário autenticado.
* `GET /reviews`: Busca paginada por nome de filme, nota ou ano.

### 📂 Listas (`/listas`)
* `POST /listas`: Criação de coleções de filmes a partir de uma lista de IDs.

---

## 🛠️ 5. Tratamento de Erros

A API utiliza o `GlobalExceptionHandler` para fornecer mensagens de erro padronizadas e seguras.

* **422 Unprocessable Content**: Retornado em falhas de validação de campos ou lógica de negócio (ex: filme duplicado).
* **409 Conflict**: Disparado pela `RegistroDuplicadoException`.
* **403 Forbidden**: Acesso negado por falta de permissões suficientes.



---

## 🚀 6. Como Executar

Para rodar o projeto localmente, siga os passos de infraestrutura e aplicação abaixo:

### 🐳 Passo 1: Infraestrutura (Docker)
Certifique-se de ter o Docker instalado. Crie a rede e suba os containers do banco de dados e do gerenciador (pgAdmin):

```bash
# Criar rede do projeto
docker create network cinereview-network

# Rodar container PostgreSQL 16.3
docker run --name cinereviewdb -p 5432:5432 --network cinereview-network -d \
  -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=cinereview postgres:16.3

# Rodar pgAdmin4 (Acesso em http://localhost:15432)
docker run --name pgadmin4 -p 15432:80 --network cinereview-network -d \
  -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin dpage/pgadmin4:8.9
```  

### 🗄️ Passo 2: Banco de Dados (SQL)
Conecte-se ao container cinereviewdb e execute os comandos para criar a estrutura das tabelas. É fundamental criar as tabelas na ordem correta devido às chaves estrangeiras:
🗄️ Passo 2: Banco de Dados (SQL)
Após subir os containers, é necessário criar a estrutura de tabelas no PostgreSQL. Devido às dependências de chaves estrangeiras (Foreign Keys), os scripts contidos no arquivo comandos-sql.txt devem ser executados obrigatoriamente nesta ordem:


1. Tabela usuario: Base para autenticação e proprietário de listas/reviews.

2. Tabela filme: Catálogo central de títulos.

3. Tabela review: Avaliações vinculadas a filmes e usuários.

4. Tabela lista: Cabeçalho das listas personalizadas.

5. Tabela lista_filme: Tabela de junção para o relacionamento N:N entre listas e filmes.

### 👤 Passo 3: Criação de Usuário e Permissões
A API possui segurança habilitada por padrão, exigindo autenticação para quase todos os endpoints.

Cadastro Inicial: Utilize o endpoint público POST /usuarios enviando um JSON com login e senha.

Atribuição de Roles: Por padrão, o sistema utiliza Basic Auth. Para acessar recursos de administrador (como cadastrar filmes), você deve acessar a tabela usuario no banco de dados e inserir as permissões manualmente no campo roles (exemplo: {'USER', 'ADMIN'}).

Criptografia: Não se preocupe com a senha no banco; o UsuarioService utiliza BCrypt para encriptar o dado antes da persistência.

### 💻 Passo 4: Execução da Aplicação
Com a infraestrutura pronta e o usuário devidamente configurado:

1. Configuração: Verifique se as credenciais do banco no arquivo src/main/resources/application.yaml coincidem com as do seu container Docker.

2. Compilação e Execução: Na raiz do projeto, execute o Maven:

```bash
mvn spring-boot:run
```
3. Acesso: A API estará disponível em http://localhost:8080. Você pode acessar a tela de login integrada em /login.

Desenvolvido por: Marcus Aurelius Costa de Paiva.