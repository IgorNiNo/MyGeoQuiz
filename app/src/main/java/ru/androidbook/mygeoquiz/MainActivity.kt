package ru.androidbook.mygeoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.view.Gravity
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

        trueButton.setOnClickListener {
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show()
//            val toastTrue = Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT)    //Не работает, с версии api 30 и выше
//            toastTrue.setGravity(Gravity.TOP, 0, 0)
//            toastTrue.show()
        }

        falseButton.setOnClickListener {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
//            val toastFalse = Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT) //Не работает, с версии api 30 и выше
//            toastFalse.setGravity(Gravity.TOP, 0, 0)
//            toastFalse.show()
        }
    }
}