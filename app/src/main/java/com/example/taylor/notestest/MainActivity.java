package com.example.taylor.notestest;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton btn;
    final String TAG = "tag";
    private NoteDatabase dbHelper;
    private NoteAdapter adapter;
    private List<Note> noteList = new ArrayList<>();
    private Context context = this;
TextView tv;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        adapter = new NoteAdapter(getApplicationContext(),noteList);
//每次更新的时候进行一个刷新
        refreshListView();
        lv.setAdapter(adapter);

        btn = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d(TAG,"onClick:click");
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
//                startActivity(intent);
                startActivityForResult(intent, 0);

            }
        });

    }

    private void refreshListView() {
        CRUD op = new CRUD(context);
        op.open();
        //set adapter
    if(noteList.size() > 0)
//        noteList.clear();
        noteList.addAll(op.getAllNotes());
        op.close();
        adapter.notifyDataSetChanged();
    }

    //接受startActivityForResult的结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //create new note

            String content = data.getStringExtra("content");
            String time = data.getStringExtra("time");
        Toast.makeText(this,"this is"+time,Toast.LENGTH_LONG).show();
            Note note = new Note(content, time, 1);
            CRUD op = new CRUD(context);
            op.open();
            op.addNote(note);//获取的数据类型为note类型返回的值为toString中的样式
            op.close();

            refreshListView();
//        Log.d(TAG,edit);//打印edit返回的数据，按下手机上的返回键触发
//        tv.setText(edit);//显示到TextView中

        super.onActivityResult(requestCode, resultCode, data);
    }
}
