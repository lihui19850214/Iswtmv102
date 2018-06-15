package com.icomp.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by logan on 2018/6/11.
 */
public class FCBCodeHandler {

    public static String fcbCodeHandler(String fcbCode) throws Exception{
        String result = fcbCode;
        String reg = "[H,M,T,ZK,DT,MT,GY]+$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(fcbCode);
        if (matcher.find()){
            result = fcbCode.substring(0,matcher.start());
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(FCBCodeHandler.fcbCodeHandler("FCB5114GB10115T"));
        System.out.println(FCBCodeHandler.fcbCodeHandler("FCB5114GB10122MT"));
        System.out.println(FCBCodeHandler.fcbCodeHandler("FCB5114GB10121H"));
        System.out.println(FCBCodeHandler.fcbCodeHandler("FCB5154GB10029"));
    }
}
