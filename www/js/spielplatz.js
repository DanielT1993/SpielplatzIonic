var statusField = document.getElementById("status");  

function signup()
  {
    var user = new Apiomat.User();
    user.setUserName("test");
    user.setPassword("test");
    Apiomat.Datastore.configureWithCredentials(user);
 
    var saveCB = {
      onOk: function() {
        statusField.innerHTML = "Saved user successfully";
        loadTasks();
      },
      onError: function(error) {
        statusField.innerHTML = "Some error occured. "+ error.statusCode + " --> " + error.message;
      }
    };
    user.loadMe({
      onOk: function() {
        statusField.innerHTML = "Succefully logged in";
        loadTasks();
      },
      onError: function(error) {
        statusField.innerHTML= "No user there. Will create new one...";
        user.save(saveCB);
      }
    });
  }

function neuerSpielplatz() {
    
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
}

function bewerten() {
    var mybewertungen = new Apiomat.bewertungen();

            

        mybewertungen.save({
            onOk : function() {
            //object successfully saved
            alert("Saved succesfully spielplatz");
            },
            onError : function(error) {
            alert(spielplatzname);
        }
        });
    
}

