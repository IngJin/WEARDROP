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

import static com.project.weardrop.Activity.Buy.EXTRA_CONTENT;
import static com.project.weardrop.Activity.Buy.EXTRA_FILEPATH;
import static com.project.weardrop.Activity.Buy.EXTRA_ID;
import static com.project.weardrop.Activity.Buy.EXTRA_PRICE;
import static com.project.weardrop.Activity.Buy.EXTRA_TITLE;
import static com.project.weardrop.Activity.Buy.EXTRA_WRITEDATE;
import static com.project.weardrop.Activity.Buy.EXTRA_WRITER;

public class SelldetailActivity extends AppCompatActivity implements Runnable {
    String id, filepath;

    MemberDTO dto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selldetail);

        final Intent intent = getIntent(); // 데이터 수신
        dto = (MemberDTO) intent.getSerializableExtra("dto"); /*클래스*/

        // bottom) 버튼 클릭시 사용되는 리스너를 구현
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // 어떤 메뉴 아이템이 터치되었는지 확인
                        switch (item.getItemId()) {
                            case R.id.menuitem_bottombar_home:
                                Intent intent = new Intent(SelldetailActivity.this, SellActivity.class);
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
                                                Intent intent = new Intent(SelldetailActivity.this, SellUpdateActivity.class);
                                                TextView textViewId = findViewById(R.id.id);
                                                TextView textViewTitle = findViewById(R.id.title_detail);
                                                ImageView imageView = findViewById(R.id.detail_image_view);
                                                TextView textViewPrice = findViewById(R.id.price_detail);
                                                TextView textViewContent = findViewById(R.id.content_detail);
                                                TextView textViewWriter = findViewById(R.id.writer_detail);
                                                TextView textViewWritedate = findViewById(R.id.writedate_detail);

                                                intent.putExtra("id", textViewId.getText().toString() );
                                                intent.putExtra("title", textViewTitle.getText().toString());
                                                intent.putExtra("filepath", filepath);
                                                intent.putExtra("price", textViewPrice.getText().toString());
                                                intent.putExtra("content", textViewContent.getText().toString());
                                                intent.putExtra("writer", textViewWriter.getText().toString());
                                                intent.putExtra("writedate", textViewWritedate.getText().toString());
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
                                               Thread th = new Thread(SelldetailActivity.this);
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

        //서버에서 데이터 가져오기

        id = intent.getStringExtra(EXTRA_ID);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String writer = intent.getStringExtra(EXTRA_WRITER);
        String writedate = intent.getStringExtra(EXTRA_WRITEDATE);
        String price = intent.getStringExtra(EXTRA_PRICE);
        String content = intent.getStringExtra(EXTRA_CONTENT);
        filepath = intent.getStringExtra(EXTRA_FILEPATH);

        ImageView mimageView = findViewById(R.id.detail_image_view);
        TextView textViewId = findViewById(R.id.id);
        TextView textViewTitle = findViewById(R.id.title_detail);
        TextView textViewWriter = findViewById(R.id.writer_detail);
        TextView textViewWritedate = findViewById(R.id.writedate_detail);
        TextView textViewPrice = findViewById(R.id.price_detail);
        TextView textViewContent = findViewById(R.id.content_detail);


        textViewTitle.setText(title);
        textViewId.setText(id);
        textViewWriter.setText(writer);
        textViewWritedate.setText(writedate);
        textViewPrice.setText(price);
        textViewContent.setText(content);
        Glide.with(this).load("http://112.164.58.217:80/weardrop/resources" + filepath).into(mimageView);
        //Log.d("==================",filepath);

        textViewId.setVisibility(View.INVISIBLE);       //레이아웃에서 id(textview) 안보이게 하기

    }
    @Override
    public void run() {
        String url = "http://112.164.58.217:80/weardrop_app/anddelete2.com" ;

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
                        Intent intent = new Intent(SelldetailActivity.this, SellActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("dto", dto);
                        startActivity(intent);
                        Toast.makeText(SelldetailActivity.this,"게시글이 삭제되었습니다.", Toast.LENGTH_LONG).show();
                        //setResult(RESULT_OK, intent);
                        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
