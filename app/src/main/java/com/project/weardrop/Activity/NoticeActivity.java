package com.project.weardrop.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.weardrop.DTO.NoticeDTO;
import com.project.weardrop.Other.NoticeAdapter;
import com.project.weardrop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class NoticeActivity extends AppCompatActivity implements Runnable {

    private NoticeAdapter adapter;
    private ArrayList<NoticeDTO> dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        init();
        getData();
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new NoticeAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        // 임의의 데이터입니다.

        Thread th = new Thread(NoticeActivity.this);
        th.start();
    }
    @Override
    public void run() {
        String url = "http://192.168.0.32:80/pj/listex";
        try {
            HttpClient http = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            // http 에 인코딩된 값 세팅
            // post 방식으로 전달하고 응답은 response에 저장
            HttpResponse response = http.execute(httpPost);
            // response text를 String으로 변환
            String body = EntityUtils.toString(response.getEntity());
            // String 을 JSON으로...
            final JSONObject obj = new JSONObject(body);
            final JSONArray Array = obj.getJSONArray("message");

            // 백그라운드 스레드에서 메인 UI를 변경하고자 하는경우 사용
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for(int i=0; i < Array.length(); i++) {
                            JSONObject Object = Array.getJSONObject(i);
                            NoticeDTO dto = new NoticeDTO();
                            dto.setId(Object.getString("id"));
                            dto.setTitle(Object.getString("title"));
                            dto.setWriter(Object.getString("writer"));
                            dto.setContent(Object.getString("content"));
                            // 각 값이 들어간 data를 adapter에 추가합니다.
                            adapter.addItem(dto);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
