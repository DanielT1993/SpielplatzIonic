angular.module('starter.controllers', ['ionic'])


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
    
})

.controller('KontaktCtrl', function() {
    $('textarea#kontaktmessage').characterCounter();
})

.controller('MapCtrl', function ($scope, $ionicLoading, $compile, $window, $stateParams) {
  
    var lat2=0;
        var long2=0;
    function initialize() {
        
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
        
    },
    onError : function(error) {
    //handle error
    }
    });      
       
        google.maps.event.addDomListener(window, 'load');
         

        var myLatlng = new google.maps.LatLng(49.351648, 9.148309);

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
    loadScript('http://maps.googleapis.com/maps/api/js?key=AIzaSyCmpTMw7IitqkPCAfwlwsZGd6cruNNLLnY&sensor=true&callback=initialize');



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


.controller('PlaylistsCtrl', function($scope) { 
      var posa = 0;
      var posb = 0;   
     
    //Array anlegen
    $scope.playlists = [];    
    Apiomat.spielplatz.getspielplatzs(undefined, {
    onOk : function(loadedObjs) {
        
    //Now you can do sth with loaded objects (loadedObjs)
    for (i = 0; i < loadedObjs.length; i++) {
        var arrayspielplaetze = loadedObjs[i]["data"];
        //Ausgabe in Konsole
        var name = arrayspielplaetze["name"];
        var id = arrayspielplaetze["id"];
        
        
      function success(pos){
        posa = pos.coords.longitude; 
        posb = pos.coords.latitude;
      }    
    
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

      $scope.playlists.push({ title: name, ort: gerundetd + " km", color: '#FF880E', id : id });
        
      }
    },
        
    onError : function(error) {
    }
    });
}
)


.controller('DetailSpielplatzCtrl', function($scope, $scope, $scope, $stateParams, $scope, $ionicLoading, $compile ) {

    //Accordion ratings
    $('.collapsible').collapsible({
      accordion : false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
    });
    
    //Spielplatzid holen
    var url = ($stateParams.spielplatzId);
    var id = "id == id("+url+")";
    
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
            var lat = arrayspielplaetze["latitude"];
            var long = arrayspielplaetze["longitude"];
            var kletterturm = arrayspielplaetze["kletterturm"];
            var schaukel = arrayspielplaetze["schaukel"];
       $scope.playlistdetails = [
    { title: name, strasse: strasse, hausnr:hausnr, stadtteil:stadtteil, groesse:groesse, url:url, lat:lat, long:long,  kletterturm:kletterturm, schaukel:schaukel  },
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
          var bewertungaus = arraybewertungen["gesamtbewertung"];
          $scope.ratings.push({ title: name, bewertung: bewertungaus});

          }
    },
    onError : function(error) {
    //handle error
    }
    });   
    var sum = 0;
    Apiomat.bewertungen.getbewertungens(idbewertung, {
    onOk : function(loadedObjs) {
    //Now you can do sth with loaded objects (loadedObjs)
    //Now you can do sth with loaded objects (loadedObjs)
        for (i = 0; i < loadedObjs.length; i++) {
          var arraybewertungen = loadedObjs[i]["data"];
          //Ausgabe in Konsole
          var bewertungaus = arraybewertungen["gesamtbewertung"];
          sum +=  parseInt(bewertungaus);
          }
    var avg = sum/loadedObjs.length;
    $scope.arraysums.push({ avg: avg});
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
    var nickname = "Daniel";

    var spid = document.getElementById("spid").innerHTML ;
    var gesamtbewertung = ((sauber + spass + sicher)/3);
    var gerundet = gesamtbewertung;
    var mybewertungen = new Apiomat.bewertungen();
    mybewertungen.setSauberkeit(sauber);
    mybewertungen.setSpielspass(spass);
    mybewertungen.setSicherheit(sicher);
    mybewertungen.setGesamtbewertung(gerundet);
    mybewertungen.setNickname(nickname);
    mybewertungen.setBewertungsid(spid);
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
