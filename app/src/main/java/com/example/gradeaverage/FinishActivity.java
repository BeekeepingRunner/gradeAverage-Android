package com.example.gradeaverage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {

    TextView finalPromptTextView;
    Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        finalPromptTextView = (TextView) findViewById(R.id.finalPromptTextView);
        finishButton = (Button) findViewById(R.id.finishButton);

        Bundle passedData = getIntent().getExtras();
        boolean gotPass = passedData.getBoolean(MainActivity.PASS_INFO);

        if (gotPass) {
            finalPromptTextView.setText(R.string.final_prompt_text_success);
            finishButton.setText(R.string.finish);
            setFinalOnClick();
        }
        else {
            finalPromptTextView.setText(R.string.final_prompt_text_fail);
            finishButton.setText(R.string.finish);
            setFinalOnClick();
        }
    }

    void setFinalOnClick() {
        finishButton.setOnClickListener((View v) -> {
            closeProgram();
        });
    }

    void closeProgram() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}