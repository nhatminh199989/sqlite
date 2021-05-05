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

//    public int deleteStudent(int id){
//        String whereClause = "id ? ";
//        String [] whereArgs = {Integer.toString(id)};
//        SQLiteDatabase st = getWritableDatabase();
//        return st.delete("student",whereClause,whereArgs);
//    }

    public int deleteStudent(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("student",
                whereClause, whereArgs);
    }

    public int updateStudent(Student student) {
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("gender", student.isGender());
        values.put("mark",student.getMark());
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(student.getId())};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update("student",
                values, whereClause, whereArgs);
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

    //select from * student where id = ?
    public Student getStudent(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("student", null,
                whereClause, whereArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int studentId = cursor.getInt(cursor.getColumnIndex("id"));
            String studentName = cursor.getString(cursor.getColumnIndex("name"));
            boolean studentGender = cursor.getInt(cursor.getColumnIndex("gender"))==1;
            double studentMark=cursor.getDouble(cursor.getColumnIndex("mark"));
            cursor.close();
            return new Student(studentId, studentName, studentGender,studentMark);
        }
        return null;
    }

    public Student getStudentByID(int id){
        String sql = "select * from student where id = ?";
        String[] whereArgs={Integer.toString(id)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.rawQuery(sql,whereArgs);
        if( (rs != null) && (rs.moveToNext())){
            String name = rs.getString(1);
            boolean g = rs.getInt(2)==1;
            double m = rs.getDouble(3);
            return new Student(id,name,g,m);
        }
        return null;
    }

    //select *from like
    public List<Student> searchByName(String key){
        List<Student> list = new ArrayList<>();
        String whereClause = "name like ?";
        String[] whereArgs = {"%"+key+"%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("student",null,whereClause,whereArgs,null,null,null);


        return list;
    }

}
