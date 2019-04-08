package com.qfjy.project.weixin.util;

import java.io.UnsupportedEncodingException;
/**
 * 把中文字符转换为带百分号的浏览器编码
 * @author guoweixin 
 *
 */
public class CodeConverter
{
    // 把中文字符转换为带百分号的浏览器编码

    // @param word 中文字符

    // @param encoding 字符编码
    public static String toBrowserCode(String word, String encoding)
            throws UnsupportedEncodingException
    {
        byte[] textByte = word.getBytes(encoding);
        StringBuilder strBuilder = new StringBuilder();
       
        for (int j = 0; j < textByte.length; j++)
        {
            // 转换为16进制字符
            String hexStr = Integer.toHexString(textByte[j] & 0xff);
            strBuilder.append("%" + hexStr.toUpperCase());
        }
       
        return strBuilder.toString();
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException
    {
        String word = "宋林浩";  //%E5%AE%8B%E6%9E%97%E6%B5%A9
        String encoding = "UTF-8";
        String retValue = CodeConverter.toBrowserCode(word, encoding);
        System.out.print(retValue);
    }

}

