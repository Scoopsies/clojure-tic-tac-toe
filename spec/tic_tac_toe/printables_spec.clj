(ns tic-tac-toe.printables-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.moves.human-move :as human-move]
            [tic-tac-toe.printables :as sut]
            [tic-tac-toe.settings-spec :as settings]
            [speclj.stub :as stub]))

(describe "printables"
  (context "print-board"
    (with-stubs)
    (redefs-around (println (stub :println)))
    (it "prints the board with correct values incremented by 1"
      (sut/print-board (range 9))
      (should= [["  1  |  2  |  3  "]
                ["  4  |  5  |  6  "]
                ["  7  |  8  |  9  "]
                [""]]
               (stub/invocations-of :println)))

    (it "player tokens aren't effected by incrementation"
      (sut/print-board [0 1 2 3 "X" 5 6 7 8])
      (should= [["  1  |  2  |  3  "]
                ["  4  |  X  |  6  "]
                ["  7  |  8  |  9  "]
                [""]]
               (stub/invocations-of :println)))

    (it "prints a 4 x 4 board"
      (sut/print-board (range 16))
      (should= [["  1  |  2  |  3  |  4  "]
                ["  5  |  6  |  7  |  8  "]
                ["  9  |  10 |  11 |  12 "]
                ["  13 |  14 |  15 |  16 "]
                [""]]
               (stub/invocations-of :println)))

    (it "prints a 3x3x3 board"
      (sut/print-board (range 27))
      (should= [["  1  |  2  |  3    10 |  11 |  12   19 |  20 |  21 "]
                ["  4  |  5  |  6    13 |  14 |  15   22 |  23 |  24 "]
                ["  7  |  8  |  9    16 |  17 |  18   25 |  26 |  27 "]
                [""]]
               (stub/invocations-of :println)))
    )


  (context "print-game-over"
    (it "prints an accurate message if game is won."
      (should= "Scoops wins!\n\n"
               (with-out-str
                 (sut/print-win-lose-draw
                   ["X" "X" "X" 3 4 5 6 7 8]
                   settings/human-computer-settings))))

    (it "prints an accurate message if game is lost."
      (should= "Computer-O wins!\n\n"
               (with-out-str
                 (sut/print-win-lose-draw
                   ["O" "O" "O" 3 4 5 6 7 8]
                   settings/human-computer-settings))))

    (it "prints an accurate message if game is draw."
      (should= "Draw.\n\n"
               (with-out-str
                 (sut/print-win-lose-draw
                   ["X" "O" "X" "O" "O" "X" "X" "X" "O"]
                   settings/human-computer-settings))))
    )

  (context "print-get-player-info"
    (it "prints the correct info for X"
      (should= "Who will be playing as X?\n\n"
               (with-out-str
                 (sut/print-get-player-info "X"))))

    (it "prints the correct info for O"
      (should= "Who will be playing as O?\n\n"
               (with-out-str
                 (sut/print-get-player-info "O"))))
    )

  (context "make-possessive"
    (it "adds an 's to the end of a name"
      (should= "Alex's" (sut/make-possessive "Alex"))
      (should= "Micha's" (sut/make-possessive "Micha"))
      (should= "Brandon's" (sut/make-possessive "Brandon")))

    (it "only adds an ' to the end of a name ending in 's'"
      (should= "Scoops'" (sut/make-possessive "Scoops"))
      (should= "James'" (sut/make-possessive "James"))
      (should= "Louis'" (sut/make-possessive "Louis")))
    )

  (context "print-get-move"
    (it "displays proper result for 3x3 board"
      (should= "It's Micha's turn.\nPlease pick a number 1-9.\n\n"
               (with-out-str
                 (sut/print-get-move (range 9) {"X" {:player-name "Micha"
                                                     :move-fn     human-move/update-board-human}}))))

    (it "displays proper result for 4x4 board"
      (should= "It's Alex's turn.\nPlease pick a number 1-16.\n\n"
               (with-out-str
                 (sut/print-get-move (range 16) {"X" {:player-name "Alex"
                                                      :move-fn     human-move/update-board-human}}))))

    (it "displays correct possessive name for names ending with s"
      (should= "It's Scoops' turn.\nPlease pick a number 1-16.\n\n"
               (with-out-str
                 (sut/print-get-move (range 16) {"X" {:player-name "Scoops"
                                                      :move-fn     human-move/update-board-human}}))))

    (it "Doesn't display the pick a number line if not human-move"
      (should= "It's Computer-X's turn.\n\n"
               (with-out-str
                 (sut/print-get-move (range 16) {"X" {:player-name "Computer-X"}}))))
    )

  (context "print-get-player-name"
    (it "prints the prompt for getting player X's name"
      (should= "What is the name of player X?\n\n"
               (with-out-str (sut/print-get-player-name "X"))))

    (it "prints the prompt for getting player O's name"
      (should= "What is the name of player O?\n\n"
               (with-out-str (sut/print-get-player-name "O"))))
    )

  (context "print-get-move-fn"
    (it "prints the menu for get-move-fn for X"
      (should= "Choose who will play as X.\n1. Human\n2. Computer\n\n"
               (with-out-str (sut/print-get-move-fn "X"))))

    (it "prints the menu for get-move-fn for O"
      (should= "Choose who will play as O.\n1. Human\n2. Computer\n\n"
               (with-out-str (sut/print-get-move-fn "O"))))
    )

  (context "print-input-error"
    (it "prints an error message for player input ham"
      (should= "ham is not a valid input.\n" (with-out-str (sut/print-input-error "ham"))))

    (it "prints an error message for player input steve"
      (should= "steve is not a valid input.\n" (with-out-str (sut/print-input-error "steve"))))
    )

  (context "print-get-difficulty-fn"
    (with-stubs)
    (redefs-around [println (stub :println)])
    (it "prints menu for selecting difficulty"
      (sut/print-get-difficulty-fn)
      (should=
        [["Choose your difficulty level."]
         ["1. Easy"]
         ["2. Medium"]
         ["3. Hard"]
         [""]]
        (stub/invocations-of :println)))
    )

  (context "print-get-board-size"
    (with-stubs)
    (redefs-around [println (stub :println)])
    (it "prints the menu for get-board-size"
      (sut/print-get-board-size)
      (should=
        [["What size board would you like to play on?"]
         ["1. 3x3"]
         ["2. 4x4"]
         ["3. 3x3x3 (3-D)"]
         [""]]
        (stub/invocations-of :println)))
    )
  )