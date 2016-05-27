// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.controllers' is found in controllers.js
angular.module('starter', ['ionic', 'starter.controllers'])

.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if (window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      cordova.plugins.Keyboard.disableScroll(true);

    }
    if (window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleDefault();
    }
  });
})

.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider

    .state('app', {
    url: '/app',
    abstract: true,
    templateUrl: 'templates/menu.html',
    controller: 'AppCtrl'
  })

  .state('app.search', {
    url: '/search',
    views: {
      'menuContent': {
        templateUrl: 'templates/search.html'
      }
    }
  })
    .state('app.spielplatzanlegen', {
    url: '/spielplatzanlegen',
    views: {
      'menuContent': {
        templateUrl: 'templates/spielplatzanlegen.html',
          controller: 'SpielplatzCtrl'
      }
    }
  })
  
  .state('app.kontakt', {
    url: '/kontakt',
    views: {
      'menuContent': {
        templateUrl: 'templates/kontakt.html',
          controller: 'KontaktCtrl'
      }
    }
  })

    .state('app.playlists', {
      url: '/playlists',
      views: {
        'menuContent': {
          templateUrl: 'templates/playlists.html',
          controller: 'PlaylistsCtrl'
        }
      }
    })

  .state('app.single', {
    url: '/playlists/:spielplatzId',
    views: {
      'menuContent': {
        templateUrl: 'templates/detailSpielplatz.html',
        controller: 'DetailSpielplatzCtrl'
      }
    }
    })
  
    .state('app.appinfo', {
    url: '/appinfo',
    views: {
      'menuContent': {
        templateUrl: 'templates/appinfo.html'
      }
    }
  })
  
  .state('app.share', {
    url: "/share",
    views: {
      'menuContent': {
        templateUrl: "templates/share.html",
      }
    }
  })

    .state('app.login', {
      url: '/login',
      views: {
        'menuContent': {
          templateUrl: 'templates/login.html',
          
        }
      }
    });
  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/app/playlists');
});