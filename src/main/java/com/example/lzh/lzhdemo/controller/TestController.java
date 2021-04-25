package com.example.lzh.lzhdemo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.lzh.lzhdemo.po.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fir")
public class TestController {

    @Value("#{'${sanda.abExpImage.pushTypes}'.split(',')}")
    private List<String> targetPushTypeList;

    @SuppressWarnings("checkstyle:WhitespaceAfter")
    @RequestMapping("/test")
    public String test1() {
        for (String s : targetPushTypeList) {
            System.out.println(s);
        }
        return "";
    }

    @RequestMapping("/export")
    public void export(HttpServletResponse response) throws UnsupportedEncodingException {
        List<User> list = new ArrayList<>();
        list.add(new User("zhangsan", "1231"));
        list.add(new User("zhangsan1", "1232"));
        list.add(new User("zhangsan2", "1233"));
        list.add(new User("zhangsan3", "1234"));
        list.add(new User("zhangsan4", "1235"));
        list.add(new User("zhangsan5", "1236"));
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        // 自定义标题别名
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("age", "年龄");
        // 合并单元格后的标题行，使用默认标题样式
        // writer.merge(2, "申请人员信息");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        // out为OutputStream，需要写出到的目标流
        // response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        // test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        String name = "123";
        response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xls");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭writer，释放内存
            writer.close();
        }
        // 此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    @RequestMapping("/import")
    public void aaa() {
        // File file = new File("/Users/liuzihang/chrome_download/123.xls");
        CsvReader reader = CsvUtil.getReader();
        CsvData read = reader.read(FileUtil.file("/Users/liuzihang/Downloads/AI 打底内容池.csv"));
        List<CsvRow> rows = read.getRows();
        // 遍历行
        for (CsvRow csvRow : rows) {
            // getRawList返回一个List列表，列表的每一项为CSV中的一个单元格（既逗号分隔部分）
            List<String> rawList = csvRow.getRawList();
            System.out.println(rawList);
        }

        System.out.println();
    }
}
