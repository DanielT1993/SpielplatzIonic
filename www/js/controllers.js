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


 
.controller('PlaylistsCtrl', function($scope) {  
      var posa = 0;
      var posb = 0;   
     
    //Array anlegen
    $scope.playlists = [];    
    Apiomat.spielplatz.getspielplatzs("", {
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

      $scope.playlists.push({ title: name, ort: gerundetd + " km", color: '#FF880E', id:id });
      }
    },
        
    onError : function(error) {
    }
    });
    
    
    //add item to arry
    
}
)


.controller('DetailSpielplatzCtrl', function($scope) {
    var url = window.location.href.substr(38, 60);
    
    var id = "id == id("+url+")";
    $scope.playlistdetails = []; 
 Apiomat.spielplatz.getspielplatzs(id, {
onOk : function(loadedObjs) {
//Now you can do sth with loaded objects (loadedObjs)
var arrayspielplaetze = loadedObjs[0]["data"];
      //Ausgabe in Konsole
      var name = arrayspielplaetze["name"];
    var strasse = arrayspielplaetze["straße"];
   
    $scope.playlistdetails = [
    { title: name, strasse: strasse, },
  ];
alert("Count of loaded objects: " + name + strasse);
},
onError : function(error) {
//handle error
}
});   
   
});
