package com.project.weardrop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.weardrop.DTO.FreeDTO;
import com.project.weardrop.Other.FreeListAdapter;
import com.project.weardrop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FreeActivity extends AppCompatActivity implements FreeListAdapter.OnItemClickListener {
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_WRITER = "writer";
    public static final String EXTRA_WRITEDATE = "writedate";
    public static final String EXTRA_CONTENT = "content";

    private RecyclerView mRecyclerView;
    private FreeListAdapter mFreeAdapter;
    private ArrayList<FreeDTO> mFreeList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);

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
                                Intent intent = new Intent(FreeActivity.this, BoardwriteActivity.class);
                                startActivity(intent);
                                return true;

                            case R.id.menuitem_bottombar_search:
                                Toast.makeText(getApplicationContext(), "검색버튼 클릭", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                });

        //세일정보 버튼 클릭시 intent
        Button btn1 = findViewById(R.id.sale);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FreeActivity.this, SaleActivity.class);
                startActivity(intent);
            }
        });

        //후기 버튼 클릭시 intent
        Button btn2 = findViewById(R.id.hugi);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FreeActivity.this, HugiActivity.class);
                startActivity(intent);
            }
        });

        //쇼핑몰위치 버튼 클릭시 intent
        Button btn3 = findViewById(R.id.map);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FreeActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });


        //리사이클러뷰
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);            //사이즈 고정

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFreeList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }
    private void parseJSON(){
        String url = "http://192.168.0.21:80/teamproject/free.com";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("list");

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject list = jsonArray.getJSONObject(i);

                        String id = list.getString("id");
                        String title = list.getString("title");
                        String writer = list.getString("writer");
                        String writedate = list.getString("writedate");
                        String content = list.getString("content");

                        mFreeList.add(new FreeDTO(id,title, writer, writedate, content));
                    }

                    mFreeAdapter = new FreeListAdapter(FreeActivity.this, mFreeList);
                    mRecyclerView.setAdapter(mFreeAdapter);
                    mFreeAdapter.setOnItemClickListener(FreeActivity.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, FreedetailActivity.class);
        FreeDTO clickedItem = mFreeList.get(position);

        detailIntent.putExtra(EXTRA_ID, clickedItem.getId());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_WRITER, clickedItem.getWriter());
        detailIntent.putExtra(EXTRA_WRITEDATE, clickedItem.getWritedate());
        detailIntent.putExtra(EXTRA_CONTENT, clickedItem.getContent());

        startActivity(detailIntent);
    }
}//Board
