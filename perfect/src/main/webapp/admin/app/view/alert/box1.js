/**
 * Created by XiaoWei on 2014/10/23.
 */
Ext.define("Perfect.view.alert.Box1",{
    extend:"Ext.window.Window",
    id:'Box1',
    title: '新窗口',
    width: 360,
    modal: true,
    bodyPadding: 10,
    defaultType: 'textfield',
    items: [
        {
            allowBlank: false,
            fieldLabel: 'User ID',
            labelWidth:120,
            name: 'user',
            emptyText: 'user id'
        },
        {
            allowBlank: false,
            fieldLabel: 'Password',
            labelWidth:120,
            name: 'pass',
            emptyText: 'password',
            inputType: 'password'
        },
        {
            xtype: 'checkbox',
            fieldLabel: 'Remember me',
            labelWidth:120,
            name: 'remember'
        }
    ],
    buttons: [
        { text: 'Register' },
        { text: 'Login' }
    ]
});