package com.project.weardrop.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class SignActivity extends AppCompatActivity {
    EditText editid, editpw, editnickname, editpw_chack, edit_email;
    Button register, duplicate, terms_button;
    RelativeLayout layout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);


        layout = findViewById(R.id.Layout2);
        layout.setBackgroundResource(R.drawable.background);

        // id 값 지정
        editid = (EditText)findViewById(R.id.UseridInput);
        editnickname = (EditText)findViewById(R.id.NickInput);
        editpw = (EditText)findViewById(R.id.passwordInput);
        editpw_chack = (EditText)findViewById(R.id.passwordCheckInput);
        edit_email = (EditText)findViewById(R.id.EmailInput);
        register = (Button)findViewById(R.id.RegisterButton);
        duplicate = (Button)findViewById(R.id.UseridDuplicate);
        final CheckBox terms = (CheckBox)findViewById(R.id.terms);
        terms_button = (Button)findViewById(R.id.termsbutton);

        // 비밀번호 타입 *으로 보여지게 처리
        editpw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        editpw.setTransformationMethod(PasswordTransformationMethod.getInstance());
        editpw_chack.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        editpw_chack.setTransformationMethod(PasswordTransformationMethod.getInstance());

        // 폰트
        Typeface typeface = getResources().getFont(R.font.raleway_thin);
        editid.setTypeface(typeface);
        editnickname.setTypeface(typeface);
        editpw.setTypeface(typeface);
        editpw_chack.setTypeface(typeface);
        edit_email.setTypeface(typeface);

        // 비밀번호 체크가 비밀번호와 같으면 초록색, 다르면 빨간색
        editpw_chack.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = editpw.getText().toString();
                String confirm = editpw_chack.getText().toString();

                if ( password.equals(confirm) ) {
                    editpw.setBackgroundColor(Color.GREEN);
                    editpw_chack.setBackgroundColor(Color.GREEN);
                }
                else {
                    editpw.setBackgroundColor(Color.RED);
                    editpw.setBackgroundColor(Color.RED);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        // 중복검사 버튼을 누르면
        duplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( editid.getText().toString().trim().length() == 0 ) {
                    Toast.makeText(SignActivity.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    editid.setText("");
                    editid.requestFocus();
                    return;
                }

                Thread2 th2 = new Thread2();
                th2.start();
            }
        });

        // 약관확인 버튼을 누르면
        terms_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = getLayoutInflater().inflate(R.layout.trems_layout, null);
                AlertDialog.Builder fpw = new AlertDialog.Builder(SignActivity.this);
                fpw.setView(dialogView);

                fpw.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                        // Event
                    }
                });
                fpw.show();
            }
        });

        // 회원가입 버튼을 누르면
        register.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( terms.isChecked() == false) {
                    Toast.makeText(SignActivity.this, "약관에 동의해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 유효성 검사
                if ( editid.getText().toString().trim().length() == 0 ) {
                    Toast.makeText(SignActivity.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    editid.setText("");
                    editid.requestFocus();
                    return;
                }

                if(!Pattern.matches("^[a-zA-Z0-9]*$", editid.getText().toString().trim())) {
                    Toast.makeText(SignActivity.this, "아이디는 영문, 숫자만 입력 가능합니다.", Toast.LENGTH_SHORT).show();
                    editid.setText("");
                    editid.requestFocus();
                    return;
                }

                if ( editnickname.getText().toString().trim().length() == 0 ) {
                    Toast.makeText(SignActivity.this, "닉네임을 입력하세요", Toast.LENGTH_SHORT).show();
                    editnickname.setText("");
                    editnickname.requestFocus();
                    return;
                }

                if(!Pattern.matches("^[a-zA-Z0-9가-힣]*$", editnickname.getText().toString().trim())) {
                    Toast.makeText(SignActivity.this, "닉네임은 영문, 숫자, 한글만 입력 가능합니다.", Toast.LENGTH_SHORT).show();
                    editnickname.setText("");
                    editnickname.requestFocus();
                    return;
                }

                if ( editpw.getText().toString().trim().length() == 0 ) {
                    Toast.makeText(SignActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    editpw.setText("");
                    editpw.requestFocus();
                    return;
                }

                if ( editpw.getText().toString().trim().length() <= 5 ) {
                    Toast.makeText(SignActivity.this, "비밀번호는 최소 5자 이상으로 입력 가능합니다.", Toast.LENGTH_SHORT).show();
                    editpw.setText("");
                    editpw.requestFocus();
                    return;
                }

                if ( editpw.getText().toString().trim().length() > 11 ) {
                    Toast.makeText(SignActivity.this, "비밀번호는 최대 11자 미만으로 입력 가능합니다.", Toast.LENGTH_SHORT).show();
                    editpw.setText("");
                    editpw.requestFocus();
                    return;
                }

                // 비밀번호 정규식
                if(!Pattern.matches("^[a-zA-Z0-9]*$", editpw.getText().toString())) {
                    Toast.makeText(SignActivity.this, "비밀번호는 영문, 숫자만 사용가능합니다.", Toast.LENGTH_SHORT).show();
                    editpw.setText("");
                    editpw.requestFocus();
                    return;
                }

                if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{6,10}$", editpw.getText().toString())) {
                    Toast.makeText(SignActivity.this, "비밀번호는 영문 대,소문자, 숫자를 모두 사용하셔야 합니다.", Toast.LENGTH_SHORT).show();
                    editpw.setText("");
                    editpw.requestFocus();
                    return;
                }

                if ( editpw_chack.getText().toString().trim().length() == 0 ) {
                    Toast.makeText(SignActivity.this, "비밀번호 검사를 입력하세요", Toast.LENGTH_SHORT).show();
                    editpw_chack.setText("");
                    editpw_chack.requestFocus();
                    return;
                }

                if ( !editpw.getText().toString().equals(editpw_chack.getText().toString()) ) {
                    Toast.makeText(SignActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    editpw.setText("");
                    editpw_chack.setText("");
                    editpw.requestFocus();
                    return;
                }

                if ( edit_email.getText().toString().trim().length() == 0 ) {
                    Toast.makeText(SignActivity.this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                    edit_email.setText("");
                    edit_email.requestFocus();
                    return;
                }

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(edit_email.getText().toString()).matches()) {
                    Toast.makeText(SignActivity.this,"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
                    edit_email.setText("");
                    edit_email.requestFocus();
                    return;
                }


                // 아이디 중복체크를 한번 더해서, 중복나면 리턴하게.
                Thread2 th2 = new Thread2();
                th2.start();

                // 서버(DB) 스프링 통신
                Thread1 th1 = new Thread1();
                th1.start();
            }
        });
    }

    // 회원가입 서버 통신
    class Thread1 extends Thread {
        @Override
        public void run() {
            String url = "http://192.168.0.67:80/iot/sign_android";
            String userid = editid.getText().toString();
            String writer = editnickname.getText().toString();
            String userpw = editpw.getText().toString();
            String email = edit_email.getText().toString();
            try {
                // NmaeValuePair 변수명과 값을 함께 저장하는 객체
                HttpClient http = new DefaultHttpClient();
                ArrayList<NameValuePair> postData = new ArrayList<>();
                // post 방식으로 전달할 값들
                postData.add(new BasicNameValuePair("userid", userid));
                postData.add(new BasicNameValuePair("writer", writer));
                postData.add(new BasicNameValuePair("userpw", userpw));
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
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 아이디 중복검사 서버통신
    class Thread2 extends Thread {
        @Override
        public void run() {
            String url = "http://192.168.0.67:80/iot/id_check_android";
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
                        if(message != null) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

