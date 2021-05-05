package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.sqlite.Model.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btAdd,btGet,btUpdate,btDelete,btAll;
    private EditText txtId,txtName,txtMark;
    private RadioButton male,female;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    SQLiteStudentOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        db = new SQLiteStudentOpenHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                boolean g = false;
                if(male.isChecked()){
                    g=true;
                }
                double m = Double.parseDouble(txtMark.getText().toString());
                Student s = new Student(name,g,m);
                db.addStudent(s);
            }
        });
        btAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Student> list = db.getAllStudent();
                adapter.setStudents(list);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void initView(){
        btAdd=findViewById(R.id.btAdd);
        btGet=findViewById(R.id.btGet);
        btUpdate=findViewById(R.id.btUpdate);
        btDelete=findViewById(R.id.btDelete);
        btAll=findViewById(R.id.btAll);
        txtId=findViewById(R.id.stID);
        txtName=findViewById(R.id.stName);
        txtMark=findViewById(R.id.stMark);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        btUpdate.setEnabled(false);
        btDelete.setEnabled(false);
        recyclerView=findViewById(R.id.recyclerView);

    }
}