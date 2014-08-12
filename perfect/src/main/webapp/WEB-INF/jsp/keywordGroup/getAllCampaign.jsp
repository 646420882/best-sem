<%@ page import="com.perfect.dao.CampaignDAO" %>
<%@ page import="com.perfect.entity.CampaignEntity" %>
<%@ page import="com.perfect.mongodb.dao.impl.CampaignDAOImpl" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: baizz
  Date: 14-8-11
  Time: 下午5:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CampaignDAO campaignDAO = new CampaignDAOImpl();
    List<CampaignEntity> list = campaignDAO.findAll();
    String campaigns = "";
    campaigns += "<option value='' selected='selected'>请选择推广计划</option>";
    for (CampaignEntity entity : list)
        campaigns += "<option value=" + entity.getCampaignId() + ">" + entity.getCampaignName() + "</option>";
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
