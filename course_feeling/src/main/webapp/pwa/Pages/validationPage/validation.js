let nameSbj = localStorage.getItem("nameSbj");
if (nameSbj == null){
    window.location = URL_PAGE_CODE;
}

$(function() {
    /* Récupération du nom de métier depuis le stockage local*/  
    $("#nav_title").text(nameSbj);
    $("#discipline_title").text(nameSbj); 

    /**
     * Event du bouton de retour
     */
    $("#back_button_code").click(_=> {
        window.location = URL_PAGE_CODE;
    });
});