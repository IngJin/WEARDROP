package com.project.weardrop.Other;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.nio.charset.Charset;

import static com.project.weardrop.Other.CommonMethod.ipConfig;

public class ListInsert extends AsyncTask<Void, Void, Void> {

    String id, name, date, uploadType;
    String imageUploadPathA, imageFilePathA, videoUploadPathA, videoFilePathA;

    // id, name, date, 업로드타입(이미지,비디오), 디비에 입력할 이미지경로, 실제업로드할 이미지경로,디비에 입력할 비디오경로, 실제업로드할 비디오경로
    public ListInsert(String id, String name, String date, String uploadType,
                      String imageUploadPathA, String imageFilePathA, String videoUploadPathA, String videoFilePathA){
        this.id = id;
        this.name = name;
        this.date = date;
        this.uploadType = uploadType;
        this.imageUploadPathA = imageUploadPathA;
        this.imageFilePathA = imageFilePathA;
        this.videoUploadPathA = videoUploadPathA;
        this.videoFilePathA = videoFilePathA;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가

            builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("name", name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("date", date, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("uploadType", uploadType, ContentType.create("Multipart/related", "UTF-8"));

            if(uploadType.equals("image")){
                builder.addTextBody("dbImgPath", imageUploadPathA, ContentType.create("Multipart/related", "UTF-8"));
                builder.addPart("image", new FileBody(new File(imageFilePathA)));
            }else if(uploadType.equals("video")){
                builder.addTextBody("dbImgPath", videoUploadPathA, ContentType.create("Multipart/related", "UTF-8"));
                builder.addPart("video", new FileBody(new File(videoFilePathA)));
            }


            String postURL = ipConfig + "/app/anInsertMulti";
            // 전송
            //InputStream inputStream = null;
            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            //inputStream = httpEntity.getContent();

            // 응답
                /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line + "\n");
                }
                inputStream.close();*/

            // 응답결과
                /*String result = stringBuilder.toString();
                Log.d("response", result);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("SecondSubAddActivity:imageFilePath1", "추가성공");

    }

}
