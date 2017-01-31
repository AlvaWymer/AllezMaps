package com.helloworld.lyz.allezmap.util;

import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static com.google.android.gms.internal.zzir.runOnUiThread;
import static com.helloworld.lyz.allezmap.util.MyApplication.context;

/**
 * Created by paul on 2017/1/29.
 */

public class JsonNetUtil {

    private static RequestQueue mQueue;
    static StringRequest stringRequest;
    static Gson gson;
    String str;
    //    全路径
    static StringBuffer holepath;
    //    基本路径
    static StringBuffer addresspath;

    private static String KEY = "&key=AIzaSyCu6sn_9BkmsbeT2jksaYuP2uedw0pgeug";

    public static void connectNear(String locationlng,String stylename) {


        addresspath = new StringBuffer("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=");
                Toast.makeText(context, locationlng+"------", Toast.LENGTH_SHORT).show();
                //添加位置的经纬度
                holepath = addresspath.append(locationlng).append("&radius=10000&type=restaurant");
                if(stylename!=""){
                    holepath=holepath.append("&name=").append(stylename);
                }
        //        拼接后的地址
                holepath=holepath.append(KEY);


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
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        System.out.println(res);

//                        Status status = gson.fromJson(response, Status.class);
//
//                        System.out.println("status=" + status);
//                        System.out.println("-------------------------------------");
//                        List<Results> result = status.getResults();
//                        System.out.println("result=" + result);
//                        for (int i = 0; i < result.size(); i++) {
//                            System.out.println(result.get(i) + "88888888888888888");
//                        }
                    }

                });
            }
        });

    }

    //        gson = new Gson();
    //        mQueue = Volley.newRequestQueue(context);
    //
    //
    //        addresspath = new StringBuffer("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=");
    //        Toast.makeText(context, locationlng+"------", Toast.LENGTH_SHORT).show();
    //        //添加位置的经纬度
    //        holepath = addresspath.append(locationlng).append("&radius=10000&type=restaurant");
    //        if(stylename!=""){
    //            holepath=holepath.append("&name=").append(stylename);
    //        }
    ////        拼接后的地址
    //        holepath=holepath.append(KEY);
    //System.out.println(holepath);
    ////        mQueue.add(stringRequest);
    //
    //        //http://10.19.20.12/upgrade/test.txt是测试使用的json数据
    //        stringRequest = new StringRequest(holepath.toString(),
    //                new Response.Listener<String>() {
    //                    @Override
    //                    public void onResponse(String response) {
    //                        Log.d("TAG", response);
    //                        System.out.println("response=" + response);
    //                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
    ////
    ////                        Status status = gson.fromJson(response, Status.class);
    ////
    ////                        System.out.println("status=" + status);
    ////                        System.out.println("-------------------------------------");
    ////                        List<Results> result = status.getResults();
    ////                        System.out.println("result=" + result);
    ////                        for (int i = 0; i < result.size(); i++) {
    ////                            System.out.println(result.get(i) + "88888888888888888");
    ////                        }
    ////                        Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
    //                    }
    //                },



}
