function StudentList() {
    $.ajax({
        url: 'listStudent'
    }).done(function (response) {
        displayStudent(response.listOfStudent);
    });
}

function TrainerList() {
    $.ajax({
        url: 'listTrainer'
    }).done(function (response) {
        displayTrainer(response.listOfTrainer);
    });
}


function displayStudent(list) {
    var randuri = "";
    list.forEach(function (obiect) {
        randuri += "<tr>" +
            "<td>" + obiect.name + "</td>" +
            "<td>" + obiect.email + "</td>" +
            "<td>" + obiect.course + "</td>" +
            "<td>" + obiect.hasknowledge + "</td>" +
            // "<td>" + obiect.date + "</td>" +
            "</tr>";
    });
    $("#obiect").html(randuri);
}

function displayTrainer(list) {
    var randuri = "";
    list.forEach(function (obiect) {
        randuri += "<tr>" +
            "<td>" + obiect.name + "</td>" +
            "<td>" + obiect.email + "</td>" +
            "<td>" + obiect.course + "</td>" +
            "<td>" + obiect.date + "</td>" +
            //  "<td>" + obiect.date + "</td>" +
            "</tr>";
    });
    $("#obiect").html(randuri);
}

function printOnDiv(listOfStudent) {
    var listHtml = '';

    var list = document.getElementById('readtrainer');

    for (var i = 0; i < listOfStudent.length; i++) {
        var elemC = listOfStudent[i];
        var el = '<li>' + elemC.name + ' ' + elemC.email + ' ' + elemC.course + ' ' + elemC.hasknowledge + ' </li>';
        listHtml = listHtml + el;
    }
    list.innerHTML = '<ol>' + listHtml + '</ol>';

    function printOnDiv(listOfTrainer) {
        var listHtml = '';

        var list = document.getElementById('readstudent');

        for (var i = 0; i < listOfTrainer.length; i++) {
            var elemC = listOfTrainer[i];
            var el = '<li>' + elemC.name + ' ' + elemC.email + ' ' + elemC.course + ' ' + elemC.date + ' </li>';
            listHtml = listHtml + el;
        }
        list.innerHTML = '<ol>' + listHtml + '</ol>';


    }
}

function search(myText) {
    $.ajax("listStudent", {
        cache: false,
        dataType: "json",
        data: {
            // order: ordinea,
            search: myText
        }
    }).done(function (response) {
        displayStudent(response.listOfStudent);

    });
}
function deleteName() {
    $.ajax({
        url: 'delete'
    }).done(function (response) {
        location.href = "login.html";
    });
}