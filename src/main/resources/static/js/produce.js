$(document).ready(function () {
  let btn = document.getElementById('btn');
  btn.addEventListener('click', function () {

      var fileCheck = document.getElementById("input-file").value;
        if(!fileCheck){
            alert("파일을 첨부해 주세요");
            $('#fileEmpty').css({ display: 'block' });
        }
  });


});
