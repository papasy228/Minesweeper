# Minesweeper
Custom Minesweeper game I wrote for my CSX class :thumbsup:.

## Usage
Game has to be run with args[0] = size of board and args[1] = bombfile
eg.  java Minesweeper 8 bombfile.txt

bombfile contains locations of bombs. Each line is a comma separated cell location

eg.<br>
5, 2<br>
5, 3<br>
3, 2<br>
4, 5<br>
5, 5<br>
5,    5<br>

 Spaces don't matter
Board size cannot be less than 6 or greater than 30. 
Game defaults to 6 by 6 gameboard if it doesnt meet these parameters

## Rules
User gets 3 hits on a bomb before game is over instead of the traditional 1.

## Coming Soon
Version 2 will have high scores and connect to a database

[Dowload Live Demo](http://papasy.me/files/Minesweeper.jar)


