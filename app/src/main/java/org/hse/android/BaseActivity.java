package org.hse.android;

import android.content.Intent;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BaseActivity extends AppCompatActivity {
    protected enum ScheduleType { DAY, WEEK }
    protected enum ScheduleMode { STUDENT, TEACHER }

    private final static String TAG = "BaseActivity";
    public static final String URL = "https://api.ipgeolocation.io/ipgeo?apiKey=b03018f75ed94023a005637878ec0977";

    protected TextView time, current_time;
    protected Date currentTime;
    public static Date time_export;

    private OkHttpClient client = new OkHttpClient();

    protected void getTime(){
        Request request = new Request.Builder().url(URL).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("tag", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                parseResponse(response);
            }
        });
    }

    protected void showSchedule(ScheduleType type, ScheduleMode scheduleMode,Spinner spinner) {
        Object selectedItem = spinner.getSelectedItem();
        if (!(selectedItem instanceof Group)) { return; }
        showScheduleImpl(type, scheduleMode, (Group) selectedItem, currentTime);
    }

    protected void showScheduleImpl(ScheduleType type, ScheduleMode mode, Group group, Date currentTime) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra(ScheduleActivity.ARG_NAME, group.getName());
        intent.putExtra(ScheduleActivity.ARG_ID, group.getId());
        intent.putExtra(ScheduleActivity.ARG_TYPE, type);
        intent.putExtra(ScheduleActivity.ARG_MODE, mode);
        intent.putExtra(ScheduleActivity.ARG_TIME, currentTime);
        startActivity(intent);
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

    private void showTime(Date dateTime){
        time = findViewById(R.id.time);
        if (dateTime == null){ return; }
        currentTime = dateTime;

        // Get russian locale
        Locale loc = new Locale("ru", "RU");

        // Format string
        SimpleDateFormat timeHoursPattern = new SimpleDateFormat("HH:mm", loc);
        SimpleDateFormat weekDayPattern = new SimpleDateFormat("EEEE", loc);

        String hoursMinutes = timeHoursPattern.format(currentTime);
        String weekDay = capitalizeCyrillic(weekDayPattern.format(currentTime));

        time.setText(hoursMinutes + ", " + weekDay);
    }

    private void parseResponse(Response response) {
        Gson gson = new Gson();
        ResponseBody body = response.body();
        try {
            if (body == null) { return; }
            String string = body.string();
            Log.d(TAG, string);
            TimeResponse timeResponse = gson.fromJson(string, TimeResponse.class);
            String currentTimeVal = timeResponse.getTimeZone().getCurrentTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
            Date dateTime = simpleDateFormat.parse(currentTimeVal);
            // run on UI thread
            runOnUiThread(() -> showTime(dateTime));
        }
        catch (Exception e) { Log.e(TAG, "", e); }
    }
}