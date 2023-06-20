$(document).ready(function() {
    // Fetch all publishers
    $.get("/publish", function(data, status){
        var datalist = $("#publishes");
        datalist.empty();
        data.forEach(function(publish, index) {
            datalist.append($('<option>', {
                value: publish
            }));
        });
    });

    var bookId = $("#bookId").val();
    $.ajax({
        url: '/books/' + encodeURIComponent(bookId),
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            $('input[name="bookId"]').val(data.bookId);
            $('#BBK').val(data.bbk);
            $('#UDK').val(data.udk);
            $('#ISBN').val(data.isbn);
            $('#price').val(data.price);
            $('#number').val(data.number);
            $('#publishName').val(data.publishName);
            $('#title').val(data.bookName);
            $('#releaseYear').val(data.publishYear);
            $('#publishPlace').val(data.publishPlace);

            $.ajax({
                url: '/author',
                type: 'GET',
                dataType: 'json',
                success: function(authors) {
                    let authorSelect = $('#author');
                    authorSelect.empty();
                    authors.forEach(function(author) {
                        let fullName = author.firstName + ' ' + author.lastName;
                        if (author.surName) {
                            fullName += ' ' + author.surName;
                        }
                        let option = new Option(fullName, author.authorId);
                        option.selected = data.authorsList.some(a => a.authorId === author.authorId);
                        authorSelect.append(option);
                    });

                    $.ajax({
                        url: '/genre',
                        type: 'GET',
                        dataType: 'json',
                        success: function(genres) {
                            let genreSelect = $('#genre');
                            genreSelect.empty();
                            genres.forEach(function(genre) {
                                let option = new Option(genre.genreName, genre.genreId);
                                option.selected = data.genreList.some(g => g.genreId === genre.genreId);
                                genreSelect.append(option);
                            });
                        },
                        error: function() {
                            console.error('Failed to retrieve genres');
                        }
                    });
                },
                error: function() {
                    console.error('Failed to retrieve authors');
                }
            });
        },
        error: function() {
            console.error('Failed to retrieve book');
        }
    });
});

$("#bookForm").submit(function(event) {
    event.preventDefault();

    var bookId = $("#bookId").val();

    var formData = {
        bookName: $('#title').val(),
        ISBN: $('#ISBN').val(),
        publishPlace: $('#publishPlace').val(),
        publishYear: parseInt($('#releaseYear').val()),
        photo: null,
        genreList: $('#genre').val().map(Number),
        authorsList: $('#author').val().map(Number),
        publishName: $('#publishName').val(),
        UDK: $('#UDK').val(),
        BBK: $('#BBK').val(),
        number: parseInt($('#number').val()),
        price: parseInt($('#price').val())
    };

    $.ajax({
        url: '/books/' + bookId,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(formData),
        success: function(data) {
            console.log('Книга успешно обновлена');
            window.location.href = "/employee/";
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error(textStatus, errorThrown);
        }
    });
});