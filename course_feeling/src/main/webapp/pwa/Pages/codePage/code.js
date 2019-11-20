var URL_PAGE_BACK = "../../index.html";//METTRE ADRESSE RETOUR (PIERRE)
var URL_PAGE_HOME = "../../index.html";
var URL_SEND_CODE = "http://localhost:8080/ens/poll/";
var URL_PAGE_VALIDATION = ""
var TOKEN = "token";
var CODE_SONDAGE = "code";
//localStorage.setItem(TOKEN, "test"); //TEMPO
//localStorage.setItem(CODE_SONDAGE, "AB123"); //TEMPO
$(function() {
    
	/* Test token et code à afficher*/  
    let token = localStorage.getItem(TOKEN);
    let code = localStorage.getItem(CODE_SONDAGE);
    if (token == null){
        window.location = URL_PAGE_HOME;
    }

    if (token != null && code != null) {
        $("#codeFinal").text(code);
        $('#codeFinal').show();
        $('#indication').show();
    }

    /**
     * Récupération du code
     */
    $('#formCode').submit(function(e){
        e.preventDefault();
        $.get(URL_SEND_CODE+code,
            (data) => {
                let reponse = JSON.parse(data);
                if (reponse.statut!= undefined && reponse.statut == "ok" ){
                    localStorage.setItem(CODE_SONDAGE, reponse.code);
                    window.location.reload();
                } else {
                    console.log("Erreur de génération de code");
                    window.location.reload();
                }
            }
        );
    });
    /**
     * Event du bouton de retour
     */
    $("#back_button_code").click(_=> {
        window.location = URL_PAGE_BACK;
    });
});