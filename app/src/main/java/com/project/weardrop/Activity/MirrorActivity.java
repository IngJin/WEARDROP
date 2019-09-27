package com.project.weardrop.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.weardrop.DTO.MemberDTO;
import com.project.weardrop.DTO.MirrorDTO;
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

public class MirrorActivity extends AppCompatActivity {


    MemberDTO dto;
    MirrorDTO mirror;

    TextView edituserid, edittime, editweather;

    Switch mirror_time_switch, mirror_weather_switch;

    Button stop_mirror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);

        final Intent intent = getIntent(); // 데이터 수신

        dto = (MemberDTO) intent.getSerializableExtra("dto"); /*클래스*/
        mirror = (MirrorDTO) intent.getSerializableExtra("mirror");

        // bottom 버튼 클릭시 사용되는 리스너를 구현
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // 어떤 메뉴 아이템이 터치되었는지 확인
                        switch (item.getItemId()) {
                            case R.id.menuitem_bottombar_home:
                                Intent intent = new Intent(MirrorActivity.this, MypageActivity.class);
                                intent.putExtra("dto", dto);
                                startActivity(intent);
                                finish();
                                return true;
                        }
                        return false;
                    }
                });

        edituserid = findViewById(R.id.edituserid);
        edittime = findViewById(R.id.edittime);
        editweather = findViewById(R.id.editweather);
        stop_mirror = (Button) findViewById(R.id.stop_mirror);

        edituserid.setVisibility(View.GONE);
        edittime.setVisibility(View.GONE);
        editweather.setVisibility(View.GONE);

        edituserid.setText(mirror.getUserid());
        edittime.setText(mirror.getTime());
        editweather.setText(mirror.getWeather());

        mirror_time_switch = (Switch) findViewById(R.id.mirror_time_switch);
        mirror_weather_switch = (Switch) findViewById(R.id.mirror_weather_switch);

        System.out.println(edittime.getText());
        System.out.println(editweather.getText());

        if(edittime.getText().equals("0")) {
            mirror_time_switch.setChecked(false);
        } else if(edittime.getText().equals("1")) {
            mirror_time_switch.setChecked(true);

        }

        if(editweather.getText().equals("0")) {
            mirror_weather_switch.setChecked(false);
        } else if(editweather.getText().equals("1")) {
            mirror_weather_switch.setChecked(true);
        }

        mirror_time_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true) {
                    mirror_time_switch.setChecked(true);
                    Thread1 th1 = new Thread1();
                    th1.start();
                    edittime.setText("0");
                } else if(isChecked == false) {
                    mirror_time_switch.setChecked(false);
                    Thread1 th1 = new Thread1();
                    th1.start();
                    edittime.setText("1");
                }
            }
        });

        mirror_weather_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true) {
                    mirror_weather_switch.setChecked(true);
                    Thread2 th2 = new Thread2();
                    th2.start();
                    editweather.setText("0");
                } else if(isChecked == false) {
                    mirror_weather_switch.setChecked(false);
                    Thread2 th2 = new Thread2();
                    th2.start();
                    editweather.setText("1");
                }
            }
        });

        stop_mirror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = getLayoutInflater().inflate(R.layout.iot_mirror_delete_dialog, null);
                final EditText et = (EditText) dialogView.findViewById(R.id.findemail);
                AlertDialog.Builder fid = new AlertDialog.Builder(MirrorActivity.this);
                fid.setView(dialogView);

                fid.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Thread3 th3 = new Thread3();
                        th3.start();
                        dialog.dismiss();     //닫기
                        // Event
                    }
                });


                fid.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                        // Event
                    }
                });
                fid.show();
            }
        });

    }

    class Thread1 extends Thread {
        @Override
        public void run() {
            String url = "http://112.164.58.217:80/weardrop_app/iot_app_usertime";
            String userid = edituserid.getText().toString();
            String app_time = edittime.getText().toString();
            try {
                // NmaeValuePair 변수명과 값을 함께 저장하는 객체
                HttpClient http = new DefaultHttpClient();
                ArrayList<NameValuePair> postData = new ArrayList<>();
                // post 방식으로 전달할 값들
                postData.add(new BasicNameValuePair("userid", userid));
                postData.add(new BasicNameValuePair("app_time", app_time));
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
                        if(message != null) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Thread2 extends Thread {
        @Override
        public void run() {
            String url = "http://112.164.58.217:80/weardrop_app/iot_app_userweather";
            String userid = edituserid.getText().toString();
            String app_weather = editweather.getText().toString();
            try {
                // NmaeValuePair 변수명과 값을 함께 저장하는 객체
                HttpClient http = new DefaultHttpClient();
                ArrayList<NameValuePair> postData = new ArrayList<>();
                // post 방식으로 전달할 값들
                postData.add(new BasicNameValuePair("userid", userid));
                postData.add(new BasicNameValuePair("app_weather", app_weather));
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
                        if(message != null) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Thread3 extends Thread {
        @Override
        public void run() {
            String url = "http://112.164.58.217:80/weardrop_app/iot_app_delete";
            String userid = edituserid.getText().toString();
            try {
                // NmaeValuePair 변수명과 값을 함께 저장하는 객체
                HttpClient http = new DefaultHttpClient();
                ArrayList<NameValuePair> postData = new ArrayList<>();
                // post 방식으로 전달할 값들
                postData.add(new BasicNameValuePair("userid", userid));
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
                        if(message != null) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                            intent.putExtra("dto", dto);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
