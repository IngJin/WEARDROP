
package com.project.weardrop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.weardrop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity implements GalleryAdapter.OnItemClickListener {
    //190730 implements GalleryAdapter.OnItemClickListener 추가

    //190730 추가

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_WRITER = "writer";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_CONTENT = "content";
    public static final String EXTRA_READCNT = "readcnt";
    public static final String EXTRA_WRITEDATE = "writedate";
    public static final String EXTRA_FILEPATH = "filepath";

    private RecyclerView mRecyclerView;
    private GalleryAdapter mGalleryAdapter;
    private ArrayList<GalleryDTO> mGalleryList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mRecyclerView = findViewById(R.id.gallery_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(this,));
        //mGalleryAdapter.notifyDataSetChanged();//추가

        mGalleryList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJson();

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
                              Intent intent = new Intent(GalleryActivity.this, GalleryInsertActivity.class);
                              startActivity(intent);
                                return true;

                            case R.id.menuitem_bottombar_search:
                                Toast.makeText(getApplicationContext    (), "검색버튼 클릭", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                });
    }

    private void parseJson() {
        String url = "http://112.164.58.7/weardrop/json.gal";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray jsonArray = response.getJSONArray("andlist");

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject list = jsonArray.getJSONObject(i);
                                String filepath = list.getString("filepath");
                                String id = list.getString("id");
                                String writer = list.getString("writer");
                                String title = list.getString("title");
                                String content = list.getString("content");
                                String readcnt = list.getString("readcnt");
                                String writedate = list.getString("writedate");

                                mGalleryList.add(new GalleryDTO(id, writer, title, content, readcnt, filepath, writedate));
                            }

                            mGalleryAdapter = new GalleryAdapter(GalleryActivity.this, mGalleryList);
                            mRecyclerView.setAdapter(mGalleryAdapter);
                            mGalleryAdapter.setOnItemClickListener(GalleryActivity.this);

                        }catch (JSONException e){
                            e.printStackTrace();                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);

    }

    //190730 추가
    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, GalleryDetailActivity.class);
        GalleryDTO clickedItem = mGalleryList.get(position);

        detailIntent.putExtra(EXTRA_ID, clickedItem.getId());
        detailIntent.putExtra(EXTRA_WRITER, clickedItem.getWriter());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_CONTENT,clickedItem.getContent());
        detailIntent.putExtra(EXTRA_READCNT, clickedItem.getReadcnt());
        detailIntent.putExtra(EXTRA_WRITEDATE, clickedItem.getWritedate());
        detailIntent.putExtra(EXTRA_FILEPATH, clickedItem.getFilepath());

        startActivity(detailIntent);

    }


}

