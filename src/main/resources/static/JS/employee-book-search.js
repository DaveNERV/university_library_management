$(document).ready(function(){
    var authorId = $("#authorId").attr("data-selected");
    var genreId = $("#genreId").attr("data-selected");
    var publishName = $("#publishName").attr("data-selected");

    // Fetch all authors
    $.get("/author", function(data, status){
        var select = $("#authorId");
        select.empty();
        select.append($('<option>', {value: '', text: ''}));
        data.forEach(function(author) {
            let fullName = author.firstName + ' ' + author.lastName;
            if (author.surName) {
                fullName += ' ' + author.surName;
            }
            select.append($('<option>', {
                value: author.authorId,
                text : fullName,
                selected : author.authorId == authorId
            }));
        });
    });

    // Fetch all genres
    $.get("/genre", function(data, status){
        var select = $("#genreId");
        select.empty();
        select.append($('<option>', {value: '', text: ''}));
        data.forEach(function(genre) {
            select.append($('<option>', {
                value: genre.genreId,
                text : genre.genreName,
                selected : genre.genreId == genreId
            }));
        });
    });

    // Fetch all publishers
    $.get("/publish", function(data, status){
        var select = $("#publishName");
        select.empty();
        select.append($('<option>', {value: '', text: ''}));
        data.forEach(function(publish, index) {
            select.append($('<option>', {
                value: publish,
                text : publish,
                selected : publish == publishName
            }));
        });
    });
});