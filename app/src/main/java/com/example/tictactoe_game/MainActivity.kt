package com.example.tictactoe_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), StateListener {
    private lateinit var player1: TextView
    private lateinit var player2: TextView
    private lateinit var button00: Button
    private lateinit var button01: Button
    private lateinit var button02: Button
    private lateinit var button10: Button
    private lateinit var button11: Button
    private lateinit var button12: Button
    private lateinit var button20: Button
    private lateinit var button21: Button
    private lateinit var button22: Button
    private lateinit var resetGame: Button

    private lateinit var gameFields: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        player1 = findViewById(R.id.first_player_points)
        player2 = findViewById(R.id.second_player_points)
        button00 = findViewById(R.id.bttn_00)
        button01 = findViewById(R.id.bttn_01)
        button02 = findViewById(R.id.bttn_02)
        button10 = findViewById(R.id.bttn_10)
        button11 = findViewById(R.id.bttn_11)
        button12 = findViewById(R.id.bttn_12)
        button20 = findViewById(R.id.bttn_20)
        button21 = findViewById(R.id.bttn_21)
        button22 = findViewById(R.id.bttn_22)
        resetGame = findViewById(R.id.button_restart)

        gameFields = listOf(
            button00, button01, button02,
            button10, button11, button12,
            button20, button21, button22
        )

        val gameLogic = GameLogic(this)

        resetGame.setOnClickListener {
            gameLogic.resetGame()
        }

        val onClickListener = View.OnClickListener { button ->
            gameLogic.playerMove((button.tag as String).toInt())
        }
        gameFields.forEach { button ->
            button.setOnClickListener(onClickListener)
        }
    }

    override fun onNewState(
        state: GameState,
        firstPlayerPoints: String,
        secondPlayerPoints: String
    ) {
        when (state) {
            is GameState.NoWinner -> {
                state.gameField.forEachIndexed { i, arr ->
                    arr.forEachIndexed { j, value ->
                        gameFields[i * 3 + j].text = value
                    }
                }
            }

            is GameState.Winner -> {
                state.gameField.forEachIndexed { i, arr ->
                    arr.forEachIndexed { j, value ->
                        gameFields[i * 3 + j].text = value
                    }
                }
                for (i in gameFields.indices) {
                    gameFields[i].isClickable = false
                }
            }
            is GameState.OrderToPlay -> {
                for (i in gameFields.indices) {
                    gameFields[i].isClickable = true
                }
                state.gameField.forEachIndexed { i, arr ->
                    arr.forEachIndexed { j, value ->
                        gameFields[i * 3 + j].text = value
                    }
                }
            }
            is GameState.ErrorMove -> {
                Toast.makeText(this, state.errorText, Toast.LENGTH_SHORT).show()
            }
        }
        player1.text = firstPlayerPoints
        player2.text = secondPlayerPoints
    }
}

