package com.perfect.api.baidu;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.GetPreviewRequest;
import com.perfect.autosdk.sms.v3.GetPreviewResponse;
import com.perfect.autosdk.sms.v3.RankService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by vbzer_000 on 2014/9/17.
 */
@Component
public class RequestHelper {

    private int semaphoreValue;

    private Map<String, Semaphore> accountSemaphore = new ConcurrentHashMap<>();


    private Map<String, ExecutorService> executorServiceMap = new HashMap<>();

    public GetPreviewResponse addRequest(CommonService commonService, GetPreviewRequest request) {


        String token = commonService.getToken();


        if (!executorServiceMap.containsKey(token)) {
            executorServiceMap.put(token, Executors.newSingleThreadExecutor());
        }

        if (!accountSemaphore.containsKey(token)) {
            accountSemaphore.put(token, new Semaphore(1));
        }

        AccountRequestSender sender = new AccountRequestSender(commonService,
                request, accountSemaphore.get(token));


        Future<GetPreviewResponse> future = executorServiceMap.get(token).submit(sender);
        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getSemaphoreValue() {
        return semaphoreValue;
    }

    public void setSemaphoreValue(int semaphoreValue) {
        this.semaphoreValue = semaphoreValue;
    }


    private static class AccountRequestSender implements Callable<GetPreviewResponse> {


        private final CommonService commonService;
        private final GetPreviewRequest request;
        private Semaphore semaphore;

        public AccountRequestSender(CommonService commonService, GetPreviewRequest request, Semaphore semaphore) {
            this.commonService = commonService;
            this.request = request;
            this.semaphore = semaphore;
        }

        @Override
        public GetPreviewResponse call() throws Exception {

            try {
                semaphore.acquire();
                RankService rankService = null;
                rankService = commonService.getService(RankService.class);
                GetPreviewResponse response = rankService.getPreview(request);
                Thread.sleep(1000);
                return response;
            } catch (Exception ie) {

            } finally {
                semaphore.release();
            }
            return null;
        }
    }
}
