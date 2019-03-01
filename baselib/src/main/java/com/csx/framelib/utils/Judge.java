package com.csx.framelib.utils;

import android.text.TextUtils;

import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * create by cuishuxiang
 * time : 2018/12/11
 */
public class Judge {
    private static BitSet dontNeedEncoding;

    static {
        dontNeedEncoding = new BitSet(256);
        int i;
        for (i = 'a'; i <= 'z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = 'A'; i <= 'Z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = '0'; i <= '9'; i++) {
            dontNeedEncoding.set(i);
        }
        dontNeedEncoding.set('+');
        /**
         * 这里会有误差,比如输入一个字符串 123+456,它到底是原文就是123+456还是123 456做了urlEncode后的内容呢？<br>
         * 其实问题是一样的，比如遇到123%2B456,它到底是原文即使如此，还是123+456 urlEncode后的呢？ <br>
         * 在这里，我认为只要符合urlEncode规范的，就当作已经urlEncode过了<br>
         * 毕竟这个方法的初衷就是判断string是否urlEncode过<br>
         */

        dontNeedEncoding.set('-');
        dontNeedEncoding.set('_');
        dontNeedEncoding.set('.');
        dontNeedEncoding.set('*');
    }

    /**
     * 判断list是否为空
     */
    public static boolean isEmpty(List list) {
        if (list != null && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断object是否为空
     */
    public static boolean isEmpty(Object o) {
        return o == null ? true : false;
    }

    /**
     * 判断string是否为空
     */
    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !TextUtils.isEmpty(str);
    }

    /**
     * 判断string数组是否为空
     */
    public static boolean isEmpty(String[] strs) {
        if (strs != null && strs.length > 0)
            return false;
        else return true;
    }

    /**
     * 判断map是否为空
     */
    public static boolean isEmpty(Map map) {
        if (map != null && !map.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    /***
     * 判断手机号是否合法
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneValid(String phone) {
        return phone.matches("0?(13|14|15|17|18|19)[0-9]{9}");
    }

    /***
     * 判断邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmailValid(String email) {
        return email.matches("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}");
    }

    /***
     * 判断身份证号是否合法
     *
     * @param idcard
     * @return
     */
    public static boolean isIDCardValid(String idcard) {
        return idcard.matches("\\d{17}[\\d|x]|\\d{15}");
    }

    /***
     * 判断是否都是数字
     *
     * @param isNum
     * @return
     */
    public static boolean isNum(String isNum) {
        if (Judge.isEmpty(isNum)) {
            return false;
        } else {
            return isNum.matches("-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)");
        }
    }

    public static boolean isPrice(String price) {
        return price.matches("^(([1-9][0-9]*)|((([1-9][0-9]*)|0)\\.[0-9]{1,4}))$");
    }


    /***
     * 判断是否都是数字
     *
     * @param isNum
     * @return
     */
    public static boolean isIntNum(String isNum) {
        if (!Judge.isEmpty(isNum)) {
            return isNum.matches("-?[0-9]\\d*");
        } else {
            return false;
        }
    }

    /**
     * 判断str是否urlEncoder.encode过<br>
     * 经常遇到这样的情况，拿到一个URL,但是搞不清楚到底要不要encode.<Br>
     * 不做encode吧，担心出错，做encode吧，又怕重复了<Br>
     *
     * @param str
     * @return
     */
    public static boolean isUrlEncoded(String str) {

        /**
         * 支持JAVA的URLEncoder.encode出来的string做判断。 即: 将' '转成'+' <br>
         * 0-9a-zA-Z保留 <br>
         * '-'，'_'，'.'，'*'保留 <br>
         * 其他字符转成%XX的格式，X是16进制的大写字符，范围是[0-9A-F]
         */
        boolean needEncode = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (dontNeedEncoding.get((int) c)) {
                continue;
            }
            if (c == '%' && (i + 2) < str.length()) {
                // 判断是否符合urlEncode规范
                char c1 = str.charAt(++i);
                char c2 = str.charAt(++i);
                if (isDigit16Char(c1) && isDigit16Char(c2)) {
                    continue;
                }
            }
            // 其他字符，肯定需要urlEncode
            needEncode = true;
            break;
        }

        return !needEncode;
    }

    /**
     * 判断c是否是16进制的字符
     *
     * @param c
     * @return
     */
    private static boolean isDigit16Char(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F');
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、178(新)、182、184、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、170、173、177、180、181、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][3456789]\\d{9}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    /**
     * 是否包含手机号
     * @param text
     * @return
     */
    public static boolean containMobile(String text){
        Pattern pattern = Pattern.compile("[1][3456789]\\d{9}");
        return pattern.matcher(text).find();
    }

    public static boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }

    /***
     * 判断是否都是数字
     *
     * @param isNum
     * @return
     */
    public static boolean isLong(String isNum) {
        if (Judge.isEmpty(isNum)) {
            return false;
        } else {
            return isNum.matches("[0-9]\\d*");
        }
    }

}
