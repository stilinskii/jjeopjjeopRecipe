$(document).ready(function() {
    sitePlusMinus();
});

//+, -버튼
var sitePlusMinus = function() {
    $('.js-btn-minus').on('click', function(e){
        e.preventDefault();
        if ( $(this).closest('.input-group').find('.form-control').val() != 0  ) {
            $(this).closest('.input-group').find('.form-control').val(parseInt($(this).closest('.input-group').find('.form-control').val()) - 1);
        } else {
            $(this).closest('.input-group').find('.form-control').val(parseInt(0));
        }
    });
    $('.js-btn-plus').on('click', function(e){
        e.preventDefault();
        $(this).closest('.input-group').find('.form-control').val(parseInt($(this).closest('.input-group').find('.form-control').val()) + 1);
    });
};


function clear_file(){
    var file = $("#input-file");
    file.replaceWith(file = file.clone(true));
}
