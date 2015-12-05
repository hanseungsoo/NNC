package com.example.han.coaching;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by han on 2015-11-24.
 */
public class AnniEditActivity extends FragmentActivity {
    DatePicker DP;
    Button btn1,btn2;
    Spinner SP;
    EditText TV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anni_edit);
        SP = (Spinner)findViewById(R.id.spinner);
        TV =(EditText)findViewById(R.id.editText);

        final ArrayList<String> arraylist = new ArrayList<String>();
        arraylist.add("생일");
        arraylist.add("연인");
        arraylist.add("기념");
        arraylist.add("부모님");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, arraylist);
        final Anni sendAnni = new Anni();
        DP = (DatePicker)findViewById(R.id.datePicker);
        sendAnni.setYear(DP.getYear());
        sendAnni.setMonth(DP.getMonth());
        sendAnni.setDay(DP.getDayOfMonth());
        DP.init(DP.getYear(), DP.getMonth(), DP.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                sendAnni.setYear(year);
                sendAnni.setMonth(monthOfYear);
                sendAnni.setDay(dayOfMonth);
            }
        });
        btn1 = (Button)findViewById(R.id.button2);
        btn2 = (Button)findViewById(R.id.button3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dh = DBHandler.open(MainActivity.mContext);
                sendAnni.setSubject(TV.getText().toString());
                if(dh.insertAnni(sendAnni)){
                    Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), ""+sendAnni.getMonth(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AnniEditActivity.this,AnniActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sendAnni.setCate(arraylist.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sendAnni.setCate(arraylist.get(0));
            }
        });
        SP.setAdapter(adapter);
        SP.setPrompt("카테고리");
    }
}
