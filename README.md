# TDD e Refactoring - Projeto da atividade de Desenvolvimento Orientado a Testes

O projeto se trata de um dos itens entregáveis da atividade **A5 | Desenvolvimento Ágil de Software** da disciplina
**Laboratório de Engenharia de Software III**


Link da atividade: https://unisinos.instructure.com/courses/23764/assignments/64368

## O projeto

Projeto criado com **Gradle 7.5.1**, desenvolvido em **Java 17** com framework **Spring Boot 2.7.4**. Foi utilizado
também **H2 Database Engine** para criação de uma simples base de dados em memória.

**Todo o desenvolvimento foi feito com a técnica TDD.**

## Instalação e primeiros passos

1 - Necessário baixar e instalar JDK 17: https://www.oracle.com/java/technologies/downloads/#java17

1.1 - Configurar variável de ambiente JAVA_HOME.

2 - Clonar o repositório e abrir o projeto com sua IDE preferida (IntelliJ, Eclipse, etc). As IDEs costumam já
configurar automaticamente um projeto com Spring Boot.

3 - Rodar ou Debugar o projeto. O servidor Tomcat será iniciardo em http://localhost:8080. Caso deseje mudar a porta,
basta acessar o arquivo [applicartion.yml](./src/main/resources/application.yml), alterá-la na linha 2 e reiniciar o
projeto.

3.1 - No momento em que o projeto está iniciando, o H2 cria uma base de dados de nome **abc** e uma tabela **usuario**.
A tabela é populada com 5 usuários fictícios.
É possível acessar a interface de gerenciamento do H2 em http://localhost:8080/h2 caso deseje. Usuário: **sa**, sem
senha.

4 - Para visualizar a documentação da API, acesse http://localhost:8080/swagger-ui/. Caso tenha mudado a porta,
deverá informar a porta utilizada.

## Usuários fictícios cadastrados

| Nome                             | E-mail                        | CPF         | Senha        |
|----------------------------------|-------------------------------|-------------|--------------|
| Rosângela Agatha Liz Ferreira    | rosangela.ferreira@edu.abc.br | 94845207583 | MTIzNDU2Nzg= |
| Catarina Priscila Andreia Mendes | catarina.mendes@edu.abc.br    | 19697663289 | MTIzNDU2Nzg= |
| Maya Alícia Raquel Vieira        | maya.vieira@edu.abc.br        | 40088693449 | MTIzNDU2Nzg= |
| Igor Fernando Joaquim Barbosa    | igor.barbosa@edu.abc.br       | 43540585451 | MTIzNDU2Nzg= |
| Benício Mário Ian Porto          | benicio.porto@edu.abc.br      | 01740504372 | MTIzNDU2Nzg= |


## Funcionalidades

O projeto possui apenas um endpoint que é responsável por efetuar o login do usuário.

Curl de exemplo:

```console
curl -X POST "http://localhost:8080/efetuar-login" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"senha\": \"MTIzNDU2Nzg=\", \"tipoLogin\": \"EMAIL\", \"usuario\": \"rosangela.ferreira@edu.abc.br\"}"
```

Responde **200 OK** quando os dados informados forem válidos e também for encontrado um usuário com as credenciais
informadas.

## Testes

Foi criada a classe [LoginServiceTest.java](./src/test/java/br/com/abc/tdd/service/LoginServiceTest.java) para testar
a classe [LoginService.java](./src/main/java/br/com/abc/tdd/service/LoginService.java) responsável pelo requisito de Efetuar Login.