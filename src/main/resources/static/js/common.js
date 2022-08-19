// 삭제 확인
$(function() {
     $('a.confirmDeletion').click(function(){
      if(!confirm('삭제확인')) return false;});
});