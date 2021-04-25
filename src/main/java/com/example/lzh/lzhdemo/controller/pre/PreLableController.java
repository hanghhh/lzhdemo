package com.example.lzh.lzhdemo.controller.pre;

import cn.hutool.core.io.FileUtil;
import com.example.lzh.lzhdemo.service.PreLableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;


@RestController
@RequestMapping("/pre/lable/")
public class PreLableController {

    @Autowired
    private PreLableService preLableService;

    @RequestMapping(value = "/preLable4Csv")
    public String preLable4Csv() {
        try {
            String filePath = "/Users/liuzihang/Downloads/AI 打底内容池.csv";
            String newFilePath = "/Users/liuzihang/Downloads/result.csv";
            preLableService.preLable4Csv(filePath, newFilePath);
            return "success";
        } catch (Exception exp) {
            exp.printStackTrace();
            return "faile";
        }
    }

    @PostMapping(value = "/preLable4Csv2", produces = "application/json; charset=utf-8")
    public String preLable4Csv2(@RequestBody Map<String, String> dataMap) {
        try {
            String filePath = dataMap.get("filePath");
            String newFilePath = dataMap.get("newFilePath");
            preLableService.preLable4Csv(filePath, newFilePath);
            return "success";
        } catch (Exception exp) {
            exp.printStackTrace();
            return "faile";
        }
    }

    @RequestMapping(value = "/preLable4CsvDownLoad")
    public void preLable4CsvDownLoad(HttpServletResponse response, @RequestParam(required = false) String tempPath) {
        InputStream is = null;
        OutputStream os = null;
        try {
            preLableService.preLable4Csv("", tempPath);
            String fileName = URLEncoder.encode("result.csv", "UTF-8");
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
            is = new FileInputStream(FileUtil.file(tempPath));
            byte[] buffer = new byte[1024];
            os = response.getOutputStream();
            int len;
            while ((len = is.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
        } catch (Exception exp) {
            exp.printStackTrace();

        } finally {
            try {
                if (is != null) is.close();
                if (os != null) os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
