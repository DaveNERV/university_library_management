$(document).ready(function() {
    $.ajax({
        url: '/com',
        type: 'GET',
        success: function(response) {
            // Получить цену из объекта ComResponseList
            var totalPrice = response.totalPrice;

            // Установить цену в текстовый элемент
            $('.price').text(totalPrice + ' lei');
            var comList = response.comList;

            // Создать массив для хранения данных заказа
            var orderData = [];

            // Пройти по каждому элементу в comList
            comList.forEach(function(item) {
                var orderItem = {
                    bookId: item.bookDTO.bookId,
                    number: item.nr
                };
                orderData.push(orderItem);
            });

            // Функция для обработки нажатия кнопки
            function handleSubmit() {
                // Отправить данные заказа на адрес /order с методом POST
                $.ajax({
                    url: '/order',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(orderData),
                    success: function() {
                        console.log('Заказ успешно отправлен');
                        window.location.href = '/user/browsebooks/savereservation';
                    },
                    error: function(error) {
                        console.log('Ошибка при отправке заказа:', error);
                    }
                });
            }

            // Привязать обработчик события submit к форме
            $('#myForm').on('submit', function(e) {
                e.preventDefault();
                handleSubmit();
            });
        },
        error: function(error) {
            console.log(error);
        }
    });
});