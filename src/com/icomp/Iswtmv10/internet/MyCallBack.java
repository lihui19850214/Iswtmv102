package com.icomp.Iswtmv10.internet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public abstract class MyCallBack<T> implements Callback<T> {
    public abstract void _onResponse(Response<T> response);
    public abstract void _onFailure(Throwable t);
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
//        if(response.raw().code() == 200){
//            _onResponse(response);
//        }else{
//            _onFailure(null);
//        }
        _onResponse(response);
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        _onFailure(t);
    }
}
