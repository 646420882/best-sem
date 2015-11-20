package com.perfect.utils;


import com.perfect.core.AppContext;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.keyword.KeywordInfoDTO;
import com.perfect.service.BaiduAccountService;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by XiaoWei on 2014/8/7.
 */
public class CsvReadUtil implements Iterator<List<String>> {
    public static final String ENCODING_GBK = "GBK";
    private CsvListReader reader = null;
    private List<String> row = null;
    private String encoding;
    private String csvFile;
    private BaiduAccountInfoDTO baiduAccountInfoDTO;

    public CsvReadUtil(String csvFile, String encoding, BaiduAccountInfoDTO baiduAccountInfoDTO) {
        super();
        try {
            this.encoding = encoding;
            this.csvFile = csvFile;
            this.baiduAccountInfoDTO = baiduAccountInfoDTO;
            reader = new CsvListReader(new InputStreamReader(new FileInputStream(csvFile), encoding), CsvPreference.EXCEL_PREFERENCE);
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        try {
            if (reader.getLineNumber() == 0) {
                row = reader.read();
            }
            row = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return row != null;
    }

    @Override
    public List<String> next() {
        return row;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("本CSV解析器是只读的.");
    }

    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getLineNumber() {
        return reader.getRowNumber();
    }

    /**
     * 获取关键词CSV文件的list集合的对象
     *
     * @return
     */
    public List<KeywordDTO> getList() {
        List<KeywordDTO> list = new LinkedList<>();
        while (hasNext()) {
            KeywordDTO keywordEntity = new KeywordDTO();
            keywordEntity.setKeywordId(Long.valueOf(next().get(12)));
            keywordEntity.setAdgroupId(Long.valueOf(next().get(11)));
            keywordEntity.setKeyword(next().get(4));
            keywordEntity.setPrice(BigDecimal.valueOf(Double.valueOf(next().get(5))));
            keywordEntity.setPcDestinationUrl(null);
            keywordEntity.setMobileDestinationUrl(null);
            keywordEntity.setMatchType(getMatchType(next().get(6)));
            keywordEntity.setPause(false);
            keywordEntity.setStatus(42);
            keywordEntity.setAccountId(Long.valueOf(next().get(9)));
            list.add(keywordEntity);
        }
        close();
        return list;
    }

    public List<KeywordInfoDTO> getImportKeywordList() {
        List<KeywordInfoDTO> keywordDTOs = new LinkedList<>();
        String regDomain = baiduAccountInfoDTO.getRegDomain();
        int i = 0;
        while (hasNext()) {
            if (i < 200000) {
                KeywordInfoDTO kinf = new KeywordInfoDTO();
                KeywordDTO kwd = new KeywordDTO();
                if (next().get(0) == null) {
                    continue;
                } else {
                    if (next().get(0).getBytes().length < 30) {
                        kinf.setCampaignName(next().get(0));
                    } else {
                        continue;
                    }
                }
                if (next().get(1) == null) {
                    continue;
                } else {
                    if (next().get(1).getBytes().length < 30) {
                        kinf.setAdgroupName(next().get(1));
                    } else {
                        continue;
                    }
                }
                if (next().get(2) == null) {
                    continue;
                } else {
                    if (next().get(2).getBytes().length < 40) {
                        kinf.setKeyword(next().get(2).toString());
                        kwd.setKeyword(next().get(2));
                    } else {
                        continue;
                    }
                }
                if (next().get(3) == null)
                    continue;
                kwd.setMatchType(getImportMatchType(next().get(3), kwd));
                if (next().get(4) == null) {
                    continue;
                } else {
                    double price = getImportPrice(next().get(4)).doubleValue();
                    if (price > 999.9 || price < 0) {
                        continue;
                    }
                    kwd.setPrice(getImportPrice(next().get(4)));
                }
                if (next().get(5) == null) {
                    continue;
                } else {
                    Pattern p = Pattern.compile("" + regDomain + "$", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(next().get(5));
                    if (m.find()) {
                        kwd.setPcDestinationUrl(next().get(5));
                    } else {
                        continue;
                    }
                }
                if (next().get(6) == null) {
                    continue;
                } else {
                    Pattern p = Pattern.compile("" + regDomain + "$", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(next().get(6));
                    if (m.find()) {
                        kwd.setMobileDestinationUrl(next().get(6));
                    } else {
                        continue;
                    }
                }
                if (next().get(7) == null)
                    continue;
                kwd.setPause(getImportPause(next().get(7)));
                kinf.setObject(kwd);
                keywordDTOs.add(kinf);
                i++;
            } else {
                break;
            }
        }
        close();
        return keywordDTOs;
    }

    public List<CreativeDTO> getImportCreativeList() {
        List<CreativeDTO> creativeDTOs = new ArrayList<>();
        String regDomain = baiduAccountInfoDTO.getRegDomain();
        int i = 0;
        while (hasNext()) {
            CreativeDTO crea = new CreativeDTO();
            if (i < 200000) {
                if (next().get(0) == null) {
                    continue;
                } else {
                    if (next().get(0).getBytes().length < 30) {
                        crea.setCampaignName(next().get(0));
                    } else {
                        continue;
                    }
                }
                if (next().get(1) == null) {
                    continue;
                } else {
                    if (next().get(1).getBytes().length < 40) {
                        crea.setAdgroupName(next().get(1));
                    } else {
                        continue;
                    }
                }
                if (next().get(2) == null) {
                    continue;
                } else {
                    if (next().get(2).getBytes().length <= 50 && next().get(2).getBytes().length >= 9) {
                        crea.setTitle(next().get(2));
                    } else {
                        continue;
                    }
                }
                if (next().get(3) == null) {
                    continue;
                } else {
                    if (next().get(3).getBytes().length <= 80 && next().get(3).getBytes().length >= 9) {
                        crea.setDescription1(next().get(3));
                    } else {
                        continue;
                    }
                }
                if (next().get(4) == null) {
                    continue;
                } else {
                    if (next().get(4).getBytes().length <= 80 && next().get(4).getBytes().length >= 9) {
                        crea.setDescription2(next().get(4));
                    } else {
                        continue;
                    }
                }
                if (next().get(5) == null) {
                    continue;
                } else {
                    Pattern p = Pattern.compile("" + regDomain + "$", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(next().get(5));
                    if (next().get(5).getBytes().length <= 1024 && m.find()) {
                        crea.setPcDestinationUrl(next().get(5));
                    } else {
                        continue;
                    }
                }
                if (next().get(6) == null) {
                    continue;
                } else {
                    Pattern p = Pattern.compile("" + regDomain + "$", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(next().get(6));
                    if (next().get(6).getBytes().length <= 36 && m.find()) {
                        crea.setPcDisplayUrl(next().get(6));
                    } else {
                        continue;
                    }
                }
                if (next().get(7) == null) {
                    continue;
                } else {
                    Pattern p = Pattern.compile("" + regDomain + "$", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(next().get(7));
                    if (next().get(7).getBytes().length <= 1024 && m.find()) {
                        crea.setMobileDestinationUrl(next().get(7));
                    } else {
                        continue;
                    }
                }
                if (next().get(8) == null) {
                    continue;
                } else {
                    Pattern p = Pattern.compile("" + regDomain + "$", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(next().get(8));
                    if (next().get(8).getBytes().length <= 36 && m.find()) {
                        crea.setMobileDisplayUrl(next().get(8));
                    } else {
                        continue;
                    }
                }
                if (next().get(9) == null) {
                    continue;
                } else {
                    crea.setPause(getImportPause(next().get(9)));
                }
                if (next().get(10) == null) {
                    continue;
                } else {
                    crea.setDevicePreference(getCreativeDevice(next().get(10)));
                }
                creativeDTOs.add(crea);
            }else{
                break;
            }
            i++;
        }
        return creativeDTOs;
    }

    private Integer getMatchType(String matchType) {
        Integer matchTypeInteger = 1;
        switch (matchType) {
            case "高级短语匹配":
                matchTypeInteger = 2;
                break;
            case "广泛匹配":
                matchTypeInteger = 3;
                break;
            default:
                return matchTypeInteger;
        }
        return matchTypeInteger;
    }

    private Integer getCreativeDevice(String device) {
        return device.equals("全部设备") ? 0 : 1;
    }

    private Integer getImportMatchType(String matchType, KeywordDTO kwd) {
        switch (matchType) {
            case "精确":
                return 1;
            case "广泛":
                return 3;
            default:
                if (matchType.contains("-")) {
                    String phraseType = matchType.split("-")[1];
                    switch (phraseType) {
                        case "精确":
                            kwd.setPhraseType(2);
                            break;
                        case "同义":
                            kwd.setPhraseType(1);
                            break;
                        case "核心":
                            kwd.setPhraseType(3);
                            break;
                    }
                }
                return 2;
        }
    }

    private Boolean getImportPause(String pauseStr) {
        return pauseStr.equals("启用") ? true : false;
    }

    private BigDecimal getImportPrice(String price) {
        try {
            Double d = Double.valueOf(price);
            return BigDecimal.valueOf(d);
        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.valueOf(0.0);
        }
    }

    /**
     * 获取关键词CSV文件的map对象的CSV集合
     *
     * @return
     */
    public Map<Integer, KeywordDTO> getMap() {
        Map<Integer, KeywordDTO> map = new HashMap<>();
        while (hasNext()) {
            KeywordDTO keywordEntity = new KeywordDTO();
            keywordEntity.setKeywordId(Long.valueOf(next().get(12)));
            keywordEntity.setAdgroupId(Long.valueOf(next().get(11)));
            keywordEntity.setKeyword(next().get(4));
            keywordEntity.setPrice(BigDecimal.valueOf(Double.valueOf(next().get(5))));
            keywordEntity.setPcDestinationUrl(null);
            keywordEntity.setMobileDestinationUrl(null);
            keywordEntity.setMatchType(getMatchType(next().get(6)));
            keywordEntity.setPause(false);
            keywordEntity.setStatus(42);
            keywordEntity.setAccountId(Long.valueOf(next().get(9)));
            map.put(getLineNumber(), keywordEntity);
        }
        close();
        return map;
    }


}
