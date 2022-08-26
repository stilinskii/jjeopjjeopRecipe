    var manualNo = 1;
    var inputContainer = document.querySelector(".manual_container");
    var inputNode = document.querySelector(".rcp_manual");
    var n1 = inputNode.cloneNode(true);
    var manual_txt = "";

    $('#addManual').on("click", function(e){
        e.preventDefault();
        manualNo++;
        n1.querySelector(".manual_step").innerHTML = manualNo;
        inputContainer.appendChild(n1.cloneNode(true));
    });

    $(document).on('click', '.removeManual', function(){
        if(manualNo > 1){
            manualNo--;
            var manualIndex = $(this).parent().parent().children(":first").html();
            $(this).parent().parent().remove();

            $('.rcp_manual').each(function(index, item){
                var manualIndex2 = item.firstChild.nextSibling.innerHTML;
                if(manualIndex < manualIndex2){
                    item.firstChild.nextSibling.innerHTML = manualIndex2 - 1;
                };
            });
        }
    });

    var imgUp = document.querySelector(".img_up");
    var imgSrc = imgUp.querySelector("img");
    var fileInput = imgUp.querySelector(`input[type="file"]`);
    fileInput.addEventListener('input', () => {
        const reader = new FileReader();
		reader.addEventListener('load', () => {
		    imgSrc.setAttribute('src', reader.result);
		});
		reader.readAsDataURL(fileInput.files[0]);
    });

    var cateArr = [];

    $(".cate_box a").on("click", function(e){
        e.preventDefault();
        if(cateArr.indexOf($(this).attr("value")) == -1){
            cateArr.push($(this).attr("value"));
            $(this).attr("style", "color: black; font-weight: bold;");
        }else{
            cateArr.splice(cateArr.indexOf($(this).attr("value")), 1);
            $(this).attr("style", "color: #fff;");
        }
        $('#cateArr').attr('value', cateArr);
    });

    $("#submitBtn").on("click", function(){
        console.log($("textarea[name=manual_txt]").val());
        if($("textarea[name=manual_txt]").val() == ""){
            alertMsg = "<p>요리 순서별 요리 방법을 입력하세요.</p>";
            $(".modal-body p").html(alertMsg);
            $("#alertBtn").click();
        }else{
            $("form").submit();
        }
    });

    $("textarea[name=manual_txt]").on("change", function(){
        manual_txt = $("textarea[name=manual_txt]").val();
    });