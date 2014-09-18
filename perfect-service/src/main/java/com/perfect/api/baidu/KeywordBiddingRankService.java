package com.perfect.api.baidu;

import com.perfect.autosdk.core.CommonService;
import com.perfect.core.AppContext;
import com.perfect.dto.CreativeDTO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.service.AccountManageService;
import com.perfect.utils.BaiduServiceSupport;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by baizz on 2014-9-18.
 */
@Component("keywordBiddingRankService")
public class KeywordBiddingRankService {

    @Resource
    private BaiduPreviewHelperFactory baiduPreviewHelperFactory;

    @Resource
    private AccountManageService accountManageService;

    public Integer getKeywordBiddingRank(String keyword, Integer region) {

        BaiduAccountInfoEntity entity = accountManageService.getBaiduAccountInfoById(AppContext.getAccountId());

        String host = entity.getRegDomain();

        CommonService service = BaiduServiceSupport.getCommonService(entity);

        BaiduPreviewHelper baiduPreviewHelper = baiduPreviewHelperFactory.createInstance(service);

        String[] keywords = {keyword};
        List<BaiduPreviewHelper.PreviewData> previewDatas = baiduPreviewHelper.getPageData(keywords, region);

        for (BaiduPreviewHelper.PreviewData previewData : previewDatas) {
            if ((previewData.getLeft() == null || previewData.getLeft().isEmpty()) && (previewData.getRight() == null || previewData.getRight().isEmpty())) {
                continue;
            }

            List<CreativeDTO> leftList = previewData.getLeft();
            List<CreativeDTO> rightList = previewData.getRight();

            int rank = 0;
            try {
                URL url = new URL("http", host, "");
                for (CreativeDTO leftDTO : leftList) {
                    if (url.sameFile(new URL("http", leftDTO.getUrl(), ""))) {
                        rank = leftList.indexOf(leftDTO) + 1;
                        return rank;
//                        break;
                    }
                }

                if (rank == 0) {
                    for (CreativeDTO rightDTO : rightList) {
                        if (url.sameFile(new URL("http", rightDTO.getUrl(), ""))) {
                            rank = -1 * rightList.indexOf(rightDTO) - 1;
                            return rank;
//                            break;
                        }
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }

        return null;
    }
}