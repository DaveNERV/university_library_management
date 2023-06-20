$(document).ready(function() {
    $('.orderRow').on('click', function(e) {
        if (!$(e.target).hasClass('deleteOrder')) {
            let target = e.currentTarget;
            let orderId = $(target).children('td').eq(0).text();
            let reader = $(target).children('td').eq(3).text();
            let status = $(target).children('td').eq(2).text(); // Get the order status from the row
            $('.reader').text('Cititorul ' + reader);
            $('#orderId').val(orderId);

            // Check the status and control the button and checkboxes
            if (status === 'Format' || status === 'Anulat') {
                $('#offerSubmitButton').hide();
            } else {
                $('#offerSubmitButton').show();
            }

            fetchBooksInOrder(orderId, status);
            console.log('Fetch books in order called with orderId: ' + orderId);
        }
    });
});

$(document).ready(function() {
    var orderId;

    $(document).on('click', '.deleteOrder', function(e) {
        e.preventDefault();

        orderId = $(this).closest('tr').children('td:first').text();
        $('#deleteModal').show();
    });

    $(document).on('click', '#deleteCancel', function() {
        $('#deleteModal').hide();
    });

    $(document).on('click', '#deleteConfirm', function(e) {
        e.preventDefault();

        $.ajax({
            url: '/order/' + orderId,
            type: 'PUT',
            success: function(result) {
                var row = $('[data-id="' + orderId + '"]').closest('tr');
                row.find('td:eq(0)').text(result.orderId);
                row.find('td:eq(1)').text(result.orderDate);
                row.find('td:eq(2)').text(result.status);
                row.find('td:eq(3)').text(result.reader);
                row.find('td:eq(4)').text(result.responsible);
                row.find('td:eq(5)').text(result.price + " lei");

                var button = row.find('.deleteOrder');
                button.prop('disabled', true);
                button.css('background-color', '#555');
                button.val('Anulat');

                $('#deleteModal').hide();
            },
            error: function(request,msg,error) {
                console.log(error);
                $('#deleteModal').hide();
            }
        });
    });
});

function fetchBooksInOrder(orderId, status) {
    fetch('/order/books?orderId=' + orderId)
        .then(response => response.json())
        .then(data => {
            let tableBook = document.querySelector(".tableBook");
            // Clear table rows
            tableBook.innerHTML = `
                <tr>
                    <th>ID</th>
                    <th>Titlu</th>
                    <th>Autor</th>
                    <th>Genuri</th>
                    <th>Anul publicării</th>
                    <th>Ediția</th>
                    <th>Locul publicării</th>
                    <th>ISBN</th>
                    <th>Anulat</th>
                    <th>Numărul</th>
                    <th>Pret</th>
                </tr>`;
            // Add new rows
            data.forEach(book => {
                let row = document.createElement('tr');
                row.innerHTML = `
                    <td>${book.bookDTO.bookId}</td>
                    <td>${book.bookDTO.bookName}</td>
                    <td>${book.bookDTO.authorsList}</td>
                    <td>${book.bookDTO.genreList}</td>
                    <td>${book.bookDTO.publishYear}</td>
                    <td>${book.bookDTO.publish}</td>
                    <td>${book.bookDTO.publishPlace}</td>
                    <td>${book.bookDTO.isbn}</td>
                    <td>${book.canceled ? '&#9989;' : (status === 'Format' || status === 'Anulat' ? '<input type="checkbox" value="' + book.bookDTO.bookId + '" disabled="true"/>' : '<input type="checkbox" value="' + book.bookDTO.bookId + '" />')}</td>
                    <td>${book.nr}</td>
                    <td>${book.price}</td>
                `;
                tableBook.appendChild(row);
            });
            // Show book information
            document.querySelector('.booksInOrder').style.display = 'block';
        })
        .catch(error => console.error(error));
}

$(document).ready(function() {
    $('#offerSubmitButton').on('click', function(e) {
        e.preventDefault();

        var orderId = $('#orderId').val();
        var bookIds = [];

        $('input[type="checkbox"]:checked').each(function() {
            bookIds.push(parseInt($(this).val()));
        });

        var offerBookDTO = {
            order: orderId,
            bookIds: bookIds
        };

        $.ajax({
            url: '/order/offer',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(offerBookDTO),
            success: function(response) {
                window.location.href = '/employee/reservations';
            },
            error: function(request, status, error) {
                // Обработка ошибки
            }
        });
    });
});