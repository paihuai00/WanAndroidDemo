package com.csx.framelib.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringUtils {
    public static final String EMPTY = "";

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }


    public static String urldecode(String s) {
        String result = s;
        try {
            result = URLDecoder.decode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getUtf8(String str){
        try {
            return new String(str.getBytes("gbk"),"utf-8");
        } catch (UnsupportedEncodingException e) {

        }
        return "";
    }

    public static String urlencode(String s) {
        String result = s;
        try {
            result = URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getMasterId(String url) {
        String masterId = "";
        if (!TextUtils.isEmpty(url)) {
            String[] params = url.split("=");
            if (params.length > 0)
                masterId = params[params.length - 1];
        }
        return masterId;
    }

    public static int getValueOfInt(String value, int def) {
        int valueInt = def;
        try {
            if (!isNull(value)) {
                valueInt = Integer.valueOf(value);
            }
            return valueInt;
        } catch (Exception e) {
            return def;
        }

    }

    public static boolean isNull(CharSequence str) {
        if (TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str.toString())) {
            return true;
        }
        return false;
    }

    public static String getTextString(String str) {
        return (isNull(str)) ? "暂无" : str;
    }

    /**
     * Helper function for making null strings safe for comparisons, etc.
     *
     * @return (s == null) ? "" : s;
     */
    public static String makeSafe(String s) {
        return (s == null) ? "" : s;
    }

    /**
     * @param str
     * @return
     */
    public static String trim(String str) {
        return str == null ? EMPTY : str.trim();
    }

    public static String getStringOfArray(ArrayList<String> arr) {
        String result = "";
        if (arr != null) {
            for (String str : arr) {
                result += (str + ",");
            }
        }
        if (!TextUtils.isEmpty(result)) {
            result.substring(0, result.length() - 1);
        }
        return result;
    }

    public static List<String> StringToList(String str, String sp) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split(sp);
            return Arrays.asList(split);
        }
        return Collections.emptyList();
    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /**
     * Get XML String of utf-8
     *
     * @return XML-Formed string
     */
    public static String getUTF8XMLString(String xml) {
        // A StringBuffer Object
        StringBuffer sb = new StringBuffer();
        sb.append(xml);
        String xmString = "";
        String xmlUTF8 = "";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"));
            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
            System.out.println("utf-8 编码：" + xmlUTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // return to String Formed
        return xmlUTF8;
    }

    /**
     * 文字高亮显示
     * 标识为{}
     * color传值例如 "#2896fe"
     */
    public static SpannableString getHighlightedText(String color, String text) {
        List<Integer> starts = new ArrayList<>();
        int a = text.indexOf("}&>");
        while (a != -1) {
            starts.add(a);
            a = text.indexOf("}&>", a + 3);
        }
        List<Integer> ends = new ArrayList<>();
        int b = text.indexOf("<&{");
        while (b != -1) {
            ends.add(b);
            b = text.indexOf("<&{", b + 3);
        }
        String newText = text.replace("}&>", "").replace("<&{", "");
        SpannableString s = new SpannableString(newText);
        for (int i = 0; i < starts.size(); i++) {
            int start = starts.get(i) - (i * 2) * 3;
            int end = ends.get(i) - (i * 2 + 1) * 3;
            s.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }
}
