package com.baidu.api.client.examples.people;

import com.baidu.api.client.core.*;
import com.baidu.api.sem.common.v2.ResHeader;
import com.baidu.api.sem.nms.v2.GetAllPeopleRequest;
import com.baidu.api.sem.nms.v2.GetAllPeopleResponse;
import com.baidu.api.sem.nms.v2.PeopleService;

import java.rmi.RemoteException;

/**
 * ClassName: GetAllPeopleExample  <br>
 * Function: TODO ADD FUNCTION
 *
 * @author Baidu API Team
 * @date Feb 9, 2012
 */
public class GetAllPeopleExample {

    private PeopleService service;

    public GetAllPeopleExample() {
        // Get service factory. Your authentication information will be popped up automatically from
        // baidu-api.properties
        VersionService factory = ServiceFactory.getInstance();
        // Get service stub by given the Service interface.
        // Please see the bean-api.tar.gz to get more details about all the service interfaces.
        this.service = factory.getService(PeopleService.class);
    }

    public GetAllPeopleResponse getAllPeople() {
        // Prepare your parameters.
        GetAllPeopleRequest parameters = new GetAllPeopleRequest();
        // Invoke the method.
        GetAllPeopleResponse ret = service.getAllPeople(parameters);
        // Deal with the response header, the second parameter controls whether to print the response header to console
        // or not.
        ResHeader rheader = ResHeaderUtil.getResHeader(service, true);
        // If status equals zero, there is no error. Otherwise, you need to check the errors in the response header.
        if (rheader.getStatus() == 0) {
            System.out.println("result\n" + ObjToStringUtil.objToString(ret.getPeoples()));
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
        GetAllPeopleExample example = new GetAllPeopleExample();
        example.getAllPeople();
    }

}
