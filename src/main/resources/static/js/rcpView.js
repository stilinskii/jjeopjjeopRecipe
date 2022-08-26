    var rcp_seq = $('#rcp_seq').val();
    var co_page_seq = $('.page-link');
    var user_id = $('#user_id').val();
    var startPageNum = $('#startPageNum').val() * 1;
    var endPageNum = $('#endPageNum').val() * 1;
    var blockPageCnt = $('#blockPageCnt').val() * 1;
    var totalPageCnt = $('#totalPageCnt').val() * 1;
    var count = $('#count').val() * 1;
    var commentCurrentPage = 1;
    var userData = {
        "rcp_seq": $("#rcp_seq").val(),
        "user_id": '<%=(String)session.getAttribute("uid")%>'
    };

    $(function(){
        $('.page-num').first().click();
    });

    function getCommentList(){
        $.getJSON("/recipe/comment/list", {rcp_seq : rcp_seq, commentCurrentPage : commentCurrentPage}, function(obj){
            let comment_list = '';

            // 댓글 내용 출력
            $(obj.list).each(function(i, obj){
                comment_list += '<p>';
                comment_list += '<b><span class="left" style="font-size: 15px;">'+ obj.user_id +'</span></b>&nbsp&nbsp&nbsp';
                comment_list += '<span style="color: gray;">' + obj.co_date + '</span>&nbsp&nbsp&nbsp';
                if(user_id == obj.user_id){
                    comment_list += '<span class="coUpdateBtn viewBtn" style="color: blue;">수정</span>&nbsp&nbsp&nbsp';
                    comment_list += '<span class="coDeleteBtn viewBtn">삭제</span>&nbsp&nbsp&nbsp';
                }
                comment_list += '<br/>';
                comment_list += '<span>' + obj.comment_txt + '</span>';
                comment_list += '<span style="display: none;">' + obj.co_rcp_seq + '</span>';
                comment_list += '</p>';
            });

            $(".comment_area").html(comment_list);

            // 댓글 페이징 처리
            let page_list = '';
            startPageNum = obj.page.startPageNum;
            endPageNum = obj.page.endPageNum;
            totalPageCnt = obj.page.totalPageCnt;

            $('.page-area').empty();

            page_list += '<li class="page-item"><a class="page-link" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>';

            for(i=startPageNum; i<=endPageNum; i++){
                if(i == commentCurrentPage){
                    page_list += '<li class="page-item active">';
                }else{
                    page_list += '<li class="page-item">';
                }
                page_list += '<a class="page-link page-num">' + i + '</a>';
                page_list += '</li>';
               };

            page_list += '<li class="page-item"><a class="page-link" aria-label="Previous"><span aria-hidden="true">&raquo;</span></a></li>';
            $(".page-area").append(page_list);
        });
    }

    // 댓글 출력 관리
    $(document).on("click", ".page-link", function(){
        commentCurrentPage = $(this).text().trim();
        if(commentCurrentPage=="»"){
            commentCurrentPage = Math.min(startPageNum + blockPageCnt, totalPageCnt) * 1;
        }else if(commentCurrentPage=="«"){
            commentCurrentPage = Math.max(startPageNum - blockPageCnt, 1) * 1;
        }

        $('.active').removeClass('active');
        $(this).parent().addClass('active');
        getCommentList();

    });

    // 댓글 작성 관리
    coWriteBtn.onclick = function(){
        var commentData = {
        "rcp_seq": $("#rcp_seq").val(),
        "user_id": '<%=(String)session.getAttribute("uid")%>',
        "comment_txt": $("#comment_txt").val()
        };

        $.ajax({
            type: 'post',
            url: '/recipe/comment/write',
			data: commentData,
			success : function(data) {
			    getCommentList();
			    $("#comment_txt").val('');
                count = count + 1;
			    $("#coCount").text('댓글 ' + count);
			},
			error : function(data) {
			    $('#needLoginBtn').click();
			}
        });
    };

    // 댓글 수정 관리
    $(document).on("click", ".coUpdateBtn", function(){
        $(this).css("display", "none");
        var coUpdateConfirmBtn = '<span id="coUpdateConfirmBtn" class="viewBtn" style="color: blue;">수정 완료</span>';
        $(this).after(coUpdateConfirmBtn);

        var updateInput = '';
        updateInput += '<p><textarea class="form-control" id="update_txt" style="width: 100%; height: 69px;">'+$(this).next().next().next().next().text()+'</textarea>';
        updateInput += '</p>';
        $(this).parent().append(updateInput);
    });

    $(document).on("click", "#coUpdateConfirmBtn", function(){
        var updateData = {
        "co_rcp_seq": $(this).next().next().next().next().text(),
        "comment_txt": $("#update_txt").val()
        };

        $.ajax({
            type: 'post',
            url: '/recipe/comment/update',
			data: updateData,
			success : function(data) {
			    getCommentList();
			    $("#coUpdateConfirmBtn").css("display", "none");
                var coUpdateBtn = '<span class="coUpdateBtn viewBtn" style="color: blue;">수정</span>';
                $("#coUpdateConfirmBtn").after(coUpdateBtn);
			},
			error : function(data) {
			    $('#needLoginBtn').click();
			}
        });
    });

    // 댓글 삭제 관리
    $(document).on("click", ".coDeleteBtn", function(){
        $(this).css("display", "none");
        var coDeleteConfirmBtn = '<span id="coDeleteConfirmBtn" class="viewBtn">삭제 확인</span>';
        $(this).after(coDeleteConfirmBtn);
    });

    $(document).on("click", "#coDeleteConfirmBtn", function(){
        var deleteData = {
            "co_rcp_seq": $(this).next().next().next().text()
        };

        $.ajax({
            type: 'post',
            url: '/recipe/comment/delete',
			data: deleteData,
			success : function(data) {
			    getCommentList();
			    $("#coDeleteConfirmBtn").css("display", "none");
                var coDeleteBtn = '<span class="coUpdateConfirmBtn viewBtn">삭제</span>';
                $("#coDeleteConfirmBtn").after(coDeleteBtn);

                count = count - 1;
			    $("#coCount").text('댓글 ' + count);
			},
			error : function(data) {
			    $('#needLoginBtn').click();
			}
        });
    });

    // 스크랩 관리
    var scraps = $("#scraps").val();
    var scrapBtn = document.getElementById("scrapBtn");

    if($("#scrapOrNot").val() > 0){
        scrapBtn.setAttribute('style', 'color:red;');
    }else{
        scrapBtn.setAttribute('style', 'color:gray;');
    }

    scrapBtn.onclick = function(){
        $.ajax({
            type: 'post',
            url: '/recipe/scrap',
			data: userData,
			success : function(data) {
            if($("#scrapOrNot").val() == 1){
                $("#scraps").attr('value', $("#scraps").val() * 1 - 1);
                $("#scraps2").text($("#scraps").val());
                $("#scrapOrNot").attr('value', 0);
                scrapBtn.setAttribute('style', 'color:gray;');
            }else{
                $("#scraps").attr('value', $("#scraps").val() * 1 + 1);
                $("#scraps2").text($("#scraps").val());
                $("#scrapOrNot").attr('value', 1);
                scrapBtn.setAttribute('style', 'color:red;');
            }
			},
			error : function(data) {
			    $('#needLoginBtn').click();
			}
        })
    };

    // 신고 관리
    reportBtn.onclick = function(){
        $.ajax({
            type: 'post',
            url: '/recipe/report',
			data: userData,
			success : function(data) {
				$("#report1").parent().hide();
			},
			error : function(data) {
			    $('#needLoginBtn').click();
			}
        })
    };

    // 공유 관리
    shareBtn.onclick = function(){
      var copyText = "http://localhost:8081/recipe/view/" + $('#rcp_seq').val();
      var textArea = document.createElement("textarea");//textarea 생성

      textArea.value = copyText;//textarea에 텍스트 입력
      document.body.appendChild(textArea);//body에 textarea 추가

      textArea.select();//선택
      document.execCommand("Copy");//복사
      textArea.remove();//생성한 textarea 삭제
    };