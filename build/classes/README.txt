I have stored set of words(European contry names) in a text file. When starting the application, 
the dictionary file is loaded and read line by line and store all the words into an arraylist.
For a new word, what I am doing is pick a random word from the list. Player has 6 chances for wrong guesses.
For showing the hangamn sticks figure, I use corresponding images according to the current try. 
Player need to type a character and hit enter to proceed his guess. When the player hit enter, 
first the code validates the typed input text(check whether it is an character or word). Then 
checks the guessed character is in the word or not. If the character is a successful guessed, 
update the current guess string. If the guess is a wrong guess, update the current guess and 
the hangman figure. Then check whether the player has won or lost by no longer chance for wrong answers.
If the game is in the game over state, show and confirmation alert message to user asking to continue the game
and quit the game.