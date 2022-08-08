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
  $.post('/community/like', value).done(function (fragment) {
    $('#like-btn').replaceWith(fragment);
  });
  alert('좋아용완료');
}
