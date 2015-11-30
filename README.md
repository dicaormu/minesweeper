# Minesweeper
Minesweeper game solved in java using TDD

## Game steps:

The game uses command line to prompt information and show the game status.

- When the game starts, the player gets prompted for the grid size: width (cells in x) and height (cells in y)
and the number of mines.
- The grid is generated according to these requirements using # character to show the cells, a number to show
how many adjacent cells hold mines, and @ when a mine is found .
- When the games starts, the user is prompted for the coordinates (x, y) of the first cell to uncover.
- The game shows the resulting grid and prompts for new coordinates.
- And so on ...
- The game ends when there is no more non-mined cell to uncover or the player uncovers a mine.

## Rules

- Uncover a mine, and the game ends.
- Uncover an empty cell, and the player keeps playing.
- In each empty uncovered cell the number of adjacent cells holding mines is displayed.
- Uncovering a cell holding the number 0 (i.e. no adjacent mined cells) uncovers all adjacent cells, and so on.

## Prerequisites
JDK 1.8, Maven

## Installation

From your command line, clone the project using

```sh
$ git clone https://github.com/dicaormu/minesweeper.git
```

This project uses maven and you have to compile it

From your command line, in the directory of your project, build it using:

```sh
$ mvn clean compile
```
You can execute the tests by using

```sh
$ mvn test
```

You can execute the game by using

```sh
$ mvn exec:java
```

## How it works?

The project contains a class 'com.fr.mines.Launcher'.
This class contains the main method and prompts for:
- cells in x
- cells in y
- number of mines

This launcher uses a 'MinesGame' to instantiate and to draw the grid of the game.
The game logic is controlled by 'com.fr.mines.service.MinesWeeperService' and the strategy for the creation of the grid
is managed by 'RandomMinesStrategy'






