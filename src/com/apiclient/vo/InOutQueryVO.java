package com.apiclient.vo;


import com.apiclient.pojo.DjOwnerAkp;

import java.util.List;

/**
 * Created by logan on 2018/6/16.
 */
public class InOutQueryVO {

    private List<DjOwnerAkp> djOwnerAkps;

    private String wwcode;

    public List<DjOwnerAkp> getDjOwnerAkps() {
        return djOwnerAkps;
    }

    public void setDjOwnerAkps(List<DjOwnerAkp> djOwnerAkps) {
        this.djOwnerAkps = djOwnerAkps;
    }

    public String getWwcode() {
        return wwcode;
    }

    public void setWwcode(String wwcode) {
        this.wwcode = wwcode;
    }
}
