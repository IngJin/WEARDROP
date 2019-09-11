package com.project.weardrop.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.project.weardrop.R;

import java.util.ArrayList;

public class FreeListAdapter extends RecyclerView.Adapter<FreeListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Freelistitem> mItems;
    private static OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public FreeListAdapter(Context mContext, ArrayList<Freelistitem> items) {
        this.mContext = mContext;
        this.mItems = items;
    }

    //새로운 뷰 홀더 생성(레이아웃을 만드는 부분), onCreateViewHolder는 최초에 레이아웃을 생성하고 뷰 홀더에 보관하는 부분
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //카드뷰로 작성한 아이템 레이아웃을 뷰 홀더에 전달
        View view = LayoutInflater.from(mContext).inflate(R.layout.free_cardview, parent, false);
        return new ViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.O)

    //뷰 홀더에 데이터를 설정하는 부분
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Freelistitem item = mItems.get(position);

        String title = item.getTitle();
        String writer = item.getWriter();
        String writedate = item.getWritedate();

        holder.title.setText(title);
        holder.writer.setText(writer);
        holder.writedate.setText(writedate);
    }

    //아이템 수
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    //각각의 아이템의 레퍼런스를 저장할 뷰 홀더 클래스
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView writer;
        public TextView writedate;

        public ViewHolder(View itemView) {      //카드뷰
            super(itemView);
            title = itemView.findViewById(R.id.title);
            writer = itemView.findViewById(R.id.writer);
            writedate = itemView.findViewById(R.id.writedate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
