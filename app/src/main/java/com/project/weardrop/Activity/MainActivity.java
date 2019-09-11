package com.project.weardrop.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;
import com.project.weardrop.DTO.MemberDTO;
import com.project.weardrop.R;

public class MainActivity extends AppCompatActivity {

    RelativeLayout layout;
    Button button1, button2, button3, button4, button5, button6;
    ImageButton logout;

    public String NICKNAME;
    public String EMAIL;
    static Context mContext;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        layout = findViewById(R.id.mainlayout);
        layout.setBackgroundResource(R.drawable.main_layout);

        Typeface typeface = getResources().getFont(R.font.bmdohyeon_ttf);

        Intent intent = getIntent(); // 데이터 수신

        NICKNAME = intent.getStringExtra("name");
        EMAIL = intent.getStringExtra("email");
        final MemberDTO dto = (MemberDTO) intent.getSerializableExtra("dto"); /*클래스*/

        TextView tx1 = (TextView) findViewById(R.id.NickName);
        tx1.setTypeface(typeface);

        tx1.setText(dto.getWriter());

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                });
            }
        });

        button1 = findViewById(R.id.mypage);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                intent.putExtra("dto", dto);
                startActivity(intent);
                finish();
            }
        });


        button2 = findViewById(R.id.weather);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WeatherActivity.class);
                startActivity(intent);
            }
        });

        button3 = findViewById(R.id.dailylook);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                startActivity(intent);
            }
        });

        button4 = findViewById(R.id.secondhand);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        button5 = findViewById(R.id.community);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Board.class);
                intent.putExtra("dto", dto);
                startActivity(intent);
                finish();
            }
        });

        button6 = findViewById(R.id.notice);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
                intent.putExtra("dto", dto);
                startActivity(intent);
                finish();
            }
        });
/*
        TextView tx1 = (TextView)findViewById(R.id.textView1); *//*TextView선언*//*
        TextView tx2 = (TextView)findViewById(R.id.textView2);
        TextView tx3 = (TextView)findViewById(R.id.textView3);
        TextView tx4 = (TextView)findViewById(R.id.textView4);
        TextView tx5 = (TextView)findViewById(R.id.textView5);
        TextView tx6 = (TextView)findViewById(R.id.textView6);
        MemberDTO dto = (MemberDTO)intent.getSerializableExtra("dto"); *//*클래스*//*
        tx1.setText("아이디 : " + dto.getUserid());
        tx2.setText("닉네임 : " + dto.getWriter());
        tx3.setText("비밀번호 : " + dto.getUserpw());
        tx4.setText("이메일 : " + dto.getEmail());
        tx6.setText("관리자권한 : " + dto.getAdmin());
        */
    }
}
