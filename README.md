 # Currency API
Esta é uma API rest usando Spring Boot, PostegreSQL, rodando em docker utlizando docker-compose.
Esta API tem finalidade de conversão de moeda, baseado em uma API Externa gratuita de taxas de câmbio.

[Documentação da API Externa pode ser encontrada aqui.](https://exchangeratesapi.io/documentation/)

---

## Requerimentos
- Java 11
- Maven 3.6.0
- Docker 1.41
- Docker-compose 1.29.2

---

##  Build and Run
buildar, empacotar e criar uma nova imagem Docker com a aplicação

```mvn clean package```

Rodar a aplicação e Postgres container

```docker-compose up --build```

---

## Uso
* Depois de rodar a aplicação, é possível acessar API Swagger disponível em:

    **http://localhost:8080/swagger-ui.html#/**
  
* Para realizar uma conversão, primeiramente se deve realizar o cadastro de um *Cliente*.

Descrição | Método | Endereço 
--- | --- | ---
Cadastrar novo Cliente | **POST** | v1/client
Recuperar todos Clientes cadastrados | **GET** | v1/client
Recuperar um Cliente pelo ID | **GET** | v1/client/{id}
Excluir um Cliente pelo ID | **DELETE** | v1/client/{id}
Realizar conversão de moeda | **POST** | v1/transaction
Recuperar transação por Cliente | **GET** | v1/transaction/client/{id}
Recuperar todas transações | **GET** |v1/transaction


---


**Desenvolvido por Pery Vieira**



