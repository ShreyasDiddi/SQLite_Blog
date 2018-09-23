package com.a6studios.sqlite_blog.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.a6studios.sqlite_blog.database.model.Blog;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "blogs_db";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Blog.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Blog.TABLE_NAME);

    }

    public long insertBlog(String title,String content) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Blog.COLUMN_TITLE, title);
        values.put(Blog.COLUMN_CONTENT, content);


        long id = db.insert(Blog.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public Blog getBlog(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Blog.TABLE_NAME,
                new String[]{Blog.COLUMN_ID, Blog.COLUMN_TIMESTAMP,Blog.COLUMN_TITLE,Blog.COLUMN_CONTENT},
                Blog.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Blog blog = new Blog(
                cursor.getInt(cursor.getColumnIndex(Blog.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Blog.COLUMN_TIMESTAMP)),
                cursor.getString(cursor.getColumnIndex(Blog.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(Blog.COLUMN_CONTENT)));

        cursor.close();

        return blog;
    }

    public List<Blog> getAllBlogs() {
        List<Blog> blogs = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Blog.TABLE_NAME + " ORDER BY " +
                Blog.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Blog blog = new Blog(
                        cursor.getInt(cursor.getColumnIndex(Blog.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Blog.COLUMN_TIMESTAMP)),
                        cursor.getString(cursor.getColumnIndex(Blog.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(Blog.COLUMN_CONTENT)));

                blogs.add(blog);
            } while (cursor.moveToNext());
        }

        db.close();

        return blogs;
    }

    public int updateBlog(Blog b) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Blog.COLUMN_TITLE, b.getTitle());
        values.put(Blog.COLUMN_CONTENT, b.getContent());

        // updating row
        return db.update(Blog.TABLE_NAME, values, Blog.COLUMN_ID + " = ?",
                new String[]{String.valueOf(b.getId())});
    }

    public void deleteBlog(Blog b) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(b.TABLE_NAME, b.COLUMN_ID + " = ?",
                new String[]{String.valueOf(b.getId())});
        db.close();
    }

}
