
function signup()
  {
    var user = new Apiomat.User();
    user.setUserName("test23");
    user.setPassword("test23");
    Apiomat.Datastore.configureWithCredentials(user);
 
    var saveCB = {
      onOk: function() {
       
        
      },
      onError: function(error) {
       
      }
    };
    user.loadMe({
      onOk: function() {
      
        
      },
      onError: function(error) {
       
        user.save(saveCB);
      }
    });
  }




function neuerSpielplatz() {
    
    alert("geht");
    var myspielplatz = new Apiomat.spielplatz();
    


        myspielplatz.save({
            onOk : function() {
            //object successfully saved
            alert("Saved succesfully spielplatz");
            },
            onError : function(error) {
            alert("Error");
        }
        });
}

function detail(){
 Apiomat.spielplatz.getspielplatzs("", {
    onOk : function(loadedObjs) {
    //Now you can do sth with loaded objects (loadedObjs)
    
    var deeper = loadedObjs[1]["data"];
    console.log(deeper["name"]);
    document.getElementById("demo").innerHTML = deeper["name"];
    },
    onError : function(error) {
    }
    });

     alert("Function functioniert");
}


function deg2rad(deg) {
  return deg * (Math.PI/180)
}

function logout(){
    alert ("Erfolgreich abgemeldet");
    window.location = "#/app/login"; 
}



var attempt = 3;
    function login(){
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    if ( username == "admin" && password == "bier"){
        alert ("Login successfully");
            window.location = "#/app/spielplatzanlegen"; // Redirecting to other page.
        return false;
        }
    else{
        attempt --;// Decrementing by one.
        alert("Noch "+attempt+" Versuche;");
        // Disabling fields after 3 attempts.
    if( attempt == 0){
        document.getElementById("username").disabled = true;
        document.getElementById("password").disabled = true;
        document.getElementById("submit").disabled = true;
        return false;
        }
    }
}


