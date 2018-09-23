package com.a6studios.sqlite_blog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.a6studios.sqlite_blog.database.DatabaseHelper;
import com.a6studios.sqlite_blog.database.model.Blog;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogViewHolder> {

    private Context context;
    private List<Blog> blogsList;
    DatabaseHelper db;

    public class BlogViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView content;
        public ImageButton delete;
        public ImageButton edit;

        public BlogViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            content = view.findViewById(R.id.content);
            delete = view.findViewById(R.id.delete);
            edit = view.findViewById(R.id.edit);
        }
    }

    public BlogAdapter(Context context, List<Blog> blogsList, DatabaseHelper db) {
        this.context = context;
        this.blogsList = blogsList;
        this.db = db;
    }

    @Override
    public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blog_item, parent, false);

        return new BlogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BlogViewHolder holder, final int position) {
        Blog blogs = blogsList.get(position);

        holder.title.setText(blogs.getTitle());

        holder.content.setText(blogs.getContent());

        holder.itemView.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,blogsList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent((Activity)context, EditActivity.class);
                i.putExtra("TITLE",blogsList.get(position).getTitle());
                i.putExtra("CONTENT",blogsList.get(position).getContent());
                ((Activity)context).startActivityForResult(i,2+position);
            }
        });

        holder.itemView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemRemoved(position);
                db.deleteBlog(blogsList.get(position));
                blogsList.remove(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return blogsList.size();
    }

    public void changeItem(){}


}
