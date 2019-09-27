package com.project.weardrop.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.weardrop.R;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private Context mContext;
    private ArrayList<GalleryDTO> mGalleryList; //DTO
    private OnItemClickListener mListener;

    //190730 상세보기 구현
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public GalleryAdapter(Context context, ArrayList<GalleryDTO> galleryList){
        mContext = context;
        mGalleryList = galleryList;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.gallery_item2, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        return new GalleryViewHolder(v);

    }

    @TargetApi(Build.VERSION_CODES.O) //Oreo 버전 이상에서만 동작, 카드뷰 작동 안해서 넣어봄

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        GalleryDTO currentItem = mGalleryList.get(position);

        String id = currentItem.getId();
        String writer = currentItem.getWriter();
        String title = currentItem.getTitle();
        String content = currentItem.getContent();
        String filepath = currentItem.getFilepath();
        String writedate = currentItem.getWritedate();

        //holder.mTextViewID.setText(id);
        //holder.mTextViewWriter.setText(writer);
        //holder.mTextViewTitle.setText(title);
        //holder.mTextViewContent.setText(content);
        //holder.mTextViewWritedate.setText(writedate);
        Glide.with(mContext).load("http://112.164.58.217:80/weardrop/resources" + filepath).into(holder.mimageView); //.override(250,250)
        Log.i("xx",filepath);

    }

    @Override
    public int getItemCount() {
        return mGalleryList.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder{
        public ImageView mimageView;
        //public TextView mTextViewID;
        //public TextView mTextViewWriter;
        //public TextView mTextViewTitle;
        //public TextView mTextViewContent;
        public TextView mTextViewReadcnt;
        //public TextView mTextViewWritedate;

        public GalleryViewHolder(View itemView) {//190729 @NonNull : null허용 x / @Nullable : null허용
            super(itemView);
            mimageView = itemView.findViewById(R.id.image_view);
            //mTextViewID = itemView.findViewById(R.id.gal_id);
            //mTextViewWriter = itemView.findViewById(R.id.gal_writer);
            //mTextViewTitle = itemView.findViewById(R.id.gal_title);
            //mTextViewContent = itemView.findViewById(R.id.gal_content);
            //mTextViewWritedate = itemView.findViewById(R.id.gal_writedate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null){
                        int position =  getAdapterPosition();
                        if( position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }

                    }
                }
            });
        }
    }
}
