function neuerSpielplatz() {
/* Create a new member/user of your app */
var user = new Apiomat.User('test' + new Date().getTime(), '1,618');
Apiomat.Datastore.configureWithCredentials(user);

 
/*
Later on you may want to load data using query arguments.
More query examples can be found in {@see <a href="http://doc.apiomat.com">our wiki</a>}
This statement will return all objects with exact name \"XYZ\"
*/
Apiomat.spielplatz.getspielplatzs("name == \"XYZ\"", {
onOk : function(loadedObjs) {
//Now you can do sth with loaded objects (loadedObjs)
alert("Count of loaded objects: " + loadedObjs.length);
},
onError : function(error) {
//handle error
}
});
}

function detail(){
    alert("Geht");
Apiomat.Spielplatz.getSpielplatzs(undefined, {
  onOk: function(tasks) {
    for (var i=0; i < spielplatzs.length;i++) {
      addToTable(spielplatzs[i]);
    }
  },
  onError: function(error) {
    status.innerHTML = "Can't load list: "+ error.statusCode + " --> " + error.message;
  }
});

    
}