package com.project.weardrop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.weardrop.R;

public class HugiActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hugi);

        // bottom) 버튼 클릭시 사용되는 리스너를 구현
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // 어떤 메뉴 아이템이 터치되었는지 확인
                        switch (item.getItemId()) {
                            case R.id.menuitem_bottombar_home:
                                Toast.makeText(getApplicationContext(), "홈버튼 클릭", Toast.LENGTH_SHORT).show();
                                return true;

                            case R.id.menuitem_bottombar_write:
                                Intent intent = new Intent(HugiActivity.this, BoardwriteActivity.class);
                                startActivity(intent);
                                return true;

                            case R.id.menuitem_bottombar_search:
                                Toast.makeText(getApplicationContext(), "검색버튼 클릭", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                });

        //자유글 버튼 클릭시 intent
        Button btn1 = findViewById(R.id.free);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HugiActivity.this, FreeActivity.class);
                startActivity(intent);
            }
        });

        //세일정보 버튼 클릭시 intent
        Button btn2 = findViewById(R.id.sale);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HugiActivity.this, SaleActivity.class);
                startActivity(intent);
            }
        });

        //쇼핑몰 위치 버튼 클릭시 intent
        Button btn3 = findViewById(R.id.map);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HugiActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

    }
}
