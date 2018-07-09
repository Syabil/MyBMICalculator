package com.example.a16019990.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvDate;

    TextView tvBMI;
    TextView tvMsg;

    String msg;
    String datetime;
    float bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvMsg = findViewById(R.id.textViewMessage);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                //Prevents crashing when user presses the button even if the fields are empty
                if (!etWeight.getText().toString().equals("") && !etHeight.getText().toString().equals("")) {
                    float weight = Float.parseFloat(etWeight.getText().toString() + "f");
                    float height = Float.parseFloat(etHeight.getText().toString() + "f");
                    bmi = weight / (height * height);

                    if (bmi >= 30) {
                        msg = "You are obese";
                    } else if (bmi < 30 && bmi >= 25) {
                        msg = "You are overweight";
                    } else if (bmi < 25 && bmi >= 18.5) {
                        msg = "You are normal";
                    } else {
                        msg = "You are underweight";
                    }

                    tvDate.setText(String.format(getString(R.string.tvDate) + " %s", datetime));
                    tvBMI.setText(String.format(getString(R.string.tvBMI) + " %.3f", bmi));
                    tvMsg.setText(msg);
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etHeight.getText().clear();
                etWeight.getText().clear();
                tvDate.setText(getString(R.string.tvDate));
                tvMsg.setText(getString(R.string.tvMessage));
                tvBMI.setText(getString(R.string.tvBMI));
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.clear();
                prefEdit.commit();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String date = prefs.getString("date","");
        String bmi = prefs.getString("bmi", "");
        String msg = prefs.getString("msg", "");

        tvDate.setText(date);
        tvBMI.setText(bmi);
        tvMsg.setText(msg);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("date",tvDate.getText().toString());
        prefEdit.putString("bmi",tvBMI.getText().toString());
        prefEdit.putString("msg",tvMsg.getText().toString());
        prefEdit.commit();
    }

}
