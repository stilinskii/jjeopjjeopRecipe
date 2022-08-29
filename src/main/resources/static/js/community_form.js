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

//레시피 조회
const searchBtn = document.querySelector('.searchBtn');
const searchInput = document.querySelector('.searchInput');

searchBtn.addEventListener('click', searchRecipe);

function searchRecipe() {
  console.log('access recipe');
  let searchKeyword = searchInput.value;
  let data = { searchKey: searchKeyword };

  $.post('/community/form/searchRecipe', data).done(function (fragment) {
    $('.recipe-box').replaceWith(fragment);
  });

  $(document).ajaxComplete(function () {
    selectRecipeProcess();
  });
}

//레시피 선택
function selectRecipeProcess() {
  const selectBtns = document.querySelectorAll('.recipe-select');
  const selectValueInput = document.querySelector('.select-value');
  console.log(selectBtns);
  selectBtns.forEach((selectbtn) => {
    selectbtn.addEventListener('click', selectRecipe);
  });

  function selectRecipe() {
    selectValueInput.value = this.previousElementSibling.value;
  }
}
