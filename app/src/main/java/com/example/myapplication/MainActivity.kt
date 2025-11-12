package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnEasy).setOnClickListener {
            startGame(3, 2)
        }

        findViewById<Button>(R.id.btnMedium).setOnClickListener {
            startGame(10, 10)
        }

        findViewById<Button>(R.id.btnHard).setOnClickListener {
            startGame(15, 30)
        }
    }

    private fun startGame(size: Int, mines: Int) {
        val intent = Intent(this, Game2::class.java)
        intent.putExtra("size", size)
        intent.putExtra("mine", mines)
        startActivity(intent)
    }
}
