/**
 * Created by XiaoWei on 2014/10/16.
 */
Ext.create('Ext.data.Store', {
    storeId:'testStore',
    fields:['index', 'Url', 'Status'],
    data:{'items':[
        { 'index': '1',  "Url":"lisa.simpsons.com",  "Status":true  },
        { 'index': '2',  "Url":"bart.simpsons.com",  "Status":false },
        { 'index': '3', "Url":"homer.simpsons.com",  "Status":true  },
        { 'index': '4', "Url":"marge.simpsons.com", "Status":false  }
    ]},
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            rootProperty: 'items'
        }
    }
});
function getFact(sr){
    if(sr){
        return "<span style='color: green;'>空闲</span>";
    }else{
        return "<span style='color: red;'>不空闲</span>";
    }
}
Ext.define("Perfect.view.model.JingJia",{
    extend:'Ext.panel.Panel',
    alias:"widget.JJ",
    title:'智能竞价',
    bodyPadding: 10,
    items:[
        {
            xtype:'toolbar',
            items:['请输入Url请求地址',{
                xtype:'textfield',
                emptyText:'请输入Url地址',
                allowBlank:false,
                afterLabelTextTpl:required,
                msgTarget: 'side'

            },{
                text:'提交'
            }]
        },{
            xtype:'grid',
            title:'执行状态',
            store:Ext.data.StoreManager.lookup("testStore"),
            columns: [
                { text: 'Index',  dataIndex: 'index' },
                { text: 'Url', dataIndex: 'Url', flex: 1 },
                { text: 'Status', dataIndex: 'Status',renderer:getFact }
            ]
        }
    ]
})