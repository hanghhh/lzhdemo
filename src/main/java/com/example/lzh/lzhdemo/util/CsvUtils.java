package com.example.lzh.lzhdemo.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.text.csv.*;
import cn.hutool.core.util.CharsetUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 配合Hutools使用的csv工具类
 */
public class CsvUtils {
    /**
     * 通过地址读
     *
     * @param filePath
     */
    public static List<String[]> readCSV(String filePath) {
        List<String[]> csvResult = new ArrayList<>();
        CsvReader reader = new CsvReader();
        CsvData data = reader.read(FileUtil.file(filePath));
        data.getRows().stream().forEach(e -> csvResult.add(e.getRawList().toArray(new String[5])));
        return csvResult;
    }

    /**
     * 通过流读
     */
    public static List<String[]> readCSV(InputStream inputStream) {
        List<String[]> csvResult = new ArrayList<>();
        CsvReader csvReader = new CsvReader();
        Reader reader = (new InputStreamReader(inputStream));
        CsvData data = csvReader.read(reader);
        data.getRows().stream().forEach(e -> csvResult.add(e.getRawList().toArray(new String[5])));
        return csvResult;
    }


    /**
     * 导出excle
     *
     * @param filePath
     * @param lines
     */
    public static void writeCSV(String filePath, Collection<String[]> lines) {
        // 指定路径和编码
        CsvWriter writer = null;
        try {
            writer = new CsvWriter(filePath, CharsetUtil.CHARSET_UTF_8);
            // 按行写出
            writer.write(lines);
        } catch (IORuntimeException e) {
            e.printStackTrace();
        } finally {
            assert writer != null;
            writer.close();
        }
    }

}
