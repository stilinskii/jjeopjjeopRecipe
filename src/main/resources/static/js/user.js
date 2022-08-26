$(function(){
            var dtToday = new Date();

            var month = dtToday.getMonth() + 1;
            var day = dtToday.getDate();
            var year = dtToday.getFullYear();

            if(month < 10)
                month = '0' + month.toString();
            if(day < 10)
                day = '0' + day.toString();

            var maxDate = year + '-' + month + '-' + day;
            $('#birthday').attr('max', maxDate);
});

function check_password(password){
            var password = document.getElementById('password').value;
            var password2 = document.getElementById('password2').value;
            if(password != '' && password2 != ''){
                if(password == password2){
                    document.getElementById('check').innerHTML='비밀번호가 일치합니다.';
                    document.getElementById('check').style.color='gray';
                } else {
                    document.getElementById('check').innerHTML='비밀번호가 일치하지 않습니다.';
                    document.getElementById('check').style.color='red';
                }
            } else if(password2 == ''){
                document.getElementById('check').innerHTML='비밀번호 재입력 항목이 누락되었습니다.';
                document.getElementById('check').style.color='red';
            }
        }
