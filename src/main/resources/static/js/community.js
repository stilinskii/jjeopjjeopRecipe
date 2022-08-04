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
