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

/*  TODO someday:
    1. Persistence of the entered data in the first activity, when someone returns back from
    the second one using an arrow.
    2. Let the user enter subjects
 */

/*  User has to enter a name, surname and a number of grades to fill later.
    If all requested info is given, a button becomes visible.
    After clicking that button, another activity is invoked and the number
    of grades is passed to it.
    Later this activity gets calculated average from invoked activity, and a program finish is
    then available.
 */
public class MainActivity extends AppCompatActivity {

    // Prepared identifiers for layout widgets reference (set in onCreate())
    EditText nameEditText;
    EditText surnameEditText;
    EditText gradesNumEditText;
    TextView averageTextView;
    Button gradesButton;

    // String constant keys for saving instance state in a Bundle
    private static final String NAME_TEXT = "nameText";
    private static final String SURNAME_TEXT = "surnameText";
    public static final String GRADES_NUM = "gradesNum";
    private static final String AVERAGE_TEXT = "averageText";
    private static final String BUTTON_VISIB = "buttonVisib";

    public static final int GRADE_ACTIVITY_CODE = 1;
    public static final String RESULT_AVERAGE = "average";

    public static final String IS_AVERAGE_VISIBLE = "avrVis";
    public static boolean isAverageVisible = false;

    public static final String PASS_INFO = "passInfo";
    private static final boolean PASSED = true;
    private static final boolean NOT_PASSED = false;

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
        averageTextView = (TextView) findViewById(R.id.averageTextView);
        gradesButton = (Button) findViewById(R.id.gradeButton);

        restoreState(savedInstanceState);

        setOnFocusChangeListeners();        // Input validation end alerts
        setTextChangedListeners();          // Input validation and button show
        setButtonOnClickListener();         // Starting new Activity
    }

    void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {   // try to restore data
            nameEditText.setText(savedInstanceState.getString(NAME_TEXT));
            surnameEditText.setText(savedInstanceState.getString(SURNAME_TEXT));
            gradesNumEditText.setText(savedInstanceState.getString(GRADES_NUM));
            averageTextView.setText((savedInstanceState.getString(AVERAGE_TEXT)));
            if (savedInstanceState.getBoolean(IS_AVERAGE_VISIBLE)) {
                averageTextView.setVisibility(View.VISIBLE);
            }
            gradesButton.setVisibility(savedInstanceState.getInt(BUTTON_VISIB));
        }
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
        gradesButton.setOnClickListener((View v) -> {

            Intent intent = new Intent(MainActivity.this, GradeActivity.class);
            intent.putExtra(GRADES_NUM, Integer.parseInt(gradesNumEditText.getText().toString()));

            // We want pass or fail info back
            startActivityForResult(intent, GRADE_ACTIVITY_CODE);
        });
    }

    // Saves temporary data if for example app view will rotate on a screen
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(NAME_TEXT, nameEditText.getText().toString());
        outState.putString(SURNAME_TEXT, surnameEditText.getText().toString());
        outState.putString(GRADES_NUM, gradesNumEditText.getText().toString());
        outState.putString(AVERAGE_TEXT, averageTextView.getText().toString());
        outState.putBoolean(IS_AVERAGE_VISIBLE, isAverageVisible);
        outState.putInt(BUTTON_VISIB, gradesButton.getVisibility());
    }

    // For now, that method gets the calculated average from the second activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            double average = data.getDoubleExtra(RESULT_AVERAGE, 0);

            averageTextView = findViewById(R.id.averageTextView);
            averageTextView.append(String.format(" %.2f", average));
            averageTextView.setVisibility(View.VISIBLE);
            isAverageVisible = true;

            setButtonTextAndAction(average);
        }
        else if (resultCode == RESULT_CANCELED) {
            this.finish();
        }
    }

    // Invokes final activity with proper result message
    private void setButtonTextAndAction(double average) {

        if (average > 2) {
            gradesButton.setText(R.string.final_success_button_text);
            setFinalOnClick(PASSED);
        }
        else {
            gradesButton.setText(R.string.final_fail_button_text);
            setFinalOnClick(NOT_PASSED);
        }
    }

    private void setFinalOnClick(boolean isPassed) {
        gradesButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, FinishActivity.class);
            intent.putExtra(PASS_INFO, isPassed);
            startActivityForResult(intent, RESULT_CANCELED);
        });
    }
}