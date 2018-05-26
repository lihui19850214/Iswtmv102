package com.icomp.Iswtmv10.internet;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
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
    private static final int DEFAULT_TIMEOUT = 5000;
//    static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

    private static Retrofit mRetrofit = null;

    public static void getmRetrofit() {
//            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);


            // todo 大众接口ip
            mRetrofit = new Retrofit.Builder().baseUrl("http://39.106.122.167:81")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(genericClient())
                    .build();
    }

    public static OkHttpClient genericClient() {

        //设定用户访问信息
        SharedPreferences sharedPreferences = ReaderHelper.getContext().getSharedPreferences("userInfo", CommonActivity.MODE_APPEND);
        String userInfoJson = sharedPreferences.getString("loginInfo", null);

        AuthCustomer authCustomer = null;

        if (userInfoJson != null && !"".equals(userInfoJson)) {
            Gson gson = new Gson();
            authCustomer = gson.fromJson(userInfoJson, AuthCustomer.class);
        }

        final String longinUserCode = (authCustomer == null)? "" : authCustomer.getCode();


        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Accept", "application/json")
                                .addHeader("loginUserCode", longinUserCode)
//                                .addHeader("Accept-Encoding", "gzip, deflate")
//                                .addHeader("Connection", "keep-alive")
//                                .addHeader("Cookie", "add cookies here")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .build();

        return httpClient;
    }

    public static Retrofit newInstance(){
        getmRetrofit();
        return mRetrofit;
    }
}
