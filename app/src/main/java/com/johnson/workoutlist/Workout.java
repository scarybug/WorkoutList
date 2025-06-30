package com.johnson.workoutlist;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class Workout {
    public String id;
    public String name;
    public int duration = 0;
    public String equipment;
    public String difficulty;

    protected boolean has_equipment = true;
    protected boolean has_difficulty = true;


    public Workout() {
        id = UUID.randomUUID().toString();
        name = "New Workout";
        duration = 0;
        has_equipment = false;
        has_difficulty = false;
    }

    public Workout(JSONObject jsonObject) {
        populateFromJSONObject(jsonObject);
    }

    public Workout(String jsonString) {
        populateFromJSONString(jsonString);
    }

    public void populateFromJSONObject(JSONObject jsonObject)
    {
        try {
            if (jsonObject.has("id")) {
                id = jsonObject.getString("id");
            }
            if (jsonObject.has("name")) {
                name = jsonObject.getString("name");
            }
            if (jsonObject.has("duration")) {
                duration = jsonObject.getInt("duration");
            }
            if (jsonObject.has("equipment")) {
                equipment = jsonObject.getString("equipment");
                has_equipment = true;
            }
            else
            {
                has_equipment = false;
            }
            if (jsonObject.has("difficulty")) {
                difficulty = jsonObject.getString("difficulty");
                has_difficulty = true;
            }
            else
            {
                has_difficulty = false;
            }
        } catch (JSONException e) {
            Log.e("Workout", "populateFromJSONObject error " + e);
        }
    }

    public void populateFromJSONString(String jsonString)
    {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            populateFromJSONObject(jsonObject);
        } catch (JSONException e) {
            Log.e("Workout", "populateFromJSONString error " + e);
        }
    }

    public String toJSONString()
    {
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("name", name);
            jsonObject.put("duration", duration);
            if(hasEquipment())
            {
                jsonObject.put("equipment", equipment);
            }
            if(hasDifficulty()) {
                jsonObject.put("difficulty", difficulty);
            }
        } catch (JSONException e) {
            Log.e("Workout", "toJSONObject error " + e);
        }
        return jsonObject;
    }

    //Formats the duration based on the formatted string which is defined in string.xml as "%d minutes"
    public String getDurationFormattedString(String formattedString)
    {
        if(formattedString == null)
        {
            return Integer.toString(duration);
        }
        else if(formattedString.contains("%d")) {
            return String.format(Locale.getDefault(Locale.Category.FORMAT), formattedString, duration);
        }
        else
        {
            return Integer.toString(duration);
        }
    }

    //Removes any non numeric characters from the string and stores just the number in duration
    //Set the duration to 0 if the string contains no valid digits
    public void setDurationFromFormattedString(String formattedString)
    {
        if(formattedString == null)
        {
            duration = 0;
            return;
        }

        try {
            duration = Integer.parseInt(formattedString.replaceAll("\\D", ""));
        } catch (NumberFormatException e) {
            duration = 0;
        }
    }

    public String getName()
    {
        return name;
    }

    public String getEquipment()
    {
        return Objects.requireNonNullElse(equipment, "");
    }

    public String getDifficulty()
    {
        return Objects.requireNonNullElse(difficulty, "");
    }

    public boolean hasEquipment()
    {
        return has_equipment;
    }

    public boolean hasDifficulty()
    {
        return has_difficulty;
    }

}
