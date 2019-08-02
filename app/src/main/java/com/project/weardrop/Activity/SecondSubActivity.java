package com.project.weardrop.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.project.weardrop.DTO.MyItemDTO;
import com.project.weardrop.Other.ListSelect;
import com.project.weardrop.Other.MyListAdapter;
import com.project.weardrop.R;

import java.util.ArrayList;

import static com.project.weardrop.Other.CommonMethod.isNetworkConnected;


public class SecondSubActivity extends AppCompatActivity {

    ListSelect listSelect;

    ArrayList<MyItemDTO> myItemArrayList;
    Button btn1, btn4;
    ListView listView1;
    MyListAdapter adapter;

    MyItemDTO selItem = null;
    String selName, selDate, selId, selTitle, selContent, selImg, selUploadType, selVideoImage;

    ImageLoader imageLoader;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//막 생성되었을때 리스트를 보여주는것
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1);

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

        //ListView시작(DB에서 가져온걸 담음)
        myItemArrayList = new ArrayList<MyItemDTO>();
        adapter = new MyListAdapter(this, R.layout.sub1_item, myItemArrayList);

        listView1 = findViewById(R.id.listView1);
/*        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SecondDetailActivity.class);

               intent.putExtra("d_id", selId);
                intent.putExtra("d_title", selTitle);
                intent.putExtra("d_content", selContent);
                intent.putExtra("d_name", selName);
                intent.putExtra("d_date", selDate);
                intent.putExtra("d_img", selImg);
                intent.putExtra("d_uploadType", selUploadType);
                intent.putExtra("d_selVideoImage", selVideoImage);

                startActivity(intent);
            }
        });*/
    // Friends

        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override                // 상세화면
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 리스트뷰에 커스텀어댑터를 적용한거에서 각항목 가져오기
                selItem = (MyItemDTO) adapter.getItem(position);
                selId = selItem.getId();            // 아이디
                selTitle = selItem.getTitle();              // 제목
                selContent = selItem.getContent();       //  내용
                selName = selItem.getName();        // 작성자
                selDate = selItem.getDate();        // 작성일
                selImg = selItem.getImage1();
                selUploadType = selItem.getUploadType();
                selVideoImage = selItem.getVideoimage();

                Intent intent = new Intent(getApplicationContext(), SecondDetailActivity.class);
                intent.putExtra("d_id", selId);
                intent.putExtra("d_title", selTitle);
                intent.putExtra("d_content", selContent);
                intent.putExtra("d_name", selName);
                intent.putExtra("d_date", selDate);
                intent.putExtra("d_img", selImg);
                intent.putExtra("d_uploadType", selUploadType);
                intent.putExtra("d_selVideoImage", selVideoImage);
                startActivity(intent);

            }
        });
        //ListView종료

        btn1 = findViewById(R.id.button1);
        btn4 = findViewById(R.id.button4);

        //myVideoview1 = findViewById(R.id.videoView1);

//        listThreadView();  // xml파싱하여 DB select 하기
        listSelect = new ListSelect(myItemArrayList, adapter, progressDialog);
        listSelect.execute();//ATask 안에 DB연동하는거 들어있음


    }

    //추가
    public void btn1Clicked(View v){
        if(isNetworkConnected(this) == true){
            Intent intent = new Intent(getApplicationContext(), SecondSubAddActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }


    }

  /*  // 수정
    public void btn2Clicked(View v){
        if(isNetworkConnected(this) == true){
            if(selId != null && !selId.equals("")){
                Intent intent = new Intent(getApplicationContext(), Sub1Updatee.class);
                intent.putExtra("id", selId);
                intent.putExtra("title", selTitle);
                intent.putExtra("content", selContent);
                intent.putExtra("name", selName);
                intent.putExtra("date", selDate);
                intent.putExtra("img", selImg);
                intent.putExtra("uploadType", selUploadType);
                intent.putExtra("selVideoImage", selVideoImage);

                startActivity(intent);

            }else {
                Toast.makeText(getApplicationContext(), "항목 선택을 해 주세요",
                        Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    // 삭제
    public void btn3Clicked(View v){
        if(isNetworkConnected(this) == true){
            if(selImg != null && !selImg.equals("")) {
                Log.d("SecondSubActivity : selImg => ", selImg);

                ListDelete listDelete = new ListDelete(selId, selImg, selUploadType);
                listDelete.execute();

                // 화면갱신
                Intent refresh = new Intent(this, SecondSubActivity.class);
                startActivity(refresh);
                this.finish(); //

                adapter.notifyDataSetChanged();
            }else {
                Toast.makeText(getApplicationContext(), "항목 선택을 해 주세요",
                        Toast.LENGTH_SHORT).show();
            }


        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }

    }*/

    // 돌아가기
    public void btn4Clicked(View v){
        finish();
    }

    // 이미 화면이 있을때 받는곳
    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("SecondSubActivity", "onNewIntent() 호출됨");

        // 새로고침하면서 이미지가 겹치는 현상 없애기 위해...
        adapter.removeAllItem();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("데이터 업로딩");
        progressDialog.setMessage("데이터 업로딩 중입니다\n" + "잠시만 기다려주세요 ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        processIntent(intent);

    }

    private void processIntent(Intent intent){
        if(intent != null){
            listSelect = new ListSelect(myItemArrayList, adapter, progressDialog);
            listSelect.execute();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}