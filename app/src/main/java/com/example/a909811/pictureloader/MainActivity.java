package com.example.a909811.pictureloader;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a909811.pictureloader.Control.ParsePicData;
import com.example.a909811.pictureloader.Control.PictureLoader;
import com.example.a909811.pictureloader.Model.Picture;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {


    private Button btn_show_girls;
    private ImageView iv_show_girls;
    private Button btn_refresh;
    private int curPos = 0;
    private int page = 1;
    private PictureLoader loader;
    private ArrayList<Picture> data;
    private ParsePicData parsePicData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parsePicData = new ParsePicData();
        loader = new PictureLoader();
        initData();
        initUI();
    }

    private void initData(){
        data = new ArrayList<>();
        new PictureTask(page).execute();
    }

    private class PictureTask extends AsyncTask<Void,Void,ArrayList<Picture>>{

        private int  page;
        public PictureTask(int page) {
            this.page = page;
        }

        @Override
        protected void onPostExecute(ArrayList<Picture> pictures) {
            super.onPostExecute(pictures);
            data.clear();
            data.addAll(pictures);
        }

        @Override
        protected ArrayList<Picture> doInBackground(Void... voids) {
            return parsePicData.fetchPic(10,page);
        }
    }
    private void initUI(){
        iv_show_girls = findViewById(R.id.iv_show_girls);
        btn_show_girls = findViewById(R.id.btn_show_girls);
        btn_refresh = findViewById(R.id.btn_refresh);
        btn_show_girls.setOnClickListener(this);
        btn_refresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_show_girls:
                if(data != null && !data.isEmpty()){
                    if(curPos >9){
                        curPos = 0;
                    }
                    loader.load(iv_show_girls,data.get(curPos).getUrl());
                    curPos++;
                }
                break;
            case R.id.btn_refresh:
                page++;
                new PictureTask(page).execute();
                curPos = 0;
                break;
            default:
                break;
        }
    }
}
