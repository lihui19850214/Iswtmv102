package com.serialport;

import java.io.File;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Parcelable;
import android.view.inputmethod.InputMethodManager;

public class LogicFacade {

    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";

    /**
     * 
     * 方法名称：isTopApp
     * 作者：jiangtao
     * 方法描述：判断是否当前进程正与用户交互
     * 输入参数：@param AppContext.current
     * 输入参数：@return
     * 返回类型：boolean：
     * 备注：
     */
    public static int activities()
    {
        if (null == AppContext.current)
        {
            return 0;
        }
        ActivityManager am =
            (ActivityManager) AppContext.current
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> TaskList = am.getRunningTasks(2);
        if (TaskList == null || TaskList.isEmpty())
        {
            return 0;
        }
        RunningTaskInfo rti = TaskList.get(0);
        String tmp = rti.topActivity.getPackageName();
        if (tmp.equals(AppContext.current.getPackageName()))
        {
            return rti.numActivities;
        }
        else
        {
            return 0;
        }
    }

    /**
     * 
     * 方法名称：addShortCut
     * 作者：jiangtao
     * 方法描述：
     * 输入参数：@param packageName like "com.android.phone"
     * 返回类型：void：
     * 备注：
     */
    public static void addShortCut(String packagename)
    {
        PackageManager pm = AppContext.current.getPackageManager();
        PackageInfo ap = null;
        try
        {
            ap = pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
        }
        catch (NameNotFoundException e)
        {
            return;
        }
        String name = ap.applicationInfo.loadLabel(pm).toString();
        int icon = ap.applicationInfo.icon;
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn =
            new ComponentName(ap.packageName, ap.activities[0].name);
        intent.setComponent(cn);
        Intent addShortcut = new Intent(LogicFacade.ACTION_ADD_SHORTCUT);
        Parcelable iconp = null;
        iconp =
            Intent.ShortcutIconResource.fromContext(AppContext.current, icon);
        addShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconp);
        addShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        addShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        AppContext.current.sendBroadcast(addShortcut);
    }

    @SuppressWarnings("unused")
	private static ConnectivityManager connectivityManager;

    /**
     * 方法名称：fromSharedPreferences
     * 作者：jiangtao
     * 方法描述：
     * 输入参数：@param key
     * 输入参数：@param defValue
     * 输入参数：@return
     * 返回类型：int：
     * 备注：
     */
    public static int fromSharedPreferences(String key, int defValue)
    {
        return AppContext.current.getSharedPreferences(AppContext.PREFER, 0)
            .getInt(key, 0);
    }

    /**
     * 方法名称：fromSharedPreferences
     * 作者：jiangtao
     * 方法描述：
     * 输入参数：@param key
     * 输入参数：@param defValue
     * 输入参数：@return
     * 返回类型：String：
     * 备注：
     */
    public static String fromSharedPreferences(String key, String defValue)
    {
        return AppContext.current.getSharedPreferences(AppContext.PREFER, 0)
            .getString(key, defValue);
    }

    /**
     * 方法名称：getExternalDirectory
     * 作者：jiangtao
     * 方法描述：
     * 输入参数：@return
     * 返回类型：File：
     * 备注：
     */
    public static File getExternalDirectory()
    {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 方法名称：getPackageInfo
     * 作者：jiangtao
     * 方法描述：
     * 输入参数：@param apkFilePath
     * 输入参数：@return
     * 返回类型：String：例：com.chinasoftinc.ass
     * 备注：
     */
    public static String getPackageInfo(String apkFilePath)
    {
        PackageManager pm = AppContext.current.getPackageManager();
        PackageInfo info =
            pm
                .getPackageArchiveInfo(apkFilePath,
                    PackageManager.GET_ACTIVITIES);
        if (info != null)
        {
            ApplicationInfo appInfo = info.applicationInfo;
            return appInfo.packageName;
        }
        return null;
    }

    /**
     * 方法名称：getSdcardPath
     * 作者：jiangtao
     * 方法描述：
     * 输入参数：@return
     * 返回类型：File：
     * 备注：
     */
    public static File getSdcardPath()
    {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 方法名称：hasSDcard
     * 作者：jiangtao
     * 方法描述：
     * 输入参数：@return
     * 返回类型：boolean：
     * 备注：
     */
    public static boolean hasSDcard()
    {
        return MemoryStatus.getAvailableExternalMemorySize() > 1024 * 1024;
    }

    /**
     * 
     * 方法名称：hideSearchDialog
     * 作者：jiangtao
     * 方法描述：隐藏系统搜索
     * 输入参数：
     * 返回类型：void：
     * 备注：
     */
    public static void hideSearchDialog()
    {
        SearchManager searchService =
            (SearchManager) AppContext.current
                .getSystemService(Context.SEARCH_SERVICE);
        searchService.stopSearch();
    }

    /**
     * 
     * 方法名称：hideSoftwareKeyboard
     * 作者：jiangtao
     * 方法描述：隐藏系统软键盘
     * 输入参数：
     * 返回类型：void：
     * 备注：
     */
    public static void hideSoftwareKeyboard()
    {
        InputMethodManager imm =
            (InputMethodManager) AppContext.current
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(AppContext.current.getCurrentFocus()
            .getWindowToken(), 0);
    }

    /**
     * please ues full path like "/sdcard/123.apk"
     * @param filename
     */
    public static void install(String filename)
    {
        Intent install = new Intent(Intent.ACTION_VIEW);
        String action = "application/vnd.android.package-archive";
        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 覆盖安装，如果存在原应用的话
        install.addFlags(2);
        install.setDataAndType(Uri.parse("file://" + filename), action);
        AppContext.current.startActivity(install);
    }

    /**
     * 方法名称：killPackage
     * 作者：jiangtao
     * 方法描述：
     * 输入参数：@return
     * 返回类型：boolean：
     * 备注：android.os.Process.killProcess(android.os.Process.myPid());
     */
    public static boolean killPackage()
    {
        ActivityManager manager =
            (ActivityManager) AppContext.current
                .getSystemService(Context.ACTIVITY_SERVICE);
        int sdkVersion = VERSION.SDK_INT;
        if (8 > sdkVersion)
        {
            manager.restartPackage(AppContext.current.getPackageName());
        }
        else
        {
            manager
                .killBackgroundProcesses(AppContext.current.getPackageName());
        }
        return activities() == 0;
    }

    public static void toSharedPreferences(String key, int value) {
        SharedPreferences.Editor editor = AppContext.current.getSharedPreferences(AppContext.PREFER, 0).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void toSharedPreferences(String key, String value) {
        SharedPreferences.Editor editor = AppContext.current.getSharedPreferences(AppContext.PREFER, 0).edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 方法名称：uninstall
     * 作者：jiangtao
     * 方法描述：卸载apk应用程序的函数
     * 输入参数：@param packageName
     */
    public static void uninstall(String packageName)
    {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        AppContext.current.startActivity(uninstallIntent);
    }
}