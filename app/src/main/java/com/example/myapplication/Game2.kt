package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Grid
import kotlin.random.Random
import android.graphics.Color

class Game2 : AppCompatActivity() {

    private var mine = 2
    private var size= 3

    private lateinit var tvin : TextView
    private lateinit var gridl : Array<Array<Button>>
    private lateinit var mines : Array<Array<Boolean>>

    private lateinit var punkte : Array<Array<Int>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        size = intent.getIntExtra("size",3);
        mine = intent.getIntExtra("mine",2);

        var grid = findViewById<GridLayout>(R.id.gridLayout)
        var tvin= findViewById<TextView>(R.id.tvStatus)

        grid.columnCount = size
        grid.rowCount = size

        gridl = Array(size){Array(size){Button(this) }}
        mines = Array(size) { Array(size) { false } }
        fillmine(mine)

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

    fun fillmine(temp : Int){
        repeat(temp){
            var x=0
            var y=0
            do {
                x = Random.nextInt(size);
                y = Random.nextInt(size);
            }while (mines[x][y])
            mines[x][y]=true
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
            val count = countAdjacentMines(x, y)
            button.text = if (count > 0) "$count" else ""
            button.isEnabled = false
            button.setBackgroundColor(Color.LTGRAY)
        }
    }

    fun check(x: Int,y: Int){

    }
}

















