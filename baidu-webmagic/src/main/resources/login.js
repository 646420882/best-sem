var casper = require('casper').create();

casper.start('http://www2.baidu.com',function(){
    this.fill('form#login',{
        'entered_login':'baidu-bjperfrct2131113',
        'entered_password':'Perfect2014'
    }, true);

    this.echo(this.getHTML());

})

casper.then(function() {
    this.echo(document.body.innerText);
    this.evaluateOrDie(function() {

        return /message sent/.test(document.body.innerText);
    }, 'sending message failed');
});


casper.run();