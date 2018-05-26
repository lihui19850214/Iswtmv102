package com.icomp.common.utils;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * @author TYY
 * @className SysApplication
 * @date 2016/8/13 15:23
 */
public class SysApplication extends Application {

    private List<Activity> mList = new LinkedList<Activity>();
    private static SysApplication instance;

    private SysApplication() {
    }

    public synchronized static SysApplication getInstance() {
        if (null == instance) {
            instance = new SysApplication();
        }
        return instance;
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit(Activity act) {
        try {
            for (Activity activity : mList) {
                if (activity != act && activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //      System.exit(0);
        }
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            System.exit(0);
        }
    }

    public Activity getCurrentActivity() {
        return mList.get(mList.size()-1);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}