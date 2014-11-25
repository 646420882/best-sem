package com.perfect.service.impl;

import com.perfect.api.baidu.BaiduPreviewHelperFactory;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.api.baidu.BaiduSpiderHelper;
import com.perfect.autosdk.core.CommonService;
import com.perfect.core.AppContext;
import com.perfect.dto.creative.CreativeInfoDTO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.service.AccountManageService;
import com.perfect.service.KeywordBiddingRankService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by baizz on 2014-9-18.
 * 2014-11-24 refactor
 */
@Component("keywordBiddingRankService")
public class KeywordBiddingRankServiceImpl implements KeywordBiddingRankService {

    @Resource
    private BaiduPreviewHelperFactory baiduPreviewHelperFactory;

    @Resource
    private AccountManageService accountManageService;

    @Override
    public Integer getKeywordBiddingRank(String keyword, Integer region) {

        BaiduAccountInfoEntity baiduAccount = accountManageService.getBaiduAccountInfoById(AppContext.getAccountId());

        String host = baiduAccount.getRegDomain();

        CommonService service = BaiduServiceSupport.getCommonService(baiduAccount.getBaiduUserName(), baiduAccount.getBaiduPassword(), baiduAccount.getToken());

        BaiduSpiderHelper baiduSpiderHelper = baiduPreviewHelperFactory.createInstance(service);

        // TODO remove BaiduSpiderHelper
        String[] keywords = {keyword};
        List<BaiduSpiderHelper.PreviewData> previewDatas = baiduSpiderHelper.getPageData(keywords, region);

        for (BaiduSpiderHelper.PreviewData previewData : previewDatas) {
            if ((previewData.getLeft() == null || previewData.getLeft().isEmpty()) && (previewData.getRight() == null || previewData.getRight().isEmpty())) {
                continue;
            }

            List<CreativeInfoDTO> leftList = previewData.getLeft();
            List<CreativeInfoDTO> rightList = previewData.getRight();

            int rank = 0;
            try {
                URL url = new URL("http", host, "");
                for (CreativeInfoDTO leftDTO : leftList) {
                    if (url.sameFile(new URL("http", leftDTO.getUrl(), ""))) {
                        rank = leftList.indexOf(leftDTO) + 1;
                        return rank;
                    }
                }

                if (rank == 0) {
                    for (CreativeInfoDTO rightDTO : rightList) {
                        if (url.sameFile(new URL("http", rightDTO.getUrl(), ""))) {
                            rank = -1 * rightList.indexOf(rightDTO) - 1;
                            return rank;
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