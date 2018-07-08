package com.example.android.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    int rightAnswer = 0;
    @BindView(R.id.radioGroup1) RadioGroup getRadioGroup1;
    @BindView(R.id.radioGroup2) RadioGroup getRadioGroup2;
    @BindView(R.id.radioGroup3) RadioGroup getRadioGroup3;
    @BindView(R.id.radiobutton_type1) RadioButton radioButton1;
    @BindView(R.id.radiobutton_type5) RadioButton radioButton5;
    @BindView(R.id.radiobutton_type8) RadioButton radioButton8;
    @BindView(R.id.checkbox_type1) CheckBox checkBox1;
    @BindView(R.id.checkbox_type2) CheckBox checkBox2;
    @BindView(R.id.checkbox_type3) CheckBox checkBox3;
    @BindView(R.id.submitButton) Button submitButton;
    @BindView(R.id.resetButton) Button resetButton;
    @BindView(R.id.inputAnswer) EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    // method to calculate correct answer from given questions. Increment counter to 1 for every correct answer

    public void submitAnswer(View v) {

        if (radioButton1.isChecked()) {
            rightAnswer++;
        }

        if (radioButton5.isChecked()) {
            rightAnswer++;
        }

        if (radioButton8.isChecked()) {
            rightAnswer++;
        }

        if (checkBox1.isChecked() && checkBox3.isChecked() && !checkBox2.isChecked()) {
            rightAnswer++;
        }

        Editable answerEditable = editText.getText();
        String answer = answerEditable.toString();
        if(answer.equalsIgnoreCase(("Carnivore"))) {
            rightAnswer++;
        }

        // method call to display the score as a toast message

        displayScore();
    }

    // method to fetch the entered user name and then display the total score as a toast message

    public void displayScore() {

        EditText userName = (EditText) findViewById(R.id.inputUserName);
        Editable nameEditable = userName.getText();
        String name = nameEditable.toString();

        Toast.makeText(getApplicationContext(), "Hey " + name + " Your total score is: " + rightAnswer + " Out of 5", Toast
            .LENGTH_SHORT)
            .show();

        rightAnswer = 0;  // resets the counter for next run
    }

    // method to reset all selections after tapping Reset button

    public void resetAnswer(View v) {

        getRadioGroup1.clearCheck();
        getRadioGroup2.clearCheck();
        getRadioGroup3.clearCheck();
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        editText.setText("");

    }
}
