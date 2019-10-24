var URL_PAGE_CODE = "../../index.html";
var NAME_SUBJECT = "nameSbj";
localStorage.setItem(NAME_SUBJECT, "Nom de la matière"); //TEMPO

$(function() {
    /* Récupération du nom de métier depuis le stockage local*/  
    let nameSbj = localStorage.getItem(NAME_SUBJECT);
    if (nameSbj != null) {
        $("#nav_title").text(nameSbj);
        $("#discipline_title").text(nameSbj); 
    } else {
        window.location = URL_PAGE_CODE;
    }

    /**
     * Event du bouton de retour
     */
    $("#back_button_code").click(_=> {
        window.location = URL_PAGE_CODE;
    });
});