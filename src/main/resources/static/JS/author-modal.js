var modalAuthor = document.getElementById('authorsListModal');
var modalAddAuthor = document.getElementById('addAuthorModal');
var modalEditAuthor = document.getElementById('editAuthorModal');
var modalDeleteAuthor = document.getElementById('deleteAuthorModal');

var btnAuthor = document.getElementById("authors");
var btnAddAuthor = document.getElementById("addAuthorButton");

var spanAuthor = document.getElementsByClassName("closeAuthor")[0];
var spanAddAuthor = document.getElementsByClassName("closeAuthor")[1];
var spanEditAuthor = document.getElementsByClassName("closeAuthor")[2];
var spanDeleteAuthor = document.getElementsByClassName("closeAuthor")[3];

var addAuthorForm = document.getElementById("addAuthorForm");
var editAuthorForm = document.getElementById("editAuthorForm");

var btnCancelDeleteAuthor = document.getElementById("cancelDeleteAuthorButton");

btnAuthor.onclick = function() {
    modalAuthor.style.display = "block";
    loadAuthors();
}

btnAddAuthor.onclick = function() {
    modalAddAuthor.style.display = "block";
}

spanAuthor.onclick = function() {
    modalAuthor.style.display = "none";
}

spanAddAuthor.onclick = function() {
    modalAddAuthor.style.display = "none";
}

spanEditAuthor.onclick = function() {
    modalEditAuthor.style.display = "none";
}

spanDeleteAuthor.onclick = function() {
    modalDeleteAuthor.style.display = "none";
}

btnCancelDeleteAuthor.onclick = function() {
    modalDeleteAuthor.style.display = "none";
}

var authorId;

function loadAuthors() {
    var selectedAuthors = $('#author').val();

    $.getJSON('/author', function(data) {
        var table = $("#authorsListModal table");
        // Clear existing rows
        table.find("tr:gt(0)").remove();

        var select = $('#author');
        select.empty();
        $.each(data, function(index, value) {
            // Check if surName is null
            var surName = value.surName !== null ? value.surName : '';
            // Append a new row
            table.append('<tr><td>' + value.authorId + '</td><td>' + value.firstName + '</td><td>' + value.lastName + '</td><td>' + surName + '</td><td>' +
                '<form><input type="button" value="Modifică" class="table editAuthorButton" style="width: 110px; margin-right: 6px"/></form>' +
                '<form><input type="button" value="Șterge" class="table deleteAuthorButton" style="background-color: red; width: 110px;"></form>' +
                '</td></tr>');
            var fullName = value.firstName + ' ' + value.lastName;
            if (value.surName !== null) {
                fullName += ' ' + value.surName;
            }
            select.append('<option value="' + value.authorId + '">' + fullName + '</option>');
        });

        // Now that the select has fully updated, restore the selected authors
        select.val(selectedAuthors);
    });
}

$(document).on('click', '.editAuthorButton', function() {
    var row = $(this).closest("tr");
    var authorId = row.find("td:nth-child(1)").text();
    var firstName = row.find("td:nth-child(2)").text();
    var lastName = row.find("td:nth-child(3)").text();
    var surName = row.find("td:nth-child(4)").text();
    $("#editAuthorId").val(authorId);
    $("#editFirstName").val(firstName);
    $("#editLastName").val(lastName);
    $("#editSurName").val(surName);
    modalEditAuthor.style.display = "block";
});

$("#editAuthorSubmit").off('click').on('click', function(e) {
    e.preventDefault();
    var authorId = $("#editAuthorId").val();
    var updatedFirstName = $("#editFirstName").val();
    var updatedLastName = $("#editLastName").val();
    var updatedSurName = $("#editSurName").val();

    $.ajax({
        url: '/author/' + authorId,
        type: 'PUT',
        data: JSON.stringify(
            { firstName: updatedFirstName,
                    lastName: updatedLastName,
                    surName: updatedSurName
            }),
        contentType: 'application/json; charset=utf-8',
        success: function(data) {
            modalEditAuthor.style.display = "none";
            loadAuthors();  // Reload the genres after successful edit
        }
    });
});

$("#addAuthorSubmit").off('click').on('click', function(e) {
    e.preventDefault();
    var newFirstName = $("#newFirstName").val();
    var newLastName = $("#newLastName").val();
    var newSurName = $("#newSurName").val();

    $.ajax({
        url: '/author',
        type: 'POST',
        data: JSON.stringify({
            firstName: newFirstName,
            lastName: newLastName,
            surName: newSurName}),
        contentType: 'application/json; charset=utf-8',
        success: function(data) {
            modalAddAuthor.style.display = "none";
            loadAuthors();  // Reload the genres after successful add
            $("#newFirstName").val('');  // Clear the form
            $("#newLastName").val('');  // Clear the form
            $("#newSurName").val('');  // Clear the form
        }
    });
});

$(document).on('click', '.deleteAuthorButton', function() {
    var row = $(this).closest("tr");
    authorId = row.find("td:nth-child(1)").text(); // Обновление genreId
    var lastName = row.find("td:nth-child(2)").text();
    var firstName = row.find("td:nth-child(3)").text();
    var surName = row.find("td:nth-child(4)").text();
    var fullName = lastName + ' ' + firstName + ' ' + surName;
    $("#deleteAuthorText").text("Doriți să ștergeți autorul: " + fullName + "?");
    modalDeleteAuthor.style.display = "block";
});

$('#confirmDeleteAuthorButton').off('click').on('click', function() {
    $.ajax({
        url: '/author/' + authorId, // genreId теперь доступна здесь
        type: 'DELETE',
        success: function() {
            modalDeleteAuthor.style.display = "none";
            loadAuthors();  // Reload the genres after successful deletion
        }
    });
});


