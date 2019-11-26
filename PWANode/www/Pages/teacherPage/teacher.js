// const JSON_DISCIPLINE = '{"statut":"ok","subjects":[{"id":"unid1","name":"Conception web","polls":[{"id":"unid1poll1","date":"1572338690503"},{"id":"unid1poll2","date":"1572438690503"},{"id":"unid1poll3","date":"1572538690503"}]},{"id":"unid2","name":"Réseaux","polls":[{"id":"unid2poll1","date":"1572338690503"},{"id":"unid2poll2","date":"1572438690503"},{"id":"unid2poll3","date":"1572538690503"}]}]}';
var MAIL = localStorage.getItem("mail");

if (MAIL == null) {
    // MAIL = "pierre@test.com";
    window.location = URL_PAGE_CONNEXION;
}

/**
 * Cherche le sondage le plus récent
 * @param {Poll[]} polls liste des sondages
 * @returns {Poll} sondage le plus récent
 */
function recentPoll(polls) {
    if (polls.length == 1 && polls.id == 0 && polls.data == 0){
        return {date:999999999999999};
    }
    polls.sort((a, b) => Number(a.date) - Number(b.date));
    return polls[polls.length - 1]
}

/**
 * Affiche la matière dans la vu
 * @param {Subject} subject 
 */
function displaySubject(subject) {
    // console.log(subject);
    let latePoll = recentPoll(subject.polls)
    // temps entre maintenant et la création du dernier sondage
    let code_manager = '<div class="row code_manager">'
    + '<h5 class="col-md-auto">Code du dernier sondage : <span id="code_' + latePoll.id + '" class="badge badge-dark"></span></h5>'
    + '<a id="getCode" href="' + URL_PAGE_CODESONDAGE + '?id=' + latePoll.id + '" class="btn btn-primary col-md-auto btn_action">'
        + '<img src="../../ressources/fullscreen-24px.svg" alt="">Plein écran'
        + '</a>'
        + '<button type="button" class="btn btn-primary col-auto mr-auto btn_action" onclick="generateCode(\'' + latePoll.id + '\')">'
        + '<img src="../../ressources/refresh-24px.svg" alt="">Regénérer'
        + '</button>'
        + '<button type="button" class="btn btn-danger col-auto btn_remove" onclick="deletDiscipline(\'' + subject.id + '\')">'
        + '<img src="../../ressources/delete-24px.svg" alt="">Supprimer'
        + '</button>'
        + '</div>';
    
    let time;
    if (latePoll.date == 999999999999999) {
        time = 'Pas de sondage';
        code_manager = '<div class="row code_manager">'
            + '<button type="button" class="btn btn-danger col-auto btn_remove" onclick="deletDiscipline(\'' + subject.id + '\')">'
            + '<img src="../../ressources/delete-24px.svg" alt="">Supprimer'
            + '</button>'
            + '</div>';
    }else{
        time = 'Dernier sondage : ' + moment(Number(latePoll.date)).locale("fr").fromNow();
    }
    $("#list_discipline").append(
        '<div class="list-group-item list-group-item-action item_discipline"> '
        + '<a href="' + URL_PAGE_DISCIPLINE + "?discipline=" + subject.id + '" class="container link_discipline">'
        + '<div class="row">'
        + '<p class="col">' + subject.name + '</p>'
        + '<img class="col-auto" src="../../ressources/arrow_forward_ios-24px_white.svg" alt="">'
        + '</div>'
        + '<small class="row col">' + time + '</small>'
        + '</a>'
        + code_manager
        + '</div>'
    );

    //Lancement de l'actualisation des codes de sondages
    // codeUpdater(latePoll.id)
}
// function debug(){
//     let response = JSON.parse(JSON_DISCIPLINE);
//     if (response.statut != undefined && response.statut == "ok") {
//         // console.log(response.subjects);
//         let subjects = response.subjects;
//         // on tri les matière du plus grand au plus récent au plus ancien
//         subjects.sort((a, b) => Number(recentPoll(b.polls).date) - Number(recentPoll(a.polls).date));
//         for (let subject of response.subjects) {
//             displaySubject(subject);
//         }
//     }
// }
/**
 * Récupère la liste des matières d'un professeur
 * et les affiches dans le DOM
 */
function loadDiscipline() {
    $("#list_discipline").html("");
    // debug();
    $.ajax({
        type: "GET",
        url: URL_SUBJECT,
        success: function (data) {
            let response = JSON.parse(data);
            if (response.statut != undefined && response.statut == "ok") {
                // console.log(response.subjects);
                let subjects = response.subjects;
                // on tri les matière du plus grand au plus récent au plus ancien
                subjects.sort((a, b) => Number(recentPoll(b.polls).date) - Number(recentPoll(a.polls).date));
                for (let subject of response.subjects) {
                    displaySubject(subject);
                }
            } else {
                window.location = URL_PAGE_CONNEXION;
            }
        },
        error: error => {
            // window.location = URL_PAGE_CONNEXION;
        },
        beforeSend: (xhr) => xhr.setRequestHeader('Authorization', TOKEN),
        timeout: 2000
    });
}

/**
 * Supprime une matière
 * @param {String} idSubject id de la matière
 */
function deletDiscipline(idSubject) {
    $.ajax({
        type: "DELETE",
        url: URL_SUBJECT + idSubject,
        success: (data) => {
            let response = JSON.parse(data);
            if (response.statut == "ok") {
                loadDiscipline();
            }
        },
        error: (e) => {
            console.error(e);
        },
        beforeSend: (xhr) => xhr.setRequestHeader('Authorization', TOKEN),
        timeout: 1500
    });
}

$(function () {
    let result;
    if (/^(.*)@.*$/.test(MAIL)) {
        result = RegExp.$1;
    } else {
        window.location = URL_PAGE_CONNEXION;
    }
    $("#account_name").text(result);
    loadDiscipline();
    $("#signout_btn").click((e) => {
        e.preventDefault();
        $.ajax({
            type: "GET",
            url: URL_DECO,
            success: (data) => {
                window.location = URL_PAGE_CODE;
            },
            error: (e) => {
                console.error(e);
                window.location = URL_PAGE_CODE;
            },
            beforeSend: (xhr) => xhr.setRequestHeader('Authorization', TOKEN)
        });
    });
});