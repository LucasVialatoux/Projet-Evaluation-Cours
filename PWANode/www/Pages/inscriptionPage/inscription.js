$(function () {

	/**
     * Gérer le submit du formulaire
     */
	$('#formCode').submit(function (e) {
		e.preventDefault();
		$('#retourID').hide();
		$('#retourMdp2').hide();
		if ($('#inputMdp1').val() == $('#inputMdp2').val()) {
			$.post(
				URL_INSCRIPTION,
				{
					email: $('#inputID').val(),
					password: $('#inputMdp1').val()
				},
				(data) => {
					let reponse = JSON.parse(data);
					if (reponse.statut != undefined && reponse.statut == "ok") {
						localStorage.setItem('token', reponse.token);
						localStorage.setItem('mail', $('#inputID').val());
						window.location = URL_PAGE_TEACHER;
					} else if (reponse.statut == "emailAlreadyUsed") {
						$('#retourID').text("Cette adresse email existe déjà.");
						$('#retourID').show();
					} else {
						$('#retourMdp2').text("Erreur d'inscription");
						$('#retourMdp2').show();
					}
				}
			);
		} else {
			$('#retourMdp2').text("Les mots de passe ne sont pas identiques.");
			$('#retourMdp2').show();
		}
	});
    /**
     * Event du bouton de retour
     */
	$("#back_button_code").click(_ => {
		window.location = URL_PAGE_CONNEXION;
	});
});