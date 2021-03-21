package org.hse.android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TeacherActivity extends BaseActivity {
    protected MainViewModel mainViewModel;

    private TextView time, status, subject, cabinet, corp, teacher;
    private Date currentTime;

    ArrayAdapter<Group> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        Objects.requireNonNull(getSupportActionBar()).hide();
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);


        final Spinner spinner = findViewById(R.id.grouplist);

        List<Group> groups = new ArrayList<>();
        initGroupList(groups);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
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

        private void initGroupList(List<Group> groups) {
            mainViewModel.getTeachers().observe(this, new Observer<List<TeacherEntity>>() {
                @Override
                public void onChanged(@Nullable List<TeacherEntity> list) {
                    List<Group> groupsResult = new ArrayList<>();
                    for (TeacherEntity listEntity : list) {
                        groupsResult.add(new Group(listEntity.id, listEntity.fio));
                    }
                    adapter.clear();
                    adapter.addAll(groupsResult);
                }
            });
        }
    private void initData() { initDataFromTimeTable(null); }

    private void initDataFromTimeTable(TimeTableWithTeacherEntity timeTableTeacherEntity) {
        if (timeTableTeacherEntity == null) {
            status.setText("Нет пар");

            subject.setText("Дисциплина");
            cabinet.setText("Кабинет");
            corp.setText("Корпус");
            teacher.setText("Преподаватель");
            return;
        }
        status.setText("Идет пара");
        TimeTableEntity timeTableEntity = timeTableTeacherEntity.timeTableEntity;

        subject.setText(timeTableEntity.subjName);
        cabinet.setText(timeTableEntity.cabinet);
        corp.setText(timeTableEntity.corp);
        teacher.setText(timeTableTeacherEntity.teacherEntity.fio);
    }
}