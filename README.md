<h1 align="center">Projeto Aegro Academy - Construindo uma API REST</h1>
<br>
<p align="justify">
  O projeto consiste na construção de uma aplicação cujo domínio é composto por fazendas que podem ter de 0 a N talhões
(marcação de uma subárea da fazenda), cada. Os talhões devem conter um atributo numérico de área (em hectares) e deve
ser possível fazer N registros de produção (em KG) para cada talhão. A aplicação deve ser capaz de calcular a produtividade
de cada fazenda e de cada talhão, sendo a produtividade a quantidade de produção (em KG) por área (em hectares).
</p>

## Índice
- [Objetivo](#objetivo)
- [Tecnologias](#tecnologias)
- [Requisitos](#requisitos)
- [Entidades](#entidades)
- [Configurando e executando o projeto](#configurando-e-executando-o-projeto)


## Objetivo
* Cadastro, edição e remoção de uma fazenda;
* Cadastro, edição e remoção de um talhão (área da fazenda);
* Cadastro, edição e remoção da produção de um talhão;
* Calcular produtividade de uma fazenda;
* Calcular produtividade de um talhão;
* A API deve atender às especificações básicas previamente descritas;
* A API deve ser construída em Java com Spring;
* Os dados devem ser persistidos em um banco de dados local (H2 ou MongoDB);
* A aplicação deve possuir um coverage maior do que 50%;
* É importante que o código seja construído por partes e revisado por pares.


## Tecnologias
<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Toolss"/> <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java"/> 
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Graddle"/>
<img src="https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit"/>
<img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white" alt="Postman"/>


## Requisitos
### (Obrigatório)
* <a href="https://www.oracle.com/java/technologies/javase-downloads.html">Java 11+</a> 

* <a href="https://gradle.org/install/">Gradle 7+</a> 

### (Opcional)
IDE usada no projeto:
* <a href="https://spring.io/tools">Spring Tool Suite (STS)</a>


## Entidades
![image](https://user-images.githubusercontent.com/103016217/166948565-8d5a1c4e-7802-4b5e-883b-a2937ebee2b6.png)


## Configurando e executando o projeto

```shell
# clonando o repositorio e acessando o diretorio
$ git clone git@github.com:gmenin/projeto-aegro.git && cd projeto-aegro

# executando a aplicacao 
$ gradle bootrun

# executando os testes
$ gradle test
```
