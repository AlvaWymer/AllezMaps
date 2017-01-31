package com.helloworld.lyz.allezmap.util;

import android.widget.Toast;

import com.google.gson.Gson;
import com.helloworld.lyz.allezmap.bean.Results;
import com.helloworld.lyz.allezmap.bean.Status;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import static com.google.android.gms.internal.zzir.runOnUiThread;
import static com.helloworld.lyz.allezmap.util.MyApplication.context;

/**
 * Created by paul on 2017/1/29.
 */

public class JsonNetUtil {


    static Gson gson;
    String str;
    //    全路径
    static StringBuffer holepath;
    //    基本路径
    static StringBuffer addresspath;

    private static String KEY = "&key=AIzaSyCu6sn_9BkmsbeT2jksaYuP2uedw0pgeug";

    public static void connectNearOne(String locationlng, String stylename) {

        //地址拼接第一部分
        addresspath = new StringBuffer("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=");
Toast.makeText(context, locationlng + "------", Toast.LENGTH_SHORT).show();
        //添加位置的经纬度
        holepath = addresspath.append(locationlng).append("&radius=5000&type=restaurant");
        if (stylename != "") {
            holepath = holepath.append("&name=").append(stylename);
        }
        //        拼接后的地址
        holepath = holepath.append(KEY);


        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url(holepath.toString())
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String res = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        System.out.println(res);
                        gson = new Gson();
                        Status status = gson.fromJson(res, Status.class);

                        System.out.println("status=" + status);
                        System.out.println("-------------------------------------");
                        List<Results> result = status.getResults();
                        System.out.println("result=" + result);
                        for (int i = 0; i < result.size(); i++) {
                            System.out.println(result.get(i) + "88888888888888888");
                        }
                    }

                });
            }
        });

    }




}
