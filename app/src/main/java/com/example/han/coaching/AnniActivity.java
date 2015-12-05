package com.example.han.coaching;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by han on 2015-11-24.
 */
public class AnniActivity extends FragmentActivity implements AdapterView.OnItemClickListener {
    ListView listview;
    ArrayAdapter<String> adapter;
    ArrayList<Anni> Annis;
    ArrayList<String> names;
    Button btn;
    private AlertDialog mDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anni);
        DBHandler dh = DBHandler.open(MainActivity.mContext);
        names = new ArrayList<>();
        Annis = dh.selectAnni();
        listview = (ListView) findViewById(R.id.listView);
        if(Annis ==null)
            names.add("기념일을 입력해주세요.");
        else{
            for (int i=0;i<Annis.size();i++){
                names.add(Annis.get(i).getSubject());
            }
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,names);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        btn = (Button)findViewById(R.id.addAnniButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnniActivity.this,AnniEditActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mDialog = createDialog(Annis.get(position));
                mDialog.show();
                return false;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(Annis !=null){
            Intent intent = new Intent(AnniActivity.this,AnniModify.class);
            intent.putExtra("year",Annis.get(position).getYear());
            intent.putExtra("month", Annis.get(position).getMonth());
            intent.putExtra("day",Annis.get(position).getDay());
            intent.putExtra("subject", Annis.get(position).getSubject());
            intent.putExtra("cate", Annis.get(position).getCate());
            intent.putExtra("seq",Annis.get(position).getSeq());
            startActivity(intent);
            finish();
        }


    }
    private AlertDialog createDialog(final Anni anin) {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("삭제확인");
        ab.setMessage("정말 삭제하시겠습니까?");
        ab.setCancelable(false);

        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                DBHandler dh = DBHandler.open(MainActivity.mContext);
                if (dh.deleteAnni(anin)){
                    Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AnniActivity.this,AnniActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });

        return ab.create();
    }
}
