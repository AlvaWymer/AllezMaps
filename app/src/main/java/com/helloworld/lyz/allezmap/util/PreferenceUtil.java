package com.helloworld.lyz.allezmap.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：SharedPreferences的管理类
 *
 */
public class PreferenceUtil {

    private static SharedPreferences mSharedPreferences = null;
    private static Editor mEditor = null;
    
    public static void init(Context context){
    	if (null == mSharedPreferences) {
    		mSharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context) ;
    	}
    }
    
    public static void removeKey(String key){
        mEditor = mSharedPreferences.edit();
        mEditor.remove(key);
        mEditor.commit();
    }
    
    public static void removeAll(){
        mEditor = mSharedPreferences.edit();
        mEditor.clear();
        mEditor.commit();
    }

    public static void commitString(String key, String value){
        mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.commit();
    }
    
    public static String getString(String key, String faillValue){
            return mSharedPreferences.getString(key, faillValue);
    }
    
    public static void commitInt(String key, int value){
        mEditor = mSharedPreferences.edit();
        mEditor.putInt(key, value);
        mEditor.commit();
    }
    
    public static int getInt(String key, int failValue){
        return mSharedPreferences.getInt(key, failValue);
    }
    
    public static void commitLong(String key, long value){
        mEditor = mSharedPreferences.edit();
        mEditor.putLong(key, value);
        mEditor.commit();
    }
    
    public static long getLong(String key, long failValue) {
        return mSharedPreferences.getLong(key, failValue);
    }
    
    public static void commitBoolean(String key, boolean value){
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }
    
    public static Boolean getBoolean(String key, boolean failValue){
        return mSharedPreferences.getBoolean(key, failValue);
    }

    /**
     * 保存应用程序数据 到sharedpreference
     * @param context 上下文
     * @param name 姓名
     * @param password 密码
     */
    public static void saveTOSP (Context context, String name, String password){
        //获取系统的一个sharedpreference文件  名字叫 sp
        SharedPreferences sp = context.getSharedPreferences("sp", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
        //创建一个编辑器 可以编辑 sp
        Editor editor = sp.edit();
        editor.putString("name", name);
        editor.putString("password", password);
        editor.putBoolean("boolean", false);
        editor.putInt("int", 8888);
        editor.putFloat("float",3.14159f);


        //注意:调用 commit 提交 数据到文件.
        editor.commit();

        //清空数据
        //editor.clear();
    }

    /**
     * 获取系统sharepreference里面的数据
     * @param context
     * @return
     */
    public static Map<String,String> readFromSP(Context context){
        //往map集合里面塞数据  map 键值对
        Map<String,String> map = new HashMap<String, String>();

        SharedPreferences sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE);

        String name = sp.getString("name", "defaultname");//第二个参数是默认值
        String password = sp.getString("password", "password");//第二个参数是默认值
        map.put("name", name);
        map.put("password", password);
        //返回一个map集合
        return map;
    }


    //系统用户数据
}
