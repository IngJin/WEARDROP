package com.project.weardrop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.weardrop.R;

public class CenterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_centerdetail);
        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String writer = intent.getStringExtra("writer");
        String content = intent.getStringExtra("content");

        TextView textView1 = findViewById(R.id.textView1);
        textView1.setText(title);
        TextView textView2 = findViewById(R.id.textView2);
        textView2.setText(writer);
        TextView textView3 = findViewById(R.id.textView3);
        textView3.setText(content);

    }
}
