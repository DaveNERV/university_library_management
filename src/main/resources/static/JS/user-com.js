
function deleteCom(bookId) {
    const url = "/com";
    const bookUrl = url + "?bookId=" + bookId;

    $.ajax({
        url: bookUrl,
        type: 'DELETE',
        success: function(result) {
            $("#tablestyle tr:not(:first)").remove();
            if (result.comList.length > 0) {
                result.comList.forEach(function(item) {
                    let row = `<tr>
                        <td>${item.bookDTO.bookId}</td>
                        <td>${item.bookDTO.bookName}</td>
                        <td>${item.bookDTO.authorsList}</td>
                        <td>${item.bookDTO.genreList}</td>
                        <td>${item.bookDTO.publishYear}</td>
                        <td>${item.bookDTO.publish}</td>
                        <td>${item.bookDTO.publishPlace}</td>
                        <td>X${item.nr}</td>
                        <td style="width: 100px;"><input class="table" type="submit" style="background-color: red" value="Șterge" onclick="deleteCom(${item.bookDTO.bookId})"></td>
                    </tr>`;
                    $("#tablestyle").append(row);
                });
                $("#totalOrder").text(result.totalPrice);
            } else {
                $("#noCom").show();
                $("#tablestyle").hide();
                $("#orderSummary").hide();
            }
        }
    });

    $.get('/books', function(data) {
        // Предположим, что data - это массив объектов BookUserResponseDTO
        data.forEach(function(book) {
            // Найдите соответствующую строку в основной таблице
            let row = $('#tablestyle').find(`tr:has(td:first:contains(${book.bookId}))`);

            // Обновите количество книг
            $(row).find('td:nth-child(9)').text(book.nrBooks);

            // Обновите состояние кнопки
            if (book.nrBooks === 0) {
                $(row).find('input.table').prop('disabled', true);
            } else {
                $(row).find('input.table').prop('disabled', false);
            }
        });
    });
}

$(document).ready(function() {
    const url = "/com";

    $.get(url, function(data, status) {
        if (status === "success") {
            if (data.comList.length > 0) {
                $("#noCom").hide();
                $("#tablestyle").show();
                $("#orderSummary").show();
                data.comList.forEach(function(item) {
                    let row = `<tr>
                        <td>${item.bookDTO.bookId}</td>
                        <td>${item.bookDTO.bookName}</td>
                        <td>${item.bookDTO.authorsList}</td>
                        <td>${item.bookDTO.genreList}</td>
                        <td>${item.bookDTO.publishYear}</td>
                        <td>${item.bookDTO.publish}</td>
                        <td>${item.bookDTO.publishPlace}</td>
                        <td>X${item.nr}</td>
                        <td style="width: 100px;"><input class="table" type="submit" style="background-color: red" value="Șterge" onclick="deleteCom(${item.bookDTO.bookId})"></td>
                    </tr>`;
                    $("#tablestyle").append(row);
                });
                $("#totalOrder").text(data.totalPrice);

                $("#comResponseListInput").val(JSON.stringify(data));
            } else {
                $("#noCom").show();
                $("#tablestyle").hide();
                $("#orderSummary").hide();
            }
        } else {
            console.log(`Failed to fetch com data: ${status}`);
        }
    });
});

$(document).ready(function() {
    $(".table").click(function(e) {
        e.preventDefault();

        var bookId = $(this).closest('tr').find('td:first').text();
        var quantity = $(this).closest('tr').find('td:nth-child(9)').text();

        // Проверка наличия книг
        if (parseInt(quantity, 10) === 0) {
            alert("Nu sunt cărți disponibile");
            return; // остановить выполнение функции
        }

        $.ajax({
            url: '/com',
            type: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            data: {bookId: bookId},
            success: function(response) {
                if (response.comList.length === 0) {
                    $('.comTable').hide();
                    $('#noCom').show();
                    $('#orderSummary').hide();
                } else {
                    // Если список не пуст, покажите таблицу и скройте сообщение
                    $('.comTable').show();
                    $('#noCom').hide();
                    $('#orderSummary').show();

                    // Удалим все текущие строки в таблице, кроме заголовка
                    $('.comTable tr:not(:first)').remove();

                    // новые строки в таблицу на основе данных ответа
                    $.each(response.comList, function(i, item) {
                        $('.comTable').append(
                            '<tr>' +
                            '<td>' + item.bookDTO.bookId + '</td>' +
                            '<td>' + item.bookDTO.bookName + '</td>' +
                            '<td>' + item.bookDTO.authorsList + '</td>' +
                            '<td>' + item.bookDTO.genreList + '</td>' +
                            '<td>' + item.bookDTO.publishYear + '</td>' +
                            '<td>' + item.bookDTO.publish + '</td>' +
                            '<td>' + item.bookDTO.publishPlace + '</td>' +
                            '<td>' + 'X' + item.nr + '</td>' +
                            '<td style="width: 100px;"><input class="table" type="submit" style="background-color: red" value="Șterge" onclick="deleteCom(' + item.bookDTO.bookId + ')"></td>' +
                            '</tr>'
                        );
                    });
                    $('#totalOrder').text(response.totalPrice);
                }
            },
            error: function(error) {
                console.log(error);
            }
        });
    });
});