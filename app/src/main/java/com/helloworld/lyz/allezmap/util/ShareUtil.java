package com.helloworld.lyz.allezmap.util;

import android.content.Context;
import android.content.Intent;

import com.helloworld.lyz.allezmap.BaseActivity;

/**
 * Created by paul on 2017/1/15.
 * 一键分享工具类
 * @Version 1.0
 * @Author paul (yangnaihua.2008at163.com)
 * @desc: ShareUtil
 */

public class ShareUtil extends BaseActivity{

    public static   void  share(Context context,String share_select_title,String share_title, String share_content){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, share_title );
        intent.putExtra(Intent.EXTRA_TEXT, share_content );
        context.startActivity(Intent.createChooser(intent, share_select_title));//第二个参数是设置选择要分享的应用的标题


    }

}
