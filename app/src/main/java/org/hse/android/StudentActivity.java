package org.hse.android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StudentActivity extends AppCompatActivity {

    private TextView time, status, subject, cabinet, corp, teacher;
    private Date currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

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

            public void onNothingSelected(AdapterView<?> parent) { }
        });

        time = findViewById(R.id.time);
        initTime();

        status = findViewById(R.id.status);
        subject = findViewById(R.id.subject);
        cabinet = findViewById(R.id.cabinet);
        corp = findViewById(R.id.corp);
        teacher = findViewById(R.id.teacher);

        initData();
    }

    private String capitalizeCyrillic(String str) {
        String firstChar = str.substring(0, 1);
        String restPart = str.substring(1);

        String lowerCaseAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        String upperCaseAlphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

        int firstCharIndex = lowerCaseAlphabet.indexOf(firstChar);
        if (firstCharIndex == -1) {
            return str;
        }

        return upperCaseAlphabet.charAt(firstCharIndex) + restPart;
    }

    @SuppressLint("SetTextI18n")
    private void initTime() {
        currentTime = new Date();

        // Get russian locale
        Locale loc = new Locale("ru", "RU");

        // Format string
        SimpleDateFormat timeHoursPattern = new SimpleDateFormat("HH:mm", loc);
        SimpleDateFormat weekDayPattern = new SimpleDateFormat("EEEE", loc);

        String hoursMinutes = timeHoursPattern.format(currentTime);
        String weekDay = capitalizeCyrillic(weekDayPattern.format(currentTime));

        time.setText(hoursMinutes + ", " + weekDay);
    }

    private void initData() {
        status.setText("Нет пар");
        subject.setText ("Дисциплина");
        cabinet.setText("Кабинет");
        corp.setText("Корпус");
        teacher.setText("Преподаватель");
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
}

