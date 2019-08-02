package com.project.weardrop.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.project.weardrop.DTO.MemberDTO;
import com.project.weardrop.R;

import org.json.JSONException;
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

public class LoginActivity extends AppCompatActivity implements Runnable {

    private boolean saveLoginData;
    RelativeLayout layout;
    EditText editid, editpwd;
    Button btnLogin, btnsign;
    Button KaKaoButton, NaverButton;

    private CheckBox checkBox;
    private String id;
    private String pwd;

    private SharedPreferences appData;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        // 앱 배경화면
        layout = findViewById(R.id.layout);
        layout.setBackgroundResource(R.drawable.background);

        // 버튼 이미지
        KaKaoButton = findViewById(R.id.KaKaoButton);
        KaKaoButton.setBackgroundResource(R.drawable.kakao);

        NaverButton = findViewById(R.id.NaverButton);
        NaverButton.setBackgroundResource(R.drawable.naver);

        // id 값 지정
        editid = (EditText)findViewById(R.id.UseridInput);
        editpwd = (EditText)findViewById(R.id.passwordInput);
        btnLogin = (Button)findViewById(R.id.loginButton);
        btnsign = (Button)findViewById(R.id.signupButton);
        checkBox = (CheckBox) findViewById(R.id.checkBox);


        // 비밀번호 타입 *으로 보여지게 처리
        editpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        editpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

        // 폰트(나눔고딕)
        Typeface typeface = getResources().getFont(R.font.nanumgothic);
        editid.setTypeface(typeface);
        editpwd.setTypeface(typeface);
        btnLogin.setTypeface(typeface);
        btnsign.setTypeface(typeface);

        // 로그인 정보 저장
        if (saveLoginData) {
            editid.setText(id);
            editpwd.setText(pwd);
            checkBox.setChecked(saveLoginData);
        }

        // 버튼 클릭시 서버 통신 Thread 호출
        btnLogin.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                 save();

                if ( editid.getText().toString().length() == 0 ) {
                    Toast.makeText(LoginActivity.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    editid.requestFocus();
                    return;
                }

                if ( editpwd.getText().toString().length() == 0 ) {
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    editpwd.requestFocus();
                    return;
                }

                Thread th = new Thread(LoginActivity.this);
                th.start();
            }
        });

        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void run() {
        String url = "http://192.168.0.67:80/iot/login_android";
        String userid = editid.getText().toString();
        String userpw = editpwd.getText().toString();
        try {
            // NmaeValuePair 변수명과 값을 함께 저장하는 객체
            HttpClient http = new DefaultHttpClient();
            ArrayList<NameValuePair> postData = new ArrayList<>();
            // post 방식으로 전달할 값들
            postData.add(new BasicNameValuePair("userid", userid));
            postData.add(new BasicNameValuePair("userpw", userpw));
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
            final JSONObject obj = new JSONObject(body);
            final String message = obj.getString("message");
            final JSONObject Object = new JSONObject(message);

            // 백그라운드 스레드에서 메인 UI를 변경하고자 하는경우 사용
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(message != null) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        try {
                            String userid = Object.getString("userid");
                            String writer = Object.getString("writer");
                            String userpw = Object.getString("userpw");
                            String email = Object.getString("email");
                            String phone = Object.getString("phone");
                            String admin = Object.getString("admin");
                            MemberDTO dto = new MemberDTO(userid, writer, userpw, email, phone, admin);
                            intent.putExtra("dto", dto);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 설정값을 저장하는 함수
    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", checkBox.isChecked());
        editor.putString("ID", editid.getText().toString().trim());
        editor.putString("PWD", editpwd.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
        pwd = appData.getString("PWD", "");
    }
}



