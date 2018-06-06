package com.study.apfox.barbershopapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Client> adapter;
    private EditText nameText;
    private TextView dateText, timeText;
    private List<Client> clients;
    ListView listView;

    String name = null;
    String date ="Выберите дату...";
    String time = "Выберите время...";

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = findViewById(R.id.et_name);
        dateText = findViewById(R.id.tv_date);
        timeText = findViewById(R.id.tv_time);


        clients = new ArrayList<>();
        listView = findViewById(R.id.lv_clients);
        adapter = new ArrayAdapter<>(this, R.layout.list_layout, R.id.tv_client, clients);
        listView.setAdapter(adapter);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dateText.setTextColor(getResources().getColor(R.color.colorAccent));
                                dateText.setText(dayOfMonth + "."
                                        + (monthOfYear + 1) + "." + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMin = c.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                timeText.setTextColor(getResources().getColor(R.color.colorAccent));
                                timeText.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMin, true);
                timePickerDialog.show();
            }
        });

        open();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                clients.remove(position);
                adapter.notifyDataSetChanged();

                boolean result = JSONHelper.exportToJSON(getApplicationContext(), clients);
                if(result){
                    Toast.makeText(getApplicationContext(), "Клиент удалён", Toast.LENGTH_LONG).show();
                }

            return true;}
        });

    }

    public void addClient(View view){
        name = nameText.getText().toString();
        date = dateText.getText().toString();
        time = timeText.getText().toString();
        if(date=="Выберите дату..." || time=="Выберите время..." || name==null){
            Toast.makeText(getApplicationContext(), "Вы не указали все данные", Toast.LENGTH_LONG).show();
        }else {
            Client client = new Client(name, date, time);
            clients.add(0, client);
            adapter.notifyDataSetChanged();

            boolean result = JSONHelper.exportToJSON(this, clients);
            if (result) {
                Toast.makeText(this, "Клиент добавлен", Toast.LENGTH_LONG).show();
            }
        }

        nameText.setText(null);
        dateText.setText("Выберите дату...");
        timeText.setText("Выберите время...");

    }


    public void open(){
        clients = JSONHelper.importFromJSON(this);
        if(clients !=null){
            adapter = new ArrayAdapter<>(this, R.layout.list_layout, R.id.tv_client, clients);
            listView.setAdapter(adapter);
            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
        }
    }
}