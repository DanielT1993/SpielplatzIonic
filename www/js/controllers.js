angular.module('starter.controllers', [])


.controller('AppCtrl', function($scope, $ionicModal, $timeout) {

  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //$scope.$on('$ionicView.enter', function(e) {
  //});

  // Form data for the login modal
  $scope.loginData = {};

  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.modal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeLogin = function() {
    $scope.modal.hide();
  };

  // Open the login modal
  $scope.login = function() {
    $scope.modal.show();
  };

  // Perform the login action when the user submits the login form
  $scope.doLogin = function() {
    console.log('Doing login', $scope.loginData);

    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeLogin();
    }, 1000);
  };
})

.controller('SpielplatzCtrl', function(){
    $('select').material_select();
      $('numberarea#plz').characterCounter();
})

.controller('KontaktCtrl', function() {
    $('textarea#kontaktmessage').characterCounter();
})

.controller('FilterspielplatzCtrl', function($scope, $scope, $scope) {
    
    $scope.showMe = true;
    $scope.filter = function() {
    var posa = 0;
    var posb = 0;   
    avg = 0;
        
        
    var spielplatzsanitar = document.getElementById("sanitar").checked; 
    var spielplatzgrillplatz = document.getElementById("grillplatz").checked;    
        
    var grillen;
    var sanitar;
    var filter3;
        
    if(spielplatzsanitar == true){
       sanitar = "sanitar.png";
        filter3 ="sanitäranlagen==\"" + sanitar + "\"";
    }else{
          filter3 ="";
    }
    if(spielplatzgrillplatz == true){
             grillen ="grillplatz.png";
            filter4 = "grillplatz==\"" +grillen + "\"";  }
    else{
          filter4 ="";
    }  
        
     if(spielplatzgrillplatz == true && spielplatzsanitar == true ){
             grillen ="grillplatz.png";
            filter3 ="sanitäranlagen==\"" + sanitar + "\"";
            filter4 = "and grillplatz==\"" +grillen + "\"";  }
    else{
          filter5 ="";
    }     
        
    filter5 = filter3  + filter4;    
     console.log(filter5);    
    var filter= "grillplatz==\"grillplatz.png\"and sanitäranlagen==\"sanitarinaktiv.png\"";   
    var filter2 = "grillplatz==\"" + grillen + "\" and sanitäranlagen==\"" +sanitar + "\"" ;   
 
    //Array anlegen
    $scope.playlists = [];  
    $scope.arraysums = [];    
    Apiomat.spielplatz.getspielplatzs(filter5, {
    onOk : function(loadedObjs) {
        
    //Now you can do sth with loaded objects (loadedObjs)
    for (i = 0; i < loadedObjs.length; i++) {
        var arrayspielplaetze = loadedObjs[i]["data"];
        //Ausgabe in Konsole
        var name = arrayspielplaetze["name"];
        var strasse = arrayspielplaetze["straße"];
        var nummer = arrayspielplaetze["hausnummer"];
        var stadtteil = arrayspielplaetze["stadtteil"];
        var plz = arrayspielplaetze["plz"];
        var altersgruppe = arrayspielplaetze["altersgruppe"];
        var groesse = arrayspielplaetze["größe"];
        var status = arrayspielplaetze["status"];
        var bild = arrayspielplaetze["hauptbild"];
        var bewertung = arrayspielplaetze["gesamtbewertungsp"];

        var id = arrayspielplaetze["id"];
        
      function success(pos){
        posa = pos.coords.longitude; 
        posb = pos.coords.latitude;
      }    
     // alert(posa);   
      

      if (navigator.geolocation){         
         navigator.geolocation.getCurrentPosition(success);
      }else{
           alert("Geoortung wird nicht unterstützt!");
         }
     
      var lat1 = arrayspielplaetze["latitude"];
      var lon1 = arrayspielplaetze["longitude"];
      var lat2 = 49.351648;
      var lon2 = 9.148309;
      var R = 6371; // Radius of the earth in km
      var dLat = deg2rad(lat2-lat1);  // deg2rad below
      var dLon = deg2rad(lon2-lon1); 
      var a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);        ; 
      var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
      var d = R * c; // Distance in km
      var gerundetd = Math.round(d * 100)/100;

        var spid = "bewertungsid == ("+id+")";//document.getElementById("spid").innerHTML ;
        var sum = 0;
        //var avg =0;
        //alert(spid);
          Apiomat.bewertungen.getbewertungens(spid, {
          onOk : function(loadedObjs) {
          //alert(loadedObjs.length);
          for (i = 0; i < loadedObjs.length; i++) {
                var arraybewertungen = loadedObjs[i]["data"];
                //Ausgabe in Konsole
                var bewertungaus = arraybewertungen["gesamtbewertung"];
                sum +=  parseInt(bewertungaus);
          }
          avg = sum/loadedObjs.length;
          //console.log(avg);
          //alert(avg);
          $scope.arraysums.push({ avg: avg});
          },


          onError : function(error) {
          //handle error
          }

      })



      $scope.playlists.push({ title: name, bewertung: bewertung, bild: bild, altersgruppe: altersgruppe, groesse: groesse, status: status , plz: plz ,strasse: strasse, stadtteil: stadtteil, hausnummer: nummer, ort: gerundetd + " km", color: '#FF880E', id:id, avg: avg});
        
      }
    },
        
    onError : function(error) {
    }
    });
    /* Filter braucht keine Sternenansicht
    var checkExist = setInterval(function() {
           if ($('span.stars2').length) {
              $('span.stars2').stars2();
              clearInterval(checkExist);
           }
        }, 20);
          
    
        $.fn.stars2 = function() {
            return $(this).each(function() {
                $(this).html($('<span />').width(Math.max(0, (Math.min(5, parseFloat($(this).html())))) * 16));
            });
          }
          */
    }

  })

.controller('ShareCtrl', function($scope, $cordovaSocialSharing) {})

.controller('MapCtrl', function ($scope, $ionicLoading, $compile, $window, $stateParams) {
  
    var lat2=0;
        var long2=0;
    
    var url = ($stateParams.spielplatzId);
    var id = "id == id("+url+")";
    
    var idbewertung = "bewertungsid == ("+url+")";
        
    Apiomat.spielplatz.getspielplatzs(id, {
    onOk : function(loadedObjs) {
    //Now you can do sth with loaded objects (loadedObjs)
    //Now you can do sth with loaded objects (loadedObjs)
        for (i = 0; i < loadedObjs.length; i++) {
          var arraylocation = loadedObjs[i]["data"];
          //Ausgabe in Konsole
           lat2 = arraylocation["latitude"];
           long2 = arraylocation["longitude"]; 
        }
        initialize(lat2, long2);
    },
    onError : function(error) {
    //handle error
    }
    });
    function initialize(lat2, long2) {
        
              
       
        google.maps.event.addDomListener(window, 'load');
         

        var myLatlng = new google.maps.LatLng(lat2, long2);

        var mapOptions = {
            center: myLatlng,
            zoom: 16,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById("map"),
        mapOptions);

        //Marker + infowindow + angularjs compiled ng-click
        var contentString = "<div><a ng-click='clickTest()'>Click me!</a></div>";
        var compiled = $compile(contentString)($scope);

        var infowindow = new google.maps.InfoWindow({
            content: compiled[0]
        });

        var marker = new google.maps.Marker({
            position: myLatlng,
            map: map,
            title: 'Uluru (Ayers Rock)'
        });

        google.maps.event.addListener(marker, 'click', function () {
            infowindow.open(map, marker);
        });

        $scope.map = map;
    }

    $window.initialize = initialize; // callback in global context

    function loadScript(src) {
        var script = document.createElement("script");
        script.type = "text/javascript";
        document.getElementsByTagName("head")[0].appendChild(script);
        script.src = src;
    }

    loadScript('http://www.google.com.mt/jsapi');
    loadScript('http://maps.googleapis.com/maps/api/js?key=AIzaSyCmpTMw7IitqkPCAfwlwsZGd6cruNNLLnY&callback=initialize');



    $scope.centerOnMe = function () {
        if (!$scope.map) {
            return;
        }

        $scope.loading = $ionicLoading.show({
            content: 'Getting location',
            showBackdrop: false
        });

        navigator.geolocation.getCurrentPosition(function (pos) {
            $scope.map.setCenter(new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude));
            $scope.loading.hide();
        }, function (error) {
            alert('Unable to get location: ' + error.message);
        });
    };

    $scope.clickTest = function () {
        alert('Example of infowindow with ng-click')
    };
})

.controller('PlaylistsCtrl', function($scope, $scope) { 


      var posa = 0;
      var posb = 0;   
      avg = 0;
     
    //Array anlegen
    $scope.playlists = [];  
    $scope.arraysums = [];    
    Apiomat.spielplatz.getspielplatzs("", {
    onOk : function(loadedObjs) {
        
    //Now you can do sth with loaded objects (loadedObjs)
    for (i = 0; i < loadedObjs.length; i++) {
        var arrayspielplaetze = loadedObjs[i]["data"];
        //Ausgabe in Konsole
        var name = arrayspielplaetze["name"];
        var strasse = arrayspielplaetze["straße"];
        var nummer = arrayspielplaetze["hausnummer"];
        var stadtteil = arrayspielplaetze["stadtteil"];
        var plz = arrayspielplaetze["plz"];
        var altersgruppe = arrayspielplaetze["altersgruppe"];
        var groesse = arrayspielplaetze["größe"];
        var status = arrayspielplaetze["status"];
        var bild = arrayspielplaetze["hauptbild"];
        var bewertung = arrayspielplaetze["gesamtbewertungsp"];

        var id = arrayspielplaetze["id"];
        
      function success(pos){
        posa = pos.coords.longitude; 
        posb = pos.coords.latitude;
      }    
     // alert(posa);   
      

      if (navigator.geolocation){         
         navigator.geolocation.getCurrentPosition(success);
      }else{
           alert("Geoortung wird nicht unterstützt!");
         }
     
      var lat1 = arrayspielplaetze["latitude"];
      var lon1 = arrayspielplaetze["longitude"];
      var lat2 = 49.351648;
      var lon2 = 9.148309;
      var R = 6371; // Radius of the earth in km
      var dLat = deg2rad(lat2-lat1);  // deg2rad below
      var dLon = deg2rad(lon2-lon1); 
      var a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);        ; 
      var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
      var d = R * c; // Distance in km
      var gerundetd = Math.round(d * 100)/100;

        var spid = "bewertungsid == ("+id+")";//document.getElementById("spid").innerHTML ;
        var sum = 0;
        //var avg =0;
        //alert(spid);
          Apiomat.bewertungen.getbewertungens(spid, {
          onOk : function(loadedObjs) {
          //alert(loadedObjs.length);
          for (i = 0; i < loadedObjs.length; i++) {
                var arraybewertungen = loadedObjs[i]["data"];
                //Ausgabe in Konsole
                var bewertungaus = arraybewertungen["gesamtbewertung"];
                sum +=  parseInt(bewertungaus);
          }
          avg = sum/loadedObjs.length;
          //console.log(avg);
          //alert(avg);
          $scope.arraysums.push({ avg: avg});
          },


          onError : function(error) {
          //handle error
          }

      })



      $scope.playlists.push({ title: name, bewertung: bewertung, bild: bild, altersgruppe: altersgruppe, groesse: groesse, status: status , plz: plz ,strasse: strasse, stadtteil: stadtteil, hausnummer: nummer, ort: gerundetd + " km", color: '#FF880E', id:id, avg: avg});
        
      }
    },
        
    onError : function(error) {
    }
    });

    var checkExist = setInterval(function() {
           if ($('span.stars2').length) {
              $('span.stars2').stars2();
              clearInterval(checkExist);
           }
        }, 20);
          
    
        $.fn.stars2 = function() {
            return $(this).each(function() {
                $(this).html($('<span />').width(Math.max(0, (Math.min(5, parseFloat($(this).html())))) * 16));
            });
          }

        


})

.controller('PlaylistsCtrl2', function($scope, $scope) { 


      var posa = 0;
      var posb = 0;   
      avg = 0;
     
    //Array anlegen
    $scope.playlists = [];  
    $scope.arraysums = [];    
    Apiomat.spielplatz.getspielplatzs("", {
    onOk : function(loadedObjs) {
        
    //Now you can do sth with loaded objects (loadedObjs)
    for (i = 0; i < loadedObjs.length; i++) {
        var arrayspielplaetze = loadedObjs[i]["data"];
        //Ausgabe in Konsole
        var name = arrayspielplaetze["name"];
        var strasse = arrayspielplaetze["straße"];
        var nummer = arrayspielplaetze["hausnummer"];
        var stadtteil = arrayspielplaetze["stadtteil"];
        var plz = arrayspielplaetze["plz"];
        var altersgruppe = arrayspielplaetze["altersgruppe"];
        var groesse = arrayspielplaetze["größe"];
        var status = arrayspielplaetze["status"];
        var bild = arrayspielplaetze["hauptbild"];
        var bewertung = arrayspielplaetze["gesamtbewertungsp"];

        var id = arrayspielplaetze["id"];
        
      function success(pos){
        posa = pos.coords.longitude; 
        posb = pos.coords.latitude;
      }    
     // alert(posa);   
      

      if (navigator.geolocation){         
         navigator.geolocation.getCurrentPosition(success);
      }else{
           alert("Geoortung wird nicht unterstützt!");
         }
     
      var lat1 = arrayspielplaetze["latitude"];
      var lon1 = arrayspielplaetze["longitude"];
      var lat2 = 49.351648;
      var lon2 = 9.148309;
      var R = 6371; // Radius of the earth in km
      var dLat = deg2rad(lat2-lat1);  // deg2rad below
      var dLon = deg2rad(lon2-lon1); 
      var a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);        ; 
      var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
      var d = R * c; // Distance in km
      var gerundetd = Math.round(d * 100)/100;

        var spid = "bewertungsid == ("+id+")";//document.getElementById("spid").innerHTML ;
        var sum = 0;
        //var avg =0;
        //alert(spid);
          Apiomat.bewertungen.getbewertungens(spid, {
          onOk : function(loadedObjs) {
          //alert(loadedObjs.length);
          for (i = 0; i < loadedObjs.length; i++) {
                var arraybewertungen = loadedObjs[i]["data"];
                //Ausgabe in Konsole
                var bewertungaus = arraybewertungen["gesamtbewertung"];
                sum +=  parseInt(bewertungaus);
          }
          avg = sum/loadedObjs.length;
          //console.log(avg);
          //alert(avg);
          $scope.arraysums.push({ avg: avg});
          },


          onError : function(error) {
          //handle error
          }

      })



      $scope.playlists.push({ title: name, bewertung: bewertung, bild: bild, altersgruppe: altersgruppe, groesse: groesse, status: status , plz: plz ,strasse: strasse, stadtteil: stadtteil, hausnummer: nummer, ort: gerundetd + " km", color: '#FF880E', id:id, avg: avg});
        
      }
    },
        
    onError : function(error) {
    }
    });


        var checkExist = setInterval(function() {
           if ($('span.stars2filter2').length) {
              $('span.stars2filter2').stars2filter2();
              clearInterval(checkExist);
           }
        }, 20);
          
    
        $.fn.stars2filter2 = function() {
            return $(this).each(function() {
                $(this).html($('<span />').width(Math.max(0, (Math.min(5, parseFloat($(this).html())))) * 16));
            });
          }

      
})

.controller('PlaylistsCtrl3', function($scope, $scope) { 


      var posa = 0;
      var posb = 0;   
      avg = 0;
     
    //Array anlegen
    $scope.playlists = [];  
    $scope.arraysums = [];    
    Apiomat.spielplatz.getspielplatzs("", {
    onOk : function(loadedObjs) {
        
    //Now you can do sth with loaded objects (loadedObjs)
    for (i = 0; i < loadedObjs.length; i++) {
        var arrayspielplaetze = loadedObjs[i]["data"];
        //Ausgabe in Konsole
        var name = arrayspielplaetze["name"];
        var strasse = arrayspielplaetze["straße"];
        var nummer = arrayspielplaetze["hausnummer"];
        var stadtteil = arrayspielplaetze["stadtteil"];
        var plz = arrayspielplaetze["plz"];
        var altersgruppe = arrayspielplaetze["altersgruppe"];
        var groesse = arrayspielplaetze["größe"];
        var status = arrayspielplaetze["status"];
        var bild = arrayspielplaetze["hauptbild"];
        var bewertung = arrayspielplaetze["gesamtbewertungsp"];

        var id = arrayspielplaetze["id"];
        
      function success(pos){
        posa = pos.coords.longitude; 
        posb = pos.coords.latitude;
      }    
     // alert(posa);   
      

      if (navigator.geolocation){         
         navigator.geolocation.getCurrentPosition(success);
      }else{
           alert("Geoortung wird nicht unterstützt!");
         }
     
      var lat1 = arrayspielplaetze["latitude"];
      var lon1 = arrayspielplaetze["longitude"];
      var lat2 = 49.351648;
      var lon2 = 9.148309;
      var R = 6371; // Radius of the earth in km
      var dLat = deg2rad(lat2-lat1);  // deg2rad below
      var dLon = deg2rad(lon2-lon1); 
      var a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);        ; 
      var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
      var d = R * c; // Distance in km
      var gerundetd = Math.round(d * 100)/100;

        var spid = "bewertungsid == ("+id+")";//document.getElementById("spid").innerHTML ;
        var sum = 0;
        //var avg =0;
        //alert(spid);
          Apiomat.bewertungen.getbewertungens(spid, {
          onOk : function(loadedObjs) {
          //alert(loadedObjs.length);
          for (i = 0; i < loadedObjs.length; i++) {
                var arraybewertungen = loadedObjs[i]["data"];
                //Ausgabe in Konsole
                var bewertungaus = arraybewertungen["gesamtbewertung"];
                sum +=  parseInt(bewertungaus);
          }
          avg = sum/loadedObjs.length;
          //console.log(avg);
          //alert(avg);
          $scope.arraysums.push({ avg: avg});
          },


          onError : function(error) {
          //handle error
          }

      })



      $scope.playlists.push({ title: name, bewertung: bewertung, bild: bild, altersgruppe: altersgruppe, groesse: groesse, status: status , plz: plz ,strasse: strasse, stadtteil: stadtteil, hausnummer: nummer, ort: gerundetd + " km", color: '#FF880E', id:id, avg: avg});
        
      }
    },
        
    onError : function(error) {
    }
    });

        var checkExist = setInterval(function() {
           if ($('span.stars2filter3').length) {
              $('span.stars2filter3').stars2filter3();
              clearInterval(checkExist);
           }
        }, 20);
          
    
        $.fn.stars2filter3 = function() {
            return $(this).each(function() {
                $(this).html($('<span />').width(Math.max(0, (Math.min(5, parseFloat($(this).html())))) * 16));
            });
          }

        


})

.controller('BewertungCtrl', function($stateParams, $scope){
     

    $(document).ready(function(){
    $('.collapsible').collapsible({
      accordion : false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
        });
      });
    var url = ($stateParams.bewertungId);
    var id = "id == id("+url+")";
    $scope.bewertung = []; 

    Apiomat.bewertungen.getbewertungens(id, {
    onOk : function(loadedObjs) {
    //Now you can do sth with loaded objects (loadedObjs)
    //Now you can do sth with loaded objects (loadedObjs)
        for (i = 0; i < loadedObjs.length; i++) {
          var arraybewertungen = loadedObjs[i]["data"];
          //Ausgabe in Konsole
          var name = arraybewertungen["nickname"];
          var kommentar = arraybewertungen["ueberschriftbewertungskommentar"];
          var freitext = arraybewertungen["textbewertung"];
          var sauber = arraybewertungen["sauberkeit"];
          var sicher = arraybewertungen["sicherheit"];
          var spass = arraybewertungen["spielspass"];
          var bewertungaus = arraybewertungen["gesamtbewertung"];

          $scope.bewertung.push({ name: name, bewertung: bewertungaus, kommentar: kommentar, freitext: freitext, sauber: sauber, sicher: sicher, spass: spass});

          }
    },
    onError : function(error) {
    //handle error
    }
    });
        var checkExist = setInterval(function() {
           if ($('span.stars3').length) {
              $('span.stars3').stars3();
              clearInterval(checkExist);
           }
        }, 20);
          
    
        $.fn.stars3 = function() {
            return $(this).each(function() {
                $(this).html($('<span />').width(Math.max(0, (Math.min(5, parseFloat($(this).html())))) * 16));
            });
          }
        
        


    
})

.controller('DetailSpielplatzCtrl', function($scope, $scope, $scope, $stateParams, $scope, $ionicLoading, $compile ) {
    $('textarea#freitext').characterCounter();

    //Accordion ratings
    $('.collapsible').collapsible({
      accordion : false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
    });

    //Spielplatzid holen
    var url = ($stateParams.spielplatzId);
    var id = "id == id("+url+")";
    var avg;
    var avgsau;
    var avgsich;
    var avgspa;
    
    var idbewertung = "bewertungsid == ("+url+")";
    
    //Array Spielplatzdetail erstellen
    $scope.playlistdetails = []; 
    $scope.ratings = []; 
    $scope.arraysums = [];
    
    //DB-verbindung mit Spielplatztabelle
    Apiomat.spielplatz.getspielplatzs(id, {
        onOk : function(loadedObjs) {
            //Now you can do sth with loaded objects (loadedObjs)
            var arrayspielplaetze = loadedObjs[0]["data"];
    //Ausgabe in Konsole
    var name = arrayspielplaetze["name"];
    var strasse = arrayspielplaetze["straße"];
    var hausnr = arrayspielplaetze["hausnummer"];
    var stadtteil = arrayspielplaetze["stadtteil"];
    var groesse = arrayspielplaetze["größe"];
    var bank = arrayspielplaetze["sitzgelegenheiten"];
    var alter = arrayspielplaetze["altersgruppe"];
    var bild = arrayspielplaetze["hauptbild"];
    var plz = arrayspielplaetze["plz"];
    var feuer = arrayspielplaetze["grillplatz"];
    var sanitar = arrayspielplaetze["sanitäranlagen"];
    var lat = arrayspielplaetze["latitude"];
    var long = arrayspielplaetze["longitude"];
    var status = arrayspielplaetze["status"];   
            
            var statusfarbe;
            if(status=="bespielbar"){
                statusfarbe ="#00e676"
            }else{
                statusfarbe ="#ff5252"
            }
            
    var rutsche = arrayspielplaetze["rutsche"];
    var kletterturm = arrayspielplaetze["kletterturm"];
    var wippe = arrayspielplaetze["wippe"];
    var tischtennis = arrayspielplaetze["tischtennis"];
    var ballspielfeld = arrayspielplaetze["ballsport"];
    var schaukel = arrayspielplaetze["schaukel"];
    var sandkasten = arrayspielplaetze["sandkasten"];
    var wasserspiele = arrayspielplaetze["wasserspiele"];
    var klettergerust = arrayspielplaetze["klettergerüst"];
    var seilbahn = arrayspielplaetze["seilbahn"];
    var spielobjekt = arrayspielplaetze["spielobjekt"];
    var sonstiges = arrayspielplaetze["sonstiges"];
            
       $scope.playlistdetails = [
    { title: name, sanitar: sanitar, feuer: feuer, strasse: strasse,plz: plz , bank: bank, alter: alter, hausnr:hausnr, stadtteil:stadtteil, groesse:groesse, url:url, bild:bild, lat:lat, long:long, rutsche:rutsche, kletterturm:kletterturm, wippe:wippe, tischtennis:tischtennis, ballspielfeld:ballspielfeld, schaukel:schaukel, sandkasten:sandkasten, wasserspiele:wasserspiele, klettergerust:klettergerust, seilbahn:seilbahn, spielobjekt:spielobjekt, sonstiges:sonstiges, status:status, statusfarbe:statusfarbe},
  ];

},
onError : function(error) {
//handle error
}
});


    Apiomat.bewertungen.getbewertungens(idbewertung, {
    onOk : function(loadedObjs) {
    //Now you can do sth with loaded objects (loadedObjs)
    //Now you can do sth with loaded objects (loadedObjs)
        for (i = 0; i < loadedObjs.length; i++) {
          var arraybewertungen = loadedObjs[i]["data"];
          //Ausgabe in Konsole
          var name = arraybewertungen["nickname"];
          var kommentar = arraybewertungen["ueberschriftbewertungskommentar"];
          var freitext = arraybewertungen["textbewertung"];
          var bewertungaus = arraybewertungen["gesamtbewertung"];
          var id = arraybewertungen["id"];
          var bid = arraybewertungen["bewertungsid"];
          $scope.ratings.push({ title: name, bewertung: bewertungaus, kommentar: kommentar, freitext: freitext, id: id , bid: bid});

          }
    },
    onError : function(error) {
    //handle error
    }
    });   
    var sum = 0;
    var sum2 = 0;
    var sum3 = 0;
    var sum4 = 0;
    Apiomat.bewertungen.getbewertungens(idbewertung, {
    onOk : function(loadedObjs) {
    //Now you can do sth with loaded objects (loadedObjs)
    //Now you can do sth with loaded objects (loadedObjs)
        for (i = 0; i < loadedObjs.length; i++) {
          var arraybewertungen = loadedObjs[i]["data"];
          //Ausgabe in Konsole
          var bewertungaus = arraybewertungen["gesamtbewertung"];
          var sauberkeit2 = arraybewertungen["sauberkeit"];
          var sicherheit2 = arraybewertungen["sicherheit"];
          var spielspass2 = arraybewertungen["spielspass"];
          sum +=  parseInt(bewertungaus);
          sum2 +=  parseInt(sauberkeit2);
          sum3 +=  parseInt(sicherheit2);
          sum4 +=  parseInt(spielspass2);
          }
    avg = sum/loadedObjs.length;
    avgsau = sum2/loadedObjs.length;
    avgsich = sum3/loadedObjs.length;
    avgspa = sum4/loadedObjs.length;
    $scope.arraysums.push({ avg: avg, avgsau: avgsau, avgsich: avgsich, avgspa: avgspa});
    //alert(url);
    var myspielplatz;
    var id = url; // replace this with the id of the item yout want to fetch
    Apiomat.spielplatz.getspielplatzs("id==id(" + id + ")", {
      onOk: function(result) {
        myspielplatz = result[0];
        myspielplatz.setGesamtbewertungsp(avg);
        myspielplatz.save({
          onOk: function() {
            //do some other stuff here
            //alert("successfully saved");
          },
          onError: function(error) {
            console.log(error);
          }
        })
      },
      onError: function(error) {
        console.log(error);
      }
    });
    },

    onError : function(error) {
    //handle error
    }

});
    
    var checkExist = setInterval(function() {
           if ($('span.stars').length) {
              $('span.stars').stars();
              clearInterval(checkExist);
           }
        }, 20);
          
    
        $.fn.stars = function() {
            return $(this).each(function() {
                $(this).html($('<span />').width(Math.max(0, (Math.min(5, parseFloat($(this).html())))) * 16));
            });
          }

});

function bewertungdb(){

    var sauberkeit = ($('input[name="rating-sauber"]:checked', '#sauberkeit').val()); 
    var spielspass = ($('input[name="rating-spass"]:checked', '#spielspass').val()); 
    var sicherheit = ($('input[name="rating-sicherheit"]:checked', '#sicherheit').val());
    var sauber = parseInt(sauberkeit);
    var spass = parseInt(spielspass);
    var sicher = parseInt(sicherheit);

    var spid = document.getElementById("spid").innerHTML ;
    var nickname = document.getElementById("nickname").value ;
    var kommentar = document.getElementById("kommentar").value ;
    var freitext = document.getElementById("freitext").value ;
    var gesamtbewertung = ((sauber + spass + sicher)/3);
    var gerundet = gesamtbewertung;
    var mybewertungen = new Apiomat.bewertungen();
    mybewertungen.setSauberkeit(sauber);
    mybewertungen.setSpielspass(spass);
    mybewertungen.setSicherheit(sicher);
    mybewertungen.setGesamtbewertung(gerundet);
    mybewertungen.setNickname(nickname);
    mybewertungen.setUeberschriftbewertungskommentar(kommentar);
    mybewertungen.setBewertungsid(spid);
    mybewertungen.setTextbewertung(freitext);
        mybewertungen.save({
            onOk : function() {
            //object successfully saved
            alert("Saved succesfully Bewertung");
            },
            onError : function(error) {
            alert("error");
        }
        });
    
    //alert (sauberkeit + spielspass + sicherheit + gesamtbewertung);
}



