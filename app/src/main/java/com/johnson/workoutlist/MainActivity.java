package com.johnson.workoutlist;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    protected JSONArray workoutList;

    private LinearLayout verticalLayout;

    Button restoreDefaultButton;

    protected Workout edited_workout;

    static protected String FILENAME = "workouts.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i = getIntent();

        String edited_workout_json_string = i.getStringExtra("workout");
        if(edited_workout_json_string !=null )
        {
            edited_workout = new Workout(edited_workout_json_string);
        }

        //Try to load the saved workouts file. If it does not exist, load the default json file from the assets folder
        if (!loadSavedWorkoutList(this)) {
            loadDefaultWorkoutList();
        }

        restoreDefaultButton = findViewById(R.id.restore_defaults_button);

        //If we have come back from the the Edit Workout activity save button, save the new changes
        if(edited_workout != null)
        {
            saveWorkoutsToFile(this);
            restoreDefaultButton.setVisibility(VISIBLE);
        }
        else
        {
            restoreDefaultButton.setVisibility(GONE);
        }
    }

    //A button to clear all saved data and load the original JSON asset is here mainly to make sure this app still works without any save file
    //A more user friendly version would include an "are you sure?" dialogue check
    public void restoreDefaultWorkouts(View v){
        File file = new File(this.getFilesDir(), FILENAME);
        if(file.delete()) {
            verticalLayout = findViewById(R.id.vertical_layout);
            verticalLayout.removeAllViews();
            edited_workout = null;
            loadDefaultWorkoutList();
            restoreDefaultButton = (Button) v;
            restoreDefaultButton.setVisibility(GONE);
        }
        else
        {
           Log.e("WorkoutLog", "MainActivity.restoreDefaultWorkouts Unable to delete "+this.getFilesDir().toString() + FILENAME);
        }

    }

    //Load the workouts.json file from the save folder created for this context
    //if the file DNE, return false
    private boolean loadSavedWorkoutList(Context context)
    {
        File file = new File(context.getFilesDir(), FILENAME);

        if( !file.exists() ) {
            Log.d("Workout", "MainActivity.loadSavedWorkoutList: FILE DOES NOT EXIST");
            return false;
        }
        Log.d("Workout", "MainActivity.loadSavedWorkoutList: FILE EXISTS!");

        try {
            workoutList = new JSONArray(getJsonStringFromFile(file));
            readWorkoutList();

        } catch (Exception e) {
            Log.e("WorkoutLog", "MainActivity.loadSavedWorkoutList: error " + e);
        }
        return true;
    }

    //This was moved to a static function at the suggestion of Android Studio.
    //It could be simplified with the use of a more modern method of reading the file into a whole string at once
    @NonNull
    protected static String getJsonStringFromFile(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    //Load the workouts.json file from the assets folder
    protected void loadDefaultWorkoutList() {
        try {
            InputStream inputStream = getAssets().open(FILENAME);
            int ioStreamSize = inputStream.available();
            byte[] buffer = new byte[ioStreamSize];
            int readResult = inputStream.read(buffer);
            inputStream.close();

            if (readResult == 0) {
                Log.e("WorkoutLog", "loadDefaultWorkoutList: JSON file was empty");
            }

            String  json = new String(buffer, StandardCharsets.UTF_8);
            workoutList = new JSONArray(json);

            readWorkoutList();

        }
        catch(IOException | JSONException e) {
            Log.e("WorkoutLog", "loadDefaultWorkoutList: error " + e);
        }
    }

    //Reads through the workoutList JSONArray and sends each object to AddWorkoutView
    //If the current object has the same ID as edited_id, first replace its data with the edited data
    //That we stored from the intent, when coming back from the EditWorkout activity
    protected void readWorkoutList()
    {
        try {
            for (int i = 0; i < workoutList.length(); i++) {
                Workout workout = new Workout(workoutList.getJSONObject(i));

                //Replace the edited workout in the workoutList
                if(edited_workout != null && workout.id.equals(edited_workout.id))
                {
                    workout = edited_workout;
                    workoutList.put(i, workout.toJSONObject());
                }

                addWorkoutView(workout);
            }
        } catch(JSONException e) {
            Log.e("WorkoutLog", "readWorkoutList: error " + e);
        }
    }

    //Takes a Workout and creates a button for it, adding it to the layout
    //The buttons is given an On Click behavior to go to the EditWorkout Activity with the workout's json string added to the intent
    protected void addWorkoutView(Workout workout)
    {
        verticalLayout = findViewById(R.id.vertical_layout);

        @SuppressLint("PrivateResource") Button newButton = new Button(new ContextThemeWrapper(this, com.google.android.material.R.style.Base_Widget_AppCompat_Button), null, 0);

        newButton.setText(workout.name);
        newButton.setAllCaps(false);

        newButton.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, EditWorkout.class);
            i.putExtra("workout", workout.toJSONString());
            startActivity(i);
        });
        verticalLayout.addView(newButton);
    }

    //Writes the workoutList JSONArray to a local file, as a compacted JSON String
    protected void saveWorkoutsToFile(Context context)
    {
        try {
            File file = new File(context.getFilesDir(), FILENAME);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(workoutList.toString());
            fileWriter.close();
        } catch (Exception e) {
            Log.e("WorkoutLog", "saveWorkoutsToFile: error " + e);
        }
    }
}