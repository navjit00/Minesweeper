package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.graphics.Color
import android.widget.Toast

class Game2 : AppCompatActivity() {

    private var mine = 2
    private var size = 3

    private lateinit var tvin: TextView
    private lateinit var gridl: Array<Array<Button>>
    private lateinit var mines: Array<Array<Boolean>>
    private lateinit var punkte: Array<Array<Int>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        size = intent.getIntExtra("size", 3)
        mine = intent.getIntExtra("mine", 2)

        val grid = findViewById<GridLayout>(R.id.gridLayout)
        tvin = findViewById(R.id.tvStatus)

        grid.columnCount = size
        grid.rowCount = size

        gridl = Array(size) { Array(size) { Button(this) } }
        mines = Array(size) { Array(size) { false } }
        punkte = Array(size) { Array(size) { 0 } }

        fillmine(mine)
        check()

        for (i in 0 until size) {
            for (j in 0 until size) {
                val button = Button(this)
                button.textSize = 8f
                button.layoutParams = GridLayout.LayoutParams().apply {
                    width = 80
                    height = 80
                    setMargins(2, 2, 2, 2)
                }
                button.setBackgroundColor(Color.GRAY)
                button.setOnClickListener { reveal(i, j) }
                gridl[i][j] = button
                grid.addView(button)
            }
        }
    }

    fun fillmine(temp: Int) {
        repeat(temp) {
            var x: Int
            var y: Int
            do {
                x = Random.nextInt(size)
                y = Random.nextInt(size)
            } while (mines[x][y])
            mines[x][y] = true
        }
    }

    private fun reveal(x: Int, y: Int) {
        val button = gridl[x][y]

        if (mines[x][y]) {
            button.text = "💣"
            button.setBackgroundColor(Color.RED)
            tvin.text = "Game Over!"
            Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show()
            disableAll()
        } else {
            val count = punkte[x][y]
            button.text = if (count > 0) "$count" else ""
            button.isEnabled = false
            button.setBackgroundColor(Color.LTGRAY)
        }
    }

    fun check() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (mines[i][j]) continue

                var count = 0

                for (dx in -1..1) {
                    for (dy in -1..1) {
                        val nx = i + dx
                        val ny = j + dy

                        if (nx in 0 until size && ny in 0 until size) {
                            if (mines[nx][ny]) count++
                        }
                    }
                }

                punkte[i][j] = count
            }
        }
    }

    fun disableAll() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                val btn = gridl[i][j]
                if (mines[i][j]) {
                    btn.text = "💣"
                } else {
                    val count = punkte[i][j]
                    btn.text = if (count > 0) "$count" else ""
                }
                btn.isEnabled = false
            }
        }
    }
}
