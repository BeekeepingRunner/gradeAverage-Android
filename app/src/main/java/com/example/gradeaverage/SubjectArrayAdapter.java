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

import javax.security.auth.Subject;

// Binds RecyclerView (which represents data in a List format)
// and Data source (List of grades)
public class SubjectArrayAdapter extends RecyclerView.Adapter<SubjectArrayAdapter.SubjectsViewHolder> {

    private List<String> subjects;
    private LayoutInflater layoutInflater;

    // Every row has its own ViewHolder
    class SubjectsViewHolder extends RecyclerView.ViewHolder {

        // Future references to the elements of the row
        TextView subjectPosition;


        // Gets references and sets listeners
        public SubjectsViewHolder(@NonNull View mainRowElement) {
            super(mainRowElement);

            // subjectNameTextView = mainRowElement.findViewById(R.id.subjectName);

        }
    }

    public SubjectArrayAdapter(Activity context, List<String> subjects) {
        layoutInflater = context.getLayoutInflater();
        this.subjects = subjects;
    }

    // Creates main layout element and returns ViewHolder for each created row.
    @NonNull
    @Override
    public SubjectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = layoutInflater.inflate(R.layout.list_row, null);
        return new SubjectsViewHolder(row);
    }

    // Fills each row with data from source, every time a row has to be displayed
    @Override
    public void onBindViewHolder(@NonNull SubjectsViewHolder holder, int subjectPosition) {

        String subject = subjects.get(subjectPosition);
        /*
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

         */
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
