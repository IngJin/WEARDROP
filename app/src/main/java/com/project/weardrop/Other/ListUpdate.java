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

public class ListUpdate extends AsyncTask<Void, Void, Void> {

    String id, name, date, uploadType, pUploadType, pUploadPathU;
    String imageUploadPathU, imageFilePathU, videoUploadPathU, videoFilePathU;

    public ListUpdate(String id, String name, String date, String uploadType, String pUploadType, String pUploadPathU,
                      String imageUploadPathU, String imageFilePathU, String videoUploadPathU, String videoFilePathU){
        this.id = id;
        this.name = name;
        this.date = date;
        this.uploadType = uploadType;
        this.pUploadType = pUploadType;
        this.pUploadPathU = pUploadPathU;
        this.imageUploadPathU = imageUploadPathU;
        this.imageFilePathU = imageFilePathU;
        this.videoUploadPathU = videoUploadPathU;
        this.videoFilePathU = videoFilePathU;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            String postURL = "";
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가
            builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("name", name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("date", date, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("uploadType", uploadType, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("pUploadType", pUploadType, ContentType.create("Multipart/related", "UTF-8"));

            Log.d("Sub1Update11", id);
            Log.d("Sub1Update12", name);
            Log.d("Sub1Update13", date);
            Log.d("Sub1Update14", uploadType);
            Log.d("Sub1Update15", pUploadType);
            Log.d("Sub1Update16", pUploadPathU);
            Log.d("Sub1Update17", imageUploadPathU);
            /*Log.d("Sub1Update18", imageFilePathU);
            Log.d("Sub1Update19", videoUploadPathU);
            Log.d("Sub1Update20", videoFilePathU);*/

            if(uploadType.equals("image") && !imageFilePathU.equals("")){
                Log.d("Sub1Update:postURL", "1");
                builder.addTextBody("dbImgPath", imageUploadPathU, ContentType.create("Multipart/related", "UTF-8"));
            //    if(!imageUploadPathU.equals(pUploadPathU)){
                    builder.addTextBody("pDbImgPath", pUploadPathU, ContentType.create("Multipart/related", "UTF-8"));
                    builder.addPart("image", new FileBody(new File(imageFilePathU)));
            //    }
                postURL = ipConfig + "/app/anUpdateMulti";
            }else if(uploadType.equals("video") && !videoFilePathU.equals("")){
                Log.d("Sub1Update:postURL", "2");
                builder.addTextBody("dbImgPath", videoUploadPathU, ContentType.create("Multipart/related", "UTF-8"));
            //    if(!videoUploadPathU.equals(pUploadPathU)){
                    builder.addTextBody("pDbImgPath", pUploadPathU, ContentType.create("Multipart/related", "UTF-8"));
                    builder.addPart("video", new FileBody(new File(videoFilePathU)));
            //    }
                postURL = ipConfig + "/app/anUpdateMulti";
            }else if(uploadType.equals("image") && imageFilePathU.equals("")){
                Log.d("Sub1Update:postURL", "3");
                postURL = ipConfig + "/app/anUpdateMultiNo";
            }else if(uploadType.equals("video") && videoFilePathU.equals("")){
                Log.d("Sub1Update:postURL", "4");
                postURL = ipConfig + "/app/anUpdateMultiNo";
            }else{
                Log.d("Sub1Update:postURL", "5");
            }
            Log.d("Sub1Update:postURL", postURL);
            //postURL = ipConfig + "/app/anUpdateMulti";
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
                }*/
            //inputStream.close();

            // 응답결과
               /* String result = stringBuilder.toString();
                Log.d("response", result);*/



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //dialog.dismiss();

    }


}
