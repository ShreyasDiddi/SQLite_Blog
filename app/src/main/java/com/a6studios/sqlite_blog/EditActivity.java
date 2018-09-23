package com.a6studios.sqlite_blog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    EditText et_title ,et_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        et_content = findViewById(R.id.edit_content);
        et_title = findViewById(R.id.edit_title);
        Intent i = getIntent();
        et_title.setText(i.getStringExtra("TITLE"));
        et_content.setText(i.getStringExtra("CONTENT"));


    }

    public void edit(View view) {
        String title = et_title.getText().toString();
        String content = et_content.getText().toString();

        if(title.isEmpty()){
            Toast.makeText(getApplicationContext(),"Title should be filled", Toast.LENGTH_SHORT).show();
        }
        if(content.isEmpty()){
            Toast.makeText(getApplicationContext(),"Content should be filled", Toast.LENGTH_SHORT).show();
        }
        if(!title.isEmpty()&&!content.isEmpty()){
            Intent i= new Intent();
            i.putExtra("TITLE",title);
            i.putExtra("CONTENT",content);
            setResult(5,i);
            finish();
        }

    }
}
