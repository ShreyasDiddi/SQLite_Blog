package com.a6studios.sqlite_blog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewBlog extends AppCompatActivity {
    EditText et1 ;
    EditText et2;
    Button bt ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_blog);
        et1 = findViewById(R.id.new_title);
        et2 = findViewById(R.id.new_content);
        bt = findViewById(R.id.btn);
        


    }

    public void post(View view) {
        String title = et1.getText().toString();
        String content = et2.getText().toString();

        if(title.isEmpty()){
            Toast.makeText(this,"Title should be filled", Toast.LENGTH_SHORT).show();
        }
        if(content.isEmpty()){
            Toast.makeText(this,"Content should be filled", Toast.LENGTH_SHORT).show();
        }
        if(!title.isEmpty()&&!content.isEmpty()){
            Intent i= new Intent();
            i.putExtra("TITLE",title);
            i.putExtra("CONTENT",content);
            setResult(1,i);
            finish();
        }
    }

}
