package com.baidu.api.client.examples.ad;

import com.baidu.api.client.core.*;
import com.baidu.api.sem.common.v2.ResHeader;
import com.baidu.api.sem.nms.v2.AdService;
import com.baidu.api.sem.nms.v2.GetAdByAdIdRequest;
import com.baidu.api.sem.nms.v2.GetAdByAdIdResponse;

import java.rmi.RemoteException;

/**
 * ClassName: GetAdByAdIdExample
 * Function: TODO ADD FUNCTION
 *
 * @author Baidu API Team
 * @date 2012-2-14
 */
public class GetAdByAdIdExample {
    private AdService service;

    public GetAdByAdIdExample() {
        // Get service factory. Your authentication information will be popped up automatically from
        // baidu-api.properties
        VersionService factory = ServiceFactory.getInstance();
        // Get service stub by given the Service interface.
        // Please see the bean-api.tar.gz to get more details about all the service interfaces.
        this.service = factory.getService(AdService.class);
    }

    public GetAdByAdIdResponse getAdByAdId(long[] adIds) {
        // Prepare your parameters.
        GetAdByAdIdRequest parameters = new GetAdByAdIdRequest();
        for (long adId : adIds) {
            parameters.getAdIds().add(adId);
        }
        // Invoke the method.
        GetAdByAdIdResponse ret = service.getAdByAdId(parameters);
        // Deal with the response header, the second parameter controls whether to print the response header to console
        // or not.
        ResHeader rheader = ResHeaderUtil.getResHeader(service, true);
        // If status equals zero, there is no error. Otherwise, you need to check the errors in the response header.
        if (rheader.getStatus() == 0) {
            System.out.println("result\n" + ObjToStringUtil.objToString(ret.getAdTypes()));
            return ret;
        } else {
            throw new ClientBusinessException(rheader, ret);
        }
    }

    /**
     * @param args
     * @throws Throwable
     * @throws RemoteException
     */
    public static void main(String[] args) throws Throwable {
        GetAdByAdIdExample example = new GetAdByAdIdExample();
        long[] adIds = new long[]{9283664, 9240997, 9277249};
        example.getAdByAdId(adIds);
    }
}
