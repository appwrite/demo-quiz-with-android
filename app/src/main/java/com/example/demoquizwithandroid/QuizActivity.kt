package com.example.demoquizwithandroid

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider

class QuizActivity : AppCompatActivity() {
    private lateinit var viewModel: QuizViewModel
    private var currentQuestion : QuestionModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        viewModel.create(this)
        viewModel.questions.observe(this) { questions ->
            if (questions != null) {
                viewModel.selectedQuestion.observe(this) { index ->
                    findViewById<TextView>(R.id.questionNumber).text =
                        "Question ${(index + 1)}/${questions.size}"
                    currentQuestion = questions[index]

                    val options = currentQuestion!!.options
                    findViewById<TextView>(R.id.question).text =
                        currentQuestion!!.question
                    findViewById<RadioButton>(R.id.option1).text = options[0]
                    findViewById<RadioButton>(R.id.option2).text = options[1]
                    val option3 = findViewById<RadioButton>(R.id.option3)
                    val option4 = findViewById<RadioButton>(R.id.option4)
                    if (options.size > 2) {
                        option4.text = options[3]
                        option3.text = options[2]
                        option3.visibility = View.VISIBLE
                        option4.visibility = View.VISIBLE
                    } else {
                        option3.visibility = View.GONE
                        option4.visibility = View.GONE

                    }
                }
            }
        }
        findViewById<Button>(R.id.btnNext).setOnClickListener {
            _ -> onNextClicked()
        }
    }

    private fun onNextClicked() {
        if(currentQuestion == null) return
        val rbGroup = findViewById<RadioGroup>(R.id.options)
        val rbSelected : RadioButton = findViewById(rbGroup.checkedRadioButtonId) ?: return
        val ans: String = rbSelected.text.toString()
        if (ans == currentQuestion!!.answer) {
            viewModel.correct.postValue(viewModel.correct.value?.inc())
        }
        rbGroup.clearCheck()
        if(viewModel.selectedQuestion.value!! >= (viewModel.questions.value!!.size - 1)) {
            //show complete dialog
            Log.d("Quiz complete","Quiz complete")
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Quiz complete")
            alertDialog.setMessage("Score: ${viewModel.correct.value!!} / ${viewModel.questions.value!!.size}")
            alertDialog.setNeutralButton("Done", DialogInterface.OnClickListener { _, _ ->
                val intent = Intent(this,MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            })
            alertDialog.show()
        } else {
            viewModel.selectedQuestion.postValue(viewModel.selectedQuestion.value?.inc())
        }
    }
}