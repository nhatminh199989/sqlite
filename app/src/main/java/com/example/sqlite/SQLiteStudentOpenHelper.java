package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlite.Model.Student;

import java.util.ArrayList;
import java.util.List;

public class SQLiteStudentOpenHelper extends SQLiteOpenHelper {
    private  static  final String DATABASE_NAME = "StudentDB.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteStudentOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateDatabase="CREATE TABLE student(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "gender BOOLEAN," +
                "mark REAL)";
        db.execSQL(sqlCreateDatabase);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //add a student
    public long addStudent(Student student) {
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("gender", student.isGender());
        values.put("mark", student.getMark());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("student",
                null, values);
    }

    //select all from student

    public List<Student> getAllStudent() {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("student", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int studentId = cursor.getInt(cursor.getColumnIndex("id"));
                String studentName = cursor.getString(cursor.getColumnIndex("name"));
                boolean studentGender = cursor.getInt(cursor.getColumnIndex("gender"))==1;
                double studentMark=cursor.getDouble(cursor.getColumnIndex("mark"));
                Student student = new Student(studentId, studentName, studentGender,studentMark);
                students.add(student);
            } while (cursor.moveToNext());
        }
        return students;
    }
}
