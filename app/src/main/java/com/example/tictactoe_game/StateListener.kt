package com.example.tictactoe_game

interface StateListener {
    fun onNewState(state: GameState, firstPlayerPoints: String, secondPlayerPoints: String)
}