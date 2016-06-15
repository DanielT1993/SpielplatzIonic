var attempt = 3; // Variable to count number of attempts.
// Below function Executes on click of login button.
function validate(){
var username = document.getElementById("username").value;
var password = document.getElementById("password").value;
if ( username == "admin" && password == "bier"){

                    window.location.href = 'center.html';
}
else{
attempt --;// Decrementing by one.
    
     swal({ 
                title: "",
                text: "Benutzername oder Passwort falsch!",
                type: "error", 
                confirmButtonColor: "#ff9100", 
    });
    
// Disabling fields after 3 attempts.
if( attempt == 0){
document.getElementById("username").disabled = true;
document.getElementById("password").disabled = true;
$("#login").toggleClass('disabled');
$("#login").on('click', nologin)    

}
}
}

function nologin(){
    swal({ 
                title: "Ihr Slidoo-Zugang wurde gesperrt!",
                text: "Sie haben sich zu oft falsch eingeloggt. Bitte setzen Sie sich mit dem Slidoo-Systemadministrator in Verbindung!",
                type: "error", 
                confirmButtonColor: "#ff9100", 
    });   
}