package com.helloworld.lyz.allezmap.util;

import android.content.Context;
import android.content.Intent;

import com.helloworld.lyz.allezmap.BaseActivity;

/**
 * Created by paul on 2017/1/15.
 */

public class SendMailUtil extends BaseActivity {
    public static void sendmail(Context context, String title, String body, String clientname) {
//        Uri uri = Uri.parse("mailto:xxx@abc.com");
//        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
//        startActivity(it);
//        Intent it = new Intent(Intent.ACTION_SEND);
//        it.putExtra(Intent.EXTRA_EMAIL, "me@abc.com");
//        it.putExtra(Intent.EXTRA_TEXT, "The email body text");
//        it.setType("text/plain");
//        startActivity(Intent.createChooser(it, "Choose Email Client"));
        Intent it = new Intent(Intent.ACTION_SEND);
        String[] tos = {"yangnaihua.2008@gmail.com"};
        String[] ccs = {"yangnaihua.2008@163.com"};
        it.putExtra(Intent.EXTRA_EMAIL, tos);
        it.putExtra(Intent.EXTRA_CC, ccs);
        it.putExtra(Intent.EXTRA_SUBJECT, "title");
        it.putExtra(Intent.EXTRA_TEXT, "body");
        it.setType("text/plain");
        context.startActivity(Intent.createChooser(it, clientname));


    }

}
