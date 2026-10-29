package com.jarvis.android

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this).apply {
            text = "JARVIS\n\nBonjour. Je suis prêt."
            textSize = 28f
            setPadding(40, 80, 40, 40)
        }

        setContentView(textView)
    }
}
