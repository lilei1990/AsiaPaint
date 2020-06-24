package com.asia.paint.utils.utils;

import android.text.TextUtils;

/**
 * 数字转换工具
 * Created by hongc on 16-7-29.
 */
public class DigitUtils {

    public static int parseInt(String text,int radix,int def){

        int result = def;

        if(TextUtils.isEmpty(text)){
            return result;
        }
        try{
            result =  Integer.parseInt(text,radix);
        }catch (Exception e){
            result =  def;
        }
        return result;

    }
    /**
     * 解析成int
     * @param text 解析文本
     * @param def 默认返回
     * @return 返回结果
     */
    public static int parseInt(String text,int def){

        int result = def;

        if(TextUtils.isEmpty(text)){
            return result;
        }
        try{
            result =  Integer.parseInt(text);
        }catch (Exception e){
            result =  def;
        }
        return result;

    }

    /**
     * 解析成int
     * @param text 解析文本
     * @return 默认返回 -1
     */
    public static int parseInt(String text){

        return  parseInt(text,-1);
    }

    /**
     * 解析成long
     * @param text 解析文本
     * @param def 默认返回
     * @return 返回结果
     */
    public static long parseLong(String text,long def){

        long result = def;

        if(TextUtils.isEmpty(text)){
            return result;
        }
        try{
            result =  Long.parseLong(text);
        }catch (Exception e){
            result =  def;
        }
        return result;

    }

    /**
     * 解析成long
     * @param text 解析文本
     * @return 默认返回 -1
     */
    public static long parseLong(String text){

        return  parseLong(text,-1L);
    }

    /**
     * 解析成double
     * @param text 解析文本
     * @param def 默认返回
     * @return 返回结果
     */
    public static double parseDouble(String text,double def){

        double result = def;

        if(TextUtils.isEmpty(text)){
            return result;
        }
        try{
            result =  Double.parseDouble(text);
        }catch (Exception e){
            result =  def;
        }
        return result;

    }

    /**
     * 解析成double
     * @param text 解析文本
     * @return 默认返回 -1
     */
    public static double parseDouble(String text){

        return  parseDouble(text,-1);
    }

    /**
     * 解析成float
     * @param text 解析文本
     * @param def 默认返回
     * @return 返回结果
     */
    public static float parseFloat(String text,float def){

        float result = def;

        if(TextUtils.isEmpty(text)){
            return result;
        }
        try{
            result =  Float.parseFloat(text);
        }catch (Exception e){
            result =  def;
        }
        return result;

    }

    /**
     * 解析成float
     * @param text 解析文本
     * @return 默认返回 -1
     */
    public static float parseFloat(String text){

        return  parseFloat(text,-1F);
    }

    /**
     * 解析成short
     * @param text 解析文本
     * @param def 默认返回
     * @return 返回结果
     */
    public static short parseShort(String text,short def){

        short result = def;

        if(TextUtils.isEmpty(text)){
            return result;
        }
        try{
            result =  Short.parseShort(text);
        }catch (Exception e){
            result =  def;
        }
        return result;

    }

    /**
     * 解析成short
     * @param text 解析文本
     * @return 默认返回 -1
     */
    public static short parseShort(String text){

        return  parseShort(text,(short)-1);
    }

}
