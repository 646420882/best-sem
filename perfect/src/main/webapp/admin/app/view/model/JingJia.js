/**
 * Created by XiaoWei on 2014/10/16.
 */
Ext.define('Url', {
    extend:'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'request',  type: 'string'},
        {name: 'idle', type: 'boolean'},
        {name:'finishTime',type:'int'}
    ]
});
Ext.create("Ext.data.Store",{
    storeId:"urlStore",
    autoLoad: true,
    model:'Url',
    proxy: {
        type: 'ajax',
        url:'biddingUrl/list',
        reader: {
            type: 'json',
            rootProperty: 'rows'
        }
    }
})
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
                id:'toolbar_Url',
                emptyText:'请输入Url地址',
                allowBlank:false,
                afterLabelTextTpl:required,
                msgTarget: 'side'

            },{
                text:'提交',
                handler:function(){
                 var url=Ext.getCmp("toolbar_Url");
                    if(url.isValid()){
                       Ext.Ajax.request({
                           url:'biddingUrl/add',
                           params:{
                               url:url.getValue()
                           },
                           success: function(response){

                           },
                           failure:function(response){

                           }
                       });
                    }

                }
            }]
        },{
            xtype:'grid',
            title:'执行状态',
            id:"urlGird",
            border:true,
            store:Ext.data.StoreManager.lookup('urlStore'),
            columns: [
                { text: 'Index',  dataIndex: 'id' },
                { text: 'Url', dataIndex: 'request', flex: 1 },
                { text: 'Status', dataIndex: 'idle',renderer:getFact }
            ],
            tools: [
                {
                    type: 'refresh',
                    handler:function(){
//                        new Ext.Window({
//                            title: '新窗口',
//                            width: 360,
//                            modal: true,
//                            bodyPadding: 10,
//                            defaultType: 'textfield',
//                            id:'box1',
//                            items: [
//                                {
//                                    allowBlank: false,
//                                    fieldLabel: 'User ID',
//                                    labelWidth:120,
//                                    name: 'user',
//                                    emptyText: 'user id'
//                                },
//                                {
//                                    allowBlank: false,
//                                    fieldLabel: 'Password',
//                                    labelWidth:120,
//                                    name: 'pass',
//                                    emptyText: 'password',
//                                    inputType: 'password'
//                                },
//                                {
//                                    xtype: 'checkbox',
//                                    fieldLabel: 'Remember me',
//                                    labelWidth:120,
//                                    name: 'remember'
//                                }
//                            ],
//                            buttons: [
//                                { text: 'Register' },
//                                { text: 'Login' }
//                            ]
//                        }).show();
                        var _grid=Ext.getCmp("urlGird");
                        _grid.getStore().load();
                    }
                }
            ]
        }
    ]
});
