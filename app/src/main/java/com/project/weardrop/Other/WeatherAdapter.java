
package com.project.weardrop.Other;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.weardrop.DTO.WeatherDTO;
import com.project.weardrop.R;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<WeatherDTO> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
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

    public void addItem(WeatherDTO data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }


    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView temp;
        LinearLayout layout;
        TextView setTempcody;

        ItemViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.card_name);
            temp = itemView.findViewById(R.id.card_weather);
            layout = itemView.findViewById(R.id.weather_view);
            setTempcody = itemView.findViewById(R.id.setTempcody);

        }

        void onBind(WeatherDTO data) {
            System.out.println(data.getWeather());

            if(data.getWeather().equals("Clouds")) {
                layout.setBackgroundResource(R.drawable.clouds);
            } else if(data.getWeather().equals("Clear")) {
                layout.setBackgroundResource(R.drawable.clear);
            } else if(data.getWeather().equals("Rain") || data.getWeather().equals("Drizzle")) {
                layout.setBackgroundResource(R.drawable.rain);
            } else if(data.getWeather().equals("Snow")) {
                layout.setBackgroundResource(R.drawable.show);
            } else if(data.getWeather().equals("Thunderstorm")) {
                layout.setBackgroundResource(R.drawable.thunderstorm);
            } else {
                layout.setBackgroundResource(R.drawable.mist);
            }

            double tmp = Double.parseDouble(data.getTemperature());

            if(tmp >= 27) {
                setTempcody.setText("나시티, 반바지, 민소매, 원피스 입니다.");
            } else if(tmp >= 23) {
                setTempcody.setText("반팔, 얇은 셔츠, 얇은 긴팔, 반바지, 면바지 입니다.");
            } else if(tmp >= 20) {
                setTempcody.setText("긴팔티, 가디건, 후드티, 면바지, 슬렉스, 스키니 입니다.");
            } else if(tmp >= 17) {
                setTempcody.setText("니트, 가디건, 후드티, 맨투맨, 청바지, 면바지, 슬랙스, 원피스 입니다.");
            } else if(tmp >= 12) {
                setTempcody.setText("자켓, 셔츠, 가디건, 간절기 야상, 살색스타킹 입니다.");
            } else if(tmp >= 10) {
                setTempcody.setText("트렌치코트, 간절기 야상, 여러겹 껴입기 입니다.");
            } else if(tmp >= 6) {
                setTempcody.setText("코트, 가죽자켓 입니다.");
            } else {
                setTempcody.setText("겨울 옷(야상, 패딩, 목도리 등등 다) 입니다.");
            }

            int i_num = (int)tmp;
            name.setText(data.getName());
            temp.setText(i_num + " ℃");
        }

    }
}
