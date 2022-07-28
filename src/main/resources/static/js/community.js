$(function () {
  if ($('#content').length) {
    ClassicEditor.create(document.querySelector('#content')).catch((error) => {
      console.log(error);
    });
  }
});
