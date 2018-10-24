package com.example.a909811.pictureloader.Model;

/**
 * Created by tinker on 2018/10/24.
 */

public class PictureUrl {
    public static String PICTURE_BASE_URL = "https://gank.io/api/data/福利/";
    public static int PICTURE_SHOW_COUNT = 10;

    public static String getPictureBaseUrl() {
        return PICTURE_BASE_URL;
    }

    public static void setPictureBaseUrl(String pictureBaseUrl) {
        PICTURE_BASE_URL = pictureBaseUrl;
    }

    public static int getPictureShowCount() {
        return PICTURE_SHOW_COUNT;
    }

    public static void setPictureShowCount(int pictureShowCount) {
        PICTURE_SHOW_COUNT = pictureShowCount;
    }
}
