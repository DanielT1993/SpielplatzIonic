var saveCB = {
    onOk : function() {
        console.log("saved");
        //Now you can create objects of your class with this new user..
        var myspielplatz = new Apiomat.spielplatz();

            var spielplatzname = document.getElementById("spielplatzname").value;
            var spielplatzstraße = document.getElementById("spielplatzstraße").value;

            myspielplatz.setName(spielplatzname);
            myspielplatz.setStraße(spielplatzname);

        myspielplatz.save({
            onOk : function() {
            //object successfully saved
            alert("Saved succesfully spielplatz");
            },
            onError : function(error) {
            alert(spielplatzname);
        }
        });
    },
    onError : function(error) {
    console.log("Some error occured: (" + error.statusCode + ")" + error.message);
    }
}

function neuerSpielplatz() {
/* Create a new member/user of your app */
var user = new Apiomat.User('test' + new Date().getTime(), '1,618');
Apiomat.Datastore.configureWithCredentials(user);
user.save(saveCB);
 
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
    
    alert("Hello! I am an alert box!");
    
}