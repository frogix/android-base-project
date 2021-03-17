package org.hse.android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        View studentBtn = findViewById(R.id.student_timetable_btn);
        View teacherBtn = findViewById(R.id.teacher_timetable_btn);
        View settingsBtn = findViewById(R.id.settings_btn);


        studentBtn.setOnClickListener(v -> showStudent());
        teacherBtn.setOnClickListener(v -> showTeacher());
        settingsBtn.setOnClickListener(v -> showSettings());
    }

    private void showSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void showStudent() {
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }

    private void showTeacher() {
        Intent intent = new Intent(this, TeacherActivity.class);
        startActivity(intent);
    }
}