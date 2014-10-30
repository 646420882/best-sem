/**
 * Created by XiaoWei on 2014/10/28.
 */
var Le = Ext.define("Le", {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'category', type: 'string'}
    ]
});
Ext.create("Ext.data.Store", {
    storeId: 'storeTr',
    model: "Le",
    proxy: {
        type: 'ajax',
        url: '../person/getHyk',
        reader: {
            type: 'json',
            rootProperty: 'rows'
        }
    }
});

var hykModel=Ext.define("hykModel",{
    extend:'Ext.data.Model',
    fields:[
        {name:'id',type:"string"},
        {name:'tr',type:'string'},
        {name:'cg',type:'string'},
        {name:'gr',type:'string'},
        {name:'kw',type:'string'},
        {name:'url',type:'string'}
    ]
});
Ext.create("Ext.data.Store",{
    storeId:'hykStore',
    model:'hykModel',
    proxy: {
        pageSize:10,
        type: 'ajax',
        url: '../person/getHyk',
        reader: {
            type: 'json',
            rootProperty: 'rows',
            totalProperty:'count'
        }
    }
});
Ext.define("Perfect.view.model.HYK", {
    extend: 'Ext.form.Panel',
    alias: 'widget.HYK',
    bodyPadding: 10,
    title: '行业库',
    items: [
        {
            xtype: "form",
            title: '查询',
            bodyPadding:10,
            border:true,
            items: [
                {
                    fieldLabel: '选择行业',
                    xtype: 'combobox',
                    query: 'remote',
                    name: 'trade',
                    id:'tradeComboBox',
                    displayField: 'category',
                    valueField: 'category',
                    emptyText: '行业..',
                    anchor:'100%',
                    editable: false,
                    store: Ext.StoreManager.lookup("storeTr")
                },
                {
                    xtype:'grid',
                    border:true,
                    title:'行业库列表',
                    height:330,
                    minHeight:280,
//                    store:hykStore,
                    columns:[
                        {text:'行业名'},
                        {text:'类别'},
                        {text:'分组'},
                        {text:'关键字'},
                        {text:'Url',flex:1}
                    ],
                    bbar: {
                        xtype: 'pagingtoolbar',
                        pageSize: 10,
                        store: Ext.StoreManager.lookup("storeTr"),
                        displayInfo: true,
                        plugins: new Ext.ux.ProgressBarPager()
                    }
                }
            ],
            tools:[
                {
                    type:'refresh',
                    handler:function(){
                        Ext.StoreManager.lookup("storeTr").load();
                    }
                }
            ]
        },
        {
            xtype: "form",
            title: '添加行业库',
            url: '../person/saveTr',
            border: true,
            bodyPadding: 10,
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: '行业名',
                    msgTarget: 'side',
                    anchor:'100%',
                    name: 'tr',
                    allowBlank: false,
                    blankText: '你准备连行业名都填么？你叶大爷的...'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: '行业类别',
                    name: 'cg',
                    anchor:'100%',
                    msgTarget: 'side',
                    allowBlank: false
                },
                {
                    xtype: 'textfield',
                    fieldLabel: '分组',
                    name: 'gr',
                    anchor:'100%',
                    msgTarget: 'side',
                    allowBlank: false
                },
                {
                    xtype: 'textfield',
                    fieldLabel: '关键字',
                    name: 'kw',
                    anchor:'100%',
                    msgTarget: 'side',
                    allowBlank: false
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'url',
                    msgTarget: 'side',
                    anchor:'100%',
                    name: 'url',
                    allowBlank: false
                }
            ],
            buttons: [
                {
                    text: '提交',
                    handler: function () {
                        var _form = this.up("form").getForm();
                        if (_form.isValid()) {
                            _form.submit({
                                waitMsg: '正在提交中..',
                                success: function (form, action) {
                                    if (action.result.success == 1) {
                                        form.reset();
                                        Ext.StoreManager.lookup("storeTr").load();
                                        Ext.Msg.alert("提示", "添加成功!");
                                    }else if(action.result.success==0){
                                        Ext.Msg.alert("提示", "已经存在该\"行业名\"和\"关键字\"!");
                                    }
                                },
                                failure: function (form, action) {
                                    Ext.Msg.alert("提示!", "访问异常!");
                                }
                            });
                        }
                    }
                }
            ]
        }
    ]
});
