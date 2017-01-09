package com.helloworld.lyz.allezmap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class GuideActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        Toast.makeText(GuideActivity.this, "GuideActivity", Toast.LENGTH_LONG).show();
    }
}
