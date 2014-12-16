
var x = require("./json.json");

var newjson = [];


for (var i = 0; i < x.length; i++) {
    var obj = {};

    obj.clientCustomerId = x[i][0];
    obj.currencyCode = x[i][1];
    obj.timezone = x[i][2];
    obj.canManageClients = (x[i][4] == 0) ? false : true;
    obj.name= x[i][5];
    obj.topAccountId = x[i][6];

    newjson.push(obj);
}


console.log(JSON.stringify(newjson, 0, 2));

