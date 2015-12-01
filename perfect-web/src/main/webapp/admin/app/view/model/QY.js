Ext.define('QYModel', {
    extend:'Ext.data.Model',
    fields: [
        {name: 'idObj', type: 'int'},
        {name: 'userName',  type: 'string'},
        {name: 'userState', type: 'int'},
        {name:'baiduUserName',type:'string'},
        {name:'baiduState',type:'int'}
    ]
});
Ext.create("Ext.data.Store",{
    storeId:"QYStore",
    autoLoad: true,
    model:'QYModel',
    proxy: {
        type: 'ajax',
        url:'../account/getAccountAll',
        reader: {
            type: 'json',
            rootProperty: 'rows'
        }
    }
});
function convert(val){
    if(val==0){
        return "<span style='color: red'>未通过审核</span>";
    }
    else{
        return "<span>已通过审核</span>";
    }

};
function geiBaidu(val){
    if(val==0){
        return "<span style='color: red'>未启用</span>";
    }
    else{
        return "<span>已启用</span>";
    }

};
Ext.define("Perfect.view.model.QY", {
    extend: 'Ext.form.Panel',
    alias: 'widget.QY',
    bodyPadding: 10,
    title: '百度账号启用/禁用',
    items:[
        {
            xtype:'grid',
            title:'执行状态',
            border:true,
            columnLines:true,
            store: Ext.data.StoreManager.lookup('QYStore'),
            columns: [
                { text: 'id',  dataIndex: 'idObj',hidden:true},
                { text: '系统账号',  dataIndex: 'userName', flex: 2},
                { text: '审核状态', dataIndex: 'userState',renderer: convert, flex: 1},
                { text: '百度账号', dataIndex: 'baiduUserName', flex: 3},
                { text: '启用状态', dataIndex: 'baiduState',renderer: geiBaidu,flex: 1 },
                {text: '操作',renderer:function(value,cellmeta,record,rowIndex,columnIndex,store){
                    var value=record.get("userName");
                    var id=record.get("idObj");
                    if(id != 0){
                        return "<a href='javascript:void(0)' onclick='operate(\""+value+"\",1,\""+id+"\")'>启用</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                               "<a href='javascript:void(0)' onclick='operate(\""+value+"\",0,\""+id+"\")'>禁用</a>"
                    }

                },flex: 1}
            ],
            tools: [
                {
                    type: 'refresh',
                    handler: function () {
                       Ext.StoreManager.lookup("QYStore").load();
                    }
                }
            ]
        }
    ]

    }
);
function operate(userName , state ,id){
    Ext.Msg.show({
        title:'提示!',
        message:"是否确认启用/禁用?",
        icon:Ext.Msg.QUESTION,
        buttons:Ext.Msg.YESNO,
        fn:function(chiose){
            if(chiose=="yes"){
                Ext.Ajax.request({
                    url:'/account/updateAccountAllState',
                    params:{
                        userName: userName,
                        baiduId: id,
                        state: state
                    },
                    success:function(response){
                        var json= JSON.parse(response.responseText);
                      if(json.rows==1){
                          var grid=Ext.getCmp("urlGird");
                          grid.getStore().load();
                      }else{
                          Ext.Msg.alert("提示!","操作失败!");
                      }

                    }
                });
            }
        }
    });
}


