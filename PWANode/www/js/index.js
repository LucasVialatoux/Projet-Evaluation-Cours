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
            $.get(URL_SONDAGE + $('#inputID').val(),
                (data) => {
                    let reponse = JSON.parse(data);
                    if (reponse.statut!= undefined && reponse.statut == "ok" ){
                        localStorage.setItem(NAME_SUBJECT, reponse.matiere);
                        localStorage.setItem(CODE_SUBJECT, $('#inputID').val());
                        $( location ).attr("href", URL_ABS_PAGE_FEELING);
                    } else {
                        $('#retour').text("Code incorrect, veuillez le vérifier.");
                        $('#retour').show();
                    }
                }
            ); 
        } else {
            console.log("code trop petit")
            $('#retour').text("Code incorrect, veuillez vérifier que le code est de 5 caractères.");
            $('#retour').show();
        }
    });
    /**
     * Event du bouton Enseignant
     */
    $("#connect_button_code").click(_=> {
        window.location = URL_ABS_PAGE_CONNEXION;
    });

});
