# Pagamento de Funcionários

Projeto desenvolvido para o teste técnico da Unisoma.

## Requisitos

- **JDK 8**
- **Maven**
- **PostgreSQL**

## Rodar a Aplicação

1. **Clonar o repositório**

```
    git clone https://github.com/mateusmercon/pagamento-funcionarios.git
    cd pagamento-funcionarios
```

2. **Configurar o Banco de Dados**

- Abra o arquivo `src/main/resources/application.properties` e configure as propriedades do banco de dados conforme necessário:

  ```
  spring.datasource.url=jdbc:postgresql://localhost:5432/pagamento-funcionarios-db
  spring.datasource.username=seu-usuario-do-postgres
  spring.datasource.password=sua-senha-do-postgres
  ```

3. **Build e Rodar a Aplicação**

- Usando o Maven:

  ```
  mvn spring-boot:run
  ```

- Ou, você também pode importar o projeto em uma IDE de sua preferência e executá-lo a partir de lá.

4. **Acessar a Aplicação**

- Após iniciar a aplicação ela estará disponível no localhost:8080. Você pode acessá-la com o Swagger no seu navegador em [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).
