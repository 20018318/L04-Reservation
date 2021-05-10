package sg.edu.rp.c346.id20018318.l04_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText phone;
    EditText size;
    DatePicker dp;
    TimePicker tp;
    RadioGroup area;
    Button confirm;
    Button reset;
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.editTextName);
        phone = findViewById(R.id.editTextPhone);
        size = findViewById(R.id.editTextSize);
        dp = findViewById(R.id.datePicker);
        tp = findViewById(R.id.timePicker);
        area = findViewById(R.id.radioGroupArea);
        confirm = findViewById(R.id.buttonConfirm);
        reset = findViewById(R.id.buttonReset);
        display = findViewById(R.id.textViewDisplay);

        tp.setCurrentHour(19);
        tp.setCurrentMinute(30);
        dp.updateDate(2021, 05, 01);

        dp.setMinDate(System.currentTimeMillis());

        Calendar current = Calendar.getInstance();
        int currentHour = current.get(Calendar.HOUR_OF_DAY);
        int currentMin = current.get(Calendar.MINUTE);
        if (tp.getCurrentHour() < currentHour && tp.getCurrentMinute() < currentMin) {
            tp.setCurrentHour(currentHour);
            tp.setCurrentMinute(currentMin);
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = dp.getYear();
                int month = dp.getMonth() + 1;
                int day = dp.getDayOfMonth();
                String date = day + "/" + month + "/" + year;

                if (name.getText().toString().trim().length() > 0 && phone.getText().toString().trim().length() > 0 && size.getText().toString().trim().length() > 0) {
                    String time = tp.getCurrentHour() + ":" + tp.getCurrentMinute();

                    String choice = "";

                    int checkedRadioId = area.getCheckedRadioButtonId();
                    if (checkedRadioId == R.id.radioButtonSmoke) {
                        choice =  "Smoking Area";
                    } else if (checkedRadioId == R.id.radioButtonNoSmoke) {
                        choice = "Non-Smoking Area";
                    }

                    String output = "You have successfully make a reservation on " +  date + " at " + time + "\nReservation Details: " + "\nName: " + name.getText().toString() + "\nPhone Number: " + phone.getText().toString() + "\nGroup Size: " + size.getText().toString() + "\nSeating: " + choice;

                    display.setText(output);

                } else {
                    Toast.makeText(MainActivity.this, "Please fill in all fields.", Toast.LENGTH_LONG).show();
                }
            }
        });

        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay < 8) {
                    Toast.makeText(MainActivity.this, "The restaurant opens at 8:00AM", Toast.LENGTH_LONG).show();
                    tp.setCurrentHour(8);
                    tp.setCurrentMinute(00);
                } else if (hourOfDay > 20){
                    Toast.makeText(MainActivity.this, "The restaurant closes at 9:00PM", Toast.LENGTH_LONG).show();
                    tp.setCurrentHour(20);
                    tp.setCurrentMinute(00);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                phone.setText("");
                size.setText("");
                tp.setCurrentHour(19);
                tp.setCurrentMinute(30);
                dp.updateDate(2021, 05, 01);
                area.clearCheck();
                display.setText("");
            }
        });
    }
}