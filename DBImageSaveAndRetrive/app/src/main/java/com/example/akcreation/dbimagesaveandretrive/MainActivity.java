package com.example.akcreation.dbimagesaveandretrive;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private DBhelper DbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper =new DBhelper(this);
        Employee  emp =new Employee ("Venkat", "25", BitmapFactory.decodeResource(getResources(), R.drawable.photo));
        DbHelper.open();
        DbHelper.insertEmpDetails(emp);
        DbHelper.close();

        EditText etName= (EditText)findViewById(R.id.edit_text_name);
        EditText etAge=(EditText)findViewById(R.id.edit_text_age);
        ImageView photo = (ImageView)findViewById(R.id.photo);

        etName.setText(emp.getName());
        etAge.setText(emp.getAge());
        photo.setImageBitmap(emp.getBitmap());

    }
}
