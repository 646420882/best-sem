/**
 * Created by XiaoWei on 2014/10/13.
 */
Ext.onReady(function(){
    Ext.require("Perfect.view.Navigation");
});
Ext.Date.patterns = {
    ISO8601Long:"Y-m-d H:i:s",
    ISO8601Short:"Y-m-d",
    ShortDate: "n/j/Y",
    LongDate: "l, F d, Y",
    FullDateTime: "l, F d, Y g:i:s A",
    MonthDay: "F d",
    ShortTime: "g:i A",
    LongTime: "g:i:s A",
    SortableDateTime: "Y-m-d\\TH:i:s",
    UniversalSortableDateTime: "Y-m-d H:i:sO",
    YearMonth: "F, Y"
};
var dt = new Date();
var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.define("Perfect.view.Viewport", {
    extend: "Ext.container.Viewport",
    layout:'border',
    items: [{
        region: 'north',
        html: '<h1 class="x-panel-header">Perfect后台管理系统.</h1>',
        border: true,
        margin: '0 0 5 0'
    }, {
        region: 'west',
        xtype:"nav"
    }, {
        xtype:"toolbar",
        region: 'south',
        split: true,
        items:["当前日期："+Ext.Date.format(dt, Ext.Date.patterns.ISO8601Short)+"",'->','&copy;成都松石科技有限公司'],
        height:50,
        maxHeight:50,
        minHeight:50
    }, {
        region: 'east',
        title: '右边面板',
        collapsible: true,
        split: true,
        width: 150
    }, {
        region: 'center',
        id:'tabs',
        xtype: 'tabpanel',
        activeTab: 0     // 默认选中第几个选项卡
    }]

});
