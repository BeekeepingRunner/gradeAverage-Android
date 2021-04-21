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

public class InteractiveArrayAdapter extends RecyclerView.Adapter {

    private List<Grade> gradeList;
    private LayoutInflater layoutInflater;

    // Every row has its own ViewHolder
    class GradesViewHolder extends RecyclerView.ViewHolder
            implements RadioGroup.OnCheckedChangeListener {

        TextView subjectName;
        RadioGroup radioGroup;
        RadioButton radioButton2;
        RadioButton radioButton3;
        RadioButton radioButton4;
        RadioButton radioButton5;

        public GradesViewHolder(@NonNull View mainRowElement) {
            super(mainRowElement);

            subjectName = mainRowElement.findViewById(R.id.subjectName);
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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = layoutInflater.inflate(R.layout.list_row, null);
        return new GradesViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int gradeNum) {

        // powiazanie grupy przyciskow radiowych z wierszem listy
        // ...
        Grade grade = gradeList.get(gradeNum);


        // ustawienie nazwy przedmiotu
        // ...
        // zaznaczenie odpowiedniego przycisku radiowego
        // ...
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }
}
