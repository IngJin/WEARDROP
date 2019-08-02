package com.project.weardrop.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.project.weardrop.DTO.MyItemDTO;
import com.project.weardrop.Other.ListDelete;
import com.project.weardrop.Other.ListSelect;
import com.project.weardrop.Other.MyListAdapter;
import com.project.weardrop.R;

import java.io.File;
import java.util.ArrayList;

// SecondDetailActivity.java
public class SecondDetailActivity extends AppCompatActivity {
    TextView d_id;
    TextView d_name;
    TextView d_title;
    TextView d_date;
    TextView d_price;
    TextView d_content;
    ImageView d_imageView;
    VideoView d_videoView;
    String selName, selDate, selId, selTitle, selContent, selImg, selUploadType, selVideoImage, selPrice;
    MyListAdapter adapter; // 삭제
    MyItemDTO selItem = null;
    ArrayList<MyItemDTO> myItemArrayList;
    ListSelect listSelect;
    ImageLoader imageLoader;
    ProgressDialog progressDialog;
    Button btn2, btn3;

 //   MediaController m;
    public String imagePath;
    public String uploadType, pUploadType;
    public String pUploadPathU;
 //   public String imageFilePathU = "", imageUploadPathU;
 //   public String videoFilePathU = "", videoUploadPathU;


    File file = null;
    long fileSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 이미지 로딩 써드파트(무조건 제일앞에 셋팅, 이미지로더 사용하기 위해서)
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.blank) // resource or drawable
                .showImageForEmptyUri(R.drawable.blank) // resource or drawable (이미지 없을때 블랭크 보여줌)
                .showImageOnFail(R.drawable.blank)// resource or drawable (이미지 실패했을때 블랭크 보여줌)
                .build();

        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(getApplicationContext())
                        //  .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 10 * 1000))
                        .defaultDisplayImageOptions(options)
                        .build();

        imageLoader.getInstance().init(config); // Get singleton instance 여기까지 적어줌


        d_id = findViewById(R.id.d_id);
        d_name = findViewById(R.id.d_name);
        d_title = findViewById(R.id.d_title);
        d_date = findViewById(R.id.d_date);
        d_price = findViewById(R.id.d_price);
        d_content = findViewById(R.id.d_content);
        d_imageView = findViewById(R.id.d_imageView);
        d_videoView = findViewById(R.id.d_videoView);

        d_imageView.setVisibility(View.VISIBLE);
        d_videoView.setVisibility(View.GONE);

        Intent intent = getIntent();


//        final int id = intent.getIntExtra("id", 0);
        String s_id = intent.getStringExtra("d_id");
        String s_name = intent.getStringExtra("d_name");
        String s_title = intent.getStringExtra("d_title");
        String s_date = intent.getStringExtra("d_date");
        String s_price = intent.getStringExtra("d_price");
        String s_content = intent.getStringExtra("d_content");
        String s_imageView = intent.getStringExtra("d_imageView");
        String s_videoView = intent.getStringExtra("d_videoView");

        imagePath = intent.getStringExtra("img");
        uploadType = intent.getStringExtra("uploadType");
        selVideoImage = intent.getStringExtra("selVideoImage");

        //Log.d("Sub1Update11", imagePath);
        //uploadPathU = imagePath;
        pUploadType = uploadType;
        pUploadPathU = imagePath;
        //imageFilePathU = imagePath;
     //   imageUploadPathU = imagePath;
        //videoFilePathU = imagePath;
     //   videoUploadPathU = imagePath;

        d_id.setText(s_id);
        d_name.setText(s_name);
        d_title.setText(s_title);
        d_date.setText(s_date);
        d_price.setText(s_price);
        d_content.setText(s_content);
        d_videoView.setVisibility(View.GONE);
        d_imageView.setVisibility(View.VISIBLE);


        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
/*        listSelect = new ListSelect(myItemArrayList, adapter, progressDialog);
        listSelect.execute();//ATask 안에 DB연동하는거 들어있음*/
    }
    // uploadType , selVideoImage 필수목록

}

            // 타입이 이미지일시
        /*  if (uploadType.equals("image")) {
            d_videoView.setVisibility(View.GONE);
            d_imageView.setVisibility(View.VISIBLE);
            //ImageSize targetSize1 = new ImageSize(150, 150);
            ImageLoader.getInstance().displayImage(imagePath, d_imageView);
            // 타입이 비디오일시
        } else if (uploadType.equals("video")) {
            d_videoView.setVisibility(View.GONE);
            d_imageView.setVisibility(View.VISIBLE);
            d_imageView.setImageResource(R.drawable.video);

            ImageLoader.getInstance().displayImage(selVideoImage, d_imageView, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    Log.d("Sub1Update:String s", s);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    Log.d("Sub1Update:ImageFail", failReason.getCause().toString());
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                }
            });
        }
    }

    private void videoViewSetting() {
        m = new MediaController(this);
        m.setVisibility(View.GONE);
        d_videoView.setMediaController(m);
    }
        // 파일네임 +날짜
        private File createFile () throws IOException {
            java.text.SimpleDateFormat tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
            // 파일네임 : my @@.jpg
            String imageFileName = "My" + tmpDateFormat.format(new Date()) + ".jpg";
            File storageDir = Environment.getExternalStorageDirectory();
            File curFile = new File(storageDir, imageFileName);

            return curFile;
        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
                uploadType = "image";

                try {
                    // 이미지 돌리기 및 리사이즈
                    Bitmap newBitmap = CommonMethod.imageRotateAndResize(file.getAbsolutePath());
                    if (newBitmap != null) {
                        d_imageView.setImageBitmap(newBitmap);
                    } else {
                        Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                    }

                    imageFilePathU = file.getAbsolutePath();
                    String uploadFileName = imageFilePathU.split("/")[imageFilePathU.split("/").length - 1];
                    imageUploadPathU = ipConfig + "/app/resources/" + uploadFileName;

                    ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

                    Log.d("Sub1Update:picPath", file.getAbsolutePath());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {
                uploadType = "image";

                try {
                    String path = "";
                    // Get the url from data
                    Uri selectedImageUri = data.getData();
                    if (selectedImageUri != null) {
                        // Get the path from the Uri
                        path = getPathFromURI(selectedImageUri);
                        //Log.d("Main", "Image Path : " + path);
                    }

                    // 이미지 돌리기 및 리사이즈
                    Bitmap newBitmap = CommonMethod.imageRotateAndResize(path);
                    if (newBitmap != null) {
                        d_imageView.setImageBitmap(newBitmap);
                    } else {
                        Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                    }

                    imageFilePathU = path;
                    String uploadFileName = imageFilePathU.split("/")[imageFilePathU.split("/").length - 1];
                    imageUploadPathU = ipConfig + "/app/resources/" + uploadFileName;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if ((requestCode == VIDEO_REQUEST || requestCode == LOAD_VIDEO) && resultCode == RESULT_OK) {
                uploadType = "video";

                try {
                    String path = "";
                    // Get the url from data
                    Uri selectedVideoUri = data.getData();

                    if (null != selectedVideoUri) {
                        // Get the path from the Uri
                        path = getPathFromURI(selectedVideoUri);
                        Log.d("Sub1Update", path);
                    }

                    File file = new File(path);
                    fileSize = file.length();
                    Log.d("Sub1Update:fileSize", "" + fileSize);

                    videoFilePathU = path;
                    String uploadFileName = videoFilePathU.split("/")[videoFilePathU.split("/").length - 1];
                    videoUploadPathU = ipConfig + "/app/resources/" + uploadFileName;

                    Log.d("Sub1Update9", path + " : " + videoFilePathU);
                    Log.d("Sub1Update10", path + " : " + videoUploadPathU);

                    d_videoView.setVideoURI(selectedVideoUri);
                    d_videoView.start();

                    d_videoView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            m.show(0);
                            d_videoView.pause();
                        }
                    }, 1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("Main => ", "imagepath is null, whatever something is wrong!!");
            }
        }

        public String getPathFromURI (Uri contentUri){
            String res = null;
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }
            cursor.close();
            return res;
        }*/




