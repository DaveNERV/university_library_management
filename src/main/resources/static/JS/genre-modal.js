var modalGenre = document.getElementById('genresListModal');
var modalAddGenre = document.getElementById('addGenreModal');
var modalEditGenre = document.getElementById('editGenreModal');
var modalDeleteGenre = document.getElementById('deleteGenreModal');

var btnGenre = document.getElementById("genres");
var btnAddGenre = document.getElementById("addGenreButton");

var spanGenre = document.getElementsByClassName("closeGenre")[0];
var spanAddGenre = document.getElementsByClassName("closeGenre")[1];
var spanEditGenre = document.getElementsByClassName("closeGenre")[2];
var spanDeleteGenre = document.getElementsByClassName("closeGenre")[3];

var addGenreForm = document.getElementById("addGenreForm");
var editGenreForm = document.getElementById("editGenreForm");

var btnCancelDeleteGenre = document.getElementById("cancelDeleteGenreButton");

btnGenre.onclick = function() {
    modalGenre.style.display = "block";
    loadGenres();
}

btnAddGenre.onclick = function() {
    modalAddGenre.style.display = "block";
}

spanGenre.onclick = function() {
    modalGenre.style.display = "none";
}

spanAddGenre.onclick = function() {
    modalAddGenre.style.display = "none";
}

spanEditGenre.onclick = function() {
    modalEditGenre.style.display = "none";
}

spanDeleteGenre.onclick = function() {
    modalDeleteGenre.style.display = "none";
}

btnCancelDeleteGenre.onclick = function() {
    modalDeleteGenre.style.display = "none";
}

var genreId;

function loadGenres() {
    var selectedGenres = $('#genre').val(); // Сохранить выбранные жанры

    $.getJSON('/genre', function(data) {
        var table = $("#genresListModal table");
        table.find("tr:gt(0)").remove();
        var select = $('#genre');
        select.empty();
        $.each(data, function(index, value) {
            table.append('<tr><td>' + value.genreId + '</td><td>' + value.genreName + '</td><td>' +
                '<form><input type="button" value="Modifică" class="table editGenreButton" style="width: 110px; margin-right: 6px"/></form>' +
                '<form><input type="button" value="Șterge" class="table deleteGenreButton" style="background-color: red; width: 110px;"></form>' +
                '</td></tr>');
            select.append('<option value="' + value.genreId + '">' + value.genreName + '</option>');
        });

        select.val(selectedGenres); // Вернуть выбранные жанры после обновления
    });
}

$(document).on('click', '.editGenreButton', function() {
    var row = $(this).closest("tr");
    var genreId = row.find("td:nth-child(1)").text();
    var genreName = row.find("td:nth-child(2)").text();
    $("#editGenreId").val(genreId);
    $("#editGenreName").val(genreName);
    modalEditGenre.style.display = "block";
});

$("#editGenreSubmit").off('click').on('click', function(e) {
    e.preventDefault();
    var genreId = $("#editGenreId").val();
    var updatedGenreName = $("#editGenreName").val();

    $.ajax({
        url: '/genre/' + genreId,
        type: 'PUT',
        data: JSON.stringify({ genreName: updatedGenreName }),
        contentType: 'application/json; charset=utf-8',
        success: function(data) {
            modalEditGenre.style.display = "none";
            loadGenres();  // Reload the genres after successful edit
        }
    });
});

$(document).on('click', '.deleteGenreButton', function() {
    var row = $(this).closest("tr");
    genreId = row.find("td:nth-child(1)").text(); // Обновление genreId
    var genreName = row.find("td:nth-child(2)").text();
    $("#deleteGenreText").text("Doriți să ștergeți genul: " + genreName + "?");
    modalDeleteGenre.style.display = "block";
});

$('#confirmDeleteGenreButton').off('click').on('click', function() {
    $.ajax({
        url: '/genre/' + genreId, // genreId теперь доступна здесь
        type: 'DELETE',
        success: function() {
            modalDeleteGenre.style.display = "none";
            loadGenres();  // Reload the genres after successful deletion
        }
    });
});

$("#addGenreSubmit").off('click').on('click', function(e) {
    e.preventDefault();
    var newGenreName = $("#newGenreName").val();

    $.ajax({
        url: '/genre',
        type: 'POST',
        data: JSON.stringify({ genreName: newGenreName }),
        contentType: 'application/json; charset=utf-8',
        success: function(data) {
            modalAddGenre.style.display = "none";
            loadGenres();  // Reload the genres after successful add
            $("#newGenreName").val('');  // Clear the form
        }
    });
});