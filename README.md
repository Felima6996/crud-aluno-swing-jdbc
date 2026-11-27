# crud-aluno-swing-jdbc
CRUD de alunos em Java com interface Swing e JDBC, usando MySQL. Inserir, atualizar, remover e pesquisar por RA.

# CRUD de Alunos com Java Swing e JDBC

Aplicação desktop em Java que faz o cadastro de alunos em um banco de dados MySQL. A interface é feita com Swing e o acesso ao banco usa JDBC, com os comandos SQL `INSERT`, `UPDATE`, `DELETE` e `SELECT`.

## Funcionalidades

| Botão | Ação |
|---|---|
| **Inserir** | Cadastra um novo aluno com os dados do formulário (`INSERT`) |
| **Atualizar** | Altera nome, idade e nascimento do aluno que tem o mesmo RA (`UPDATE`) |
| **Remover** | Exclui o aluno pelo RA informado (`DELETE`) |
| **Pesquisar** | Busca o aluno pelo RA e preenche o formulário (`SELECT`) |

O formulário valida os campos antes de acessar o banco: RA com até 5 caracteres, nome com até 100, idade numérica e data de nascimento no formato `dd/MM/aaaa`.

## Tecnologias

- Java 17 ou superior
- Swing (interface gráfica)
- JDBC com `PreparedStatement`
- MySQL 8 ou superior
- MySQL Connector/J (driver)

## Estrutura do projeto

```
aluno-swing/
├── lib/                      driver do MySQL (.jar), não incluído no repositório
├── sql/
│   └── aluno.sql             script de criação do banco e da tabela
└── src/edu/curso/
    ├── Aluno.java            entidade
    ├── AlunoDAO.java         comandos SQL (INSERT, UPDATE, DELETE, SELECT)
    ├── AlunoForm.java        formulário Swing
    └── Conexao.java          conexão com o banco
```

## Banco de dados

A tabela `aluno` está no banco `escola`:

| Campo | Tipo |
|---|---|
| `ra` | `VARCHAR(5)` (chave primária) |
| `nome` | `VARCHAR(100)` |
| `idade` | `INT` |
| `nascimento` | `DATE` |

## Como executar

### 1. Criar o banco

No MySQL Workbench (ou outro cliente), abra o arquivo `sql/aluno.sql` e execute o script. Ele cria o banco `escola` e a tabela `aluno`.

### 2. Baixar o driver

Baixe o **MySQL Connector/J** em https://dev.mysql.com/downloads/connector/j/ (opção *Platform Independent*), extraia o arquivo e copie o `mysql-connector-j-x.x.x.jar` para a pasta `lib/`.

### 3. Configurar a conexão

Em `src/edu/curso/Conexao.java`, ajuste o usuário e a senha do seu MySQL:

```java
private static final String URL = "jdbc:mysql://localhost:3306/escola";
private static final String USUARIO = "seu_usuario";
private static final String SENHA = "sua_senha";
```

### 4. Compilar e executar

Na pasta raiz do projeto, pelo terminal:

```
javac -cp "lib/*" -d bin src/edu/curso/Aluno.java src/edu/curso/AlunoDAO.java src/edu/curso/AlunoForm.java src/edu/curso/Conexao.java
java -cp "bin;lib/*" edu.curso.AlunoForm
```

No Linux e no macOS, troque o `;` por `:` no segundo comando (`"bin:lib/*"`).

## Problemas comuns

- **`ClassNotFoundException: com.mysql.cj.jdbc.Driver`**: o `.jar` não está na pasta `lib/` ou o classpath não a inclui.
- **`Access denied for user`**: usuário ou senha do `Conexao.java` não correspondem aos do MySQL.
- **`Unknown database 'escola'`**: o script `sql/aluno.sql` ainda não foi executado.
- **`Communications link failure`**: o servidor MySQL não está rodando ou a porta da URL está errada.
