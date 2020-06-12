package com.example.taylor.notestest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);
        et = (EditText) findViewById(R.id.et);

    }
//对返回键进行处理
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_HOME){
            return true;
        }else if(keyCode ==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
//            intent.putExtra("mode", 0); // new one note;
            intent.putExtra("content",et.getText().toString());
            intent.putExtra("time",dateToStr());
            setResult(RESULT_OK,intent);
            finish();
            return true;
        }
       return super.onKeyDown(keyCode,event);

    }

    private String dateToStr() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  simpleDateFormat.format(date);
    }
}
