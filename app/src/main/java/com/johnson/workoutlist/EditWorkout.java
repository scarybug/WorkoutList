package com.johnson.workoutlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditWorkout extends AppCompatActivity {

    Workout current_workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_workout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i = getIntent();
        current_workout = new Workout(i.getStringExtra("workout"));

        getNameView().setText(current_workout.getName());
        getDurationView().setText(current_workout.getDurationFormattedString(getString(R.string.duration_units)));

        //If the data does not include equipment or difficulty, leave the TextViews alone with their default text
        if(current_workout.hasEquipment())
        {
            getEquipmentView().setText(current_workout.getEquipment());
        }
        if(current_workout.hasDifficulty()) {
            getDifficultyView().setText(current_workout.getDifficulty());
        }
    }

    public void SaveChanges(View v)
    {
        current_workout.name = getNameView().getText().toString();
        current_workout.setDurationFromFormattedString(getDurationView().getText().toString());

        String equipment_view_string = getEquipmentView().getText().toString();
        if(!getString(R.string.equipment_default).equals(equipment_view_string))
        {
            current_workout.equipment = getEquipmentView().getText().toString();
        }

        String difficulty_view_string = getDifficultyView().getText().toString();
        if(!getString(R.string.difficulty_default).equals(difficulty_view_string))
        {
            current_workout.difficulty = difficulty_view_string;
        }

        Intent i = new Intent(EditWorkout.this, MainActivity.class);
        i.putExtra("workout", current_workout.toJSONString());
        startActivity(i);
    }

    protected TextView  getNameView()
    {
        return  findViewById(R.id.workout_name);
    }
    protected TextView  getDurationView()
    {
        return  findViewById(R.id.duration_value);
    }
    protected TextView  getEquipmentView()
    {
        return  findViewById(R.id.equipment_value);
    }
    protected TextView  getDifficultyView()
    {
        return  findViewById(R.id.difficulty_value);
    }

}