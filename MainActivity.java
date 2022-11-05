package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvQuestion, tvScore, tvQuestionNo;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button btnNext;

    int totalQuestions;
    int qCounter = 0;
    int score;
    int currentQuestion = 1;

    ColorStateList dfRbColor;
    boolean answered;


    private QuestionModel currentQuestions;

    private List<QuestionModel> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionsList = new ArrayList<>();
        tvQuestion = findViewById(R.id.textQuestion);
        tvScore = findViewById(R.id.textScore);
        tvQuestionNo = findViewById(R.id.textQuestionNo);

        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        btnNext = findViewById(R.id.btnNext);

        dfRbColor = rb1.getTextColors();

        addQuestions();
        totalQuestions = questionsList.size();
        showNextQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answered == false) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(MainActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private void checkAnswer() {
        answered = true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rbSelected) + 1;
        if (answerNo == currentQuestions.getCorrectAnsNo()) {
            score++;
            tvScore.setText("Score: " + score);
        }
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);
        switch (currentQuestions.getCorrectAnsNo()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                break;
        }
        if (qCounter < totalQuestions) {
            btnNext.setText("Next");
        } else {
            btnNext.setText("Finish");
        }
    }

    private void showNextQuestion() {
        radioGroup.clearCheck();
        rb1.setTextColor(dfRbColor);
        rb2.setTextColor(dfRbColor);
        rb3.setTextColor(dfRbColor);
        rb4.setTextColor(dfRbColor);

        if (qCounter < totalQuestions) {

            currentQuestions = questionsList.get(qCounter);
            tvQuestion.setText(currentQuestions.getQuestion());
            rb1.setText(currentQuestions.getOption1());
            rb2.setText(currentQuestions.getOption2());
            rb3.setText(currentQuestions.getOption3());
            rb4.setText(currentQuestions.getOption4());

            qCounter++;
            btnNext.setText("Submit");
            tvQuestionNo.setText("Question: " + qCounter + "/" + totalQuestions);
            answered = false;
            return;
        } else {
            finishQuiz();
        }
    }

    private void addQuestions() {
        questionsList.add(new QuestionModel("The Ozone Day is observed in memory of signing of which of the following protocols?", "Montreal Protocol", "Geneva Protocol", "Kyoto Protocol", "Nagoya Protocol", 1));
        questionsList.add(new QuestionModel("The Sepahijala Wildlife Sanctuary(SWS) is located in which State?", "Odisha", "Kerala", "Tripura", "Manipur", 3));
        questionsList.add(new QuestionModel("Sir Thomas Stamford Raffles is known to be a founder of which of the following modern nations?", "Vatican City", "Singapore", "Cyprus", "Newzealand", 2));
        questionsList.add(new QuestionModel("Which planet is known as'Bright Wandering Star'?", "Mars", "Venus", "Saturn", "Jupiter", 4));
        questionsList.add(new QuestionModel("The Khadakwasla Dam is located in which state?", "Punjab", "Maharastra", "Odisha", "Karnataka", 2));
    }

    void finishQuiz(){
        String passStatus="";
        if(score > totalQuestions*0.60){
            passStatus = "Passed";
        }
        else{
            passStatus = "Failed";
        }
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is "+score+" out of "+totalQuestions)
                        .setCancelable(false)
                        .show();
    }

}