package com.a6studios.sqlite_blog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.a6studios.sqlite_blog.database.DatabaseHelper;
import com.a6studios.sqlite_blog.database.model.Blog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Blog> blogs = new ArrayList<Blog>();
    RecyclerView recyclerView;
    BlogAdapter mAdapter;
    private DatabaseHelper db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        db = new DatabaseHelper(this);

        if(db.getAllBlogs().size()>0){
            blogs = db.getAllBlogs();
        }

        recyclerView = findViewById(R.id.rvBlogs);
        mAdapter = new BlogAdapter(this, blogs,db);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(MainActivity.this,NewBlog.class);
                startActivityForResult(i,1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void delete(Blog b){
        db.deleteBlog(b);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            String title = data.getStringExtra("TITLE");
            String content = data.getStringExtra("CONTENT");
            long id = db.insertBlog(title,content);
            Blog b = db.getBlog(id);
            if (b != null) {
                blogs.add(0, b);

                // refreshing the list
                mAdapter.notifyDataSetChanged();

            }
            else if(resultCode==5){
                String etitle = data.getStringExtra("TITLE");
                String econtent = data.getStringExtra("CONTENT");
                Blog eb = blogs.get(requestCode-2);
                eb.setTitle(etitle);
                eb.setContent(econtent);
                db.updateBlog(eb);
                blogs.set(requestCode,eb);
                mAdapter.notifyItemChanged(requestCode-2);
                Toast.makeText(this,"reached back", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
