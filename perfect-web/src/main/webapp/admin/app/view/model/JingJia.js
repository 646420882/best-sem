/**
 * Created by XiaoWei on 2014/10/16.
 */

//Ext.define('Url', {
//    extend:'Ext.data.Model',
//    fields: [
//        {name: 'id', type: 'string'},
//        {name: 'request',  type: 'string'},
//        {name: 'idle', type: 'boolean'},
//        {name:'finishTime',type:'int'}
//    ]
//});
//Ext.create("Ext.data.Store",{
//    storeId:"urlStore",
//    autoLoad: true,
//    model:'Url',
//    proxy: {
//        type: 'ajax',
//        url:'biddingUrl/list',
//        reader: {
//            type: 'json',
//            rootProperty: 'rows'
//        }
//    }
//})
//function getFact(sr){
//    if(sr){
//        return "<span style='color: green;'>空闲</span>";
//    }else{
//        return "<span style='color: red;'>不空闲</span>";
//    }
//}
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
            },"-",{
                text:'刷新',
                handler:function(){
                    Ext.Ajax.request({
                        url:'biddingUrl/list',
                        success:function(response){
                            var json=JSON.parse(response.responseText);
                            var results=json.rows;
                            var idle = 0;
                            var notIdle = 0;
                            Ext.each(results, function (item) {
                                if(item.idle == true){
                                    idle ++;
                                }else{
                                    notIdle ++;
                                }
                            });
                            Ext.getCmp('urlTotal').setValue(json.rows.length);
                            Ext.getCmp('urlFree').setValue(idle);
                            Ext.getCmp('unurlFree').setValue(notIdle);
                        },
                        failure:function(response){}
                    });
                }
            }
            ]
        },{
            xtype:'form',
            bodyPadding:10,
            border:true,
           items:[
               {
                   xtype:'displayfield',
                   fieldLabel:'Url总数',
                   id:'urlTotal',
                   value:0
               },{
                   xtype:'displayfield',
                   fieldLabel:'空闲数',
                   id:'urlFree',
                   value:0
               },{
                   xtype:'displayfield',
                   fieldLabel:'非空闲数',
                   id:'unurlFree',
                   value:0
               }
           ],
            listeners:{
                afterrender:function(i,o){
                    Ext.Ajax.request({
                        url:'biddingUrl/list',
                        success:function(response){
                            var json=JSON.parse(response.responseText);
                            var results=json.rows;
                            var idle = 0;
                            var notIdle = 0;
                            Ext.each(results, function (item) {
                                if(item.idle == true){
                                    idle ++;
                                }else{
                                    notIdle ++;
                                }
                            });
                            Ext.getCmp('urlTotal').setValue(json.rows.length);
                            Ext.getCmp('urlFree').setValue(idle);
                            Ext.getCmp('unurlFree').setValue(notIdle);
                        },
                        failure:function(response){}
                    });
                }
            }

        }
    ]
});
