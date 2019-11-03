const JSON_DISCIPLINE = '{"statut":"ok","subjects":[{"id":"unid1","name":"Conception web","polls":[{"id":"unid1poll1","date":"1572338690503"},{"id":"unid1poll2","date":"1572438690503"},{"id":"unid1poll3","date":"1572538690503"}]},{"id":"unid2","name":"Réseaux","polls":[{"id":"unid2poll1","date":"1572338690503"},{"id":"unid2poll2","date":"1572438690503"},{"id":"unid2poll3","date":"1572538690503"}]}]}';
const MAIL = "mathieu.moy@gmail.com";
const URL_PAGE_DISCIPLINE = "../disciplinePage/discipline.html?discipline=";
const URL_PAGE_CODE = "../codePage/code.html";
const URL_GET_DECO = "http://localhost:8080/signout";
const URL_PAGE_INDEX = "../../index.html";
const TOKEN = localStorage.getItem("token");

/**
 * Cherche le sondage le plus récent
 * @param {Poll[]} polls liste des sondages
 * @returns {Poll} sondage le plus récent
 */
function recentPoll(polls) {
    polls.sort((a, b)=> Number(a.date) - Number(b.date));
    return polls[polls.length - 1]
}

$( _ => {
    let response = JSON.parse(JSON_DISCIPLINE);
    /^(.*)@.*$/.test(MAIL);
    let result = RegExp.$1;
    $("#account_name").text(result);
    if (response.statut != undefined && response.statut == "ok") {
        // console.log(response.subjects);
        for(let subject of response.subjects) {
            // console.log(subject);
            let latePoll = recentPoll(subject.polls) 
            // temps entre maintenant et la création du dernier sondage
            let time = moment(Number(latePoll.date)).locale("fr").fromNow();
            $("#list_discipline").append(
                '<div class="list-group-item list-group-item-action item_discipline"> '
                +'<a href="'+URL_PAGE_DISCIPLINE + subject.id +'" class="container link_discipline">'
                +   '<div class="row">'
                +        '<p class="col">' + subject.name + '</p>'
                +        '<img class="col-auto" src="../../ressources/arrow_forward_ios-24px.svg" alt="">'
                +   '</div>'
                +   '<small class="row col">Dernier sondage : ' + time + '</small>'
                +'</a>'
                +'<div class="row code_manager">'
                +   '<h5 style="color: black;" class="col-md-auto">Code du dernier sondage : <span id="code_'+ latePoll.id + '" class="badge badge-dark"></span></h5>'
                +   '<a id="getCode" href="'+ URL_PAGE_CODE+'?id='+ latePoll.id+'" class="btn btn-primary col-md-auto btn_Discipline">'
                +       '<img src="../../ressources/fullscreen-24px.svg" alt="">Plein écran'
                +   '</a>'
                +   '<button type="button" class="btn btn-primary col-md-auto btn_Discipline" onclick="generateCode(\'' + latePoll.id + '\')">'
                +       '<img src="../../ressources/refresh-24px.svg" alt="">Regénérer'
                +   '</button>'
                +'</div>'
                +'</div>'
            );
            getCode(latePoll.id);
        }
    }
    $("#signout_btn").click((e) => { 
        e.preventDefault();
        $.ajax({
            type: "GET",
            url: URL_GET_DECO,
            success:(data) => {
               window.location = URL_PAGE_INDEX;
            },
            error: (e) => {
                console.error(e);
                window.location = URL_PAGE_INDEX;
            },
            beforeSend: (xhr) => xhr.setRequestHeader('Authorization', TOKEN)
        });
    });
});