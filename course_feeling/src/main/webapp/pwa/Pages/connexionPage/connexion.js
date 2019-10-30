var URL_PAGE_CODE = "../../index.html";
var URL_PAGE_INSC = "../inscriptionPage/inscription.html"
var URL_SEND_CONNEXION = "http://localhost:8080/signin/";
var URL_PAGE_VALIDATION = "../validationPage/validation.html";//METTRE ADRESSE DASHBOARD

$(function() {
    
	/**
     * Gérer le submit du formulaire
     */
    $('#formCode').submit(function(e){
        $.post(
            URL_SEND_CONNEXION,
            {
                email:$('#inputID').val(),
                password:$('#inputMdp').val()
            },
            (data) => {
                let reponse = JSON.parse(data);
                if (reponse.statut != undefined && reponse.statut == "ok") {
                    window.location = URL_PAGE_VALIDATION; // Redirection sur le dashboard de l'enseignant
                } else {
                    console.log("Erreur de connexion");
                    window.location.reload();
                }
            }
        );
    });
    /**
     * Event du bouton de retour
     */
    $("#home_button_code").click(_=> {
        window.location = URL_PAGE_CODE;
    });

    /**
     * Event du bouton de retour
     */
    $("#inscription_button_code").click(_=> {
        window.location = URL_PAGE_INSC;
    });
});