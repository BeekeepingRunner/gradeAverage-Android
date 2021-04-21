package com.example.gradeaverage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

// User has to enter a name, surname and a number of grades to fill later.
// If all requested info is given, a button becomes visible.
// After clicking that button, another activity is invoked and the number
// of grades is passed to it.
public class MainActivity extends AppCompatActivity {

    // Prepared identifiers for layout widgets reference (set in onCreate())
    EditText nameEditText;
    EditText surnameEditText;
    EditText gradesNumEditText;
    Button gradesButton;

    // String constant keys for saving instance state in a Bundle
    private static final String NAME_TEXT = "nameText";
    private static final String SURNAME_TEXT = "surnameText";
    public static final String GRADES_NUM = "gradesNum";
    private static final String AVERAGE_TEXT = "averageText";
    private static final String BUTTON_VISIB = "buttonVisib";

    public static final int GRADE_ACTIVITY_CODE = 1;    // TODO: is it a good solution?
    public static final String RESULT_AVERAGE = "average";

    // Sets layout,
    // gets references to the layout widgets,
    // tries to restore saved instance state
    // and sets appropriate listeners.
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_constraint);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        gradesNumEditText = (EditText) findViewById(R.id.gradeNumEditText);
        gradesButton = (Button) findViewById(R.id.gradeButton);

        if (savedInstanceState != null) {   // try to restore data
            nameEditText.setText(savedInstanceState.getString(NAME_TEXT));
            surnameEditText.setText(savedInstanceState.getString(SURNAME_TEXT));
            gradesNumEditText.setText(savedInstanceState.getString(GRADES_NUM));
            gradesButton.setVisibility(savedInstanceState.getInt(BUTTON_VISIB));
        }

        setOnFocusChangeListeners();        // Input validation end alerts
        setTextChangedListeners();          // Input validation and button show
        setButtonOnClickListener();         // Starting new Activity
    }

    // Showing input errors on focus change
    void setOnFocusChangeListeners() {

        nameEditText.setOnFocusChangeListener((View v, boolean hasFocus) -> {
            if (!hasFocus) {
                if (nameEditText.getText().length() == 0)
                    nameEditText.setError(getString(R.string.emptyNameError));
            }
        });

        surnameEditText.setOnFocusChangeListener((v, hasFocus) -> {

            if (!hasFocus) {
                if (surnameEditText.getText().length() == 0) {
                    surnameEditText.setError(getString(R.string.emptySurnameError));
                }
            }
        });

        gradesNumEditText.setOnFocusChangeListener((v, hasFocus) -> {

            if (!hasFocus && !isGradeNumCorrect()) {
                Toast.makeText(MainActivity.this, R.string.badGradeNum, Toast.LENGTH_LONG).show();
            }
        });
    }

    private class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setButtonVisibility();
        }

        @Override
        public void afterTextChanged(Editable s) { }
    }

    // On text change - checks input and sets button's visibility
    void setTextChangedListeners() {

        nameEditText.addTextChangedListener(new MyTextWatcher());
        surnameEditText.addTextChangedListener(new MyTextWatcher());
        gradesNumEditText.addTextChangedListener(new MyTextWatcher());
    }

    boolean isGradeNumCorrect() {

        if (gradesNumEditText.getText().length() != 0) {     // if user provided any number

            int gradesNum = Integer.parseInt(gradesNumEditText.getText().toString());
            if (5 <= gradesNum && gradesNum <= 15) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    void setButtonVisibility() {

        if (isDataCorrect()) {
            gradesButton.setVisibility(View.VISIBLE);
        } else {
            gradesButton.setVisibility(View.INVISIBLE);
        }
    }

    boolean isDataCorrect() {

        if (nameEditText.getText().length() != 0
        && surnameEditText.getText().length() != 0
        && isGradeNumCorrect()) {
            return true;
        } else {
            return false;
        }
    }

    // Evokes new Activity and passes the number of grades to it.
    void setButtonOnClickListener() {
        gradesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, GradeActivity.class);

                intent.putExtra(GRADES_NUM, Integer.parseInt(gradesNumEditText.getText().toString()));

                // We want pass or fail info back
                startActivityForResult(intent, GRADE_ACTIVITY_CODE);
            }
        });
    }

    // Saves temporary data if for example app view will rotate on a screen
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(NAME_TEXT, nameEditText.getText().toString());
        outState.putString(SURNAME_TEXT, surnameEditText.getText().toString());
        outState.putString(GRADES_NUM, gradesNumEditText.getText().toString());
        outState.putInt(BUTTON_VISIB, gradesButton.getVisibility());
    }

    // For now, that method gets the calculated average from the second activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            double average = data.getDoubleExtra(RESULT_AVERAGE, 0);

            TextView averageTextView = (TextView) findViewById(R.id.averageTextView);
            averageTextView.append(String.format(" %.2f", average));
            averageTextView.setVisibility(View.VISIBLE);

            setButtonTextAndAction(average);
        }
    }

    private void setButtonTextAndAction(double average) {

        if (average > 2) {
            gradesButton.setText(R.string.final_success_button_text);
            // set onClick()...
        }
        else {
            gradesButton.setText(R.string.final_fail_button_text);
            // set onClick()...
        }
    }
}