package com.project.weardrop.Other;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.project.weardrop.DTO.MyItemDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.project.weardrop.Other.CommonMethod.ipConfig;

// doInBackground 파라미터 타입, onProgressUpdate파라미터 타입, onPostExecute 파라미터 타입 순서
// AsyncTask <Params, Progress, Result> 순서임
public class ListSelect extends AsyncTask<Void, Void, Void> {
    // 생성자
    ArrayList<MyItemDTO> myItemArrayList;
    MyListAdapter adapter;
    ProgressDialog progressDialog;

    public ListSelect(ArrayList<MyItemDTO> myItemArrayList, MyListAdapter adapter, ProgressDialog progressDialog) {
        this.myItemArrayList = myItemArrayList;
        this.adapter = adapter;
        this.progressDialog = progressDialog;
    }

    //무조건 복붙해야함
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        myItemArrayList.clear(); // 쌓이는거 방지하기위해 클리어 시켜야함
        String result = "";
        String postURL = ipConfig + "/app/anSelectMulti";
        //  ipConfig(자기 아이피 써주면 됨)
        //  public static String  ipConfig = "http://(자기아이피)192.168.0.163:(포트번호)8989";
        //  + /톰캣에서 실행하고있는 프로젝트 패스/
        //  spring - servers - server.xml
        //  <Context docBase="AnSpring_App" path="/app" reloadable="true" source="org.eclipse.jst.jee.server:AnSpring_App"/></Host>

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
                // postURL을 서버에 보내면 ip알고,프로젝트 알고,다 알고 아무튼 컨트롤러(AController)에서 받으면
                // 스프링의 AController
            	/*@RequestMapping(value="/anSelectMulti", method = {RequestMethod.GET, RequestMethod.POST}  )
            	    public String anSelectMulti(HttpServletRequest req, Model model){
            		System.out.println("anSelectMulti()");

            		command = new ASelectMultiCommand();
            		command.execute(model);

            		return "anSelectMulti";
            	}*/
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            readJsonStream(inputStream);

            /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            String jsonStr = stringBuilder.toString();

            inputStream.close();*/

        } catch (Exception e) {
            Log.d("SecondSubActivity", e.getMessage());
            e.printStackTrace();
        }finally {
            if(httpEntity != null){
                httpEntity = null;
            }
            if(httpResponse != null){
                httpResponse = null;
            }
            if(httpPost != null){
                httpPost = null;
            }
            if(httpClient != null){
                httpClient = null;
            }

        }

        return null;
    }

    @Override//실행 끝났을때
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(progressDialog != null){
            progressDialog.dismiss();
        }

        Log.d("SecondSubActivity", "List Select Complete!!!");
        adapter.notifyDataSetChanged();//데이터가 추가되면 어댑터도 갱신
    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                myItemArrayList.add(readMessage(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    public MyItemDTO readMessage(JsonReader reader) throws IOException {
        String id = "", title = "", content = "",  name = "", date = "", image = "", uploadtype = "", videoimage = "";
     //   int readcnt = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("id")) {
                id = reader.nextString();
            } else if (readStr.equals("name")) {
              name = reader.nextString();
            }else if(readStr.equals("title")) {
                title = reader.nextString();
            } else if (readStr.equals("hire_date")) {
                String[] temp = reader.nextString().replace("월", "-").replace(",", "-")
                                                    .replace(" ", "").split("-");
                date = temp[2] + "-" + temp[0] + "-" + temp[1];
            } else if (readStr.equals("image1")) {
                image = reader.nextString();
            } else if (readStr.equals("uploadtype")) {
                uploadtype = reader.nextString();
            }else if (readStr.equals("videoImagePath")) {
                videoimage = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("listselect:myitem", id + "," + title + "," + content
                +","+ name + "," + date + "," + image
                                            + "," + uploadtype + "," + videoimage);
        return new MyItemDTO(id, title, content, name, date, image, uploadtype, videoimage);

    }

    /*public List<Double> readDoublesArray(JsonReader reader) throws IOException {
        List<Double> doubles = new ArrayList<Double>();

        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return doubles;
    }

    public User readUser(JsonReader reader) throws IOException {
        String username = null;
        int followersCount = -1;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                username = reader.nextString();
            } else if (name.equals("followers_count")) {
                followersCount = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new User(username, followersCount);
    }*/

}
