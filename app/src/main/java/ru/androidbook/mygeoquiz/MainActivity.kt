package ru.androidbook.mygeoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val KEY_USEDANSWER = "usedAnswer"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex



        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_vew)

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
        val usAnsw = quizViewModel.usedAnswer.toBooleanArray()
        outState.putBooleanArray(KEY_USEDANSWER, usAnsw)
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        setEnabledButton()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            quizViewModel.numberCorrectAnswers = ++quizViewModel.numberCorrectAnswers
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        quizViewModel.numberAnswers = ++quizViewModel.numberAnswers
        quizViewModel.usedAnswer[quizViewModel.currentIndex] = true
        setEnabledButton()
        checkGameOver()
    }

    private fun setEnabledButton() {
        trueButton.isEnabled = !quizViewModel.usedAnswer[quizViewModel.currentIndex]
        falseButton.isEnabled = !quizViewModel.usedAnswer[quizViewModel.currentIndex]
//        trueButton.isEnabled = !usAnsw[quizViewModel.currentIndex]
//        falseButton.isEnabled = !usAnsw[quizViewModel.currentIndex]
    }

    private fun checkGameOver() {
        if (quizViewModel.numberAnswers == quizViewModel.questionBankSize) {
            val str = String.format(
                getString(R.string.number_correct_answer),
                quizViewModel.numberCorrectAnswers,
                quizViewModel.numberAnswers
            )
            questionTextView.setText(str)
        }
    }
}