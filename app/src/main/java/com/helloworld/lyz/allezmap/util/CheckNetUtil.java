package com.helloworld.lyz.allezmap.util;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.helloworld.lyz.allezmap.BaseActivity;
import com.helloworld.lyz.allezmap.R;

/**
 * Created by paul on 2017/1/17.
 * 检查网络工具类
 * @Version 1.0
 * @Author paul (yangnaihua.2008at163.com)
 * @desc: CheckEmailTel
 */


public class CheckNetUtil extends BaseActivity {
    // 判断网络连接 方法
    public static void NetWorkStatus(final Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.getActiveNetworkInfo();

        boolean netSataus = true;
        if (connectivityManager.getActiveNetworkInfo() != null) {
            netSataus = connectivityManager.getActiveNetworkInfo().isAvailable();
        } else {
            AlertDialog.Builder aler_builder = new AlertDialog.Builder(context)
                    .setIcon(R.mipmap.ic_launcher).setTitle(R.string.login_network_title)
                    .setMessage(R.string.login_network_message);
            aler_builder.setPositiveButton(R.string.login_network_setting, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    /* Intent mIntent = new Intent("/"); */
                    /**
                     * 判断手机系统的版本！如果API大于10 就是3.0+
                     * 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
                     */

                    Intent intent = null;
                    /**
                     * 判断手机系统的版本！如果API大于10 就是3.0+
                     * 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
                     */
                    if (android.os.Build.VERSION.SDK_INT > 10) {
                        intent = new Intent(
                                android.provider.Settings.ACTION_WIFI_SETTINGS);
                    } else {
                        intent = new Intent();
                        ComponentName component = new ComponentName(
                                "com.android.settings",
                                "com.android.settings.WirelessSettings");
                        intent.setComponent(component);
                        intent.setAction("android.intent.action.VIEW");
                    }
                    context.startActivity(intent);
                }
            }).setNeutralButton(R.string.login_network_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            }).show();
        }


    }
}
