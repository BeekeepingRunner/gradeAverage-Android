package com.example.gradeaverage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/*  User checks appropriate radioButtons to enter grades for each subject.
    Based on input, an average grade is calculated and passed back to the main activity.
 */
public class GradeActivity extends AppCompatActivity {

    ArrayList<Grade> grades = new ArrayList<Grade>();
    RecyclerView gradesRecView;         // Represents data in a List format
    InteractiveArrayAdapter adapter;    // Binds RecyclerView with data source (ArrayList of grades)

    Button calculationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        fillGradeListWithInitialData(getGradesNum());
        setRecyclerViewAndAdapter();
        setCalculationButton();
    }

    int getGradesNum() {
        Bundle passedData = getIntent().getExtras();
        return passedData.getInt(MainActivity.GRADES_NUM);
    }

    void setRecyclerViewAndAdapter() {
        gradesRecView = (RecyclerView) findViewById(R.id.gradeRecyclerView);
        adapter = new InteractiveArrayAdapter(this, grades);
        gradesRecView.setAdapter(adapter);
        gradesRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    void setCalculationButton() {
        calculationButton = (Button) findViewById(R.id.calculationButton);
        calculationButton.setText(R.string.sendGradesButton);
        calculationButton.setOnClickListener((View v) -> {

            returnAverage(calculateAverage());
        });
    }

    private static final int DEFAULT_GRADE = 2;
    // Fills arrayList with subject names and default grades
    void fillGradeListWithInitialData(int gradesNum) {

        grades = new ArrayList<Grade>();
        Resources res = getResources();
        String[] subjects = res.getStringArray(R.array.subjects);

        for (int i = 0; i < gradesNum; ++i) {
            grades.add(new Grade(subjects[i], DEFAULT_GRADE));
        }
    }

    double calculateAverage() {
        int sum = 0;
        for (Grade grade : grades) {
            sum += grade.getGrade();
        }

        return (double)sum / (double)grades.size();
    }

    // Pass calculated average back to the main activity
    void returnAverage(double average) {

        Intent intent = new Intent();
        intent.putExtra(MainActivity.RESULT_AVERAGE, average);
        setResult(RESULT_OK, intent);
        finish();
    }
}