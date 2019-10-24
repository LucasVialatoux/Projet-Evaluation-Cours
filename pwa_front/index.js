var length=1;
//Supprimer text de l'input
function jQ_append(id_of_input, text) {
    if(length <=5) {
    	var input_id = '#'+id_of_input;
    	$(input_id).val($(input_id).val() + text);
    	length+=1;
    }
}

//Supprimer un caractère à l'input
function jQ_correct(id_input) {
	var str = $('#'+id_input)[0];
	var longueur = str.value.length;
	if(longueur<=1){
		$(str).val('');
		length=1;
	} else {
		$(str).val(str.value.substr(0, longueur-1));
		length=length-1;
	}
}

//Gérer le submit du formulaire
$(document).ready(function(){
 
    $('#formCode').submit(function(e){
        e.preventDefault();
        var lg = $('#inputID').val().length
        if (lg==5) {
           $.get('',
                {
                    code : $('#inputID').val()
                },
                function(){
                    let reponse = JSON.parse('{"statut":"ok","matiere":"unNom"}');
                    if (reponse.statut!= undefined && reponse.statut == "ok" ){
                        $( location ).attr("href", "Pages/feelingPage/feeling.html?matiere="+reponse.matiere);
                    } else {
                        $('#retour')[0].style="visibility:visible";
                        alert("Code incorrect ! Veuille vérifier le code.");
                    }
                }
             ); 
       } else {
            alert("Merci d'entrer un code plus long.");
       }
    });

});
