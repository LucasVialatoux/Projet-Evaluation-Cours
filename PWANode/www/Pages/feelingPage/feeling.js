// localStorage.setItem(NAME_SUBJECT, "Nom de la matière"); //TEMPO
// localStorage.setItem(CODE_SUBJECT, "7895A"); //TEMPO

$(function() {
    /* Récupération du nom de métier et du code matière depuis le stockage local*/  
    let nameSbj = localStorage.getItem(NAME_SUBJECT);
    let codeSbj = localStorage.getItem(CODE_SUBJECT);
    if (nameSbj != null && codeSbj != null) {
        $("#nav_title").text(nameSbj);
        $("#discipline_title").text(nameSbj); 
    } else {
        window.location = URL_PAGE_CODE;
    }
        
    /**
     * Event de selection d'une émotion (fonction appellé par l'event onclick des boutons)
     */
    $("#container_emoti button").click((event) => {
        let value = event.currentTarget.attributes.feeling.value;
        let feeling = Number(value);
        // console.log(feeling);
        // window.location = URL_PAGE_VALIDATION;
        $.post(
            URL_SONDAGE + codeSbj,
            {
                ressenti:feeling
            },
            (data) => {
                let reponse = JSON.parse(data);
                if (reponse.statut != undefined && reponse.statut == "ok") {
                    window.location = URL_PAGE_VALIDATION; // Redirection sur la page de validation de transfert
                } else {
                    console.log("Erreur d'envoie d'une émotion");
                    window.location = URL_PAGE_CODE;
                }
            }
        );
    });

    /**
     * Event du bouton de retour
     */
    $("#back_button_code").click(_=> {
        window.location = URL_PAGE_CODE;
    });
});