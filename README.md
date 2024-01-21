# Projeto Spring Boot

Este é um projeto Spring Boot que inclui um banco de dados MySQL. A aplicação tem uma API RESTful com documentação gerada pelo Swagger.

## Pré-requisitos

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html) (versão 17 ou superior)

## Configuração do Banco de Dados

Execute o seguinte comando para iniciar o banco de dados MySQL usando o Docker Compose:

```bash
docker-compose up -d
```

Este comando inicia o MySQL no contêiner Docker definido no arquivo \`docker-compose.yml\`.

## Rodando os Testes

Certifique-se de ter o Java JDK instalado. Navegue até o diretório raiz do projeto e execute:

```bash
./gradlew test
```

Este comando irá compilar o projeto e executar os testes automatizados.

## Rodando a Aplicação

Para iniciar a aplicação Spring Boot, execute:

```bash
./gradlew bootRun
```

A aplicação estará disponível em [http://localhost:8091](http://localhost:8091). Certifique-se de que a porta 8091 esteja disponível.

## Acessando o Swagger

Após iniciar a aplicação, você pode acessar a documentação Swagger em:

- [Swagger UI](http://localhost:8091/macher/swagger-ui/index.html)

A documentação Swagger fornece uma interface interativa para explorar e testar os endpoints da API.

## Autenticação

A aplicação possui um endpoint post /macher/auth onde é possível criar a primeira credencial de usuário, passando uma api key que se encnotra no arquivo application.properties. 
Esse endpoint deve ser utilizado somente para criar o primeiro admin do sistema. Todos os outros usuários 
devem ser criados pelo endpoint post /macher/usuarios.

## Operações da aplicação

Na raiz do projeto há um arquivo de nome:
```crud-macher-collection-Insomnia_2024-01-21.json```
Este arquivo é uma collection do Insomnia que pode ser importado e utilizado para executar as operações da aplicação. 



## Encerrando o Banco de Dados

Após finalizar o uso da aplicação, você pode parar e remover o contêiner Docker do banco de dados MySQL usando o seguinte comando:

```bash
docker-compose down
```
