$( document ).ready(function () {
    $('#siteNameInput').keyup(function () {
        if ($(this).val() == '' || $('#email1Input').val() == '' || $('#email2Input').val() == '' || $('#latitudeInput').val() == '' || $('#longitudeInput').val() == '') {
            $('#registerSubmit').prop('disabled', true);
        } else {
            $('#registerSubmit').prop('disabled', false);
        }
    });
    $('#email1Input').keyup(function () {
        if ($(this).val() == '' || $('#siteNameInput').val() == '' || $('#email2Input').val() == '' || $('#latitudeInput').val() == '' || $('#longitudeInput').val() == '') {
            $('#registerSubmit').prop('disabled', true);
        } else {
            $('#registerSubmit').prop('disabled', false);
        }
    });
    $('#email2Input').keyup(function () {
        if ($(this).val() == '' || $('#siteNameInput').val() == '' || $('#email1Input').val() == '' || $('#latitudeInput').val() == '' || $('#longitudeInput').val() == '') {
            $('#registerSubmit').prop('disabled', true);
        } else {
            $('#registerSubmit').prop('disabled', false);
        }
    });
    $('#latitudeInput').keyup(function () {
        if ($(this).val() == ''|| $('#siteNameInput').val() == '' || $('#email1Input').val() == '' || $('#email2Input').val() == '' || $('#longitudeInput').val() == '') {
            $('#registerSubmit').prop('disabled', true);
        } else {
            $('#registerSubmit').prop('disabled', false);
        }
    });
    $('#longitudeInput').keyup(function () {
        if ($(this).val() == '' || $('#siteNameInput').val() == '' || $('#email1Input').val() == '' || $('#email2Input').val() == '' || $('#latitudeInput').val() == '') {
            $('#registerSubmit').prop('disabled', true);
        } else {
            $('#registerSubmit').prop('disabled', false);
        }
    });
});