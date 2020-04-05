package com.example.tictactoe_game

import com.example.tictactoe_game.GameState.*

private const val X: String = "X"
private const val ZERO: String = "O"
private const val CLEAR: String = " "

class GameLogic(
    private var callback: StateListener
) {
    private var player1Turn: Boolean = true
    private var player1Points: Int = 0
    private var player2Points: Int = 0
    private var gameField: Array<Array<String>> = Array(3) { Array(3) { CLEAR } }
    private lateinit var currentState: GameState

    init {
        changeState(GameState.OrderToPlay(gameField))
    }
    
    fun playerMove(number: Int) {
        val playerSymbol: String = getPlayerSymbol()

        // Check if player chooses free number
        val movePosition: IntArray = getPositionByNumber(number)
        if (gameField[movePosition[0]][movePosition[1]] !== CLEAR) {
            changeState(ErrorMove("Current field is not empty. Make another move."))
            return
        } else {
            gameField[movePosition[0]][movePosition[1]] = playerSymbol
        }
        // Проверка прямых
        for (i in 0..2) {
            if ((gameField[i][0] === X) and (gameField[i][1] === X) and (gameField[i][2] === X) ||
                (gameField[0][i] === X) and (gameField[1][i] === X) and (gameField[2][i] === X)
            ) {
                ++player1Points
                changeState(Winner(gameField))
                return
            } else if ((gameField[i][0] === ZERO) and (gameField[i][1] === ZERO) and (gameField[i][2] === ZERO) ||
                (gameField[0][i] === ZERO) and (gameField[1][i] === ZERO) and (gameField[2][i] === ZERO)
            ) {
                ++player2Points
                changeState(Winner(gameField))
                return
            }
        }
        // Проверка диагоналей
        if ((gameField[0][0] === X) and (gameField[1][1] === X) and (gameField[2][2] === X) ||
            (gameField[0][2] === X) and (gameField[1][1] === X) and (gameField[2][0] === X)
        ) {
            ++player1Points
            changeState(Winner(gameField))
            return
        } else if ((gameField[0][0] === ZERO) and (gameField[1][1] === ZERO) and (gameField[2][2] === ZERO) ||
            (gameField[0][2] === ZERO) and (gameField[1][1] === ZERO) and (gameField[2][0] === ZERO)
        ) {
            ++player2Points
            changeState(Winner(gameField))
            return
        }

        // Проверка на ничью
        var isGamePlayable = false
        for (arr in gameField) {
            for (element in arr) {
                if (element === CLEAR) {
                    isGamePlayable = true
                }
            }
        }
        if (!isGamePlayable) {
            changeState(NoWinner(gameField))
            return
        }

        // Switch player move
        player1Turn = player1Turn.not()
        changeState(OrderToPlay(gameField))
    }

    private fun getPositionByNumber(number: Int): IntArray {
        when (number) {
            1 -> return intArrayOf(0, 0)
            2 -> return intArrayOf(0, 1)
            3 -> return intArrayOf(0, 2)
            4 -> return intArrayOf(1, 0)
            5 -> return intArrayOf(1, 1)
            6 -> return intArrayOf(1, 2)
            7 -> return intArrayOf(2, 0)
            8 -> return intArrayOf(2, 1)
            9 -> return intArrayOf(2, 2)
        }
        return intArrayOf(0, 0)
    }

    private fun getPlayerSymbol(): String {
        return if (player1Turn) {
            X
        } else {
            ZERO
        }
    }

    fun resetGame() {
        for (i in gameField.indices) {
            for (j in gameField.indices) {
                gameField[i][j] = CLEAR
            }
        }
        player1Turn = true
        changeState(GameState.OrderToPlay(gameField))
    }

    private fun changeState(newState: GameState) {
        currentState = newState
        callback.onNewState(newState, player1Points.toString(), player2Points.toString())
    }
}
