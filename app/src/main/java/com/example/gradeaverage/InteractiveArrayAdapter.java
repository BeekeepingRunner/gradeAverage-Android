package com.example.gradeaverage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InteractiveArrayAdapter extends RecyclerView.Adapter<InteractiveArrayAdapter.GradesViewHolder> {

    private List<Grade> gradeList;
    private LayoutInflater layoutInflater;

    // Every row has its own ViewHolder
    class GradesViewHolder extends RecyclerView.ViewHolder
            implements RadioGroup.OnCheckedChangeListener {

        TextView subjectNameTextView;
        RadioGroup radioGroup;
        RadioButton radioButton2;
        RadioButton radioButton3;
        RadioButton radioButton4;
        RadioButton radioButton5;

        public GradesViewHolder(@NonNull View mainRowElement) {
            super(mainRowElement);

            subjectNameTextView = mainRowElement.findViewById(R.id.subjectName);
            radioGroup = mainRowElement.findViewById(R.id.radioGroup);
            radioButton2 = mainRowElement.findViewById(R.id.radioButton2);
            radioButton3 = mainRowElement.findViewById(R.id.radioButton3);
            radioButton4 = mainRowElement.findViewById(R.id.radioButton4);
            radioButton5 = mainRowElement.findViewById(R.id.radioButton5);

            radioGroup.setOnCheckedChangeListener(this);
            // ...?
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            // todo
        }
    }

    public InteractiveArrayAdapter(Activity context, List<Grade> grades) {
        layoutInflater = context.getLayoutInflater();
        this.gradeList = grades;
    }

    // Called every time a row is created
    @NonNull
    @Override
    public GradesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = layoutInflater.inflate(R.layout.list_row, null);
        return new GradesViewHolder(row);
    }

    // Called every time when a new row has to be displayed
    @Override
    public void onBindViewHolder(@NonNull GradesViewHolder holder, int gradePosition) {

        Grade grade = gradeList.get(gradePosition);
        holder.subjectNameTextView.setText(grade.getName());

        // zaznaczenie odpowiedniego przycisku radiowego (?)
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }
}
