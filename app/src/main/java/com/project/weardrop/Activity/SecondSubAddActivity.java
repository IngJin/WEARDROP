package com.project.weardrop.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.project.weardrop.Other.CommonMethod;
import com.project.weardrop.Other.ListInsert;
import com.project.weardrop.R;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

import static com.project.weardrop.Other.CommonMethod.ipConfig;
import static com.project.weardrop.Other.CommonMethod.isNetworkConnected;


public class SecondSubAddActivity extends AppCompatActivity {

    EditText etId , etName;
    DatePicker datePicker;

    String id = "", name = "", date = "";
    Button photoBtn;
    Button photoLoad;

    Button btnVideo, btnVideoLoad;

    ImageView imageView;
    VideoView videoView;
    MediaController m;
    //Uri previewUri;

    public String uploadType;
    public String imageFilePathA, imageUploadPathA;
    public String videoFilePathA, videoUploadPathA;

    final int CAMERA_REQUEST = 1000;
    final int LOAD_IMAGE = 1001;
    final int VIDEO_REQUEST = 1003;
    final int LOAD_VIDEO = 1004;

    File file = null;
    long fileSize = 0;

    java.text.SimpleDateFormat tmpDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1_add);

        tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");

        etId = (EditText) findViewById(R.id.etId);
        etName = (EditText) findViewById(R.id.etName);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        photoBtn = (Button) findViewById(R.id.btnPhoto);
        photoLoad = (Button) findViewById(R.id.btnLoad);

        btnVideo = findViewById(R.id.btnVideo);
        btnVideoLoad = findViewById(R.id.btnVideoLoad);

        imageView = findViewById(R.id.imageView);
        videoView = findViewById(R.id.videoView);
        videoViewSetting();

        imageView.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.GONE);

        Date tempDate = new Date();
        date = new DecimalFormat("0000").format(tempDate.getYear()) + "/" +
               new DecimalFormat("00").format(tempDate.getMonth() + 1)
               + "/" + new DecimalFormat("00").format(tempDate.getDay());

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                        date = new DecimalFormat("0000").format(year) + "/" +
                               new DecimalFormat("00").format(month + 1)
                               + "/" + new DecimalFormat("00").format(day);
                    }
                });

        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    file = createFile();
                    Log.d("FilePath ", file.getAbsolutePath());

                }catch(Exception e){
                    Log.d("SecondSubAddActivity", "Something Wrong", e);
                }

                imageView.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//사진 찍는 기능
                /*intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));*/
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(getApplicationContext(), "My37_CaptureIntent.fileprovider", file));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST);//1000 동작
                }

            }
        });

        photoLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);

                Intent intent=new Intent("android.media.action.VIDEO_CAPTURE");
//                intent.putExtra("android.intent.extra.durationLimit", VIDEO_DURATION);
//                intent.putExtra("android.intent.extra.videoQuality",1);
                intent.putExtra(android.provider.MediaStore.EXTRA_SIZE_LIMIT,30485760);
                intent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY,-1);

                startActivityForResult(intent, VIDEO_REQUEST);
            }
        });

        btnVideoLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, LOAD_VIDEO);
            }
        });


    }

    private void videoViewSetting() {
        m = new MediaController(this);
        m.setVisibility(View.GONE);
        videoView.setMediaController(m);
    }

    private File createFile() throws IOException {

        String imageFileName = "My" + tmpDateFormat.format(new Date()) + ".jpg";
        File storageDir = Environment.getExternalStorageDirectory();//외부 저장장치의 결대경로를 가져옴
        File curFile = new File(storageDir, imageFileName);

        return curFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {//RESULT_OK 정상적으로 종료를 함
            uploadType = "image";

            try {
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(file.getAbsolutePath());//파일의 절대경로를 가져옴
                //imageRotateAndResize 요건 리사이징 하는건데 신경쓸필요없음
                if(newBitmap != null){
                    imageView.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageFilePathA = file.getAbsolutePath();
                String uploadFileName = imageFilePathA.split("/")[imageFilePathA.split("/").length - 1];
                imageUploadPathA = ipConfig + "/app/resources/" + uploadFileName;

            } catch (Exception e){
                e.printStackTrace();
            }
        }else if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {
            uploadType = "image";

            try {
                String path = "";
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedImageUri);
                }
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(path);
                if(newBitmap != null){
                    imageView.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageFilePathA = path;
                Log.d("SecondSubAddActivity", "imageFilePathA Path : " + imageFilePathA);
                String uploadFileName = imageFilePathA.split("/")[imageFilePathA.split("/").length - 1];
                imageUploadPathA = ipConfig + "/app/resources/" + uploadFileName;

            } catch (Exception e){
                e.printStackTrace();
            }
        }else if ((requestCode == VIDEO_REQUEST || requestCode == LOAD_VIDEO)  && resultCode == RESULT_OK) {
            uploadType = "video";

            try {
                String path = "";
                // Get the url from data
                Uri selectedVideoUri = data.getData();
                if (null != selectedVideoUri) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedVideoUri);
                    Log.d("SecondSubAddActivity", path);
                }

                File file = new File(path);
                fileSize = file.length();
                Log.d("Sub1Add11:fileSize", "" + fileSize);

                videoFilePathA = path;
                String uploadFileName = videoFilePathA.split("/")[videoFilePathA.split("/").length - 1];
                videoUploadPathA = ipConfig + "/app/resources/" + uploadFileName;

                Log.d("Sub1Add11", path + " : " + videoUploadPathA);

                videoView.setVideoURI(selectedVideoUri);
                videoView.start();

                videoView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        m.show(0);
                        videoView.pause();
                    }
                }, 1000);

            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            Log.d("SecondSubAddActivity => ", "imagepath is null, whatever something is wrong!!");
        }

    }

    // Get the real path from the URI
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public void btnAddClicked(View view){
        if(isNetworkConnected(this) == true){

            if(fileSize <= 30000000){  // 파일크기가 30메가 보다 작아야 업로드 할수 있음
                id = etId.getText().toString();
                name = etName.getText().toString();
                date = new DecimalFormat("0000").format(datePicker.getYear()) + "/" +
                        new DecimalFormat("00").format(datePicker.getMonth() + 1)
                        + "/" + new DecimalFormat("00").format(datePicker.getDayOfMonth());

                ListInsert listInsert = new ListInsert(id, name, date, uploadType, imageUploadPathA,imageFilePathA, videoUploadPathA, videoFilePathA);
                listInsert.execute();

                Intent showIntent = new Intent(getApplicationContext(), SecondSubActivity.class);
                showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                        Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                        Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                startActivity(showIntent);

                finish();
            }else{
                // 알림창 띄움
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("알림");
                builder.setMessage("파일 크기가 30MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택해 주십시요!!!");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }

        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void btnCancelClicked(View view){
        finish();
    }

}
