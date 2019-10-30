var URL_PAGE_FEELING = "Pages/feelingPage/feeling.html";
var URL_PAGE_CONNEXION = "Pages/connexionPage/connexion.html";
var URL_SEND_CODE = "http://localhost:8080/sondage/";
var NAME_SUBJECT = "nameSbj";
var CODE_SUBJECT = "codeSbj";

//Supprimer text de l'input
function jQ_append(text) {
    if($("#inputID").val().length <5) {
    	$("#inputID").val($("#inputID").val() + text);
    }
}

$(function() {
    $("#inputID").val("");
    
    //Supprimer un caractère à l'input
    $("#correction_button").click( _ => {
        let str = $("#inputID").val();
        let longueur = str.length;
        if(longueur <= 1){
           $("#inputID").val('');
        } else {
           $("#inputID").val(str.substr(0, longueur-1));
        }
    });

    //Gérer le submit du formulaire
    $('#formCode').submit(function(e){
        e.preventDefault();
        if ($('#inputID').val().length == 5) {
            $('#retour').hide();
            $.get(URL_SEND_CODE+$('#inputID').val(),
                (data) => {
                    let reponse = JSON.parse(data);
                    if (reponse.statut!= undefined && reponse.statut == "ok" ){
                        localStorage.setItem(NAME_SUBJECT, reponse.matiere);
                        localStorage.setItem(CODE_SUBJECT, $('#inputID').val());
                        $( location ).attr("href", URL_PAGE_FEELING);
                    } else {
                        $('#retour').text("Code incorrect, veuillez le vérifier.");
                        $('#retour').show();
                    }
                }
            ); 
        } else {
            console.log("code trop petit")
            $('#retour').text("Code incorrect, veuillez vérifier que le code est de 5 charactères.");
            $('#retour').show();
        }
    });
    /**
     * Event du bouton Enseignant
     */
    $("#connect_button_code").click(_=> {
        window.location = URL_PAGE_CONNEXION;
    });

});
