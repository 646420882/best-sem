Ext.define('ShenheModel', {
    extend:'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'userName',  type: 'string'},
        {name: 'password', type: 'string'},
        {name:'companyName',type:'string'},
        {name:'state',type:'int'},
        {name:'access',type:'int'},
        {name:'email',type:'string'}
    ]
});
Ext.create("Ext.data.Store",{
    storeId:"ShenheStore",
    autoLoad: true,
    model:'ShenheModel',
    proxy: {
        type: 'ajax',
        url:'../account/getAccount',
        reader: {
            type: 'json',
            rootProperty: 'rows'
        }
    }
});
function convert(val){
    if(val==0){
        return "<span>未审核</span>";
    }
    else{
        return "<span>未审核</span>";
    }

};
function operate(){
    return "<a>审核</a>"

};
Ext.define("Perfect.view.model.SH", {
    extend: 'Ext.form.Panel',
    alias: 'widget.ZH',
    bodyPadding: 10,
    title: '账号审核',
    items:[
        {
            xtype:'grid',
            title:'执行状态',
            id:"urlGird",
            border:true,
            columnLines:true,
            store: Ext.data.StoreManager.lookup('ShenheStore'),
            columns: [
                { text: '帐号',  dataIndex: 'userName', flex: 1 },
                { text: '审核状态', dataIndex: 'state',renderer: convert },
                {text: '操作',renderer:function(value,cellmeta,record,rowIndex,columnIndex,store){
                    var value=record.get("userName");
                    return "<a href='javascript:void(0)' onclick='operate(\""+value+"\")'>操作</a>"
                }}
            ],
            tools: [
                {
                    type: 'refresh',
                    handler: function () {
                       Ext.StoreManager.lookup("ShenheStore").load();
                    }
                }
            ]
        }
    ]

    }
);
function operate(userName){
    Ext.Msg.show({
        title:'提示!',
        message:"是否通过该条数据审核？",
        icon:Ext.Msg.QUESTION,
        buttons:Ext.Msg.YESNO,
        fn:function(chiose){
            if(chiose=="yes"){
                Ext.Ajax.request({
                    url:'/account/auditAccount',
                    params:{
                        userName: userName
                    },
                    success:function(response){
                       var json= JSON.parse(response.responseText);
                      if(json.struts==1){
                          Ext.Msg.alert("提示!","审核成功!");
                          var grid=Ext.getCmp("urlGird");
                          grid.getStore().load();
                      }

                    }
                });
            }
        }
    });
}


