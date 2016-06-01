var attempt = 3; // Variable to count number of attempts.
// Below function Executes on click of login button.
function validate(){
var username = document.getElementById("username").value;
var password = document.getElementById("password").value;
if ( username == "admin" && password == "bier"){
            window.location = "center.html"; // Redirecting to other page.
return false;
}
else{
attempt --;// Decrementing by one.
alert("Noch "+attempt+" Versuche");
// Disabling fields after 3 attempts.
if( attempt == 0){
document.getElementById("username").disabled = true;
document.getElementById("password").disabled = true;
document.getElementById("submit").disabled = true;
return false;
}
}
}

function logout() {
	alert("Logout erfolgreich!");
}


function download(){
            window.location = "www.play.google.com/store?hl=de";
}
