    var manualNo = $('.rcp_manual').last().children(":first").text() * 1;
    var inputContainer = document.querySelector(".manual_container");
    var inputNode = $('.rcp_manual').last();
    var n1 = inputNode.clone();

    $('#addManual').on("click", function(e){
        e.preventDefault();
        manualNo++;
        n1.attr("style", "");
        n1.children(".manual_step").text(manualNo);
        n1.children("textarea").text('');
        n1.clone().appendTo($('.manual_container'));
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

    $(".cate_box a[style='color: black; font-weight: bold;']").each(function(index, item){
        cateArr.push($(item).attr('value'));
    });

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

    var alertMsg = "";

    $("#submitBtn").on("click", function(e){
        e.preventDefault();
        if($("#rcp_name").val() == ""){
            alertMsg = "<p>레시피 이름을 입력하세요.</p>";
            $(".modal-body p").html(alertMsg);
            $("#alertBtn").click();
        }else if($("select[name=time] option:selected").val() == ""){
            alertMsg = "<p>소요시간을 입력하세요.</p>";
            $(".modal-body p").html(alertMsg);
            $("#alertBtn").click();
        }else if($("select[name=difficulty] option:selected").val() == ""){
            alertMsg = "<p>난이도를 입력하세요.</p>";
            $(".modal-body p").html(alertMsg);
            $("#alertBtn").click();
        }else if($("select[name=amount] option:selected").val() == ""){
            alertMsg = "<p>양을 입력하세요.</p>";
            $(".modal-body p").html(alertMsg);
            $("#alertBtn").click();
        }else if($("textarea[name=summary]").val() == ""){
            alertMsg = "<p>레시피 소개글을 입력하세요.</p>";
            $(".modal-body p").html(alertMsg);
            $("#alertBtn").click();
        }else if($("textarea[name=rcp_parts_dtls]").val() == ""){
            alertMsg = "<p>레시피 재료를 입력하세요.</p>";
            $(".modal-body p").html(alertMsg);
            $("#alertBtn").click();
        }else{
            $("form").submit();
        }
    });