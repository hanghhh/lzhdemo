package com.example.lzh.lzhdemo.service;


import com.example.lzh.lzhdemo.enums.EnumLandPageKey;
import com.example.lzh.lzhdemo.util.CsvUtils;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PreLableService {
    private static final Joiner JOINER = Joiner.on(",").skipNulls();
    private static final int LAND_PAGE_INDEX = 2;
    private static final int TAG_INDEX = 3;
    private static final int IMAGE_INDEX = 4;
    private static final String TAG = "tag";
    private static final String SOURCE_PATH = "source/source.csv";
    private static final String IMAGE_URL = "imageUrl";
    private static final String KEEP_DEFAULT_IMAGE_URL =
            "https://static1.keepcdn.com/infra-cms/2021/04/09/19/07/664545369802_512x512.png?imageView2/1/w/200/h/200";


    public void preLable4Csv(String filePath, String newFilePath) {
        //List<String[]> lineList1 = CsvUtils.readCSV(filePath);
        List<String[]> lineList = getLineList();
        List<String> entryIdList = getEntyrIdsFromCsvData(lineList);
        List<String> articleIdList = getArticleIdsFromCsvData(lineList);
        /*
         * Map<String, List<Long>> lableMap =
         * keepLabelRelationRpcService.batchGetEntityLabelIds(EntityType.ENTRY, entryIdList);
         * Map<String, List<Long>> artcleMap =
         * keepLabelRelationRpcService.batchGetEntityLabelIds(EntityType.ARTICLE, articleIdList);
         */
        // lableMap.putAll(artcleMap);
        Map<String, List<Long>> lableMap = batchGetEntityLabelIds(entryIdList);
        Map<String, List<Long>> articleMap = batchGetEntityLabelIds(articleIdList);
        Map<String, String> idUrlMap = getImageUrlDate(entryIdList, articleIdList);
        lableMap.putAll(articleMap);
        addLableIdToCsvData(lineList, lableMap);
        addImageUrlToCsvData(lineList, idUrlMap);
        CsvUtils.writeCSV(newFilePath, lineList);
    }

    private void addImageUrlToCsvData(List<String[]> lineList, Map<String, String> urlMap) {
        String[] titleLine = lineList.get(0);
        titleLine[IMAGE_INDEX] = IMAGE_URL;
        for (int i = 1; i < lineList.size(); i++) {
            String[] line = lineList.get(i);
            String id = getEntryIdFromLine(line, EnumLandPageKey.ALL);
            line[IMAGE_INDEX] = StringUtils.isEmpty(urlMap.get(id)) ? KEEP_DEFAULT_IMAGE_URL : urlMap.get(id);
        }
    }

    private Map<String, String> getImageUrlDate(List<String> entryIdList, List<String> articleIdList) {
        Map<String, String> idUrlMap = new HashMap<>();
        /*Map<String, Entry> entryMap = entryRpcService.batchGet(entryIdList);
        List<Article> articles = articleRpcService.batchGetByIds(articleIdList);
        Set<Map.Entry<String, Entry>> entries = entryMap.entrySet();
        for (Map.Entry<String, Entry> entry : entries) {
            idUrlMap.put(entry.getKey(), entry.getValue().getPhoto());
        }
        for (Article article : articles) {
            idUrlMap.put(article.getId(), article.getPhoto());
        }*/
        return idUrlMap;
    }

    private Map<String, List<Long>> batchGetEntityLabelIds(List<String> entryIdList) {
        Map<String, List<Long>> map = new HashMap();
        for (String s : entryIdList) {
            ArrayList<Long> longs = Lists.newArrayList(3L, 4L, 5L);
            map.put(s, longs);
        }
        return map;
    }

    private List<String> getEntyrIdsFromCsvData(List<String[]> lineList) {
        List<String> entryIdList = new ArrayList<>();
        if (lineList.isEmpty()) {
            return entryIdList;
        }
        for (String[] line : lineList) {
            String entryId = getEntryIdFromLine(line, EnumLandPageKey.Entries);
            if (!StringUtils.isEmpty(entryId)) {
                entryIdList.add(entryId);
            }
        }
        return entryIdList;
    }

    private List<String> getArticleIdsFromCsvData(List<String[]> lineList) {
        List<String> articleIdList = new ArrayList<>();
        if (lineList.isEmpty()) {
            return articleIdList;
        }
        for (String[] line : lineList) {
            String articleId = getEntryIdFromLine(line, EnumLandPageKey.Articles);
            if (!StringUtils.isEmpty(articleId)) {
                articleIdList.add(articleId);
            }
        }
        return articleIdList;
    }

    private void addLableIdToCsvData(List<String[]> lineList, Map<String, List<Long>> lableMap) {
        String[] titleLine = lineList.get(0);
        titleLine[TAG_INDEX] = TAG;
        for (int i = 1; i < lineList.size(); i++) {
            String[] line = lineList.get(i);
            List<Long> longs = lableMap.get(getEntryIdFromLine(line, EnumLandPageKey.ALL));
            if (longs != null) {
                line[TAG_INDEX] = JOINER.join(longs);
            }else {
                line[TAG_INDEX] = "";
            }
        }
    }

    private String getEntryIdFromLine(String[] line, EnumLandPageKey enumLandPageKey) {
        assert (line.length > 4);
        String link = line[LAND_PAGE_INDEX];
        if (StringUtils.contains(link, EnumLandPageKey.Entries.getValue())
                && (enumLandPageKey == EnumLandPageKey.Entries || enumLandPageKey == EnumLandPageKey.ALL)) {
            return StringUtils.substringAfter(link, EnumLandPageKey.Entries.getValue());
        }
        if (StringUtils.contains(link, EnumLandPageKey.Articles.getValue())
                && (enumLandPageKey == EnumLandPageKey.Articles || enumLandPageKey == EnumLandPageKey.ALL)) {
            return StringUtils.substringAfter(link, EnumLandPageKey.Articles.getValue());
        }
        return "";
    }

    /**
     * 没有文件路径时默认从source下读取数据
     * @return
     */
    private List<String[]> getLineList() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(SOURCE_PATH);
        List<String[]> strings = CsvUtils.readCSV(inputStream);
        return strings;
    }

}
