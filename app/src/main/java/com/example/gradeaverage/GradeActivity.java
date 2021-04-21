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


public class GradeActivity extends AppCompatActivity {

    ArrayList<Grade> grades = new ArrayList<Grade>();
    RecyclerView gradesRecView;
    InteractiveArrayAdapter adapter;

    Button calculationButton;
    int gradesNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        calculationButton = (Button) findViewById(R.id.calculationButton);

        // Get the number of grades
        Bundle passedData = getIntent().getExtras();
        gradesNum = passedData.getInt(MainActivity.GRADES_NUM);

        // Fill arrayList with subject names and default grades
        fillGrades(gradesNum);

        // Create adapter and set it in a RecyclerView
        gradesRecView = (RecyclerView) findViewById(R.id.gradeRecyclerView);
        adapter = new InteractiveArrayAdapter(this, grades);
        gradesRecView.setAdapter(adapter);
        gradesRecView.setLayoutManager(new LinearLayoutManager(this));

        // Set final button's onClick()
        calculationButton.setOnClickListener((View v) -> {

            if (gradesFilled()) {
                double average = calculateAverage();
                // double average = (double) this.gradesNum;
                returnAverage(average);
            }
        });
    }

    private static final int DEFAULT_GRADE = 2;
    // Fills arrayList with subject names and default grades
    void fillGrades(int gradesNum) {

        grades = new ArrayList<Grade>();
        Resources res = getResources();
        String[] subjects = res.getStringArray(R.array.subjects);

        for (int i = 0; i < gradesNum; ++i) {
            grades.add(new Grade(subjects[i], DEFAULT_GRADE));
        }
    }

    boolean gradesFilled() {
        // TODO
        return true;
    }

    double calculateAverage() {
        int sum = 0;
        for (Grade grade : grades) {
            sum += grade.getGrade();
        }

        return (double)sum / (double)grades.size();
    }

    void returnAverage(double average) {

        // Bundle bundle = new Bundle();
        // bundle.putDouble("average", average);

        Intent intent = new Intent();
        intent.putExtra(MainActivity.RESULT_AVERAGE, average);
        setResult(RESULT_OK, intent);
        finish();
    }
}