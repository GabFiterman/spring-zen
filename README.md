# spring-zen

## Descrição

Uma aplicação Spring Boot que gerencia profissionais e contatos.

### Funcionalidades

-   Profissionais:
    -   Cadastro, atualização e exclusão lógica de profissionais.
-   Contatos:
    -   Cadastro, busca, atualização e exclusão lógica de contatos associados a profissionais.

### Tecnologias Utilizadas
[![Java 17](https://img.shields.io/badge/Java-17-blue.svg?style=flat&logo=java&logoColor=white)](https://www.oracle.com/java/technologies/javase-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.5.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13.4-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-latest-blue.svg)](https://www.docker.com/)

-------------------

# Configuração do Ambiente de Desenvolvimento

## Clonar o Repositório:

`git clone https://github.com/seu-usuario/spring-zen.git`

## Configurar Banco de Dados construindo a imagem Docker:

Certifique-se de ter o Docker instalado, navegue para a pasta destino e execute o seguinte comando para iniciar um container PostgreSQL:

``` shell
docker-compose up -d
```

> Alternativamente você pode criar manualmente um banco de dados Postgres com o nome `spring-zen` <br/> E então configurar em sua máquina as variáveis de ambiente: `${POSTGRES_USERNAME}` e `${POSTGRES_PASSWORD}`

## Compilar e Executar a Aplicação:

Navegue até o diretório do projeto e execute:

``` shell
./mvnw clean install
```

``` shell
java -jar target/spring-zen-0.0.1-SNAPSHOT.jar
```

## Acessar a Aplicação:

Acesse http://localhost:8081 para interagir com a aplicação.

---

# Endpoints Principais

<details>
    <summary>/profissionais</summary>

## GET /profissionais:

### Response:

Lista de profissionais com base nos critérios definidos em Params.

**Params**:

-   `q` - String: Filtro para buscar profissionais que contenham o texto em qualquer um de seus atributos. <br />
-   `fields` - List: (Opcional) Quando presente, apenas os campos listados em fields deverão ser retornados.

![image](https://user-images.githubusercontent.com/94033226/282867092-5617f503-675f-4d35-8674-42c78bcb25d5.png)
![image](https://user-images.githubusercontent.com/94033226/282866921-afcb902e-83cd-4607-a856-964383e78b51.png)

## GET /profissionais/{id}:

### Response:

Todos os dados do profissional que possui o ID passado na URL.
![image](https://user-images.githubusercontent.com/94033226/282868981-f44de020-ecde-47bf-b415-169cc664a261.png)

## POST /profissionais:

> Insere no banco de dados os dados do profissional enviados via body.

### Body:

```json
Content-type: application/json
{
    "name": "Nome do profissional",
    "role": "Cargo do profissional", // aceitando apenas: [Desenvolvedor, Designer, Suporte, Tester]
    "birthDate": "YYYY-MM-DD",
    "createdDate": "YYYY-MM-DD"
}
```

### Response:

Sucesso! Profissional com id {ID} cadastrado
![image](https://user-images.githubusercontent.com/94033226/282865027-c3c6da13-6d2e-4307-8f52-8d2e1f4cbb3e.png)

## PUT /profissionais/{id}:

> Permite a atualização dos dados de um profissional identificado pelo ID. Os dados podem ser modificados, proporcionando flexibilidade na gestão das informações.

![image](https://user-images.githubusercontent.com/94033226/283212923-a7f5f18b-8e71-48a8-957d-1a75331dfb0b.png)

### DELETE /profissionais/{id}:

> Implementa a exclusão lógica de um profissional, marcando-o como inativo. Dessa forma, o profissional não é removido definitivamente, mantendo um histórico de atividade.

![image](https://user-images.githubusercontent.com/94033226/283213432-9ed38ae3-c6d0-4ff0-81bf-07b9a312da2d.png)

</details>

<details>
    <summary>/contatos</summary>

## GET /contatos:

### Response:

> Lista de contatos com base nos critérios definidos em Params.

> **Params**:

    <br/> -   `q` - String: Filtro para buscar contatos que contenham o texto em qualquer um de seus atributos.
    <br/> -   `fields` - List: (Opcional) Quando presente, apenas os campos listados em fields deverão ser retornados.

![image](https://user-images.githubusercontent.com/94033226/282867473-b0512c2b-1462-43c8-a969-6a3aa8c91060.png)
![image](https://user-images.githubusercontent.com/94033226/282868701-bf5cc894-9857-43e4-8e16-378fb7c443bb.png)

## GET /contatos/{id}:

### Response:

Todos os dados do contato que possui o ID passado na URL.
![image](https://user-images.githubusercontent.com/94033226/282869579-71be0d59-f823-4657-a954-e5a5dc7300df.png)

## POST /contatos:

> Insere no banco de dados os dados do contato enviados via body.

### Body:

```json
Content-type: application/json
{
  "name": "Nome do tipo do Contato",
  "contact": "Registro do contato",
  "createdDate": "YYYY-MM-DD",
  "professionalId": "int: professional_id" // apenas se o id já existir em profissionais
}
```

### Response:

Sucesso: Contato com id {ID} cadastrado.
![image](https://user-images.githubusercontent.com/94033226/282869438-7bfe904a-1614-43e5-adba-18b740d40baa.png)

## PUT /contatos/{id}

> Permite a atualização dos dados de um profissional identificado pelo ID. Os dados podem ser modificados, proporcionando flexibilidade na gestão das informações.

<!-- TODO: adicionar imagem -->

### DELETE /contatos/{id}:

> Exclui logicamente um contato associado a um profissional.

<!-- TODO: adicionar imagem -->

</details>

## Um pouco sobre o projeto
Este projeto contou com o uso intensivo de SCRUM no modelo KanBan, você é convidado a acompanhar o processo de criação desta API em:
[Github Projects](https://github.com/users/GabFiterman/projects/3)

### Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

### Licença

> Este projeto é distribuído sob a licença MIT. Consulte o arquivo LICENSE para obter mais informações.
