$(document).ready(function() {
    $.ajax({
        url: '/admin/dashboard',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            $('.dropbtn span').text(data.fullName);
            $('.dropdown-content p:nth-child(1)').append(data.email);
            $('.dropdown-content p:nth-child(2)').append(data.jobTitle);
            $('.dropdown-content p:nth-child(3)').append(data.idnp);
            $('.dropdown-content p:nth-child(4)').append(data.address);
            $('.dropdown-content p:nth-child(5)').append(data.phone);
        },
        error: function(err) {
            console.log(err);
        }
    });
});