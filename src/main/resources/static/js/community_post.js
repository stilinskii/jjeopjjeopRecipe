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
const editBtn = document.querySelector('.edit-btn');
const editBox =
  editBtn.parentElement.previousElementSibling.previousElementSibling;
// let postIdVal = $('#postId').val();
console.dir(editBtn);

editBtn.addEventListener('click', editmode);

let action = true;
function editmode() {
  if (action) {
    editBox.disabled = false;
  } else {
    const commentId = editBox.previousElementSibling.value;
    let editedVal = editBox.value;
    console.log(editedVal);
    let data = { commentId: commentId, content: editedVal };
    $.post('/community/post/comment/edit', data).done(function () {
      console.log('edit success');
      editBox.disabled = true;
    });
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
