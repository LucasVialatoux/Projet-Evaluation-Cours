const URL_PAGE_HOME = "../../index.html";
const URL_SEND_CODE = "http://localhost:8080/ens/poll/";
const URL_PAGE_CONNEXION = "../connexionPage/connexion.html";
const URL_PAGE_TEACHER = "../teacherPage/teacher.html";
const URL_PAGE_VALIDATION = "";
const CODE_SONDAGE = localStorage.getItem("code");

const params = new URL(location.href).searchParams;
const idPoll = params.get('id');

/**
 * Actualise en tâche de fond le code de sondage
 */
async function codeUpdater(idPoll) {
    while (true) {
        getCode(idPoll);
        await sleep(1000);
    }
}

$(function () {

    /* Test token et code à afficher*/
    if (CODE_SONDAGE == null || idPoll == null) {
        window.location = URL_PAGE_CONNEXION;
        return
    }
   
    $("#codeContainer").html(
        '<p class="codeFinal" id="code_' + idPoll + '"></p>'
    )
    $("#regenerate").click(function (e) { 
        e.preventDefault();
        generateCode(idPoll);
    });
    $("#codeFinal").text(CODE_SONDAGE);
    $('#codeFinal').show();

    codeUpdater(idPoll);
});