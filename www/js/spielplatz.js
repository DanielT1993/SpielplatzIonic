

function neuerSpielplatz() {
    
    var myspielplatz = new Apiomat.spielplatz();
    var spielplatzstrasse = document.getElementById("spielplatzstraße").value;
    var spielplatzhausnr = document.getElementById("hausnummer").value;
    var spielplatzplz = document.getElementById("plz").value;    
    
    var geocoder = new google.maps.Geocoder();
    var address = plz + " aschaffenburg " + spielplatzstrasse + " " + spielplatzhausnr ;
    var spielplatzlatitude;
    var spielplatzlongitude ;    
    geocoder.geocode( { 'address': address}, function(results, status) {

      if (status == google.maps.GeocoderStatus.OK) {
         spielplatzlatitude = results[0].geometry.location.lat();
        spielplatzlongitude = results[0].geometry.location.lng();
      } 
    })
     
    var spielplatzname = document.getElementById("spielplatzname").value;
    var spielplatzstadtteil = document.getElementById("stadtteil").value;
    var spielplatzalter = document.getElementById("alter").value;
    var spielplatzsitz = document.getElementById("sitzgelegenheiten").value;
    var spielplatzgroesse = document.getElementById("groesse").value;
    var spielplatzstatus = document.getElementById("status").value;
    var spielplatzrutsche = document.getElementById("rutsche").checked;
    var spielplatzkletterturm = document.getElementById("kletterturm").checked;   
    var spielplatzwippe = document.getElementById("wippe").checked;   
    var spielplatzballsport = document.getElementById("ballsport").checked;   
    var spielplatztischtennis = document.getElementById("tischtennis").checked;
    var spielplatzschaukel = document.getElementById("schaukel").checked;  
    var spielplatzsandkasten = document.getElementById("sandkasten").checked;  
    var spielplatzwasserspiele = document.getElementById("wasserspiele").checked; 
    var spielplatzklettergerust = document.getElementById("klettergerust").checked; 
    var spielplatzseilbahn = document.getElementById("seilbahn").checked;
    var spielplatzspielobjekt = document.getElementById("spielobjekt").checked; 
    var spielplatzsonstiges = document.getElementById("sonstiges").checked;      

    myspielplatz.setName(spielplatzname);
    myspielplatz.setStraße(spielplatzstrasse);
    myspielplatz.setHausnummer(spielplatzhausnr);
    myspielplatz.setPlz(spielplatzplz);
    myspielplatz.setLatitude(spielplatzlatitude);
    myspielplatz.setLongitude(spielplatzlongitude);
    
    if(spielplatzstadtteil == 1){
        myspielplatz.setStadtteil("Gailbach");}
    if(spielplatzstadtteil == 2){
        myspielplatz.setStadtteil("Nilkheim");}
    if(spielplatzstadtteil == 3){
        myspielplatz.setStadtteil("Obernau");}
    if(spielplatzstadtteil == 4){
        myspielplatz.setStadtteil("Schweinheim");}
    if(spielplatzstadtteil == 5){
        myspielplatz.setStadtteil("Strietwald");}
    
    if(spielplatzalter == 1){
        myspielplatz.setAltersgruppe("Bis 5 Jahre");}
    if(spielplatzalter == 2){
        myspielplatz.setAltersgruppe("Bis 10 Jahre");}
    if(spielplatzalter == 3){
        myspielplatz.setAltersgruppe("Bis 15 Jahre");}
    
    if(spielplatzsitz == 1){
        myspielplatz.setSitzgelegenheiten("Für bis zu 5 Personen");}
    if(spielplatzsitz == 2){
        myspielplatz.setSitzgelegenheiten("Für bis zu 10 Personen");}
    if(spielplatzsitz == 3){
        myspielplatz.setSitzgelegenheiten("Für mehr als 20 Personen");}
    
    myspielplatz.setGröße(spielplatzgroesse);
    
    if(spielplatzstatus == 1){
        myspielplatz.setStadtteil("Bespielbar");}
    if(spielplatzstatus == 2){
        myspielplatz.setStadtteil("Nicht bespielbar");}
    
     if(spielplatzrutsche == true){
        myspielplatz.setRutsche("rutsche.png");} 
    else{
         myspielplatz.setRutsche("rutscheinaktiv.png");     
        }
    
    if(spielplatzkletterturm == true){
        myspielplatz.setKletterturm("kletterturm.png");} 
    else{
         myspielplatz.setKletterturm("kletterturminaktiv.png");     
        }
    
    if(spielplatzwippe == true){
        myspielplatz.setWippe("wippe.png");} 
    else{
         myspielplatz.setWippe("wippeinaktiv.png");     
        }
    
     if(spielplatzballsport == true){
        myspielplatz.setBallsport("ballspielfeld.png");} 
    else{
         myspielplatz.setBallsport("ballspielfeldinaktiv.png");     
        }
    
    if(spielplatztischtennis == true){
        myspielplatz.setTischtennis("tischtennisplatte.png");} 
    else{
         myspielplatz.setTischtennis("tischtennisplatteinaktiv.png");     
        }
    
    if(spielplatzschaukel == true){
        myspielplatz.setSchaukel("schaukel.png");} 
    else{
         myspielplatz.setSchaukel("schaukelinaktiv.png");     
        }
    
    if(spielplatzsandkasten == true){
        myspielplatz.setSandkasten("sandkasten.png");} 
    else{
         myspielplatz.setSandkasten("sandkasteninaktiv.png");     
        }
    
    if(spielplatzwasserspiele == true){
        myspielplatz.setWasserspiele("wasserspielplatz.png");} 
    else{
         myspielplatz.setWasserspiele("wasserspielplatzinaktiv.png");     
        }
    
    if(spielplatzklettergerust == true){
        myspielplatz.setKlettergerüst("klettergeruest.png");} 
    else{
         myspielplatz.setKlettergerüst("klettergeruestinaktiv.png");     
        }
    
    if(spielplatzseilbahn == true){
        myspielplatz.setSeilbahn("seilbahn.png");} 
    else{
         myspielplatz.setSeilbahn("seilbahninaktiv.png");     
        }
    
    if(spielplatzspielobjekt == true){
        myspielplatz.setSpielobjekt("spielobjekt.png");} 
    else{
         myspielplatz.setSpielobjekt("spielobjektinaktiv.png");     
        }
      if(spielplatzsonstiges == true){
        myspielplatz.setSonstiges("sonstiges.png");} 
    else{
         myspielplatz.setSonstiges("sonstigesinaktiv.png");     
        }
    
    
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

function sterne(){
    var id = "id == id(5731c934e4b0d0bb222035ef)";
    var zahl = 4;

    var myspielplatz = new Apiomat.spielplatz();
        myspielplatz.load(id, {
          onOk: function() {
            myspielplatz.setGesamtbewertungsp(zahl);
            myspielplatz.save({
              onOk: function() {
                //do some other stuff here
                alert("successfully saved");
              },
              onError: function() {
                console.log(error);
              }
            })
          },
          onError: function(error) {
            console.log(error);
          }
        });
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



