# Workout List App - Coding Assignment

## Overview

Welcome! This assignment is designed to evaluate your Android development skills, including your ability to work with JSON data, build clean UI, implement user interactions, and write testable, maintainable code.

You'll build an Android app that reads a local JSON file and displays a list of workouts. Users should be able to edit workout details.
You'll have one week to complete this assignment. While we understand everyone works at their own pace, we recommend spending no more than 4–6 hours on it. We’re not looking for a fully polished or production-ready app.  So, focus on clean, understandable code and demonstrating your thought process.

---

## ✨ Requirements

### Core Functionality

1. **Load & Display Workouts**
   - Load the provided JSON file (`workouts.json`).
   - Display the list of workouts in a scrollable list.

2. **Edit Workouts**
   - Users should be able to tap on a workout to view and edit its details in a separate screen.
   - Changes should be retained in-memory during the session.
---

## 🛠 Technical Expectations

- Use **Kotlin** or **Java** as the base language.
- Use **Jetpack Compose** or **XML** for UI.
- Add **unit tests** and/or **UI tests** to validate core functionality.

---

## 🎯 Bonus Points

- Persist workout changes.
- Deliver a polished and responsive UI.
- Scalable code structure.

---

## 🧪 Testing

Please include tests for:
- JSON parsing and data modeling
- Core logic (e.g., editing and updating workout details)
- UI interaction (if applicable)

---

## 📝 Developer Notes

### What did you focus on and why?
I needed a bit of time to learn modern Android Studio, since the last time I developed in anything like this was probably a decade ago. My Java and IntelliJ experience was luckily much more recent, so I feel like I picked up on what's new pretty quickly.

I started focusing on reading and parsing the JSON, sending the extracted data to the EditWorkout Activity, and then writing some hard-coded changes to a save file just to test that I was able to write to disk – after that was working I added the ability to actually edit the workouts.

I ended up using a lot of the built in functionality of the JSONObject and JSONArray. I had the whole project technically working before I refactored and created the Workout class to contain the data and wrap the functions, which cut down on a lot of redundant checks and made Unit testing much more straightforward. I had also initially sent relevant workout data back and forth from the activities separately, instead of just sending a JSONString, but I ended up deciding sending the JSONString made the most sense after the refactor.

I spent some time playing with different layouts and methods of giving themes to the buttons, but ended up simplifying a lot of what I had initially tested. I did decide it would be useful to populate strings.xml with the text for labels and defaults for editable text boxes. I come from game development where localization is a must, and it's usually easier to set that up sooner than later.

### What would you improve with more time?
I would probably define a custom UI view for displaying a workout that could be expanded or collapsed to show more details, and use that in both the main and the edit-workout activity. 
I would add the ability to add new workouts and delete workouts
I'd add icons to workout for equipment/difficulty
I would make sure the layout looked better when viewing in landscape mode.
I would add changeable themes and especially a darkmode/lightmode option
I'd make changing difficulty use a drop-down with options
Changing text in the editable fields would immediately delete text if it matched the default string, and as soon as the user was done editing the duration, it should add the “minutes” text.
I would also probably want to format time to add hours/minutes or even better give the user an option to view in hours:minutes or just minutes.
I would investigate how much time it takes to write the entire collection of workouts to a file as a JSON string, vs more efficient methods of saving. It seemed like a good enough solution just to keep the data in the JSON format, but there might be some heavy performance impact for very large workout lists and I assume there are better solutions for saving only things that have actually changed.

### How did you approach testing?
I decided to start simple and evaluate the logic from the Workout class using JUnit 4. I tried to simulate all of the bad/weird data that might get sent to the methods, and was able to use some of the results of those tests to write more failsafes for things like null and empty strings. Since I had already decided to do some extra stuff with string formating for displaying the workout duration, using a formatted string defined in the strings.xml, I spent a lot of time trying to send possible bad data to those getter and setter methods. The input type for the duration in the edit workout activity is limited to numbers, but since there's already the string “minutes” added to that text container, it's possible to leave it in or delete parts of it, so it felt useful to test variations on that.

When it came time to test the JSON functions, I ran into some issues trying to get Mockito and Junit 5 working correctly. Once I ran into the testing error that the JSONObject needed to be mocked, I figured out how to install mockito and Junit 5, but ended up with those test functions giving me the error “No tests found for given includes”. I suspect that the issue was my build.gradle was set up for JUnit 4 testing already, and there was some issue with the @Test annotation not being properly recognized as JUnit 5. I decided to cut my losses and move on with other aspects of the project in the interest of getting it done on time, but I'm very curious what a proper solution to JSON testing looks like in something like Android Studio. It's my understanding that with mockito I would be writing a mock JSONObject to return expected values, but that seems like it would end up with my tests being pretty redundant anyway, since my methods are basically just wrappers for functionality that already exists in JSONObject.

I also didn't end up doing any UI unit testing but would have liked to have gotten to that.

Coming from game development where testing is such a different beast was an interesting challenge. The kinds of systems I worked on were so complex and interconnected, and also relied mostly 3rd party game engines that took care of some of the more low-level functionality; Unit testing is mostly unheard of in favor of spending more time on developing features, and on fully-integrated playtesting. I certainly see the value in unit testing, and I can think of a lot of aspects of game development that would benefit from adopting it.


---

## 📁 Provided Files

- `workouts.json`
- `README.md`

---

## 🚀 Submission Instructions

1. Complete the assignment and update this `README.md` with your responses above.
2. Upload your project to a private GitHub repository or share it via a zip file.
3. Make sure your project builds and runs cleanly.

Have fun building! 💪
