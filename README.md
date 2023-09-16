# CSE143-Fall2021-Assessment4-EvilHangman
Take-home Assessment 4: Evil Hangman

## Overview
In the game of hangman, one player (in our case, a computer) picks a word that another player (the user) is trying to guess. The guesser guesses individual letters until the word is fully discovered (in which case the guesser wins) or a specified number of incorrect letters is guessed (in which case the guesser loses). As correct letters are guessed, their location in the secret word is revealed to the guesser. You can learn more about the game of hangman on its Wikipedia page. (This game has been the inspiration for many other games, including the popular American gameshow Wheel of Fortune.)

In our EVIL! game of hangman, the computer delays picking a specific secret word until it is forced to do so. As a result, the computer is always considering a set of words that could be the secret word. In order to fool the user into thinking it is playing fairly, the computer only considers words with the same letter pattern.

You can read the full specification [here](https://courses.cs.washington.edu/courses/cse143/21au/take-home-assessments/a4/a4.pdf)
