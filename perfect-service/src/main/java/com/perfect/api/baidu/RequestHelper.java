package com.perfect.api.baidu;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.GetPreviewRequest;
import com.perfect.autosdk.sms.v3.GetPreviewResponse;
import com.perfect.autosdk.sms.v3.RankService;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by vbzer_000 on 2014/9/17.
 */
public class RequestHelper {


    private static Map<String, LinkedBlockingQueue<GetPreviewRequest>> accountRequestList = new ConcurrentHashMap<>();


    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    static {

    }

    public static GetPreviewResponse addRequest(CommonService commonService, GetPreviewRequest request) {
//        String accountName = commonService.getUsername();
//        if (!accountRequestList.containsKey(accountName)) {
//            accountRequestList.put(accountName, new LinkedBlockingQueue<GetPreviewRequest>());
//        }

        Future<GetPreviewResponse> future = executorService.schedule(new AccountRequestSender(commonService,
                request), 15, TimeUnit.SECONDS);

        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
        //        accountRequestList.get(accountName).add(request);
//        accountRequestList.get(accountName).notify();
    }


    private static class AccountRequestSender implements Callable<GetPreviewResponse> {


        private final CommonService commonService;
        private final GetPreviewRequest request;

        public AccountRequestSender(CommonService commonService, GetPreviewRequest request) {
            this.commonService = commonService;
            this.request = request;
        }

        @Override
        public GetPreviewResponse call() throws Exception {
            RankService rankService = null;
            try {
                rankService = commonService.getService(RankService.class);
            } catch (ApiException e) {
                e.printStackTrace();
            }

            GetPreviewResponse response = rankService.getPreview(request);

            return response;
        }
    }
}
