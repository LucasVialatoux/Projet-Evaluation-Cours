var URL_PAGE_FEELING = "Pages/feelingPage/feeling.html";
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
        $( location ).attr("href", URL_PAGE_FEELING);
        if ($('#inputID').val().length == 5) {
           $.get('',
                {
                    code : $('#inputID').val()
                },
                function(){
                    let reponse = JSON.parse('{"statut":"ok","matiere":"Nom de la matière"}');
                    if (reponse.statut!= undefined && reponse.statut == "ok" ){
                        // localStorage.setItem(NAME_SUBJECT, reponse.matiere);
                        // localStorage.setItem(CODE_SUBJECT, $('#inputID').val());
                        // $( location ).attr("href", "Pages/feelingPage/feeling.html?matiere="+reponse.matiere+";code="+$('#inputID').val());
                        $( location ).attr("href", URL_PAGE_FEELING);
                    } else {
                        $('#retour').show();
                        alert("Code incorrect ! Veuille vérifier le code.");
                    }
                }
             ); 
       } else {
            alert("Merci d'entrer un code plus long.");
       }
    });

});
