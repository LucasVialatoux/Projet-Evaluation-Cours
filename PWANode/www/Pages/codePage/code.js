// const CODE_SONDAGE = localStorage.getItem("code");

const params = new URL(location.href).searchParams;
const idPoll = params.get('id');
/* Test token et code à afficher*/
if (idPoll == null) {
    window.location = URL_PAGE_CONNEXION;
}

$(function () {
    $("#codeContainer").html(
        '<p class="codeFinal" id="code_' + idPoll + '"></p>'
    )
    $("#regenerate").click(function (e) {
        e.preventDefault();
        generateCode(idPoll);
    });
    codeUpdater(idPoll);
});