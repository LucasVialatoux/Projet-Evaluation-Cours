$(function () {
    /**
     * Gérer le submit du formulaire
     */
    $('#formCode').submit(function (e) {
        e.preventDefault();
        $.post(
            URL_CONNEXION,
            {
                email: $('#inputID').val(),
                password: $('#inputMdp').val()
            },
            (data) => {
                let reponse = JSON.parse(data);
                if (reponse.statut != undefined && reponse.statut == "ok") {
                    localStorage.setItem('token', reponse.token);
                    localStorage.setItem('mail', $('#inputID').val());
                    window.location = URL_PAGE_TEACHER;
                } else {
                    $('#retourID').text("Veuillez vérifier votre email et/ou votre mot de passe.");
                    $('#retourID').show();
                }
            }
        );
    });
    /**
     * Event du bouton de retour
     */
    $("#home_button_code").click(_ => {
        window.location = URL_PAGE_CODE;
    });

    /**
     * Event du bouton de retour
     */
    $("#inscription_button_code").click(_ => {
        window.location = URL_PAGE_INSC;
    });
});
