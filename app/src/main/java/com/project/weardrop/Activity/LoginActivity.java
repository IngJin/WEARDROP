package com.project.weardrop.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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

import com.kakao.auth.AccessTokenCallback;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.auth.authorization.accesstoken.AccessToken;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;
import com.project.weardrop.DTO.MemberDTO;
import com.project.weardrop.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;


public class LoginActivity extends AppCompatActivity {

    private boolean saveLoginData;
    RelativeLayout layout;
    EditText editid, editpwd, email, userid, writer, userpw, find_id, find_eamil;
    Button btnLogin, btnsign;
    Button KaKaoButton;
    LoginButton KaKaoButtonLogin;

    SessionCallback callback;

    TextView findid, findpw;

    private CheckBox checkBox;
    private String id;
    private String pwd;

    private SharedPreferences appData;
    private String TAG = "LoginActivity";

    private OAuthLoginButton naverLogInButton;
    private static OAuthLogin naverLoginInstance;

    static final String CLIENT_ID = "FvUsJAIvW7W2TjTyHugl";
    static final String CLIENT_SECRET = "3G8fzOvfNo";
    static final String CLIENT_NAME = "네이버 아이디로 로그인 테스트";

    static Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        KaKaoButtonLogin = (LoginButton) findViewById(R.id.KaKaoButtonLogin);
        KaKaoButtonLogin.setVisibility(View.GONE);


        KaKaoButton = (Button) findViewById(R.id.KaKaoButton);
        KaKaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KaKaoButtonLogin.performClick();
            }
        });

        // 앱 배경화면
        layout = findViewById(R.id.layout);
        layout.setBackgroundResource(R.drawable.background);

        // 버튼 이미지
        KaKaoButton = findViewById(R.id.KaKaoButton);
        KaKaoButton.setBackgroundResource(R.drawable.kakao);

        // id 값 지정
        editid = (EditText) findViewById(R.id.UseridInput);
        editpwd = (EditText) findViewById(R.id.passwordInput);
        btnLogin = (Button) findViewById(R.id.loginButton);
        btnsign = (Button) findViewById(R.id.signupButton);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        email = (EditText) findViewById(R.id.email);
        email.setVisibility(View.GONE);
        writer = (EditText) findViewById(R.id.writer);
        writer.setVisibility(View.GONE);
        userid = (EditText) findViewById(R.id.userid);
        userid.setVisibility(View.GONE);
        userpw = (EditText) findViewById(R.id.userpw);
        userpw.setVisibility(View.GONE);

        find_id = (EditText) findViewById(R.id.find_id);
        find_id.setVisibility(View.GONE);

        find_eamil = (EditText) findViewById(R.id.find_email);
        find_eamil.setVisibility(View.GONE);

        // 비밀번호 타입 *으로 보여지게 처리
        editpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

        // 폰트(나눔고딕)
        Typeface typeface = getResources().getFont(R.font.nanumgothic);
        editid.setTypeface(typeface);
        editpwd.setTypeface(typeface);
        btnLogin.setTypeface(typeface);
        btnsign.setTypeface(typeface);

        findid = findViewById(R.id.findid);
        findpw = findViewById(R.id.findpw);

        init();
        init_View();

        findid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = getLayoutInflater().inflate(R.layout.findid_dialog, null);
                final EditText et = (EditText) dialogView.findViewById(R.id.findemail);
                AlertDialog.Builder fid = new AlertDialog.Builder(LoginActivity.this);
                fid.setView(dialogView);

                fid.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Text 값 받아서 로그 남기기
                        String email = et.getText().toString();

                        find_eamil.setText(email);

                        Thread4 th = new Thread4();
                        th.start();
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

        findpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = getLayoutInflater().inflate(R.layout.findpw_dialog, null);
                final EditText et = (EditText) dialogView.findViewById(R.id.findid);
                final EditText et2 = (EditText) dialogView.findViewById(R.id.findemail);
                AlertDialog.Builder fpw = new AlertDialog.Builder(LoginActivity.this);
                fpw.setView(dialogView);

                fpw.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Text 값 받아서 로그 남기기
                        String userid = et.getText().toString();
                        String email = et2.getText().toString();

                        find_id.setText(userid);
                        find_eamil.setText(email);

                        Thread5 th = new Thread5();
                        th.start();
                        dialog.dismiss();     //닫기
                        // Event
                    }
                });

                fpw.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                        // Event
                    }
                });
                fpw.show();
            }
        });

        // 로그인 정보 저장
        if (saveLoginData) {
            editid.setText(id);
            editpwd.setText(pwd);
            checkBox.setChecked(saveLoginData);
        }

        kakaoData();

        // 버튼 클릭시 서버 통신 Thread 호출
        btnLogin.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();

                if (editid.getText().toString().length() == 0) {
                    Toast.makeText(LoginActivity.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    editid.requestFocus();
                    return;
                }

                if (editpwd.getText().toString().length() == 0) {
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    editpwd.requestFocus();
                    return;
                }

                Thread1 th = new Thread1();
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


    /**
     * 카카오톡
     **/
    private void kakaoData() {

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);

/** 토큰 만료시 갱신을 시켜준다**/
        if (Session.getCurrentSession().isOpenable()) {
            Session.getCurrentSession().checkAndImplicitOpen();
        }
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.e(TAG, "카카오 로그인 성공 ");
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Log.e(TAG, "exception : " + exception);
            }
        }
    }

    /**
     * 사용자에 대한 정보를 가져온다
     **/
    private void requestMe() {

        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("kakao_account.email");

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                super.onFailure(errorResult);
                Log.e(TAG, "requestMe onFailure message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onFailureForUiThread(ErrorResult errorResult) {
                super.onFailureForUiThread(errorResult);
                Log.e(TAG, "requestMe onFailureForUiThread message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e(TAG, "requestMe onSessionClosed message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onSuccess(MeV2Response result) {
                handleScopeError(result.getKakaoAccount());
                Log.e(TAG, "requestMe onSuccess message : " + result.getKakaoAccount().getEmail() + " " + result.getId() + " " + result.getNickname());

                final String Writer = result.getNickname();//닉네임
                final String userId = String.valueOf(result.getId());//사용자 고유번호
                final String pImage = result.getProfileImagePath();//사용자 프로필 경로
                String Email = result.getKakaoAccount().getEmail();

                userid.setText(userId);
                writer.setText(Writer);
                email.setText(Email);

                if (Email != null) {
                    Thread2 th2 = new Thread2();
                    th2.start();

                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    MemberDTO dto = new MemberDTO(userId, Writer);
                    intent.putExtra("dto", dto);
                    startActivity(intent);
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleScopeError(final UserAccount account) {
        List<String> neededScopes = new ArrayList<>();
        if (account.needsScopeAccountEmail()) {
            neededScopes.add("account_email");
        }
        Session.getCurrentSession().updateScopes(this, neededScopes, new
                AccessTokenCallback() {
                    @Override
                    public void onAccessTokenReceived(AccessToken accessToken) {
                        // 유저에게 성공적으로 동의를 받음. 토큰을 재발급 받게 됨.
                    }

                    @Override
                    public void onAccessTokenFailure(ErrorResult errorResult) {
                        // 동의 얻기 실패
                    }
                });
    }


    class Thread1 extends Thread {
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
                        if (message != null) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            try {
                                String userid = Object.getString("userid");
                                String writer = Object.getString("writer");
                                String userpw = Object.getString("userpw");
                                String email = Object.getString("email");
                                String admin = Object.getString("admin");
                                MemberDTO dto = new MemberDTO(userid, writer, userpw, email, admin);
                                intent.putExtra("dto", dto);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "환영합니다.", Toast.LENGTH_SHORT).show();
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
    }

    // 이메일 중복검사 서버통신
    class Thread2 extends Thread {
        @Override
        public void run() {
            String url = "http://192.168.0.67:80/iot/email_check_android";
            String email_thread2 = email.getText().toString();
            try {
                // NmaeValuePair 변수명과 값을 함께 저장하는 객체
                HttpClient http = new DefaultHttpClient();
                ArrayList<NameValuePair> postData = new ArrayList<>();
                // post 방식으로 전달할 값들
                postData.add(new BasicNameValuePair("email", email_thread2));
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
                        if (message.equals("이미 사용중인 이메일입니다.")) { // 중복이면
                            // dto로 다시호출해서 값을 가져오고
                            Thread3 th3 = new Thread3();
                            th3.start();
                        } else {
                            String userId = userid.getText().toString();
                            String Writer = writer.getText().toString();
                            String Email = email.getText().toString();
                            String userPw = randomValue(10);
                            String admin = "N";
                            userpw.setText(userPw);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            MemberDTO dto = new MemberDTO(userId, Writer, userPw, Email, admin);
                            intent.putExtra("dto", dto);
                            startActivity(intent);
                            Thread6 th = new Thread6();
                            th.start();
                            finish();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 랜덤 비밀번호 생성기(10자리)
    public static String randomValue(int cnt) {
        StringBuffer strPwd = new StringBuffer();
        char str[] = new char[1];
        for (int i = 0; i < cnt; i++) {
            str[0] = (char) ((Math.random() * 94) + 33);
            strPwd.append(str);
        }
        return strPwd.toString();
    }

    class Thread3 extends Thread {
        @Override
        public void run() {
            String url = "http://192.168.0.67:80/iot/email_login_android";
            String email_Thread3 = email.getText().toString();
            try {
                // NmaeValuePair 변수명과 값을 함께 저장하는 객체
                HttpClient http = new DefaultHttpClient();
                ArrayList<NameValuePair> postData = new ArrayList<>();
                // post 방식으로 전달할 값들
                postData.add(new BasicNameValuePair("email", email_Thread3));
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
                        if (message != null) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            try {
                                String userid = Object.getString("userid");
                                String writer = Object.getString("writer");
                                String userpw = Object.getString("userpw");
                                String email = Object.getString("email");
                                String admin = Object.getString("admin");
                                MemberDTO dto = new MemberDTO(userid, writer, userpw, email, admin);
                                intent.putExtra("dto", dto);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "환영합니다.", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
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
            String url = "http://192.168.0.67:80/iot/android_userid_find";
            String email = find_eamil.getText().toString();
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
                final JSONObject obj = new JSONObject(body);
                final String message = obj.getString("message");
                // 백그라운드 스레드에서 메인 UI를 변경하고자 하는경우 사용
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (message != null) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "이메일을 찾을수 없습니다. 회원가입을 해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Thread5 extends Thread {
        @Override
        public void run() {
            String url = "http://192.168.0.67:80/iot/android_userpw_find";
            String userid = find_id.getText().toString();
            String email = find_eamil.getText().toString();
            try {
                // NmaeValuePair 변수명과 값을 함께 저장하는 객체
                HttpClient http = new DefaultHttpClient();
                ArrayList<NameValuePair> postData = new ArrayList<>();
                // post 방식으로 전달할 값들
                postData.add(new BasicNameValuePair("userid", userid));
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
                final JSONObject obj = new JSONObject(body);
                final String message = obj.getString("message");
                // 백그라운드 스레드에서 메인 UI를 변경하고자 하는경우 사용
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (message != null) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "비밀번호를 찾을 수 없습니다. 값을 잘못 입력하셨는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Thread6 extends Thread {
        @Override
        public void run() {
            String url = "http://192.168.0.67:80/iot/sign_android";
            String userId = userid.getText().toString();
            String Writer = writer.getText().toString();
            String userPw = userpw.getText().toString();
            String Email = email.getText().toString();
            try {
                // NmaeValuePair 변수명과 값을 함께 저장하는 객체
                HttpClient http = new DefaultHttpClient();
                ArrayList<NameValuePair> postData = new ArrayList<>();
                // post 방식으로 전달할 값들
                postData.add(new BasicNameValuePair("userid", userId));
                postData.add(new BasicNameValuePair("writer", Writer));
                postData.add(new BasicNameValuePair("userpw", userPw));
                postData.add(new BasicNameValuePair("email", Email));
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
                          Toast.makeText(getApplicationContext(), "환영합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //초기화
    private void init() {
        context = this;
        naverLoginInstance = OAuthLogin.getInstance();
        naverLoginInstance.init(this, CLIENT_ID, CLIENT_SECRET, CLIENT_NAME);
    }

    //변수 붙이기
    private void init_View() {
        naverLogInButton = (OAuthLoginButton) findViewById(R.id.buttonNaverLogin);

        //로그인 핸들러
        OAuthLoginHandler naverLoginHandler = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {//로그인 성공
                    new RequestApiTask().execute();
                } else {//로그인 실패
                    String errorCode = naverLoginInstance.getLastErrorCode(context).getCode();
                    String errorDesc = naverLoginInstance.getLastErrorDesc(context);
                    Toast.makeText(context, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                }
            }

        };

        naverLogInButton.setOAuthLoginHandler(naverLoginHandler);

    }

    // 로그아웃 처리(토큰도 함께 삭제)
    public void forceLogout() {
        // 스레드로 돌려야 한다. 안 그러면 로그아웃 처리가 안되고 false를 반환한다.
        new Thread(new Runnable() {
            @Override
            public void run() {
                naverLoginInstance.logoutAndDeleteToken(context);
            }
        }).start();
    }

    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {//작업이 실행되기 전에 먼저 실행.
            email.setText((String) "");//메일 란 비우기
        }

        @Override
        protected String doInBackground(Void... params) {//네트워크에 연결하는 과정이 있으므로 다른 스레드에서 실행되어야 한다.
            String url = "https://openapi.naver.com/v1/nid/me";
            String at = naverLoginInstance.getAccessToken(context);
            return naverLoginInstance.requestApi(context, at, url);//url, 토큰을 넘겨서 값을 받아온다.json 타입으로 받아진다.
        }

        protected void onPostExecute(String content) {//doInBackground 에서 리턴된 값이 여기로 들어온다.
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject response = jsonObject.getJSONObject("response");
                String user_id = response.getString("id");
                String writers = response.getString("nickname");
                String Email = response.getString("email");

                userid.setText(user_id);
                writer.setText(writers);
                email.setText(Email);

                if (Email != null) {
                    Thread2 th2 = new Thread2();
                    th2.start();
                    forceLogout();

                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    MemberDTO dto = new MemberDTO(user_id, writers);
                    intent.putExtra("dto", dto);
                    startActivity(intent);
                    forceLogout();
                    finish();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}