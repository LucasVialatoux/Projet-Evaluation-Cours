$(function() {
    
	/**
     * Gérer le submit du formulaire
     */
    $('#formCode').submit(function(e){
    	//Si les 2 mot de passe sont identiques
    	if ($('#inputMdp1').val() == $('#inputMdp2').val() ){
	        $.post(
	            URL_INSCRIPTION,
	            {
	                email:$('#inputID').val(),
	                password:$('#inputMdp1').val()
	            },
	            (data) => {
	                let reponse = JSON.parse(data);
	                if (reponse.statut != undefined && reponse.statut == "ok") {
	                	localStorage.setItem('token', reponse.token);
	                    window.location = URL_PAGE_TEACHER;
	                } else if(reponse.statut == "emailAlreadyUsed"){
	                	$('#inputID').text("Cette adresse email existe déjà.");
            			$('#inputID').show();
	                } else {
	                    console.log("Erreur d'inscription");
	                    window.location.reload();
	                    $('#retourMdp2').text("Erreur d'inscription");
            			$('#retourMdp2').show();
	                }
	            }
	        );
        } else if ($('#inputMdp1').val() != $('#inputMdp2').val()){
        	e.preventDefault();
            $('#retourMdp2').text("Veuillez vérifier votre mot de passe.");
            $('#retourMdp2').show();
        } else {
        	e.preventDefault();
        	$('#retourMdp2').text("Erreur d'inscription");
            $('#retourMdp2').show();
        }
    });
    /**
     * Event du bouton de retour
     */
    $("#back_button_code").click(_=> {
        window.location = URL_PAGE_CONNEXION;
    });
});