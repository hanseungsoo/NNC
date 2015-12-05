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
public class AnniModify extends FragmentActivity {
    EditText edit;
    DatePicker DP;
    Spinner SP;
    Button btn1,btn2;
    Anni sendAnni,ani;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anni_edit);
        sendAnni = new Anni();
        edit = (EditText)findViewById(R.id.editText);
        DP = (DatePicker)findViewById(R.id.datePicker);
        SP = (Spinner)findViewById(R.id.spinner);
        btn1 = (Button)findViewById(R.id.button2);
        btn2 = (Button)findViewById(R.id.button3);
        btn2.setText("수정");
        final Intent intent = getIntent();
        final ArrayList<String> arraylist = new ArrayList<String>();
        arraylist.add("생일");
        arraylist.add("연인");
        arraylist.add("기념");
        arraylist.add("부모님");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, arraylist);
        SP.setAdapter(adapter);
        SP.setFocusable(false);



        ani = new Anni();
        ani.setSubject(intent.getStringExtra("subject"));
        ani.setSeq(intent.getIntExtra("seq", -1));
        ani.setYear(intent.getIntExtra("year", -1));
        ani.setMonth(intent.getIntExtra("month", -1));
        ani.setDay(intent.getIntExtra("day", -1));
        ani.setCate(intent.getStringExtra("cate"));

        edit.setText(ani.getSubject().toString());
        edit.setClickable(false);
        edit.setFocusable(false);
        Toast.makeText(MainActivity.mContext,""+ani.getDay()+" "+ani.getMonth(),Toast.LENGTH_SHORT).show();


        DP.init(ani.getYear(), ani.getMonth(), ani.getDay(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                sendAnni.setYear(year);
                sendAnni.setMonth(monthOfYear);
                sendAnni.setDay(dayOfMonth);
            }
        });
        DP.setClickable(false);
        DP.setFocusable(false);
        DP.setEnabled(false);

        if(ani.getCate().equals("생일")){
            SP.setSelection(0);
        }else if(ani.getCate().equals("연인")){
            SP.setSelection(1);
        }else if(ani.getCate().equals("기념")){
            SP.setSelection(2);
        }else if(ani.getCate().equals("부모님")){
            SP.setSelection(3);
        }

        SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sendAnni.setCate(arraylist.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sendAnni.setCate(ani.getCate());
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn2.getText().equals("수정")){
                    btn2.setText("확인");
                    edit.setFocusable(true);
                    edit.setClickable(true);

                    SP.setClickable(true);
                    SP.setFocusable(true);
                    DP.setClickable(true);
                    DP.setFocusable(true);
                    DP.setEnabled(true);
                }else if(btn2.getText().equals("확인")){
                    DBHandler dh = DBHandler.open(MainActivity.mContext);
                    sendAnni.setSubject(edit.getText().toString());
                    sendAnni.setSeq(ani.getSeq());
                    if(dh.updateAnni(sendAnni)){
                        Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AnniModify.this,AnniActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }

            }
        });
    }
}
