# Clojure Tic-Tac-Toe

A tic-tac-toe game built in Clojure and ClojureScript, playable via terminal, desktop GUI, or web browser. 
Supports multiple board sizes (3x3, 4x4, and 3D 3x3x3) with three AI difficulty levels.

## Features

- **Three UIs** -- Terminal (TUI), desktop graphics (Quil), and browser (Reagent/React)
- **Board sizes** -- 3x3, 4x4, and 3x3x3 (3D, TUI only)
- **AI difficulty** -- Easy (random), Medium (basic strategy), Hard (minimax)
- **Game persistence** -- Save/load games via EDN files or PostgreSQL
- **Replay** -- Replay any previously stored game
- **Shared logic** -- Core game rules written in `.cljc` and shared across JVM and JavaScript

## Prerequisites

- [Java](https://adoptium.net/) (JDK 8+)
- [Clojure](https://clojure.org/guides/install_clojure)
- [PostgreSQL](https://www.postgresql.org/download/) (optional, for database persistence)

## Running the Game

```bash
clojure -M:run
```

### Browser Version

```bash
# Compile ClojureScript
clojure -M:cljs
```

Then open the compiled app in a browser.

## Building a JAR

```bash
clojure -T:build jar
```

This produces `target/TicTacToe.jar`, a source JAR containing all `.clj` and `.cljc` files. Used by [clojure-server](https://github.com/Scoopsies/clojure-server) as a local dependency.

## Running Tests

Backend:
```bash
clj -M:test:spec
```

Frontend:
```bash
clj -M:test:cljs
```

## AI

| Difficulty | Strategy |
|---|---|
| Easy | Random moves |
| Medium | Wins if possible, blocks opponent, otherwise random |
| Hard | Minimax with memoization (depth-limited on larger boards) |

## Tech Stack

- **Clojure 1.10+** / **ClojureScript 1.11**
- **Quil** -- Desktop graphics (Processing wrapper)
- **Reagent** -- ClojureScript React wrapper
- **next.jdbc** -- Database access
- **SpeclJ** -- Testing framework
