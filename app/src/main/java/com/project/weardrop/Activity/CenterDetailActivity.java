package com.project.weardrop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.weardrop.DTO.MemberDTO;
import com.project.weardrop.R;

public class CenterDetailActivity extends AppCompatActivity {

    MemberDTO dto;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_centerdetail);
        Intent intent = getIntent();

        dto = (MemberDTO) intent.getSerializableExtra("dto"); /*클래스*/

        String title = intent.getStringExtra("title");
        String writer = intent.getStringExtra("writer");
        String content = intent.getStringExtra("content");

        TextView textView1 = findViewById(R.id.textView1);
        textView1.setText(title);
        TextView textView2 = findViewById(R.id.textView2);
        textView2.setText(writer);
        TextView textView3 = findViewById(R.id.textView3);
        textView3.setText(content);

        //이미지뷰버튼(CenterDetail에서 CenterActivity로 intent)
        final ImageView imageView = (ImageView)findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backintent = new Intent(CenterDetailActivity.this, CenterActivity.class);
                backintent.putExtra("dto", dto);
                startActivity(backintent);
            }
        });
    }
}
