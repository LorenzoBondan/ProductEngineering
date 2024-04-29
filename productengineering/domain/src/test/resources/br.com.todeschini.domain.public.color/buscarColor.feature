#language: pt

Funcionalidade: Como um usuario, gostaria de buscar um registro de cor
  Cenário: Um usuario com permissoes para buscar cores

    Dado Um codigo existente
    Quando for solicitada a busca de um registro existente
    Então um registro e trazido

    Dado Um codigo inexistente
    Quando for solicitada a busca de um registro nao existente
    Então uma excecao e lancada