package org.asocframework.bundle.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jiqing
 * @version $Id:TestUtils , v1.0 2020/7/7 下午8:22 jiqing Exp $
 * @desc
 */
public class TestUtils {

    public static void main(String[] args) {

        String tttt="https://market.m.taobao.com/app/asr-pages/muise-cloudtheme-recommend?_mus_tpl=https://g.alicdn.com/asr-pages/muise-cloudtheme-recommend/0.0.3/muise-cloudtheme-recommend.mus.wlm&wx_navbar_transparent=true&_wx_statusbar_hidden=true&_xsl_prld_id=18982&spmA=icloud&spmB=muisetwo&sub_biz_type=general123&p2seasoning=muise&biz=general";
        String test = "https://market.m.taobao.com/app/tbsearchwireless-pages/cloudtopi" +
                "c-common-page/p/cloudtopic-common-page?abc=1&ssss=2&sfsdf=http%3A%2F%2Fsdfsdgs.sdfsd.cmo%2Fsdfs%3Faaa%3D1%26bbb%3D22";
        //String regex = "^https.*\\?";
        String regex = "^[A-Za-z0-9_.:\\-/]+\\?";
        System.out.println(test.matches(regex));
        System.out.println(test.replaceFirst(regex,"sdfsdfsdf?"));
        System.out.println(URLEncoder.encode("http://sdfsdgs.sdfsd.cmo/sdfs?aaa=1&bbb=22"));;
        Map map =   convertResultStringToMap(test.substring(test.indexOf("?")+1));
        System.out.println(test.substring(test.indexOf("?")+1));
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(test);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        }
        System.out.println(test.indexOf("^"));

    }


    /**
     * 将形如key=value&key=value的字符串转换为相应的Map对象
     *
     * @param result
     * @return
     */
    public static Map<String, String> convertResultStringToMap(String result) {
        Map<String, String> map =null;
        try {

            if(StringUtils.isNotBlank(result)){
                String localResult = result;
                if(result.startsWith("{") && result.endsWith("}")){
                    localResult = result.substring(1, result.length()-1);
                }
                map = parseQString(localResult);
            }

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return map;
    }


    /**
     * 解析应答字符串，生成应答要素
     *
     * @param str
     *            需要解析的字符串
     * @return 解析的结果map
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> parseQString(String str)
            throws UnsupportedEncodingException {

        Map<String, String> map = new HashMap<String, String>();
        int len = str.length();
        StringBuilder temp = new StringBuilder();
        char curChar;
        String key = null;
        boolean isKey = true;
        boolean isOpen = false;//值里有嵌套
        char openName = 0;
        if(len>0){
            for (int i = 0; i < len; i++) {// 遍历整个带解析的字符串
                curChar = str.charAt(i);// 取当前字符
                if (isKey) {// 如果当前生成的是key

                    if (curChar == '=') {// 如果读取到=分隔符
                        key = temp.toString();
                        temp.setLength(0);
                        isKey = false;
                    } else {
                        temp.append(curChar);
                    }
                } else  {// 如果当前生成的是value
                    if(isOpen){
                        if(curChar == openName){
                            isOpen = false;
                        }

                    }else{//如果没开启嵌套
                        if(curChar == '{'){//如果碰到，就开启嵌套
                            isOpen = true;
                            openName ='}';
                        }
                        if(curChar == '['){
                            isOpen = true;
                            openName =']';
                        }
                    }
                    if (curChar == '&' && !isOpen) {// 如果读取到&分割符,同时这个分割符不是值域，这时将map里添加
                        putKeyValueToMap(temp, isKey, key, map);
                        temp.setLength(0);
                        isKey = true;
                    }else{
                        temp.append(curChar);
                    }
                }

            }
            putKeyValueToMap(temp, isKey, key, map);
        }
        return map;
    }

    private static void putKeyValueToMap(StringBuilder temp, boolean isKey,
                                         String key, Map<String, String> map)
            throws UnsupportedEncodingException {
        if (isKey) {
            String localKey = temp.toString();
            if (localKey.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(localKey, "");
        } else {
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(key, temp.toString());
        }
    }

}
