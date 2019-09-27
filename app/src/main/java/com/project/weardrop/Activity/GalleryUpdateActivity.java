package com.project.weardrop.Activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.project.weardrop.DTO.MemberDTO;
import com.project.weardrop.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalleryUpdateActivity extends AppCompatActivity {
    String id, userid, title, content, writer, code, filepath;
    private TextView addWriter;                                                                   //레이아웃에서 사용했던 TextView
    private EditText addTitle, addContent;                                                       //레이아웃에서 사용했던 EditText
    private ImageView add_image_view;                                                            //레이아웃에서 사용했던 ImageView
    private Button ImageBtn, add_btn, cancle_btn;                                               //레이아웃에서 사용했던 Button
    private String upload_URL = "http://112.164.58.217:80/weardrop_app/andupdate.gal";         // Request를 요청 할 URL
    private final int GALLERY = 1;                                                                //갤러리로 넘어가는 상수
    private RequestQueue rQueue;                                                                   // Volley : Request를 보낼 queue를 생성한다. 필요시엔 전역으로 생성해 사용.
    private ArrayList<HashMap<String, String>> arraylist;                                         //스트링값 저장할 ArrayList
    private Bitmap bitmap;                                                                         //사진 보여줄 Bitmap

    MemberDTO dto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //상세화면 데이터 가져오기
        final Intent intent = getIntent();
        dto = (MemberDTO) intent.getSerializableExtra("dto"); /*클래스*/


        id = intent.getStringExtra("id");
        userid = intent.getStringExtra("userid");
        title = intent.getStringExtra("title");
        writer = intent.getStringExtra("writer");
        content = intent.getStringExtra("content");
        filepath = intent.getStringExtra("filepath");

        //activity_update.xml id 값
        addTitle = findViewById(R.id.addtitle);
        addWriter = findViewById(R.id.addwriter);
        addContent = findViewById(R.id.addcontent);
        ImageBtn = findViewById(R.id.ImageBtn);
        add_btn = findViewById(R.id.add_btn);
        cancle_btn = findViewById(R.id.cancle_btn);
        add_image_view = (ImageView) findViewById(R.id.add_image_view);

        addTitle.setText(title);
        addWriter.setText(writer);
        addContent.setText(content);
        Glide.with(this).load("http://112.164.58.217:80/weardrop/resources" + filepath).into(add_image_view);

        requestMultiplePermissions();

        //이미지 업로드 버튼 눌렀을때 실행되는 동작
        ImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //갤러리로 들어감
                startActivityForResult(galleryIntent, GALLERY);     //갤러리 상수 호출해서 갤러리로 (onActivityResult() 실행)
            }
        });

        //저장버튼 눌렀을 때 실행되는 동작
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(bitmap);        // 버튼을 클릭했을 때 request 객체를 만들고 request queue 에넣는다.
                Intent intent1 = new Intent(GalleryUpdateActivity.this, GalleryActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.putExtra("dto", dto);
                startActivity(intent1);
                Toast.makeText(GalleryUpdateActivity.this, "게시글이 수정되었습니다. 새로고침 해주세요!", Toast.LENGTH_LONG).show();
            }
        });

        //취소버튼 눌렀을 때 실행되는 동작
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }//onCreate

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {                       // requestCode가 GALLERY 일때 갤러리에서 사진을 가져오는 동작
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    add_image_view.setImageBitmap(bitmap); // 레이아웃에 만든 이미지뷰에 갤러리에서 선택한 사진을 넣어줌

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(GalleryUpdateActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void uploadImage(final Bitmap bitmap) {
        //VolleyMultipartRequest 호출    - Response.Listener(): 응답을 성공적으로 받았을 때 리스너의 onResponse 메소드를 자동으로 호출
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, upload_URL, new Response.Listener<NetworkResponse>() {

                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.d("ressssssoo", new String(response.data));
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));

                            jsonObject.toString().replace("\\\\", "");

                            if (jsonObject.getString("status").equals("true")) {

                                arraylist = new ArrayList<HashMap<String, String>>();
                                JSONArray dataArray = jsonObject.getJSONArray("data");

                                String url = "";
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    url = dataobj.optString("pathToFile");
                                }
                                Picasso.get().load(url).into(add_image_view);   // Picasso를 이용하여 갤러리에서 이미지뷰에 넣어준 사진을보여주는 기능
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() { //ErrorListener(): 에러가 발생했을 때 호출
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>(); // String값을 HashMap의 형태로 전송

                // 사실'try/catch'문 없이 params.put("키값","밸류값"); 이것만 리턴해도 string 값이 전송은 된다.
                // 하지만, 한국어를 보낼때 한글이 깨지는 오류가 있어 아래와 같은 과정이 필요하다
                try {
                    params.put("code", code);
                    params.put(URLEncoder.encode("id", "UTF-8"), URLEncoder.encode(id, "UTF-8"));                                       //add string parameters
                    params.put(URLEncoder.encode("userid", "UTF-8"), URLEncoder.encode(addWriter.getText().toString(), "UTF-8"));     //add string parameters
                    params.put(URLEncoder.encode("writer", "UTF-8"), URLEncoder.encode(addWriter.getText().toString(), "UTF-8"));     //add string parameters
                    params.put(URLEncoder.encode("title", "UTF-8"), URLEncoder.encode(addTitle.getText().toString(), "UTF-8"));       //add string parameters
                    params.put(URLEncoder.encode("content", "UTF-8"), URLEncoder.encode(addContent.getText().toString(), "UTF-8"));  //add string parameters
                    // [중요] 안드로이드 쪽에서 인코딩을 해주고 스프링쪽에서 디코드를 해줘야한다.
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return params;
            }//getParams()

            // 파일 전송은 이곳에서 한다.
            // 스프링에서도 Multipart를 이용하여 파일을 업로드 했기 때문에 안드로이드에서도Multipart를 이용해야한다.
            // DB에 저장하기 위해서는 filepath나 filename이 아니라 파일을 직접 전송해야한다.

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();              //파일도 HashMap의 형태로 전송
                long imagename = System.currentTimeMillis();                 //파일이름은 시스템의 현재 시간으로 명명
                params.put("filename", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                //"filename" 이라는 키값으로 getFileDataFromDrawable()에 저장했던 "Bitmap"파일을 전송.
                return params;
            }//getByteData()
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        rQueue = Volley.newRequestQueue(GalleryUpdateActivity.this);
        rQueue.add(volleyMultipartRequest);                                                          // request queue 에 request 객체를 넣어준다.
    }

    // 다른곳에 입출력하기 전에 데이터를 임시로 바이트 배열에 담아서 변환등의 작업을 하는데 사용
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);               // Bitmap 데이터를 PNG의 포맷으로 byteArrayOutputStream에 담아준다.
        return byteArrayOutputStream.toByteArray();                                                 // byteArrayOutputStream에의 내용을 byte배열로 반환한다
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                // check if all permissions are granted
                if (report.areAllPermissionsGranted()) {
                    Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                }
                // check for permanent denial of any permission
                if (report.isAnyPermissionPermanentlyDenied()) {     // show alert dialog navigating to Settings
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
}