
function signup()
  {
    var user = new Apiomat.User();
    user.setUserName("test212");
    user.setPassword("test212");
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
    
    var myspielplatz = new Apiomat.spielplatz();
    var myspielplatzausstattung = new Apiomat.ausstattung();

            var spielplatzname = document.getElementById("spielplatzname").value;
            var spielplatzstraße = document.getElementById("spielplatzstraße").value;
            var hausnummer = document.getElementById("hausnummer").value;
            var stadtteil = document.getElementById("stadtteil").value;
            var altersgruppe = document.getElementById("altersgruppe").value;
            var groesse = document.getElementById("groesse").value;
            var sitzgelegenheiten = document.getElementById("sitzgelegenheiten").value;
            var status = document.getElementById("status").value;
            var latitude = document.getElementById("latitude").value;
            var longitude = document.getElementById("longitude").value;

            var seilbahn = document.getElementById("seilbahn").value;

            myspielplatz.setName(spielplatzname);
            myspielplatz.setStraße(spielplatzstraße);
            myspielplatz.setHausnummer(hausnummer);
            myspielplatz.setStadtteil(stadtteil);
            myspielplatz.setAltersgruppe(altersgruppe);
            myspielplatz.setGröße(groesse);
            myspielplatz.setSitzgelegenheiten(sitzgelegenheiten);
            myspielplatz.setStatus(status);
            //myspielplatz.setLatitude(latitude);
            //myspielplatz.setLongitude(longitude);
            myspielplatzausstattung.setSeilbahn(seilbahn);

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
            alert("Saved succesfully Bewertung");
            },
            onError : function(error) {
            alert(spielplatzname);
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



 
         



