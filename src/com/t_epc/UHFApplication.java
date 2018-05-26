package com.t_epc;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;


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
