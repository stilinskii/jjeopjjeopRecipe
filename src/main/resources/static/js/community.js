$(function () {
  if ($('#content').length) {
    ClassicEditor.create(document.querySelector('#content'), {
      toolbar: [
        'heading',
        'bold',
        'italic',
        'undo',
        'redo',
        'numberedList',
        'bulletedList',
      ],
    }).catch((error) => {
      console.log(error);
    });
  }
});

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

// form (글쓰기) 카테고리에따라 양식 다르게 보이기
const categorySelect = document.querySelector('select.category');
const recipeSearchBtn = document.querySelector('.recipeSearch');
categorySelect.addEventListener('change', showRecipeSearchBtn);
function showRecipeSearchBtn() {
  if (categorySelect.value == 1) {
    recipeSearchBtn.classList.remove('d-none');
  } else {
    recipeSearchBtn.classList.add('d-none');
  }
}

showRecipeSearchBtn();
