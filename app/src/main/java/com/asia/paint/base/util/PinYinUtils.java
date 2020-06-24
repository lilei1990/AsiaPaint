package com.asia.paint.base.util;

import android.text.TextUtils;

import com.asia.paint.base.network.bean.Staff;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by chenhong14 on 2019-12-29.
 */
public class PinYinUtils {

    // 根据姓名设置联系人排序字母
    public static void sortLetter(List<Staff> iLetters) {
        if (iLetters == null) {
            return;
        }
        for (Staff letter : iLetters) {
            // 汉字转换成拼音
            String pinyin = getPinYin(letter.getContent());
            if (!TextUtils.isEmpty(pinyin)) {
                String sort = pinyin.substring(0, 1).toUpperCase();
                if (sort.matches("[A-Z]")) {
                    letter.setLetter(sort.toUpperCase());
                } else {
                    letter.setLetter("#");
                }
            }
        }
    }

    //汉字返回拼音，字母原样返回，都转换为小写(默认取得的拼音全大写)
    public static String getPinYin(String input) {
        ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(input);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (HanziToPinyin.Token token : tokens) {
                if (HanziToPinyin.Token.PINYIN == token.type) {
                    sb.append(token.target);
                } else {
                    sb.append(token.source);
                }
            }
        }
        return sb.toString().toLowerCase();
    }

}
