package com.project.weardrop.Other;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.weardrop.Activity.NoticeDetailActivity;
import com.project.weardrop.DTO.NoticeDTO;
import com.project.weardrop.R;

import java.util.ArrayList;

public class NoticeDetailAdapter extends RecyclerView.Adapter<NoticeDetailAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<NoticeDTO> listData = new ArrayList<>();


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticedetail, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    public void addItem(NoticeDTO data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }


    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView1;

        private String id;
        private String title;
        private String writer;
        private String content;
        private String writedate;
        private NoticeDTO data;

        ItemViewHolder(View itemView){
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);

        }
        public void onBind(NoticeDTO data) {
            textView1.setText(data.getTitle());

            id = data.getId();
            title = data.getTitle();
            writer = data.getWriter();
            content = data.getContent();
            writedate = data.getWritedate();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            Intent intent = new Intent(v.getContext(), NoticeDetailActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("title", title);
            intent.putExtra("writer", writer);
            intent.putExtra("content", content);
            intent.putExtra("writedate", writedate);
            v.getContext().startActivity(intent);
        }
    }
}
