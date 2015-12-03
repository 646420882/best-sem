<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2015/10/12
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="dropdown-menus tabmodel" id="Timings" style="width:240px;">
    <div class="tabmodel_title"><span id="TimingTitle">定时</span><span class="fr glyphicon glyphicon-remove"
                                                                      onclick="timing.TimingClose()"></span>
    </div>
    <div class="timing_content">
        <div>
            <ul>
                <li><span>选择功能：</span><label class="checkbox-inlines"><input type="radio" name="TimingsRadio"
                                                                             value="Enable">启用</label><label
                        class="checkbox-inlines"><input type="radio" name="TimingsRadio" value="Pause">暂停</label></li>
                <li><span>日期：</span><input type="text" id="TimingDate" class="timing_date"/></li>
                <li><span>时间：</span><select id="timePeriod">
                    <option>01:00</option>
                    <option>02:00</option>
                    <option>03:00</option>
                    <option>04:00</option>
                    <option>05:00</option>
                    <option>06:00</option>
                    <option>07:00</option>
                    <option>08:00</option>
                    <option>09:00</option>
                    <option>10:00</option>
                    <option>11:00</option>
                    <option>12:00</option>
                    <option>13:00</option>
                    <option>14:00</option>
                    <option>15:00</option>
                    <option>16:00</option>
                    <option>17:00</option>
                    <option>18:00</option>
                    <option>19:00</option>
                    <option>20:00</option>
                    <option>21:00</option>
                    <option>22:00</option>
                    <option>23:00</option>
                    <option>24:00</option>
                </select>
                </li>
            </ul>
        </div>
        <span style="color: red;" id="filter_msg"></span>

        <div class="fr">
            <button type="button" class="btn btn-primary btn-sm " onclick="timing.TimingOk()">确认</button>
            <button type="button" class="btn btn-default btn-sm " onclick="timing.TimingClose()">取消</button>
        </div>

    </div>
</div>
