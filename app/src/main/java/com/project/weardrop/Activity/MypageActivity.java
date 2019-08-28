package com.project.weardrop.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.weardrop.DTO.MemberDTO;
import com.project.weardrop.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

public class MypageActivity extends AppCompatActivity {

    public String NICKNAME;
    public String EMAIL;
    public String USERID;
    public String USERPW;
    public String ADMIN;

    RelativeLayout layout;
    EditText editid, editpw, editnickname, editpw_chack, edit_email;
    Button mypageView, modify, Duplicate, secession;
    TextView checkvalue;
    String check = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        layout = findViewById(R.id.Layout3);
        layout.setBackgroundResource(R.drawable.background);

        final Intent intent = getIntent(); // 데이터 수신

        final MemberDTO dto = (MemberDTO) intent.getSerializableExtra("dto"); /*클래스*/

        NICKNAME = dto.getWriter();
        EMAIL = dto.getEmail();
        USERID = dto.getUserid();
        USERPW = dto.getUserpw();
        ADMIN = dto.getAdmin();

        editid = findViewById(R.id.mypageEditid);
        editid.setVisibility(View.GONE);

        editnickname = findViewById(R.id.mypageNickInput);
        editpw = findViewById(R.id.mypagepassword);
        editpw_chack = findViewById(R.id.mypagepasswordCheckInput);
        edit_email = findViewById(R.id.mypageEmailInput);

        mypageView = findViewById(R.id.mypagePWView);
        modify = findViewById(R.id.mypagemodifedButton);
        Duplicate = findViewById(R.id.mypageEmailDuplicate);
        secession = findViewById(R.id.mypagedeleteButton);

        checkvalue = (TextView)findViewById(R.id.mypagecheckvalue);
        checkvalue.setVisibility(View.GONE);

        editid.setText(USERID);
        editnickname.setText(NICKNAME);
        editpw.setText(USERPW);
        edit_email.setText(EMAIL);

        mypageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check.equals("0")) {
                    editpw.setInputType(InputType.TYPE_CLASS_TEXT);
                    check = "1";
                } else {
                    editpw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    check = "0";
                }
            }
        });

        // 비밀번호 체크가 비밀번호와 같으면 초록색, 다르면 빨간색
        editpw_chack.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = editpw.getText().toString();
                String confirm = editpw_chack.getText().toString();

                if (password.equals(confirm)) {
                    editpw.setBackgroundColor(Color.GREEN);
                    editpw_chack.setBackgroundColor(Color.GREEN);
                } else {
                    editpw.setBackgroundColor(Color.RED);
                    editpw.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Duplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_email.getText().toString().trim().length() == 0) {
                    Toast.makeText(MypageActivity.this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                    edit_email.setText("");
                    edit_email.requestFocus();
                    return;
                }

                Thread1 th1 = new Thread1();
                th1.start();
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( editpw.getText().toString().trim().length() == 0 ) {
                    Toast.makeText(MypageActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    editpw.setText("");
                    editpw.requestFocus();
                    return;
                }

                if ( editpw.getText().toString().trim().length() <= 5 ) {
                    Toast.makeText(MypageActivity.this, "비밀번호는 최소 5자 이상으로 입력 가능합니다.", Toast.LENGTH_SHORT).show();
                    editpw.setText("");
                    editpw.requestFocus();
                    return;
                }

                if ( editpw.getText().toString().trim().length() > 11 ) {
                    Toast.makeText(MypageActivity.this, "비밀번호는 최대 11자 미만으로 입력 가능합니다.", Toast.LENGTH_SHORT).show();
                    editpw.setText("");
                    editpw.requestFocus();
                    return;
                }

                // 비밀번호 정규식
                if(!Pattern.matches("^[a-zA-Z0-9]*$", editpw.getText().toString())) {
                    Toast.makeText(MypageActivity.this, "비밀번호는 영문, 숫자만 사용가능합니다.", Toast.LENGTH_SHORT).show();
                    editpw.setText("");
                    editpw.requestFocus();
                    return;
                }

                if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{6,10}$", editpw.getText().toString())) {
                    Toast.makeText(MypageActivity.this, "비밀번호는 영문 대,소문자, 숫자를 모두 사용하셔야 합니다.", Toast.LENGTH_SHORT).show();
                    editpw.setText("");
                    editpw.requestFocus();
                    return;
                }

                if ( editpw_chack.getText().toString().trim().length() == 0 ) {
                    Toast.makeText(MypageActivity.this, "비밀번호 검사를 입력하세요", Toast.LENGTH_SHORT).show();
                    editpw_chack.setText("");
                    editpw_chack.requestFocus();
                    return;
                }

                if ( !editpw.getText().toString().equals(editpw_chack.getText().toString()) ) {
                    Toast.makeText(MypageActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    editpw.setText("");
                    editpw_chack.setText("");
                    editpw.requestFocus();
                    return;
                }

                if ( edit_email.getText().toString().trim().length() == 0 ) {
                    Toast.makeText(MypageActivity.this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                    edit_email.setText("");
                    edit_email.requestFocus();
                    return;
                }

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(edit_email.getText().toString()).matches()) {
                    Toast.makeText(MypageActivity.this,"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
                    edit_email.setText("");
                    edit_email.requestFocus();
                    return;
                }

                Thread2 th2 = new Thread2();
                th2.start();

                if(checkvalue.getText().toString() != "") {
                    if (edit_email.equals(EMAIL)) {
                        // 쓰레드 바로 실행
                        Thread3 th3 = new Thread3();
                        th3.start();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        String userId = editid.getText().toString();
                        String userPw = editpw.getText().toString();
                        String userWriter = editnickname.getText().toString();
                        String userEmail = edit_email.getText().toString();

                        MemberDTO dto =  new MemberDTO(userId, userWriter, userPw, userEmail, ADMIN);
                        intent.putExtra("dto", dto);
                        startActivity(intent);
                        finish();
                    }
                    return;
                }

                // 업데이트 스레드 호출하면 됨.
                Thread3 th3 = new Thread3();
                th3.start();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                String userId = editid.getText().toString();
                String userPw = editpw.getText().toString();
                String userWriter = editnickname.getText().toString();
                String userEmail = edit_email.getText().toString();

                MemberDTO dto =  new MemberDTO(userId, userWriter, userPw, userEmail, ADMIN);
                intent.putExtra("dto", dto);
                startActivity(intent);
                finish();
            } // onclick
        });

        secession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View dialogView = getLayoutInflater().inflate(R.layout.secession_dialog, null);
                AlertDialog.Builder fid = new AlertDialog.Builder(MypageActivity.this);
                fid.setView(dialogView);

                fid.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Text 값 받아서 로그 남기기
                        Thread4 th4 =  new Thread4();
                        th4.start();
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
    } // onCreate

    // 이메일 중복검사 서버통신
    class Thread1 extends Thread {
        @Override
        public void run() {
            String url = "http://192.168.0.67:80/iot/email_check_android";
            String email = edit_email.getText().toString();
            try {
                // NmaeValuePair 변수명과 값을 함께 저장하는 객체
                HttpClient http = new DefaultHttpClient();
                ArrayList<NameValuePair> postData = new ArrayList<>();
                // post 방식으로 전달할 값들
                postData.add(new BasicNameValuePair("email", email));
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
                            if(message.equals("이메일 사용이 가능합니다.")) {
                                checkvalue.setText("");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            } else {
                                checkvalue.setText("email");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }

                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 이메일 중복검사 서버통신
    class Thread2 extends Thread {
        @Override
        public void run() {
            String url = "http://192.168.0.67:80/iot/email_check_android";
            String email = edit_email.getText().toString();
            try {
                // NmaeValuePair 변수명과 값을 함께 저장하는 객체
                HttpClient http = new DefaultHttpClient();
                ArrayList<NameValuePair> postData = new ArrayList<>();
                // post 방식으로 전달할 값들
                postData.add(new BasicNameValuePair("email", email));
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
                            if(message.equals("이메일 사용이 가능합니다.")) {
                                checkvalue.setText("");
                            } else {
                                checkvalue.setText("email");
                                return;
                            }
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
            String url = "http://192.168.0.67:80/iot/mod_android";
            String writer = editnickname.getText().toString();
            String userpw = editpw.getText().toString();
            String email = edit_email.getText().toString();
            String userid = editid.getText().toString();
            try {
                // NmaeValuePair 변수명과 값을 함께 저장하는 객체
                HttpClient http = new DefaultHttpClient();
                ArrayList<NameValuePair> postData = new ArrayList<>();
                // post 방식으로 전달할 값들
                postData.add(new BasicNameValuePair("writer", writer));
                postData.add(new BasicNameValuePair("userpw", userpw));
                postData.add(new BasicNameValuePair("email", email));
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

                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Thread4 extends Thread {
        @Override
        public void run() {
            String url = "http://192.168.0.67:80/iot/delete_android";
            String userid = editid.getText().toString();
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
                        if(message.equals("회원 탈퇴되셨습니다. 이용해주셔서 감사합니다.")) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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


