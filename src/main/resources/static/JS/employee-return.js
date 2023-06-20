$(document).ready(function() {
    $("#offerSubmitButton").click(function(e) {
        e.preventDefault(); // Предотвращаем отправку формы при клике
        $('input[type="checkbox"]').prop('checked', true); // Выбираем все чекбоксы
    });
});

$(document).ready(function(){
    $('#checkboxes').click(function(e){
        e.preventDefault();

        let orderId = $('#orderId').val();
        let ticket = $('#ticket').val();
        let reader = $('#readerId').val();
        let offeredDate = $('#offeredDate').val();
        let deadLine = $('#deadLine').val();

        let selectedOfferIds = [];
        $('input[name="offerId"]:checked:not([disabled])').each(function() {
            selectedOfferIds.push(parseInt(this.value));
        });

        if (selectedOfferIds.length === 0) {
            alert("Please select at least one book to return.");
            return;
        }

        $.ajax({
            url: '/book-offer/' + selectedOfferIds.join(","),
            type: 'PUT',
            success: function(){
                window.location.href = "/employee/returnedbooks?orderId=" + orderId + "&ticket=" + ticket + "&readerId=" + reader + "&offeredDate=" + offeredDate + "&deadLine=" + deadLine;
            },
            error: function(error){
                console.log(error);
                alert("An error occurred while returning books.");
            }
        });
    });
});

$(document).ready(function() {
    // Отслеживаем изменения в поле ввода билета
    $("#ticket").on("input", function() {
        var selectedTicket = $(this).val(); // Получаем введенный номер билета

        // Поиск соответствующего читателя
        for (var i = 0; i < readers.length; i++) {
            if (readers[i].ticket === selectedTicket) {
                // Если найденный билет совпадает с введенным, выбираем соответствующего читателя в select
                $("#readerId").val(readers[i].readerId);
                break;
            }
        }
    });
});

function updateTicket() {
    var selectedReaderId = $("#readerId").val(); // Получаем выбранный ID читателя

    // Поиск соответствующего DTO
    for (var i = 0; i < readers.length; i++) {
        if (readers[i].readerId == selectedReaderId) {
            console.log("reached")
            // Если найденный readerId совпадает с выбранным, обновляем поле ticket
            $("#ticket").val(readers[i].ticket);
            break;
        }
    }
}

