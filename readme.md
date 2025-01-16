# Projeto Gerenciamento de Clientes com Spring Boot

Bem-vindo ao repositório do **Projeto Gerenciamento de Clientes**! Este projeto tem como objetivo fornecer uma aplicação de gerenciamento de clientes, com funcionalidades de cadastro, edição, deleção e gerenciamento de números de telefone. A aplicação é construída com **Spring Boot 3.x** e utiliza **MySQL** como banco de dados.

---

## 📁 Estrutura do Projeto

```
cliente-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── seatecnologia/
│   │   │           └── cliente/
│   │   │               ├── config/
│   │   │               ├── controller/
│   │   │               ├── model/
│   │   │               │   └── dto/
│   │   │               ├── enums/
│   │   │               ├── filter/
│   │   │               ├── handler/
│   │   │               ├── service/
│   │   │               └── util/
│   ├── resources/
│   │   ├── application.properties
├── pom.xml
└── README.md
```

- **config** - Contém a configuração da aplicação (ex: datasource, segurança, etc.).
- **controller** - Contém as classes responsáveis pela comunicação com o frontend.
- **model** - Define as entidades, como o modelo `Cliente` e `Telefone`.
  - **dto** - Contém os objetos de transferência de dados (DTOs).
- **enums** - Enumerações utilizadas no sistema.
- **filter** - Filtros e classes auxiliares para manipulação de dados.
- **handle** - Classes de manipulação e tratamento de erros.
- **service** - Define a lógica de negócios e manipulação dos dados.
- **util** - Funções utilitárias e métodos auxiliares.
---

## ✅ Pré-requisitos

Antes de configurar o projeto, certifique-se de que você tem instalado:

- **Java JDK 23.0.1**
- **Maven 3.9.9**
- **MySQL** - Certifique-se de que o MySQL está configurado e rodando na sua máquina ou em um servidor acessível.

---

## 📥 Instalação

### 1. Clonar o Repositório

```bash
git clone https://git.seatecnologia.com.br/seatecnologia/gerenciamento-clientes.git
cd gerenciamento-clientes
```

### 2. Configuração do Banco de Dados (ou use a configuração que está no meu banco remoto)

Crie o banco de dados no MySQL com o nome `clientes_db`:

```sql
CREATE DATABASE clientes_db;
```

### 3. Configuração do Banco de Dados no `application.properties`

No arquivo `src/main/resources/application.properties`, configure as credenciais do banco de dados:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/clientes_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 🚀 Executando a Aplicação

### Ambiente de Desenvolvimento

#### Executando a Aplicação

No diretório raiz do projeto, execute o seguinte comando para rodar o projeto:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

---

## 📝 Funcionalidades

- **Cadastro de Cliente**: Permite cadastrar um novo cliente com nome, e-mail e telefone.
- **Edição de Cliente**: Permite editar os dados de um cliente já cadastrado.
- **Deleção de Cliente**: Permite remover um cliente da base de dados.
- **Adição e Remoção de Números de Telefone**: Gerencia os números de telefone associados a um cliente.

---

## 🛑 Parando a Aplicação

Para parar a aplicação em execução, use o seguinte comando no terminal:

```bash
Ctrl + C
```

---

## 📚 Detalhamento de Cada Etapa

1. **Configuração do Banco de Dados**

   - A aplicação utiliza MySQL como banco de dados, e a tabela de clientes é criada automaticamente.
   - As credenciais e a URL de conexão são definidas no arquivo `application.properties`.

2. **Execução da Aplicação**

   - Para rodar a aplicação localmente, basta utilizar o Maven com o comando `mvn spring-boot:run`.
   - O banco de dados precisa estar configurado corretamente para a aplicação funcionar.

---

## ⚠️ Problemas Comuns

- **Banco de Dados Não Conectando**: Verifique as credenciais no `application.properties` e se o MySQL está rodando.
- **Erro de Dependência**: Certifique-se de que todas as dependências estão corretamente definidas no `pom.xml`.
