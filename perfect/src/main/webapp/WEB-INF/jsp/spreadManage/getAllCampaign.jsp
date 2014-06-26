<%@ page import="com.perfect.autosdk.core.CommonService" %>
<%@ page import="com.perfect.autosdk.core.ServiceFactory" %>
<%@ page import="com.perfect.autosdk.sms.v3.CampaignService" %>
<%@ page import="com.perfect.autosdk.sms.v3.CampaignType" %>
<%@ page import="com.perfect.autosdk.sms.v3.GetAllCampaignRequest" %>
<%@ page import="com.perfect.autosdk.sms.v3.GetAllCampaignResponse" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 2014-6-19
  Time: 13:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CommonService service = ServiceFactory.getInstance();
    CampaignService campaignService = service.getService(CampaignService.class);
    GetAllCampaignRequest getAllCampaignRequest = new GetAllCampaignRequest();
    GetAllCampaignResponse getAllCampaignResponse = campaignService.getAllCampaign(getAllCampaignRequest);
    List<CampaignType> list = getAllCampaignResponse.getCampaignTypes();
    String campaigns = "";
    campaigns += "<option value='' selected='selected'>请选择推广计划</option>";
    for (CampaignType campaignType : list)
        campaigns += "<option value=" + campaignType.getCampaignId() + ">" + campaignType.getCampaignName() + "</option>";
    out.print(campaigns);
%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>
