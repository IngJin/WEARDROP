package com.project.weardrop.Other;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import static com.project.weardrop.Other.CommonMethod.ipConfig;


/**
 * Created by LG on 2017-02-10.
 */

public class ListDelete extends AsyncTask<Void, Void, Void> {

    //생성자
    String id;
    String image;
    String selUploadType;

    public ListDelete(String id, String image, String selUploadType ) {
        this.id = id;
        this.image = image;
        this.selUploadType = selUploadType;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("delDbImgPath", image, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("selUploadType", selUploadType, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anDeleteMulti";
            // 전송
            //InputStream inputStream = null;
            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            //inputStream = httpEntity.getContent();

            // 응답
           /* BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }*/
            //inputStream.close();

            // 응답결과
            /*String result = stringBuilder.toString();
            Log.d("response", result);*/




            /*HttpClient client = new DefaultHttpClient();
            String postURL = ipConfig + "/app/anDelete";

            HttpPost post = new HttpPost(postURL);
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("imagePath", image));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);

            HttpResponse responsePost = client.execute(post);
            HttpEntity resEntity = responsePost.getEntity();
            if (resEntity != null) {
                Log.i("Sub1Del", EntityUtils.toString(resEntity));
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
