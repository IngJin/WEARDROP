package com.project.weardrop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.project.weardrop.R;

import static com.project.weardrop.Activity.GalleryActivity.EXTRA_CONTENT;
import static com.project.weardrop.Activity.GalleryActivity.EXTRA_FILEPATH;
import static com.project.weardrop.Activity.GalleryActivity.EXTRA_ID;
import static com.project.weardrop.Activity.GalleryActivity.EXTRA_READCNT;
import static com.project.weardrop.Activity.GalleryActivity.EXTRA_TITLE;
import static com.project.weardrop.Activity.GalleryActivity.EXTRA_WRITEDATE;
import static com.project.weardrop.Activity.GalleryActivity.EXTRA_WRITER;

//190730 생성
public class GalleryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_detail);

        Intent intent = getIntent();
        String id = intent.getStringExtra(EXTRA_ID);
        String writer = intent.getStringExtra(EXTRA_WRITER);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String content = intent.getStringExtra(EXTRA_CONTENT);
        String readcnt = intent.getStringExtra(EXTRA_READCNT);
        String writedate = intent.getStringExtra(EXTRA_WRITEDATE);
        String filepath = intent.getStringExtra(EXTRA_FILEPATH);

        //값을 읽어옴
        ImageView imageView = findViewById(R.id.detail_image_view);
        TextView textWriter = findViewById(R.id.detail_writer);
        TextView textTitle = findViewById(R.id.detail_title);
        TextView textContent = findViewById(R.id.detail_content);
        TextView textReadcnt = findViewById(R.id.detail_readcnt);
        TextView textWritedate = findViewById(R.id.detail_writedate);

        Glide.with(this).load("http://112.164.58.7:80/weardrop_app/resources" + filepath).into(imageView);
        textWriter.setText(writer);
        textTitle.setText(title);
        textContent.setText(content);
        textWritedate.setText(writedate);
        textReadcnt.setText(readcnt);

    }
}
