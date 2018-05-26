package com.icomp.Iswtmv10.v01c02.c02s002;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import com.icomp.common.activity.CommonActivity;
import com.umeng.analytics.MobclickAgent;

public class C02S002_001Activity  extends CommonActivity{

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	 
            super.onCreate(savedInstanceState);
            Intent intent = new Intent("/");
    		ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.SoundSettings");
    		intent.setComponent(cm);
    		intent.setAction("android.intent.action.VIEW");
    		startActivityForResult(intent, 0);
    		finish();
    }

}
