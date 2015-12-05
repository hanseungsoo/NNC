package com.example.han.coaching;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

/**
 * Created by han on 2015-11-24.
 */
public class optionActivity extends FragmentActivity {
    CheckBox a, b, c, d, e, f, g;
    Button btn,btn2;
    SharedInit SI;
    registerAlarm RA;

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

        SI = new SharedInit(getApplicationContext());
        RA = new registerAlarm(getApplicationContext());

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
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onClicked(View v) {
        if (noonWidget.contentValue.equals("content1")) {
            noonWidget.contentValue = "content2";
            Toast.makeText(this, "위젯이 뉴스를 보여줍니다.", Toast.LENGTH_SHORT).show();
        } else {
            noonWidget.contentValue="content1";
            Toast.makeText(this, "위젯이 추천 음식점을 보여줍니다.", Toast.LENGTH_SHORT).show();
        }
        MainActivity.mmmm();

    }

    public void onClicked2(View v) {
        Intent intent = new Intent(this, DBActivity.class);
        startActivity(intent);
    }
    public void onClicked3(View v) {
        registerAlarm rA = new registerAlarm(getApplicationContext());
        rA.testAM2("ACTION.GET.NORMAL", 3);
    }

    public void onProClicked(View v) {
        registerAlarm rA = new registerAlarm(getApplicationContext());
        rA.registerpattern();
        rA.registerplace();
        rA.registerOneWeek();
    }
}