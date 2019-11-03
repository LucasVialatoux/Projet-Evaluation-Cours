const JSON_POLLS = '{"statut":"ok","subject":{"name":"Conception web","polls":[{"id":"unid1poll1","date":"1572338690503","results":[{"num":"0","result":"20"},{"num":"1","result":"50"},{"num":"2","result":"12"},{"num":"3","result":"5"},{"num":"4","result":"15"},{"num":"5","result":"30"}]},{"id":"unid1poll2","date":"1572438690503","results":[{"num":"0","result":"50"},{"num":"1","result":"20"},{"num":"2","result":"50"},{"num":"3","result":"0"},{"num":"4","result":"5"},{"num":"5","result":"62"}]},{"id":"unid1poll3","date":"1572538690503","results":[{"num":"0","result":"85"},{"num":"1","result":"2"},{"num":"2","result":"45"},{"num":"3","result":"20"},{"num":"4","result":"15"},{"num":"5","result":"42"}]}]}}';
const URL_GET_SUBJECT = "http://localhost:8080/ens/subjects/";
const URL_ADD_POLL = "http://localhost:8080/ens/poll";
const URL_PAGE_TEACHER = "../teacherPage/teacher.html";
const URL_PAGE_CODE = "../codePage/code.html";
const EMOTIONS = ['Intéressant', 'Accessible', 'Compliqué', 'Monotone', 'Confus', 'Effrayé'];
const TOKEN = localStorage.getItem("token");
const params = new URL(location.href).searchParams;
const discipline = params.get('discipline');

if (discipline == null ){
    window.location = URL_PAGE_TEACHER;
}

/**
 * Create un graphe
 * @param ctx canvas dans lequel mettre le graphe
 * @param {string[]} emotions liste des nom d'emotion
 * @param {Number[]} data tableau de resultats pour chaque émotion
 */
function createChart(ctx, emotions, data) {
    return new Chart(ctx, {
        type: 'bar',
        data: {
            labels: emotions,
            datasets: [{
                data: data,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.5)',
                    'rgba(54, 162, 235, 0.5)',
                    'rgba(255, 206, 86, 0.5)',
                    'rgba(75, 192, 192, 0.5)',
                    'rgba(153, 102, 255, 0.5)',
                    'rgba(255, 159, 64, 0.5)'
                ],
                borderWidth: 2,
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ]
            }]
        },
        options: {
            legend: {
                position:'false',
                labels: {
                    fontStyle: 'bold',
                    fontSize: 14,
                }
            },
            scales:{
                xAxes:[{
                    gridLines: {
                        color: "rgba(255, 255, 255, 0.3)"
                    }
                }],
                yAxes:[{
                    gridLines: {
                        color: "rgba(255, 255, 255, 0.3)"
                    }
                }]
            }
        }
    });
}

/**
 * Mets à jours les
 * @param {Chart} chart 
 * @param {Number[]} data 
 */
function updateChart(chart, data) {
    chart.data.datasets[0].data = data;
    chart.update();
}

/**
 * ajoute un sondage dans la vu
 * @param {Poll} poll un sondage
 * @param {Map<string,Chart>} charts Map des charts
 */
function createPoll(poll, charts) {
    $("#poll_container").prepend(
        '<div class="container poll">'
        +    '<br/>'
        +    '<div class="row">'
        +       '<h4 class="col"> Sondage du : ' + moment(Number(poll.date)).locale("fr").format('L') +'  '
        +           '<span class="badge badge-secondary">Participants '
        +               '<span class="badge badge-light" id="nb'+ poll.id +'"></span>'
        +           '</span>'
        +       '</h4>'
        +    '</div>'
        +    '<br/>'
        +    '<canvas  width="4" height="2" class="row col" id="'+ poll.id +'"></canvas>'
        +    '<br/>'
        +    '<div class="row justify-content-md-center code_manager">'  
        +        '<h5 class="col-md-auto">Code sondage : <span id="code_'+ poll.id + '" class="badge badge-light"></span></h5>'
        +        '<a id="getCode" href="'+ URL_PAGE_CODE+'?id='+ poll.id+'" class="btn btn-primary col-md-auto btn_Discipline">'
        +            '<img src="../../ressources/fullscreen-24px.svg" alt="">Plein écran'
        +       '</a>'
        +        '<button type="button" class="btn btn-primary col-md-auto btn_Discipline" onclick="generateCode(\'' + poll.id + '\')">'
        +            '<img src="../../ressources/refresh-24px.svg" alt="">Regénérer'
        +        '</button>'
        +    '</div>'
        +    '<br/>'
        +    '<br/>'
        +'</div>'
        +'<br/>'
    );
    let ctx = document.getElementById(poll.id).getContext('2d');
    let resultats = [];
    let nbTotal = 0;
    for (data of poll.results) {
        resultats[data.num] = Number(data.result); 
        nbTotal += Number(data.result);
    }
    $("#nb"+poll.id).text(nbTotal);
    charts.set(poll.id, createChart(ctx,EMOTIONS,resultats));
    getCode(poll.id);
}

/**
 * 
 * @param {Poll} poll un sondage
 * @param {Map<string,Chart>} charts Map des charts
 */
function updatePoll(poll, charts) {
    // console.log(charts )
    if (charts.get(poll.id) != undefined) {
        let results = [];
        let nbTotal = 0;
        let diff = false; // Optimisation pour accélérer l'actualisation des données
        let oldData = charts.get(poll.id).data.datasets[0].data
        for (data of poll.results) {
            results[data.num] = Number(data.result); 
            diff = diff || (results[data.num] != oldData[data.num])
            nbTotal += Number(data.result);
        }
        if (diff) {
            $("#nb"+poll.id).text(nbTotal);
            updateChart(charts.get(poll.id), results);
        }
    } else { // Le sondage n'est pas affiché
        createPoll(poll,charts);
    }
}

/**
 * Actualise les sondages TODO
 * @param {Map<string,Chart>} charts Map des charts
 */
function refreshPolls(charts) {
    // $.ajax({
    //     type: "GET",
    //     url: URL_GET_SUBJECT + discipline + "/results",
    //     data: {
    //         subject: $("#disciplineInput").val()
    //     },
    //     dataType: "json",
    //     success: (data) => {
    //         let response = JSON.parse(data); 
    //         if (response.statut == "ok") {
    //             window.location = URL_PAGE_TEACHER;
    //         } else if (response.statut == "subjectAlreadyUsed") {
    //             $("#error").text("Le nom de matière saisie existe déjà");
    //             $("#error").show();
    //         } else {
    //             console.error(data);
    //             window.location = URL_PAGE_TEACHER;
    //         }
    //     },
    //     error: (error) => {
    //         console.error(error);
    //         window.location = URL_PAGE_TEACHER;
    //     },
    //     beforeSend: (xhr) => xhr.setRequestHeader('Authorization', TOKEN),
    //     timeout: 1000
    // });
    let response = JSON.parse(JSON_POLLS);
    if (response.statut == "ok") {
        if ( $("#discipline_title").text() == "" ){
            $("#discipline_title").text(response.subject.name);
        }
        let polls = response.subject.polls;
        polls.sort((a, b)=> Number(a.date) - Number(b.date))
        for (let poll of polls){
            updatePoll(poll, charts);
        }
    }
}

/**
 * Sleep
 * @param {Number} ms temps d'attente en milliseconde
 */
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

/**
 * Service de sondage
 * Récupére les sondages sur le serveur
 * Controle l'affichage des sondages 
 * Et leurs actualisations
 */
async function pollsService() {
    // Paramétrage des graphes
    Chart.defaults.global.defaultFontColor = "#fff";
    // Liste des charts affiché dans la page sondage
    let chartMap = new Map();
    //
    while (true) {
        refreshPolls(chartMap);
        await sleep(1000);
    }
}

/**
 * Déclenché au chargement du DOM
 */
$(_ => {
    console.log(discipline);
    //On lance le service de sondage
    pollsService();
    
    /**
     * Event ajout d'un sondage
     */
    var isAdding = false;
    $("#add_discipline").click((event) => { 
        event.preventDefault();
        if (isAdding) {
            return;
        }
        isAdding = true;
        $("#add_discipline").html('<span class="spinner-grow spinner-grow-sm" role="status" aria-hidden="true"></span>AJOUTER');
        $("#infoErreur").hide();
        $.ajax({
            type: "POST",
            url: URL_ADD_POLL,
            data: {
                id:discipline
            },
            dataType: "JSON",
            success: data => {
                $("#add_discipline").html('<img src="../../ressources/add_circle_outline-24px.svg" alt="">AJOUTER');
                let response = JSON.parse(data);
                if (response.statut == "ok") {
                }else {
                    $("#infoErreur").text("Ajout impossible d'un sondage");
                    $("#infoErreur").show();
                }
                isAdding = false;
            },
            error: error => {
                $("#add_discipline").html('<img src="../../ressources/add_circle_outline-24px.svg" alt="">AJOUTER');
                $("#infoErreur").text("Veuillez vérifier votre connexion");
                $("#infoErreur").show();
                console.error(error);
                isAdding = false;
            },
            timeout: 2000
        });
    });
}); 