package com.t_epc;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import com.icomp.Iswtmv10.R;
import com.icomp.wsdl.common.UrlBase;
import com.t_epc.reader.server.ReaderHelper;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class UHFApplication extends Application {

    private Socket mTcpSocket = null;
    private BluetoothSocket mBtSocket = null;

    private List<Activity> activities = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
//        UrlBase.setUrlPort("http://localhost:8080");
        // todo 配置自己地址
        UrlBase.setUrlPort("http://192.168.1.117:8080");
//        UrlBase.setUrlPort("http://10.216.82.241:8080");//现场本地
//        UrlBase.setUrlPort("http://10.226.65.11");//服务器
        try {
            ReaderHelper.setContext(getApplicationContext());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // ***************************** 捕获主线程异常，保持 APP 不崩溃 *****************************
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Looper.loop();
                    } catch (Exception e) {
                        e.printStackTrace();
//                        // 捕获到异常: main 在 android.app.ActivityThread
//                        Log.e("捕获到异常", Thread.currentThread().getName() + " 在 " + e.getStackTrace()[0].getClassName());
//                        // 捕获到异常: com.icomp.Iswtmv10.v01c01.c01s002.c01s002_002Activity
//                        Log.e("捕获到异常", e.getCause().getStackTrace()[0].getClassName());
//                        // 捕获到异常: c01s002_002Activity.java
//                        Log.e("捕获到异常", e.getCause().getStackTrace()[0].getFileName());
//                        // 捕获到异常: onCreate
//                        Log.e("捕获到异常", e.getCause().getStackTrace()[0].getMethodName());
//                        // 捕获到异常: 63
//                        Log.e("捕获到异常", e.getCause().getStackTrace()[0].getLineNumber()+"");

                        Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                    }

//                    catch (NullPointerException e) {
//                        Toast.makeText(getApplicationContext(), "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();
//                    } catch (Exception e){
//                        //使用Toast来显示异常信息
//                        new Thread() {
//                            @Override
//                            public void run() {
//                                Looper.prepare();
//                                Toast.makeText(getApplicationContext(), "----很抱歉,程序出现异常,即将退出----.", Toast.LENGTH_SHORT).show();
//                                Looper.loop();
//                            }
//                        }.start();
//                        try {
//                            Thread.sleep(3000);
//                        } catch (InterruptedException e1) {
//
//                        }
//                        //退出程序
//                        android.os.Process.killProcess(android.os.Process.myPid());
//                        System.exit(1);
//                    }
                }
            }
        });

        // ***************************** 捕获子线程异常，保持 APP 不崩溃 *****************************
        // 所有线程异常拦截，由于主线程的异常都被 catch 住了，所以下面的代码拦截到的都是子线程的异常
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
                Looper.prepare();
                Toast.makeText(getApplicationContext(), getString(R.string.dataError), Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });

		/*CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());*/
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    public void exit() {
        try {
            for (Activity activity : activities) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (Activity activity : activities) {
            try {
                activity.finish();
            } catch (Exception e) {
            }
        }
        for (Fragment fragment : fragments) {
            try {
                fragment.getActivity().finish();
            } catch (Exception e) {
            }
        }

        try {
            if (mTcpSocket != null) {
                mTcpSocket.close();
            }
            if (mBtSocket != null) {
                mBtSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mTcpSocket = null;
        mBtSocket = null;

        if (BluetoothAdapter.getDefaultAdapter() != null) {
            BluetoothAdapter.getDefaultAdapter().disable();
        }

        System.exit(0);
    }

//    ;

    public void setTcpSocket(Socket socket) {
        this.mTcpSocket = socket;
    }

    public void setBtSocket(BluetoothSocket socket) {
        this.mBtSocket = socket;
    }
}
