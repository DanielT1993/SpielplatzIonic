var attempt = 3; //Anzahl der erlaubten Fehlversuche/Falscheingaben
//function validate
//Login überprüft ob Password und Username korrekt sind
//Erlaubt drei loginversuche
//Nach drei fehlgeschlagenen Login-Versuchen ist kein Login mehr möglich --> Input-Felder und Login-Button werden ausgeblender
function validate() {
  //Input auslesen
  var username = document.getElementById("username").value;
  var password = document.getElementById("password").value;
  //Checken, ob Pwd und Username korrekt sind
  if (username == "admin" && password == "bier") {
    window.location.href = 'center.html';
  } else {
    attempt--; // Decrementing by one.
    swal({
      title: "",
      text: "Benutzername oder Passwort falsch!",
      type: "error",
      confirmButtonColor: "#ff9100",
    });
    // Disabling fields after 3 attempts.
    if (attempt == 0) {
      document.getElementById("username").disabled = true;
      document.getElementById("password").disabled = true;
      $("#login").toggleClass('disabled');
      $("#login").on('click', nologin)
    }
  }
}
//function nologin
//Gibt dem User ein Alert aus, dass kein Login-Versuch mehr möglich ist
function nologin() {
  swal({
    title: "Ihr Slidoo-Zugang wurde gesperrt!",
    text: "Sie haben sich zu oft falsch eingeloggt. Bitte setzen Sie sich mit dem Slidoo-Systemadministrator in Verbindung!",
    type: "error",
    confirmButtonColor: "#ff9100",
  });
}
