# Chess system CLI in Java ☕

[![License](https://img.shields.io/github/license/rogeriofrsouza/chess-system-java)](https://github.com/rogeriofrsouza/chess-system-java/blob/main/LICENSE)
[![Language](https://img.shields.io/badge/language-Java-red.svg)](https://dev.java)
[![Build](https://github.com/rogeriofrsouza/chess-system-java/actions/workflows/maven.yml/badge.svg)](https://github.com/rogeriofrsouza/chess-system-java/blob/main/.github/workflows/maven.yml)

<!--toc:start-->
- [About](#about)
- [Class diagram](#class-diagram)
- [How to run](#how-to-run)
<!--toc:end-->

## About

This is a Chess system CLI application developed up to [this commit](https://github.com/rogeriofrsouza/chess-system-java/commit/c6b2bb0b635ea402dd93ed7ba30fdc9ee984d2ec) during the course [**Java COMPLETO 2023 Programação Orientada a Objetos +Projetos**](https://www.udemy.com/course/java-curso-completo/), taught by the professor [Nelio Alves](https://www.udemy.com/user/nelio-alves/ "Perfil do Nelio Alves na Udemy").
However, there are a lot of refactorings and improvements currently being applied, which includes: unit tests, class design and best practices in OOP and Solid.

This project consists of a chess game, allowing two players to execute commands and movements on their pieces in order to capture the opposing king and win the game.
It also supports special chess moves like: Castling, En Passant and Promotion.

It must be executed in a terminal with colors support for correct color displaying during runtime.
Currently only supported on Linux 🐧

## Class diagram

![Class diagram](https://raw.githubusercontent.com/rogeriofrsouza/java-poo/main/assets/chess-system-design.png)

## How to run

Requirements: JDK 17+

```sh
git clone https://github.com/rogeriofrsouza/chess-system-java.git
cd chess-system-java
./mvnw dependency:resolve clean package
java -jar target/chess-system*
```
