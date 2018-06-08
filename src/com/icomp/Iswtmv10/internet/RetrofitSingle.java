package com.icomp.Iswtmv10.internet;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import com.apiclient.pojo.AuthCustomer;
import com.google.gson.Gson;
import com.icomp.common.activity.CommonActivity;
import com.t_epc.reader.server.ReaderHelper;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by Think on 2016/11/21.
 */

public  class RetrofitSingle {
    private static final int DEFAULT_TIMEOUT = 60;
//    static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

    private static Retrofit mRetrofit = null;

    public static void getmRetrofit() {
//        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);


        // APP 访问 IP 和 端口
        mRetrofit = new Retrofit.Builder().baseUrl("http://39.106.122.167:81")//外网测试地址
//        mRetrofit = new Retrofit.Builder().baseUrl("http://175.190.96.205:8080")//纪伟家测试地址
//        mRetrofit = new Retrofit.Builder().baseUrl("http://10.135.66.249:8086")//客户演示地址1
//        mRetrofit = new Retrofit.Builder().baseUrl("http://10.135.66.52:8086")//客户演示地址2
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(genericClient())
                    .build();
    }

    public static OkHttpClient genericClient() {
        //设定用户访问信息
        @SuppressLint("WrongConstant")
        SharedPreferences sharedPreferences = ReaderHelper.getContext().getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
        String userInfoJson = sharedPreferences.getString("loginInfo", null);
        // 用户登陆信息
        AuthCustomer authCustomer = null;
        // 判断是否缓存用户登陆信息
        if (userInfoJson != null && !"".equals(userInfoJson)) {
            Gson gson = new Gson();
            authCustomer = gson.fromJson(userInfoJson, AuthCustomer.class);
        }
        // 获取用户登陆信息中的 code，每次请求时 HTTP 请求头中都要包含此信息
        final String longinUserCode = (authCustomer == null) ?  "" : authCustomer.getCode();

        // 构建 HTTP 请求信息，固定请求头和超时时间
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                // 请求数据格式为 json，编码为 UTF-8
                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                // 响应数据格式为 json，编码为 UTF-8
                                .addHeader("Accept", "application/json;charset=UTF-8")
                                // 登陆用户 code
                                .addHeader("loginUserCode", longinUserCode)
//                                .addHeader("Accept-Encoding", "gzip, deflate")
//                                .addHeader("Connection", "keep-alive")
//                                .addHeader("Cookie", "add cookies here")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)  // 连接超时时间，建立连接超时时间
                .readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)      // 接收超时时间，响应超时时间
                .writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)     // 发送超时时间，请求超时时间
                .build();

        return httpClient;
    }

    public static Retrofit newInstance(){
        getmRetrofit();
        return mRetrofit;
    }
}
