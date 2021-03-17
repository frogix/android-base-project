package org.hse.android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeacherActivity extends BaseActivity {
    private TextView time, status, subject, cabinet, corp, teacher;
    private Date currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        final Spinner spinner = findViewById(R.id.grouplist);

        List<Group> groups = new ArrayList<>();
        initGrouplList(groups);

        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                Object item = adapter.getItem(selectedItemPosition);
                Log.d("TAG", "selectedItem: " + item);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        time = findViewById(R.id.time);
        getTime();

        status = findViewById(R.id.status);
        subject = findViewById(R.id.subject);
        cabinet = findViewById(R.id.cabinet);
        corp = findViewById(R.id.building);
        teacher = findViewById(R.id.teacher);


        View scheduleDay = findViewById(R.id.btn_day);
        scheduleDay.setOnClickListener(v -> showSchedule(BaseActivity.ScheduleType.DAY, ScheduleMode.TEACHER, spinner));
        View scheduleWeek = findViewById(R.id.btn_week);
        scheduleWeek.setOnClickListener(v -> showSchedule(BaseActivity.ScheduleType.WEEK, ScheduleMode.TEACHER, spinner));

        initData();

    }

    private void initGrouplList(List<Group> groups) {
        String[] programs = {"ПИ", "БИ", "УБ", "Э", "И"};
        String[] years = {"17", "18", "19", "20"};
        String[] groupNumbers = {"1", "2"};

        int counter = 0;

        for (int i = 0; i < programs.length; i++) {
            for (int j = 0; j < years.length; j++) {
                for (int k = 0; k < groupNumbers.length; k++) {
                    counter++;
                    groups.add(new Group(
                            counter,
                            programs[i] + "-" + years[j] + "-" + groupNumbers[k]
                    ));
                }
            }
        }
    }


    private void initData() {
        status.setText("Нет пар");
        subject.setText("Дисциплина");
        cabinet.setText("Кабинет");
        corp.setText("Корпус");
        teacher.setText("Преподаватель");
    }
}