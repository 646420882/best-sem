/**
 * Created by XiaoWei on 2014/10/13.
 */
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
var myData = [
    [1,23,'girl',"成都市天祥街","无标记"],
    [1,23,'girl',"成都市天祥街","无标记"],
    [1,23,'girl',"成都市天祥街","无标记"],
    [1,23,'girl',"成都市天祥街","无标记"]
];
var store = Ext.create('Ext.data.ArrayStore', {
    fields: [
        {name: 'id',type:'int'},
        {name: 'age',      type: 'int'},
        {name: 'sex',     type: 'string'},
        {name: 'address',  type: 'string'},
        {name: 'remark', type: 'string'}
    ],
    data: myData
});
Ext.create("Perfect.view.Navigation");
Ext.create("Perfect.view.model.GridOne");
Ext.create("Perfect.view.model.JingJia");
Ext.create("Perfect.view.model.CiKu");
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
        // could use a TreePanel or AccordionLayout for navigational items
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
        xtype: 'tabpanel', // TabPanel itself has no title
        activeTab: 0     // First tab active by default
    }]

});
