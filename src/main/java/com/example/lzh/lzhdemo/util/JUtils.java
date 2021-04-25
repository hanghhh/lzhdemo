package com.example.lzh.lzhdemo.util;

import com.beust.jcommander.internal.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuzihang
 * @description: TODO
 * @date 2021/4/15 6:12 下午
 */
public class JUtils {
    public static void main(String[] args) throws IOException {
        List<String> strings = Lists.newArrayList("1");
        System.out.println("strings.contains(null) = " + strings.contains(null));
    }

    /** 获取图片字符串中所有链接 */
    public static List<String> getImgSrc(String htmlStr) {
        String img = "";
        Pattern imgPattern;
        Matcher imgMatcher;
        List<String> pics = new ArrayList<>();
        String imgReg = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        imgPattern = Pattern.compile(imgReg, Pattern.CASE_INSENSITIVE);
        imgMatcher = imgPattern.matcher(htmlStr);
        while (imgMatcher.find()) {
            img = img + "," + imgMatcher.group();
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }
}
