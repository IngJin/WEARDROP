package com.project.weardrop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.weardrop.DTO.MemberDTO;
import com.project.weardrop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Board extends AppCompatActivity implements FreeListAdapter.OnItemClickListener{
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_WRITER = "writer";
    public static final String EXTRA_WRITEDATE = "writedate";
    public static final String EXTRA_CONTENT = "content";
    public static final String EXTRA_FILEPATH = "filepath";
    private static final int REQUEST_CODE_MENU = 100;

    private RecyclerView mRecyclerView;
    private FreeListAdapter mFreeAdapter;
    private ArrayList<Freelistitem> mFreeList;
    private RequestQueue mRequestQueue;
    private SwipeRefreshLayout swipeRefreshLayout=null;

    MemberDTO dto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        final Intent intent = getIntent(); // 데이터 수신
        dto = (MemberDTO) intent.getSerializableExtra("dto"); /*클래스*/

        // bottom) 버튼 클릭시 사용되는 리스너를 구현
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // 어떤 메뉴 아이템이 터치되었는지 확인
                        switch (item.getItemId()) {
                            case R.id.menuitem_bottombar_home:
                                Intent intent = new Intent(Board.this, MainActivity.class);
                                intent.putExtra("dto", dto);
                                startActivity(intent);
                                finish();
                                return true;

                            case R.id.menuitem_bottombar_write:
                                intent = new Intent(Board.this, BoardwriteActivity.class);
                                intent.putExtra("dto", dto);
                                startActivityForResult(intent,REQUEST_CODE_MENU);
                                return true;

                            case R.id.menuitem_bottombar_search:
                                //Toast.makeText(getApplicationContext(), "검색버튼 클릭", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(Board.this, SaleActivity.class);
                intent.putExtra("dto", dto);
                startActivity(intent);
                finish();
            }
        });

        //쇼핑몰위치 버튼 클릭시 intent
        Button btn3 = findViewById(R.id.map);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Board.this, MapActivity.class);
                intent.putExtra("dto", dto);
                startActivity(intent);
                finish();
            }
        });

        //리사이클러뷰
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);                     //사이즈 고정
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //swipeRefreshLayout(당겨서 새로고침)
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                        Intent intent = new Intent(getApplicationContext(), Board.class);
                        intent.putExtra("dto", dto);
                        startActivity(intent);
                        finish();
                    }
                },1000);
            }
        });
        mFreeList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }//onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MENU){
            parseJSON();
        }
    }

    private void parseJSON(){
        String url = "http://112.164.58.217:80/weardrop_app/free.com";
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
                        String code = list.getString("code");
                        String filepath = list.getString("filepath");
                        String userid = list.getString("userid");

                        if(filepath != "null") {
                            mFreeList.add(new Freelistitem(id, title, writer, writedate, content, code, filepath, userid));
                        }else{
                            mFreeList.add(new Freelistitem(id, title, writer, writedate, content, code, userid));
                        }
                    }
                    mFreeAdapter = new FreeListAdapter(Board.this, mFreeList);
                    mRecyclerView.setAdapter(mFreeAdapter);

                    mFreeAdapter.setOnItemClickListener(Board.this);
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
        mFreeList.clear();
    }

    //상세화면으로 데이터 넘기기
    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, Freedetail.class);
        Freelistitem clickedItem = mFreeList.get(position);
        detailIntent.putExtra("dto", dto);

        detailIntent.putExtra(EXTRA_ID, clickedItem.getId());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_WRITER, clickedItem.getWriter());
        detailIntent.putExtra(EXTRA_WRITEDATE, clickedItem.getWritedate());
        detailIntent.putExtra(EXTRA_CONTENT, clickedItem.getContent());
        detailIntent.putExtra(EXTRA_FILEPATH, clickedItem.getFilepath());
        startActivity(detailIntent);

    }
}//Board
