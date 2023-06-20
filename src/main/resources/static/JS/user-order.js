$(document).ready(function(){
    $(".orderTable tr").click(function(){
        var orderId = $(this).find("td:first").text();
        $.ajax({
            url: '/order/books',
            data: {
                orderId: orderId
            },
            type: 'GET',
            success: function(response){
                var bookTable = $('.tableBook');
                bookTable.empty();
                var trHeader = $('<tr>');
                $('<th>').html('ID').appendTo(trHeader);
                $('<th>').html('Titlu').appendTo(trHeader);
                $('<th>').html('Autor').appendTo(trHeader);
                $('<th>').html('Genuri').appendTo(trHeader);
                $('<th>').html('Anul publicării').appendTo(trHeader);
                $('<th>').html('Ediția').appendTo(trHeader);
                $('<th>').html('Locul publicării').appendTo(trHeader);
                $('<th>').html('ISBN').appendTo(trHeader);
                $('<th>').html('Numărul').appendTo(trHeader);
                $('<th>').html('Pret').appendTo(trHeader);
                trHeader.appendTo(bookTable);
                $.each(response, function(i, bookInOrder){
                    var tr = $('<tr>');
                    $('<td>').html(bookInOrder.bookDTO.bookId).appendTo(tr);
                    $('<td>').html(bookInOrder.bookDTO.bookName).appendTo(tr);
                    $('<td>').html(bookInOrder.bookDTO.authorsList).appendTo(tr);
                    $('<td>').html(bookInOrder.bookDTO.genreList).appendTo(tr);
                    $('<td>').html(bookInOrder.bookDTO.publishYear).appendTo(tr);
                    $('<td>').html(bookInOrder.bookDTO.publish).appendTo(tr);
                    $('<td>').html(bookInOrder.bookDTO.publishPlace).appendTo(tr);
                    $('<td>').html(bookInOrder.bookDTO.isbn).appendTo(tr);
                    $('<td>').html(bookInOrder.nr).appendTo(tr);
                    $('<td>').html(bookInOrder.price).appendTo(tr);
                    tr.appendTo(bookTable);
                });
                $('.booksInOrder').css("display", "block"); // Show the div after filling the table
            },
            error: function(error){
                console.log(error);
            }
        });
    });
});
