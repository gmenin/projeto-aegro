<h1 align="center">Project Aegro Academy - Building a REST API</h1>
<br>
<p align="justify">
  The project aims to build an application whose domain is composed by farms that can have from 0 to N glebes (subarea of a farm), each. Glebes must contain a numerical attribute of area (in hectares) and it must be possible to create N production records (in KG) for each glebe. The application must be able to calculate the productivity of each farm and each glebe, with productivity being the amount of production (in KG) per area (in hectares).
</p>

## Table of Contents
- [Goals](#goals)
- [Technologies](#technologies)
- [What you will need](#what-you-will-need)
- [Entities](#entities)
- [Configuring and running the project](#configuring-and-running-the-project)


## Goals
* Create, update and delete a farm;
* Create, update and delete a glebe (subarea of a farm);
* Create, update and delete the production of a glebe;
* Calculate the productivity of a farm;
* Calculate the productivity of a glebe;
* The API must follow the basic specifications previously described;
* The API must be built in Java with Spring;
* Data must be persisted in a local database (H2 or MongoDB);
* Coverage must be greater than 50%;
* Code must be built in parts and peer-reviewed.


## Technologies
<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Toolss"/> <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java"/> 
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Graddle"/>
<img src="https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit"/>
<img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white" alt="Postman"/>


## What you will need
### (Required)
* <a href="https://www.oracle.com/java/technologies/javase-downloads.html">Java 11+</a> 

* <a href="https://gradle.org/install/">Gradle 7+</a> 

### (Optional)
IDE used in the project:
* <a href="https://spring.io/tools">Spring Tool Suite (STS)</a>


## Entities
![image](https://user-images.githubusercontent.com/103016217/166948565-8d5a1c4e-7802-4b5e-883b-a2937ebee2b6.png)


## Configuring and running the project

```shell
# clone the repository and access the directory
$ git clone git@github.com:gmenin/projeto-aegro.git && cd projeto-aegro

# running the application
$ gradle bootrun

# running tests 
$ gradle test
```
