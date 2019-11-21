const TOKEN = localStorage.getItem("token");
if (TOKEN == null) {
    // window.location = URL_PAGE_CONNEXION;
}
/**
 * Sleep
 * @param {Number} ms temps d'attente en milliseconde
 */
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

/**
 * Affiche le code du sondage dans le DOM
 * @param {String} idPoll id du sondage 
 * @param {Number} code code de sondage
 */
function displayCode(idPoll, code) {
    $("#code_" + idPoll).text(code);
}

/**
 * Demande de générer un nouveau code
 * @param {string} idPoll 
 */
function generateCode(idPoll) {
    $("#infoErreur").hide();
    $.ajax({
        type: "POST",
        url: URL_POLL + idPoll,
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
        beforeSend: (xhr) => xhr.setRequestHeader('Authorization', TOKEN),
        timeout: 1500
    });
}

/**
 * Demande le code sondage
 * @param {string} idPoll 
 */
function getCode(idPoll) {
    // $("#infoErreur").hide();
    $.ajax({
        type: "GET",
        url: URL_POLL + idPoll,
        success: data => {
            let response = JSON.parse(data);
            if (response.statut == "ok") {
                displayCode(idPoll, response.code);
            }
            else {
                displayCode(idPoll, "ERREUR");
            }
            $("#infoErreur").hide();
        },
        error: error => {
            // console.error(error);
            displayCode(idPoll, "#####");
            $("#infoErreur").text("Veuillez vérifier votre connexion");
            $("#infoErreur").show();
        },
        beforeSend: (xhr) => xhr.setRequestHeader('Authorization', TOKEN),
        timeout: 1500
    });
}

/**
 * Actualise en tâche de fond le code de sondage
 */
async function codeUpdater(idPoll) {
    while (true) {
        getCode(idPoll);
        await sleep(2000);
    }
}