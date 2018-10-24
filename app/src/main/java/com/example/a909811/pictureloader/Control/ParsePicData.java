package com.example.a909811.pictureloader.Control;

import com.example.a909811.pictureloader.Model.Picture;
import com.example.a909811.pictureloader.Model.PictureUrl;
import com.example.a909811.pictureloader.Model.ReturnCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by 909811 on 2018/10/19.
 */

public class ParsePicData {

    private static final String TAG = "NetWork";
    
    public ArrayList<Picture> fetchPic(int count ,int page){
        String fetchUrl = PictureUrl.PICTURE_BASE_URL + count + "/" + page;
        ArrayList<Picture> pictures = new ArrayList<>();
        try {
            URL url = new URL(fetchUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if(connection.getResponseCode() == ReturnCode.NETWORK_RESPONSE_SUCCESS){
                InputStream inputStream = connection.getInputStream();
                byte[] data = readFromStream(inputStream);
                String result = new String(data,"UTF-8");
                pictures = parsePic(result);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pictures;
    }

    public ArrayList<Picture> parsePic(String content) throws JSONException {
        ArrayList<Picture> pictures = new ArrayList<>();
        JSONObject object = new JSONObject(content);
        JSONArray array = object.getJSONArray("results");
        for (int i =0;i < array.length();i++){
            JSONObject results = (JSONObject) array.get(i);
            Picture picture = new Picture();
            picture.setId(results.getString("_id"));
            picture.setCreateAt(results.getString("createdAt"));
            picture.setDesc(results.getString("desc"));
            picture.setPublishedAt(results.getString("publishedAt"));
            picture.setSource(results.getString("source"));
            picture.setType(results.getString("type"));
            picture.setUrl(results.getString("url"));
            picture.setUsed(results.getBoolean("used"));
            picture.setWho(results.getString("who"));
            pictures.add(picture);
        }
        return pictures;
    }
    public byte[] readFromStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        byte[] result;
        while ((len = inputStream.read(buffer)) != -1){
            outputStream.write(buffer,0,len);
        }
        inputStream.close();
        result = outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}
