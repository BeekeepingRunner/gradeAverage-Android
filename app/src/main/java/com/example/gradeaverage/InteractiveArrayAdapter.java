package com.example.gradeaverage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Binds RecyclerView (which represents data in a List format)
// and Data source (List of grades)
public class InteractiveArrayAdapter extends RecyclerView.Adapter<InteractiveArrayAdapter.GradesViewHolder> {

    private List<Grade> gradeList;
    private LayoutInflater layoutInflater;

    // Every row has its own ViewHolder
    class GradesViewHolder extends RecyclerView.ViewHolder
            implements RadioGroup.OnCheckedChangeListener {

        // Future references to the elements of the row
        TextView subjectNameTextView;
        RadioGroup radioGroup;
        RadioButton radioButton2;
        RadioButton radioButton3;
        RadioButton radioButton4;
        RadioButton radioButton5;

        // Gets references and sets listeners
        public GradesViewHolder(@NonNull View mainRowElement) {
            super(mainRowElement);

            subjectNameTextView = mainRowElement.findViewById(R.id.subjectName);
            radioGroup = mainRowElement.findViewById(R.id.radioGroup);
            radioButton2 = mainRowElement.findViewById(R.id.radioButton2);
            radioButton3 = mainRowElement.findViewById(R.id.radioButton3);
            radioButton4 = mainRowElement.findViewById(R.id.radioButton4);
            radioButton5 = mainRowElement.findViewById(R.id.radioButton5);

            radioGroup.setOnCheckedChangeListener(this);
        }

        // When given radioButton becomes checked, sets appropriate value of the grade in the List.
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            int position = (Integer) group.getTag();

            switch (checkedId)
            {
                case R.id.radioButton2:
                    gradeList.get(position).setGrade(2);
                    break;
                case R.id.radioButton3:
                    gradeList.get(position).setGrade(3);
                    break;
                case R.id.radioButton4:
                    gradeList.get(position).setGrade(4);
                    break;
                case R.id.radioButton5:
                    gradeList.get(position).setGrade(5);
                    break;
            }
        }
    }

    public InteractiveArrayAdapter(Activity context, List<Grade> grades) {
        layoutInflater = context.getLayoutInflater();
        this.gradeList = grades;
    }

    // Creates main layout element and returns ViewHolder for each created row.
    @NonNull
    @Override
    public GradesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = layoutInflater.inflate(R.layout.list_row, null);
        return new GradesViewHolder(row);
    }

    // Fills each row with data from source, every time a row has to be displayed
    @Override
    public void onBindViewHolder(@NonNull GradesViewHolder holder, int gradePosition) {

        Grade grade = gradeList.get(gradePosition);
        holder.subjectNameTextView.setText(grade.getName());

        // We have to tag radioGroup in each row with proper grade index from data source
        // to reference it later, when we'll want to change it.
        holder.radioGroup.setTag(gradePosition);

        // Check proper button
        int rowGrade = gradeList.get(gradePosition).getGrade();
        switch (rowGrade) {
            case 3:
                holder.radioGroup.check(R.id.radioButton3);
                break;
            case 4:
                holder.radioGroup.check(R.id.radioButton4);
                break;
            case 5:
                holder.radioGroup.check(R.id.radioButton5);
                break;
            case 2:
            default:
                holder.radioGroup.check(R.id.radioButton2);
        }
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }
}
