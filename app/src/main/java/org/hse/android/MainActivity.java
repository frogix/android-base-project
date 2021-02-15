package org.hse.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int a = 5;
        int[] b = new int[]{1, 2, 3};
        Log.wtf("greetings", "Hello there!");
        setContentView(R.layout.activity_main);

        View studentBtn = findViewById(R.id.student_timetable_btn);
        View teacherBtn = findViewById(R.id.teacher_timetable_btn);



        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStudent();
            }
        });
        teacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTeacher();
            }
        });
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