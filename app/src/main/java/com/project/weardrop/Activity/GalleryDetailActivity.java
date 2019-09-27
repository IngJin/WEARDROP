package com.project.weardrop.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.weardrop.DTO.MemberDTO;
import com.project.weardrop.R;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

import static com.project.weardrop.Activity.GalleryActivity.EXTRA_CONTENT;
import static com.project.weardrop.Activity.GalleryActivity.EXTRA_FILEPATH;
import static com.project.weardrop.Activity.GalleryActivity.EXTRA_ID;
import static com.project.weardrop.Activity.GalleryActivity.EXTRA_TITLE;
import static com.project.weardrop.Activity.GalleryActivity.EXTRA_WRITEDATE;
import static com.project.weardrop.Activity.GalleryActivity.EXTRA_WRITER;

//190730 생성
public class GalleryDetailActivity extends AppCompatActivity implements Runnable {
    String id;
    MemberDTO dto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_detail);

        final Intent intent = getIntent(); // 데이터 수신
        dto = (MemberDTO) intent.getSerializableExtra("dto"); /*클래스*/

        id = intent.getStringExtra(EXTRA_ID);
        String writer = intent.getStringExtra(EXTRA_WRITER);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String content = intent.getStringExtra(EXTRA_CONTENT);
        String writedate = intent.getStringExtra(EXTRA_WRITEDATE);
        final String filepath = intent.getStringExtra(EXTRA_FILEPATH);

        //값을 읽어옴
        final TextView textId = findViewById(R.id.add_id);
        textId.setVisibility(View.GONE);

        ImageView imageView = findViewById(R.id.detail_image_view);
        final TextView textWriter = findViewById(R.id.detail_writer);
        final TextView textTitle = findViewById(R.id.detail_title);
        final TextView textContent = findViewById(R.id.detail_content);
        final TextView textWritedate = findViewById(R.id.detail_writedate);

        Glide.with(this).load("http://112.164.58.217:80/weardrop/resources" + filepath).into(imageView);
        textId.setText(id);
        textWriter.setText(writer);
        textTitle.setText(title);
        textContent.setText(content);
        textWritedate.setText(writedate);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // 어떤 메뉴 아이템이 터치되었는지 확인
                        switch (item.getItemId()) {
                            case R.id.menuitem_bottombar_home:
                                Intent intent = new Intent(GalleryDetailActivity.this, GalleryActivity.class);
                                intent.putExtra("dto", dto);
                                startActivity(intent);
                                finish();
                                return true;

                            case R.id.menuitem_bottombar_update:
                                final AlertDialog.Builder builder1 = new AlertDialog.Builder(bottomNavigationView.getContext());
                                builder1.setTitle("수정");
                                builder1.setMessage("해당 항목을 수정하시겠습니까?");
                                builder1.setPositiveButton("예",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //상세화면에 있는 데이터를 수정화면으로 넘기기
                                                Intent intent = new Intent(GalleryDetailActivity.this, GalleryUpdateActivity.class);

                                                intent.putExtra("id", textId.getText().toString() );
                                                intent.putExtra("title", textTitle.getText().toString());
                                                intent.putExtra("filepath", filepath);
                                                intent.putExtra("content", textContent.getText().toString());
                                                intent.putExtra("writer", textWriter.getText().toString());
                                                intent.putExtra("writedate", textWritedate.getText().toString());
                                                intent.putExtra("dto", dto);
                                                startActivity(intent);
                                            }
                                        });
                                builder1.setNegativeButton("아니오",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                builder1.show();
                                return true;

                            case R.id.menuitem_bottombar_delete:
                                //삭제 알림창 띄우기
                                final AlertDialog.Builder builder2 = new AlertDialog.Builder(bottomNavigationView.getContext());
                                builder2.setTitle("삭제");
                                builder2.setMessage("해당 항목을 삭제하시겠습니까?");
                                builder2.setPositiveButton("예",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Thread th = new Thread(GalleryDetailActivity.this);
                                                th.start();
                                                finish();
                                            }
                                        });
                                builder2.setNegativeButton("아니오",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                builder2.show();
                                return true;
                        }
                        return false;
                    }
                });

    }

    @Override
    public void run() {
        String url = "http://112.164.58.217:80/weardrop_app/anddelete.gal" ;
        try {
            // NmaeValuePair 변수명과 값을 함께 저장하는 객체
            HttpClient http = new DefaultHttpClient();
            ArrayList<NameValuePair> postData = new ArrayList<>();
            // post 방식으로 전달할 값들
            postData.add(new BasicNameValuePair("id", id));
            // URI encoding이 필요한 한글, 특수문자 값들 인코딩
            UrlEncodedFormEntity request = new UrlEncodedFormEntity(postData, "utf-8");
            HttpPost httpPost = new HttpPost(url);
            // http 에 인코딩된 값 세팅
            httpPost.setEntity(request);
            // post 방식으로 전달하고 응답은 response에 저장
            HttpResponse response = http.execute(httpPost);
            // response text를 String으로 변환
            String body = EntityUtils.toString(response.getEntity());
            // String 을 JSON으로...
            JSONObject obj = new JSONObject(body);
            final String message = obj.getString("message");
            // 백그라운드 스레드에서 메인 UI를 변경하고자 하는경우 사용
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (message != null) {
                        Intent intent = new Intent(GalleryDetailActivity.this, GalleryActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("dto", dto);
                        startActivity(intent);
                        Toast.makeText(GalleryDetailActivity.this,"게시글이 삭제되었습니다.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
