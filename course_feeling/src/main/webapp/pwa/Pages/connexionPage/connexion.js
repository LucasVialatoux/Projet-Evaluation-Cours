var URL_PAGE_CODE = "../../index.html";
var URL_PAGE_INSC = "../inscriptionPage/inscription.html"


$(function() {
    

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