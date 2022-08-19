//좋아요 구현
const likeBtn = document.querySelector('#like-btn');
likeBtn.addEventListener('click', dataSend);

function dataSend() {
  let addVal;
  if (likeBtn.classList.contains('fa-heart')) {
    addVal = false;
  } else {
    addVal = true;
  }
  //하트 채우기 비우기
  likeBtn.classList.toggle('fa-heart');
  likeBtn.classList.toggle('fa-heart-o');

  let postIdVal = $('#postId').val();
  let value = { postId: postIdVal, add: addVal };
  // $.post('/community/like', value).done(function (fragment) {
  //   $('#like-btn').replaceWith(fragment);
  // });

  $.ajax({
    type: 'post',
    url: '/community/like',
    data: value,
    success: function (data) {
      likeBtn.innerHTML = data;
    },
  });
  alert('좋아용완료');
}

//댓글 수정 구현
const editBtns = document.querySelectorAll('.edit-btn');
const commentBox = document.querySelectorAll('input.comment-content');
let action = true;
let editBox;
let index = 0;

editBtns.forEach((editBtn) => {
  console.log(commentBox[index]);
  editBtn.addEventListener('click', function () {
    console.log('index=' + index);
    editmode(commentBox[index]);
  });
});

function editmode(editBox) {
  console.log(editBox);
  if (action) {
    editBox.disabled = false;
  } else {
    const commentId = editBox.previousElementSibling.value;
    let editedVal = editBox.value;
    let data = { commentId: commentId, content: editedVal };
    $.post('/community/post/comment/edit', data).done(function () {
      console.log('edit success');
      editBox.disabled = true;
    });
    index++;
  }
  action = !action;
}

// 삭제 확인
$(function () {
  $('a.confirmDeletion').click(function () {
    if (!confirm('삭제하시겠습니까?')) return false;
  });
});

//신고확인
$(function () {
  $('a.confirmReport').click(function () {
    if (!confirm('신고하시겠습니까?')) return false;
  });
});
