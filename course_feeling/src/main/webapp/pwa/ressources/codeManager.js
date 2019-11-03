const URL_CODE = "http://localhost:8080/ens/poll/";

/**
 * Affiche le code du sondage dans le DOM
 * @param {String} idPoll id du sondage 
 * @param {Number} code code de sondage
 */
function displayCode(idPoll,code) {
    $("#code_"+idPoll).text(code);
}

/**
 * Demande de générer un nouveau code
 * @param {string} idPoll 
 */
function generateCode(idPoll) {
    $("#infoErreur").hide();
    $.ajax({
        type: "POST",
        url: URL_CODE + idPoll,
        success: data => {
            let response = JSON.parse(data);
            if (response.statut == "ok") {
                displayCode(idPoll, response.code);
            }
            else {
                displayCode(idPoll, "ERREUR");
            }
        },
        error: error => {
            console.error(error);
            displayCode(idPoll, "#####");
            $("#infoErreur").text("Veuillez vérifier votre connexion");
            $("#infoErreur").show();
        },
        timeout: 1500
    });
}

/**
 * Demande le code sondage
 * @param {string} idPoll 
 */
function getCode(idPoll) {
    $("#infoErreur").hide();
    $.ajax({
        type: "GET",
        url: URL_CODE + idPoll,
        success: data => {
            let response = JSON.parse(data);
            if (response.statut == "ok") {
                displayCode(idPoll, response.code);
            }
            else {
                displayCode(idPoll, "ERREUR");
            }
        },
        error: error => {
            console.error(error);
            displayCode(idPoll, "#####");
            $("#infoErreur").text("Veuillez vérifier votre connexion");
            $("#infoErreur").show();
        },
        timeout: 1500
    });
}
