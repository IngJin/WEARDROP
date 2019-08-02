package com.project.weardrop.Other;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.weardrop.DTO.HugiDTO;
import com.project.weardrop.R;

import java.util.ArrayList;

public class HugiListAdapter extends RecyclerView.Adapter<HugiListAdapter.ViewHolder> {
    ArrayList<HugiDTO> mItems;

    public HugiListAdapter(ArrayList<HugiDTO> items) {
        this.mItems = items;
    }

    //새로운 뷰 홀더 생성(레이아웃을 만드는 부분), onCreateViewHolder는 최초에 레이아웃을 생성하고 뷰 홀더에 보관하는 부분
    @NonNull
    @Override
    public HugiListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //카드뷰로 작성한 아이템 레이아웃을 뷰 홀더에 전달
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hugicardview, parent, false);
        return new ViewHolder(view);
    }

    //뷰 홀더에 데이터를 설정하는 부분
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HugiDTO item = mItems.get(position);

        holder.imageView.setImageResource(item.getDrawableId());
        holder.title.setText(item.getTitle());
        holder.writer.setText(item.getWriter());
        //클릭이벤트 넣기
    }

    //아이템 수
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    //각각의 아이템의 레퍼런스를 저장할 뷰 홀더 클래스
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView writer;
        TextView writedate;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            title = itemView.findViewById(R.id.title);
            writer = itemView.findViewById(R.id.writer);
            writedate = itemView.findViewById(R.id.writedate);
        }
    }
}