package com.example.tictactoe_game

sealed class GameState {

    data class ErrorMove(val errorText: String) : GameState()

    data class NoWinner(val gameField: Array<Array<String>>) : GameState() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is NoWinner) return false

            if (!gameField.contentDeepEquals(other.gameField)) return false

            return true
        }

        override fun hashCode(): Int {
            return gameField.contentDeepHashCode()
        }
    }

    data class Winner(val gameField: Array<Array<String>>) : GameState() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Winner) return false

            if (!gameField.contentDeepEquals(other.gameField)) return false

            return true
        }

        override fun hashCode(): Int {
            return gameField.contentDeepHashCode()
        }
    }

    data class OrderToPlay constructor(
        val gameField: Array<Array<String>>
    ) : GameState() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is OrderToPlay) return false

            if (!gameField.contentDeepEquals(other.gameField)) return false

            return true
        }

        override fun hashCode(): Int {
            return gameField.contentDeepHashCode()
        }
    }
}