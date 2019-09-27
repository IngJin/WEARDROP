package com.project.weardrop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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


public class Buy extends AppCompatActivity implements BuyListAdapter.OnItemClickListener{
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_USERID = "userid";
    public static final String EXTRA_WRITER = "writer";
    public static final String EXTRA_WRITEDATE = "writedate";
    public static final String EXTRA_PRICE = "price";
    public static final String EXTRA_CONTENT = "content";
    public static final String EXTRA_FILEPATH = "filepath";
    private static final int REQUEST_CODE_MENU = 100;

    private RecyclerView mRecyclerView;
    private BuyListAdapter mFreeAdapter;
    private ArrayList<Buylistitem> mFreeList;
    private RequestQueue mRequestQueue;
    private SwipeRefreshLayout swipeRefreshLayout=null;

    MemberDTO dto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

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
                                Intent intent = new Intent(Buy.this, MainActivity.class);
                                intent.putExtra("dto", dto);
                                startActivity(intent);
                                finish();
                                return true;

                            case R.id.menuitem_bottombar_write:
                                intent = new Intent(Buy.this, BuywriteActivity.class);
                                intent.putExtra("dto", dto);
                                startActivityForResult(intent,REQUEST_CODE_MENU);
                                return true;

                            case R.id.menuitem_bottombar_search:
                                Toast.makeText(getApplicationContext(), "검색버튼 클릭", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                });


        //후기 버튼 클릭시 intent
        Button btn2 = findViewById(R.id.sale);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Buy.this, SellActivity.class);
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
                        Intent intent = new Intent(getApplicationContext(), Buy.class);
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
        String url = "http://112.164.58.217:80/weardrop_app/sale2.com";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("list");

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject list = jsonArray.getJSONObject(i);

                        String id = list.getString("id");
                        String title = list.getString("title");
                        String userid = list.getString("userid");
                        String writer = list.getString("writer");
                        String writedate = list.getString("writedate");
                        String price = list.getString("price");
                        String content = list.getString("content");
                        String code = list.getString("code");
                        String filepath = list.getString("filepath");

                        mFreeList.add(new Buylistitem(id, title, writer, writedate, price, content, code, filepath, userid));
                    }
                    mFreeAdapter = new BuyListAdapter(Buy.this, mFreeList);
                    mRecyclerView.setAdapter(mFreeAdapter);

                    mFreeAdapter.setOnItemClickListener(Buy.this);
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
        Intent detailIntent = new Intent(this, Buydetail.class);
        Buylistitem clickedItem = mFreeList.get(position);
        detailIntent.putExtra("dto", dto);

        detailIntent.putExtra(EXTRA_ID, clickedItem.getId());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_WRITER, clickedItem.getWriter());
        detailIntent.putExtra(EXTRA_WRITEDATE, clickedItem.getWritedate());
        detailIntent.putExtra(EXTRA_PRICE, clickedItem.getPrice());
        detailIntent.putExtra(EXTRA_CONTENT, clickedItem.getContent());
        detailIntent.putExtra(EXTRA_FILEPATH, clickedItem.getFilepath());

        startActivity(detailIntent);
    }
}//Buy
