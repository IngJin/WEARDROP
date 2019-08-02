package com.project.weardrop.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.weardrop.DTO.WeatherDTO;
import com.project.weardrop.Other.GpsInfo;
import com.project.weardrop.Other.WeatherAdapter;
import com.project.weardrop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class WeatherActivity extends AppCompatActivity implements Runnable {

    private Button btnShowLocation;
    private String Lat;
    private String Lon;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    // GPSTracker class
    private GpsInfo gps;
    private WeatherAdapter adapter;
    private ArrayList<WeatherDTO> dataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        btnShowLocation = (Button) findViewById(R.id.btn_start);
        btnShowLocation.setVisibility(View.GONE);
        // GPS 정보를 보여주기 위한 이벤트 클래스 등록
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // 권한 요청을 해야 함
                if (!isPermission) {
                    callPermission();
                    return;
                }

                gps = new GpsInfo(WeatherActivity.this);
                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    Lat = String.valueOf(latitude); // 35
                    Lon = String.valueOf(longitude); // 100

                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }
            }
        });

        callPermission();  // 권한 요청을 해야 함
        btnShowLocation.callOnClick();

        Thread th = new Thread(WeatherActivity.this);
        th.start();

        init();
    }

    private void init() {
        RecyclerView CardView = findViewById(R.id.cardView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        CardView.setLayoutManager(linearLayoutManager);

        adapter = new WeatherAdapter();
        CardView.setAdapter(adapter);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }

    @Override
    public void run() {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat="
                + Lat + "&lon=" + Lon
                + "&appid=e5799cbefe2fe84bbfe2c2936d4816f4";
        try {
            HttpClient http = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            // http 에 인코딩된 값 세팅
            // post 방식으로 전달하고 응답은 response에 저장
            HttpResponse response = http.execute(httpPost);
            // response text를 String으로 변환
            String body = EntityUtils.toString(response.getEntity());
            // String 을 JSON으로...

            final JSONObject obj = new JSONObject(body);
            final String message = obj.getString("main");
            final JSONObject main = new JSONObject(message);
            final JSONArray weather = obj.getJSONArray("weather");
            final JSONObject object = (JSONObject) weather.get(0);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String weather_main = object.getString("main");
                        String from = main.getString("temp");
                        String name = obj.getString("name");

                        double to = Double.parseDouble(from);
                        to = to - 273.15;
                        String temp = Double.toString(to);
                        WeatherDTO dto = new WeatherDTO(weather_main, temp, name);

                        // System.out.println(dto.getWeather());
                        // System.out.println(dto.getIcon());
                        // System.out.println(dto.getName());
                        // System.out.println(dto.getTemperature());

                        // dto까진 문제없이 들어옴.


                        // 각 값이 들어간 data를 adapter에 추가합니다.
                        adapter.addItem(dto);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
