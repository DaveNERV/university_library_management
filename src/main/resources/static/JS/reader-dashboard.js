$(document).ready(function() {
    $.ajax({
        url: '/reader', // ваш URL-адрес может отличаться
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            $('.dropbtn span').text(data.fullName);
            $('.dropdown-content p:nth-child(1)').append(data.ticket);
            $('.dropdown-content p:nth-child(2)').append(data.email);
            $('.dropdown-content p:nth-child(3)').append(data.group);
            $('.dropdown-content p:nth-child(4)').append(data.studyPeriod);
            $('.dropdown-content p:nth-child(5)').append(data.address);
            $('.dropdown-content p:nth-child(6)').append(data.phone);
        },
        error: function(err) {
            console.log(err);
        }
    });
});