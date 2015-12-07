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
        setContentView(R.layout.option_layout);


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
                    RA.AlarmCancel("ACTION.GET.FIVE",7);
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

        OB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long tt = SI.getSharedTime("0");
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                String str = df.format(tt);
                String[] click_str = str.split(":");
                new TimePickerDialog(optionActivity.this, new MyTimePickerListener("0"), Integer.parseInt(click_str[0]), Integer.parseInt(click_str[1]), false).show();
            }
        });
        TB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long tt = SI.getSharedTime("1");
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                String str = df.format(tt);
                String[] click_str = str.split(":");
                new TimePickerDialog(optionActivity.this, new MyTimePickerListener("1"), Integer.parseInt(click_str[0]), Integer.parseInt(click_str[1]), false).show();
            }
        });
        THB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long tt = SI.getSharedTime("2");
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                String str = df.format(tt);
                String[] click_str = str.split(":");
                new TimePickerDialog(optionActivity.this, new MyTimePickerListener("2"), Integer.parseInt(click_str[0]), Integer.parseInt(click_str[1]), false).show();
            }
        });
        FB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long tt = SI.getSharedTime("3");
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                String str = df.format(tt);
                String[] click_str = str.split(":");
                new TimePickerDialog(optionActivity.this, new MyTimePickerListener("3"), Integer.parseInt(click_str[0]), Integer.parseInt(click_str[1]), false).show();
            }
        });
        FIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long tt = SI.getSharedTime("4");
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                String str = df.format(tt);
                String[] click_str = str.split(":");
                new TimePickerDialog(optionActivity.this, new MyTimePickerListener("4"), Integer.parseInt(click_str[0]), Integer.parseInt(click_str[1]), false).show();
            }
        });
        SB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long tt = SI.getSharedTime("5");
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                String str = df.format(tt);
                String[] click_str = str.split(":");
                new TimePickerDialog(optionActivity.this, new MyTimePickerListener("5"), Integer.parseInt(click_str[0]), Integer.parseInt(click_str[1]), false).show();
            }
        });
        SEB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long tt = SI.getSharedTime("6");
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                String str = df.format(tt);
                String[] click_str = str.split(":");
                new TimePickerDialog(optionActivity.this, new MyTimePickerListener("6"), Integer.parseInt(click_str[0]), Integer.parseInt(click_str[1]), false).show();
            }
        });


    }
    public class MyTimePickerListener implements TimePickerDialog.OnTimeSetListener{
        String index;
        public MyTimePickerListener(String index){
            this.index = index;
        }
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            SI.setSharedTime(index,hourOfDay,minute);
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
        String timestr = "";
        Long tt = SI.getSharedTime("0");
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String str = df.format(tt);
        String[] click_str = str.split(":");
        timestr = click_str[0] +":" + click_str[1] + "\n";
        OE.setText(timestr);

        tt = SI.getSharedTime("1");
        df = new SimpleDateFormat("HH:mm:ss");
        str = df.format(tt);
        click_str = str.split(":");
        timestr = click_str[0] +":" + click_str[1] + "\n";
        TE.setText(timestr);

        tt = SI.getSharedTime("2");
        df = new SimpleDateFormat("HH:mm:ss");
        str = df.format(tt);
        click_str = str.split(":");
        timestr = click_str[0] +":" + click_str[1] + "\n";
        THE.setText(timestr);

        tt = SI.getSharedTime("3");
        df = new SimpleDateFormat("HH:mm:ss");
        str = df.format(tt);
        click_str = str.split(":");
        timestr = click_str[0] +":" + click_str[1] + "\n";
        FE.setText(timestr);

        tt = SI.getSharedTime("4");
        df = new SimpleDateFormat("HH:mm:ss");
        str = df.format(tt);
        click_str = str.split(":");
        timestr = click_str[0] +":" + click_str[1] + "\n";
        FIE.setText(timestr);

        tt = SI.getSharedTime("5");
        df = new SimpleDateFormat("HH:mm:ss");
        str = df.format(tt);
        click_str = str.split(":");
        timestr = click_str[0] +":" + click_str[1] + "\n";
        SE.setText(timestr);

        tt = SI.getSharedTime("6");
        df = new SimpleDateFormat("HH:mm:ss");
        str = df.format(tt);
        click_str = str.split(":");
        timestr = click_str[0] +":" + click_str[1] + "\n";
        SEE.setText(timestr);
    }
}