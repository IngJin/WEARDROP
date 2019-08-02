package com.project.weardrop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import com.project.weardrop.R;

import static com.project.weardrop.Activity.FreeActivity.EXTRA_CONTENT;
import static com.project.weardrop.Activity.FreeActivity.EXTRA_ID;
import static com.project.weardrop.Activity.FreeActivity.EXTRA_TITLE;
import static com.project.weardrop.Activity.FreeActivity.EXTRA_WRITEDATE;
import static com.project.weardrop.Activity.FreeActivity.EXTRA_WRITER;

public class FreedetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freedetail);

        Intent intent = getIntent();

        String id = intent.getStringExtra(EXTRA_ID);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String writer = intent.getStringExtra(EXTRA_WRITER);
        String writedate = intent.getStringExtra(EXTRA_WRITEDATE);
        String content = intent.getStringExtra(EXTRA_CONTENT);

        TextView textViewTitle = findViewById(R.id.title_detail);
        TextView textViewWriter = findViewById(R.id.writer_detail);
        TextView textViewWritedate = findViewById(R.id.writedate_detail);
        TextView textViewContent = findViewById(R.id.content_detail);

        textViewTitle.setText(title);
        textViewWriter.setText(writer);
        textViewWritedate.setText(writedate);
        textViewContent.setText(content);

    }
}

