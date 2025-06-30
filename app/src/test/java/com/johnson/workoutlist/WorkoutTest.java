package com.johnson.workoutlist;

import static org.junit.Assert.*;
import org.junit.Test;

public class WorkoutTest {

    @Test
    public void getDurationFormattedString_ExpectedFormat_Zero() {
        Workout workout = new Workout();
        workout.duration = 0;
        String expected_format = "%d minutes";
        assertEquals("0 minutes", workout.getDurationFormattedString(expected_format));
    }

    @Test
    public void getDurationFormattedString_ExpectedFormat_PositiveNumber() {
        Workout workout = new Workout();
        workout.duration = 55;
        String expected_format = "%d minutes";
        assertEquals("55 minutes", workout.getDurationFormattedString(expected_format));
    }

    @Test
    public void getDurationFormattedString_ExpectedFormat_NegativeNumber() {
        Workout workout = new Workout();
        workout.duration = -12;
        String expected_format = "%d minutes";
        assertEquals("-12 minutes", workout.getDurationFormattedString(expected_format));
    }

    @Test
    public void getDurationFormattedString_ExpectedFormat_Spanish() {
        Workout workout = new Workout();
        workout.duration = 15;
        String expected_format = "%d minutos";
        assertEquals("15 minutos", workout.getDurationFormattedString(expected_format));
    }

    @Test
    public void getDurationFormattedString_ExpectedFormat_LargeNumber() {
        Workout workout = new Workout();
        workout.duration = 999999999;
        String expected_format = "%d minutes";
        assertEquals("999999999 minutes", workout.getDurationFormattedString(expected_format));
    }

    @Test
    public void getDurationFormattedString_ExpectedFormat_UndefinedNumber() {
        Workout workout = new Workout();
        String expected_format = "%d minutes";
        assertEquals("0 minutes", workout.getDurationFormattedString(expected_format));
    }

    @Test
    public void getDurationFormattedString_BadFormat() {
        Workout workout = new Workout();
        workout.duration = 45;
        String expected_format = " minutes";
        assertEquals("45", workout.getDurationFormattedString(expected_format));
    }

    @Test
    public void getDurationFormattedString_NullString() {
        Workout workout = new Workout();
        workout.duration = 5;
        assertEquals("5", workout.getDurationFormattedString(workout.equipment)); //equipment is an uninitialized string
    }

    @Test
    public void setDurationFromFormattedString_ExpectedString() {
        Workout workout = new Workout();
        workout.setDurationFromFormattedString("20 minutes");
        assertEquals(20, workout.duration);
    }

    @Test
    public void setDurationFromFormattedString_IntString() {
        Workout workout = new Workout();
        workout.setDurationFromFormattedString("60");
        assertEquals(60, workout.duration);
    }

    @Test
    public void setDurationFromFormattedString_EmptyString() {
        Workout workout = new Workout();
        workout.setDurationFromFormattedString("");
        assertEquals(0, workout.duration);
    }

    @Test
    public void setDurationFromFormattedString_MissingNumberInString() {
        Workout workout = new Workout();
        workout.setDurationFromFormattedString("minutes");
        assertEquals(0, workout.duration);
    }

    @Test
    public void setDurationFromFormattedString_DecimalNumber() {
        Workout workout = new Workout();
        workout.setDurationFromFormattedString("1.5");
        assertEquals(15, workout.duration);
    }

    @Test
    public void setDurationFromFormattedString_DecimalNumberButExpectedFormat() {
        Workout workout = new Workout();
        workout.setDurationFromFormattedString("1.5 minutes");
        assertEquals(15, workout.duration);
    }

    @Test
    public void setDurationFromFormattedString_IntegersWithStringInMiddle() {
        Workout workout = new Workout();
        workout.setDurationFromFormattedString("60 minutes15");
        assertEquals(6015, workout.duration);
    }

    @Test
    public void setDurationFromFormattedString_Null() {
        Workout workout = new Workout();
        workout.setDurationFromFormattedString(workout.equipment);
        assertEquals(0, workout.duration);
    }

    @Test
    public void setDurationFromFormattedString_NegativeNumber() {
        Workout workout = new Workout();
        workout.setDurationFromFormattedString("-25");
        assertEquals(25, workout.duration);
    }

    @Test
    public void setDurationFromFormattedString_NegativeNumberButExpectedFormat() {
        Workout workout = new Workout();
        workout.setDurationFromFormattedString("-30 minutes");
        assertEquals(30, workout.duration);
    }

    @Test
    public void setDurationFromFormattedString_JunkAlphaNumerics() {
        Workout workout = new Workout();
        workout.setDurationFromFormattedString("fa6f45aef5asdfa1rg5a62a3s5a3ef33ag5rh4a6raer4t"); //This will make a number that is too large for an integer
        assertEquals(0, workout.duration);
    }

    @Test
    public void getName_test()
    {
        Workout workout = new Workout();
        workout.name = "Chest Press";
        assertEquals("Chest Press", workout.getName());
    }
    @Test
    public void getEquipment_NonNull()
    {
        Workout workout = new Workout();
        workout.equipment = "Bike";
        assertEquals("Bike", workout.getEquipment());
    }
    @Test
    public void getEquipment_Null()
    {
        Workout workout = new Workout();
        assertEquals("", workout.getEquipment());
    }

    @Test
    public void getDifficulty_NonNull()
    {
        Workout workout = new Workout();
        workout.difficulty = "Intermediate";
        assertEquals("Intermediate", workout.getDifficulty());
    }
    @Test
    public void getDifficulty_Null()
    {
        Workout workout = new Workout();
        assertEquals("", workout.getDifficulty());
    }

}