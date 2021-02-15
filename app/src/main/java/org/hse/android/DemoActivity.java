package org.hse.android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


@SuppressLint({"SetTextI18n", "DefaultLocale"})
public class DemoActivity extends AppCompatActivity {

    private TextView mTextView;
    private EditText number;
    private TextView result;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mTextView = (TextView) findViewById(R.id.text);
        number = findViewById(R.id.number);
        result = findViewById(R.id.result);

        View sumBtn = findViewById(R.id.sumBtn);
        View productBtn = findViewById(R.id.productBtn);

        sumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcSum();
            }
        });

        productBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcProduct();
            }
        });
    }

    // Sum of all natural numbers from 1 to typed number
    private void calcSum() {
        String numberVal = number.getText().toString();
        if (numberVal.isEmpty()) {
            numberVal = "0";
        }

        int count = Integer.parseInt(numberVal);

        if (count > 1e5) {
            Toast.makeText(
                    this,
                    "The given number is too large for product of even items!",
                    Toast.LENGTH_SHORT).show();
        }


        // array initialization
        int[] arr = new int[count];
        for (int i = 0; i < count; i++) {
            arr[i] = i + 1;
        }

        // calculate sum
        long sum = 0;
        for (int i = 0; i < count; i++) {
            sum += arr[i];
        }

        result.setText(String.format("The sum is %d", sum)); ;
    }

    // Product of all numbers that can be divided by 2
    private void calcProduct() {
        String numberVal = number.getText().toString();
        if (numberVal.isEmpty()) {
            numberVal = "0";
        }

        // divide the number by 2 w/o remainder
        int count = Integer.parseInt(numberVal) >> 1;

        // empirically calculated maximum number for even elements
        if (count > 30) {
            Toast.makeText(
                    this,
                    "The given number is too large for product of even items!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // array initialization
        int[] arr = new int[count];
        for (int i = 0; i < count; i++) {
            arr[i] = (i + 1) << 1;
        }

        // calculate product
        long product = 1;
        for (int i = 0; i < count; i++) {
            product *= arr[i];
        }

        result.setText(String.format("The product of even numbers is %d", product)); ;
    }
}