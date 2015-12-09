package com.example.han.coaching;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by han on 2015-11-24.
 */
public class optionActivity extends FragmentActivity {
    CheckBox a, b, c, d, e, f, g;
    Button btn,btn2;
    SharedInit SI;
    registerAlarm RA;
    TextView OE,TE,THE,FE,FIE,SE,SEE;
    Button OB,TB,THB,FB,FIB,SB,SEB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_layout1);


        a = (CheckBox) findViewById(R.id.checkBox0);
        b = (CheckBox) findViewById(R.id.checkBox1);
        c = (CheckBox) findViewById(R.id.checkBox2);
        d = (CheckBox) findViewById(R.id.checkBox3);
        e = (CheckBox) findViewById(R.id.checkBox4);
        f = (CheckBox) findViewById(R.id.checkBox5);
        g = (CheckBox) findViewById(R.id.checkBox6);
        btn = (Button) findViewById(R.id.stopservice);
        btn2 = (Button)findViewById(R.id.addAnni);
        OE = (TextView)findViewById(R.id.OE);
        TE = (TextView)findViewById(R.id.TE);
        THE = (TextView)findViewById(R.id.THE);
        FE = (TextView)findViewById(R.id.FE);
        FIE = (TextView)findViewById(R.id.FIE);
        SE = (TextView)findViewById(R.id.SE);
        SEE = (TextView)findViewById(R.id.SEE);
        SI = new SharedInit(getApplicationContext());
        RA = new registerAlarm(getApplicationContext());
        OB = (Button)findViewById(R.id.OB);
        TB = (Button)findViewById(R.id.TB);
        THB = (Button)findViewById(R.id.THB);
        FB = (Button)findViewById(R.id.FB);
        FIB = (Button)findViewById(R.id.FIB);
        SB = (Button)findViewById(R.id.SB);
        SEB = (Button)findViewById(R.id.SEB);

        if (SI.getSharedTrue("0")) {
            a.setChecked(true);
        } else {
            a.setChecked(false);
        }
        if (SI.getSharedTrue("1")) {
            b.setChecked(true);
        } else {
            b.setChecked(false);
        }
        if (SI.getSharedTrue("2")) {
            c.setChecked(true);
        } else {
            c.setChecked(false);
        }
        if (SI.getSharedTrue("3")) {
            d.setChecked(true);
        } else {
            d.setChecked(false);
        }
        if (SI.getSharedTrue("4")) {
            e.setChecked(true);
        } else {
            e.setChecked(false);
        }
        if (SI.getSharedTrue("5")) {
            f.setChecked(true);
        } else {
            f.setChecked(false);
        }
        if (SI.getSharedTrue("6")) {
            g.setChecked(true);
        } else {
            g.setChecked(false);
        }



        a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SI.setSharedTrue("0",true);
                } else {
                    RA.AlarmCancel("ACTION.GET.ONE",1);
                    SI.setSharedTrue("0", false);
                }
            }
        });
        b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SI.setSharedTrue("1", true);
                } else {
                    RA.AlarmCancel("ACTION.GET.TWO",2);
                    SI.setSharedTrue("1", false);
                }
            }
        });
        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SI.setSharedTrue("2", true);
                } else {
                    RA.AlarmCancel("ACTION.GET.THREE",3);
                    SI.setSharedTrue("2", false);
                }
            }
        });
        d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SI.setSharedTrue("3", true);
                } else {
                    RA.AlarmCancel("ACTION.GET.FOUR",4);
                    SI.setSharedTrue("3", false);
                }
            }
        });
        e.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SI.setSharedTrue("4", true);
                } else {
                    RA.AlarmCancel("ACTION.GET.FIVE",5);
                    SI.setSharedTrue("4", false);
                }
            }
        });
        f.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SI.setSharedTrue("5", true);
                } else {
                    RA.AlarmCancel("ACTION.GET.SIX",6);
                    SI.setSharedTrue("5", false);
                }
            }
        });
        g.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SI.setSharedTrue("6", true);
                } else {
                    RA.AlarmCancel("ACTION.GET.SEVEN",7);
                    SI.setSharedTrue("6", false);
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Service = new Intent(optionActivity.this, GpsService.class);
                stopService(Service);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(optionActivity.this,AnniActivity.class);
                startActivity(intent);
            }
        });

        OB.setOnClickListener(new MyOnClick("0"));
        TB.setOnClickListener(new MyOnClick("1"));
        THB.setOnClickListener(new MyOnClick("2"));
        FB.setOnClickListener(new MyOnClick("3"));
        FIB.setOnClickListener(new MyOnClick("4"));
        SB.setOnClickListener(new MyOnClick("5"));
        SEB.setOnClickListener(new MyOnClick("6"));


    }
    public class MyTimePickerListener implements TimePickerDialog.OnTimeSetListener{
        String index;
        public MyTimePickerListener(String index){
            this.index = index;
        }
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            SI.setSharedTime(index, hourOfDay, minute);
            //RA.registerAM("ACTION.GET.ONE", index, id);
            switch (index){
                case "1":
                    RA.AlarmCancel("ACTION.GET.ONE",1);
                    RA.registerAM("ACTION.GET.ONE","0",1);
                    break;
                case "2":
                    RA.AlarmCancel("ACTION.GET.TWO",2);
                    RA.registerAM("ACTION.GET.TWO", "1", 2);
                    break;
                case "3":
                    RA.AlarmCancel("ACTION.GET.THREE",3);
                    RA.registerAM("ACTION.GET.THREE", "2", 3);
                    break;
                case "4":
                    RA.AlarmCancel("ACTION.GET.FOUR",4);
                    RA.registerAM("ACTION.GET.FOUR", "3", 4);
                    break;
                case "5":
                    RA.AlarmCancel("ACTION.GET.FIVE",5);
                    RA.registerAM("ACTION.GET.FOUR", "4", 5);
                    break;
                case "6":
                    RA.AlarmCancel("ACTION.GET.SIX",6);
                    RA.registerAM("ACTION.GET.FOUR", "5", 6);
                    break;
                case "7":
                    RA.AlarmCancel("ACTION.GET.SEVEN",7);
                    RA.registerAM("ACTION.GET.FOUR", "6", 7);
                    break;
            }
            Toast.makeText(optionActivity.this, "저장 되었습니다.", Toast.LENGTH_SHORT).show();
            recreate();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void onClicked2(View v) {
        Intent intent = new Intent(this, DBActivity.class);
        startActivity(intent);
    }
    public void onClicked3(View v) {
        registerAlarm rA = new registerAlarm(getApplicationContext());
        rA.testAM2("ACTION.GET.NORMAL", 3);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SetViewTime("0", OE);
        SetViewTime("1",TE);
        SetViewTime("2",THE);
        SetViewTime("3",FE);
        SetViewTime("4",FIE);
        SetViewTime("5",SE);
        SetViewTime("6",SEE);
    }
    public void SetViewTime(String index,TextView tv){
        String timestr = "";
        Long tt = SI.getSharedTime(index);
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String str = df.format(tt);
        String[] click_str = str.split(":");
        timestr = click_str[0] +":" + click_str[1] + "\n";
        tv.setText(timestr);
    }
    class MyOnClick implements View.OnClickListener{
        String index;
        public MyOnClick(String index){
            this.index = index;
        }
        @Override
        public void onClick(View v) {
            Long tt = SI.getSharedTime(index);
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            String str = df.format(tt);
            String[] click_str = str.split(":");
            new TimePickerDialog(optionActivity.this, new MyTimePickerListener(index), Integer.parseInt(click_str[0]), Integer.parseInt(click_str[1]), false).show();
        }
    }
}