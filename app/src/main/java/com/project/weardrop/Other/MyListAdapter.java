package com.project.weardrop.Other;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.weardrop.DTO.MyItemDTO;
import com.project.weardrop.R;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


public class MyListAdapter extends BaseAdapter {
    MediaController m;
    Dialog dialog;
    Context context;
    LayoutInflater Inflater;
    ArrayList<MyItemDTO> arrayList;
    int layout;

    public MyListAdapter(Context context, int layout, ArrayList<MyItemDTO> arrayList) {
        this.context = context;
        this.Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
        this.layout = layout;
    }

    public void removeAllItem(){
        arrayList.clear();
        notifyDataSetChanged();
    }

    public int getCount() {
        return arrayList.size();
    }

    public Object getItem(int position) {
        return arrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // 각 항목의 뷰 생성
    public View getView(final int position, View convertView, ViewGroup parent) {

        final PersonViewHolder viewHolder;
        final String uploadType = arrayList.get(position).uploadType;

        // 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다
        if(convertView == null) {
            convertView = Inflater.inflate(layout, parent, false);

            viewHolder = new PersonViewHolder();
            viewHolder.id =  convertView.findViewById(R.id.tv_id);
            viewHolder.title = convertView.findViewById(R.id.tv_title);
            viewHolder.name =  convertView.findViewById(R.id.tv_name);
            viewHolder.date =  convertView.findViewById(R.id.tv_date);
            viewHolder.iv_img =  convertView.findViewById(R.id.iv_img);
            viewHolder.progressBar = convertView.findViewById(R.id.progressBar);
            viewHolder.imageVideo = convertView.findViewById(R.id.imageVideo);

            convertView.setTag(viewHolder);
        }else{   // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다
            viewHolder = (PersonViewHolder) convertView.getTag();
        }

        viewHolder.id.setText(arrayList.get(position).getId());
        viewHolder.title.setText(arrayList.get(position).getTitle());
        viewHolder.name.setText(arrayList.get(position).getName());
        viewHolder.date.setText(arrayList.get(position).getDate());
        viewHolder.iv_img.setImageResource(R.drawable.blank);

        if(arrayList.get(position).uploadType.equals("image")){
            viewHolder.imageVideo.setVisibility(View.GONE);
            viewHolder.iv_img.setImageResource(R.drawable.blank);

            ImageLoader.getInstance().displayImage(arrayList.get(position).image1.toString(),
                    viewHolder.iv_img, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {
                            viewHolder.progressBar.setVisibility(View.VISIBLE);
                           // Log.d("Sub1 : String s", s);
                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {
                            viewHolder.progressBar.setVisibility(View.GONE);
//                            Log.d("Sub1:ImageFail", failReason.getCause().toString());
                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            viewHolder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {
                            viewHolder.progressBar.setVisibility(View.GONE);
                        }
                    });

        }else if(arrayList.get(position).uploadType.equals("video")){
            // 비디오 파일에서 이미지 뽑아낸것 이미지뷰에 셋팅
            viewHolder.imageVideo.setVisibility(View.GONE);
            String path = arrayList.get(position).videoimage.toString(); // 이미지경로
            Log.d("Sub1Adapter", path);
            viewHolder.iv_img.setImageResource(R.drawable.video);

            ImageLoader.getInstance().displayImage(arrayList.get(position).videoimage.toString(),
                    viewHolder.iv_img, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {
                            viewHolder.progressBar.setVisibility(View.VISIBLE);
                            //Log.d("Sub1 : String s", s);
                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {
                            viewHolder.progressBar.setVisibility(View.GONE);
                            //Log.d("Sub1:ImageFail", failReason.getCause().toString());
                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            viewHolder.progressBar.setVisibility(View.GONE);
                            viewHolder.imageVideo.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {
                            viewHolder.progressBar.setVisibility(View.GONE);
                        }
                    });

        }

        //이미지를 클릭했을때 그이미지를 클릭하고 갱신해야하는 경우(좋아요, 싫어요 등) 먼저 서버에 업데이트를 시킨후 서버에서
        // 수행하는 컨트롤러에서 return "redirect:anSelect"; 로 하여 다시 데이터를 database에서 검색하여 안드로이드에서 검색한 것을
        // 받는(예를 들어 ListInsertLike extends Asynck)곳에서 anSelect 받는구문으로 바꾸어 Adapter안의 getView에서 구현하여 나타낸다.
        //이미지 클릭했을때

        viewHolder.iv_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog = new Dialog(context);
                dialog.setContentView(R.layout.listimage_dialog);
                dialog.setTitle("Image/Video");

                final MyVideoview myVideoview1 = dialog.findViewById(R.id.videoView1);
                ImageView iv = dialog.findViewById(R.id.image);
                int displayWidth = iv.getMaxWidth();
                int displayHeight = iv.getMaxHeight();

                Log.d("Sub1Adapter", displayWidth + " : " + displayHeight);

                Button btnExit = dialog.findViewById(R.id.btnExit);

                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                // 비디오뷰가 준비되었을때
                myVideoview1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        //setLayoutParams 메소드가 호출되면 onMeasure 메소드가 호출된다.
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(900, 900);
                        myVideoview1.setLayoutParams(layoutParams);
                    }
                });

                if(uploadType.equals("image")) {
                     myVideoview1.setVisibility(View.GONE);
                    iv.setVisibility(View.VISIBLE);

                    ImageLoader.getInstance().displayImage(arrayList.get(position).image1.toString(), iv);
                }else if(uploadType.equals("video")){
                    myVideoview1.setVisibility(View.VISIBLE);
                    iv.setVisibility(View.GONE);

                    MediaController m = new MediaController(context);
                    myVideoview1.setMediaController(m);
                    myVideoview1.setVideoURI(Uri.parse(arrayList.get(position).image1.toString()));
                    myVideoview1.requestFocus();
                    myVideoview1.start();
                }

                TextView id = (TextView) dialog.findViewById(R.id.id);
                TextView title = (TextView)dialog.findViewById(R.id.title);
                TextView name = (TextView) dialog.findViewById(R.id.name);
                TextView h_date = (TextView) dialog.findViewById(R.id.h_date);
                id.setText(arrayList.get(position).getId().toString());
                title.setText(arrayList.get(position).getTitle().toString());
                name.setText(arrayList.get(position).getName().toString());
                h_date.setText(arrayList.get(position).getDate().toString());

                dialog.show();
            }

        });


        return convertView;
    }

    public static class PersonViewHolder
    {
        public TextView id;
        public TextView title;
        public TextView content;
        public TextView name;
        public TextView date;
        public ImageView iv_img;
        public ProgressBar progressBar;
        public ImageView imageVideo;
    }



}



