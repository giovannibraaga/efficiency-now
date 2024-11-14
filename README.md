<div align="center">
    <img src="https://github.com/user-attachments/assets/bb116cbe-d0fe-40b6-9244-54d045d0070e" alt="Efficiency Now no bg" width="300"/>
</div>

Efficiency Now é uma aplicação Spring Boot projetada para calcular economias de energia ao substituir lâmpadas tradicionais por lâmpadas LED mais eficientes e otimizar o uso de ar condicionados.

## Funcionalidades

- Calcular economias de energia ao substituir lâmpadas fluorescentes, incandescentes e halógenas por lâmpadas LED.
- Calcular consumo de energia e economias para aparelhos de ar condicionado com base em BTU e temperatura.
- Autenticação de usuários utilizando AVL Tree para armazenamento e busca eficiente.

## Tecnologias

- Java
- Spring Boot
- Gradle
- PostgreSQL
- Docker

## Começando

### Pré-requisitos

- Java 17
- Gradle
- Docker
- PostgreSQL

### Instalação

1. Clone o repositório:
    ```sh
    git clone https://github.com/giovannibraaga/efficiencynow.git
    cd efficiencynow
    ```

2. Configure o banco de dados em `src/main/resources/application.properties`:
    ```ini
    spring.datasource.url=jdbc:postgresql://<seu-url-do-banco>:<porta>/<nome-do-banco>?user=<usuario>&password=<senha>
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    ```

3. Build do projeto:
    ```sh
    ./gradlew build
    ```

4. Execute a aplicação:
    ```sh
    ./gradlew bootRun
    ```

### Docker

Para construir e executar a aplicação usando Docker:

1. Build da imagem Docker:
    ```sh
    docker build -t efficiencynow .
    ```

2. Execute o contêiner Docker:
    ```sh
    docker run -p 8080:8080 efficiencynow
    ```

### Endpoints da API

#### Autenticação

- `POST /users/login`: Autentica um usuário e retorna um token de sessão.
- `GET /users/profile`: Obtém o perfil do usuário logado.
- `POST /users/logout`: Encerra a sessão do usuário.

#### Economia de Lâmpadas

- `POST /lamp-economy/calc`: Calcula a economia total de energia ao substituir lâmpadas fluorescentes, incandescentes e halógenas por lâmpadas LED.

#### Economia de Ar Condicionado

- `POST /ac-economy/calc-monthly-economy`: Calcula a economia mensal de energia para aparelhos de ar condicionado.

### Contribuindo

Para contribuir com este projeto, siga os passos abaixo:

1. Faça um fork do repositório.
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`).
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`).
4. Faça o push para a branch (`git push origin feature/nova-feature`).
5. Abra um Pull Request.

### Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.
