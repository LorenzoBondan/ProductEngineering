#language: pt
Funcionalidade: Como um usuario, gostaria de criar um registro de cor
  Cenário: Um usuario com permissoes para criar cores

    Dado Um formulario corretamente preenchido
    Quando for solicitado o cadastro de um novo registro
    Então um novo registro de cor sera criado


    Dado Um formulario corretamente preenchido
    E o nome utilizado ja consta no banco de dados
    Quando ele tentar cadastrar o registro
    Então uma excecao e lancada
