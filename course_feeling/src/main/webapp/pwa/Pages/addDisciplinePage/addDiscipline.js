const TOKEN = localStorage.getItem("token");
if (TOKEN == null){
    window.location = URL_PAGE_CONNEXION;
}

$(_=>{
    $("#addForm").submit(event =>{
        event.preventDefault();
        $("#error").hide();
        $.ajax({
            type: "POST",
            url: URL_SUBJECT,
            data: {
                subject: $("#disciplineInput").val()
            },
            dataType: "json",
            success: (data) => {
                let response = JSON.parse(data); 
                if (response.statut == "ok") {
                    window.location = URL_PAGE_TEACHER;
                } else if (response.statut == "subjectAlreadyUsed") {
                    $("#error").text("Le nom de matière saisie existe déjà");
                    $("#error").show();
                } else {
                    console.error(data);
                    window.location = URL_PAGE_TEACHER;
                }
            },
            error: (error) => {
                console.error(error);
                window.location = URL_PAGE_TEACHER;
            },
            beforeSend: (xhr) => xhr.setRequestHeader('Authorization', TOKEN)
        });
    });
});