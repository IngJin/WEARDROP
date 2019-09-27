$(function() {
   $('#attach-file').on('change',function(){
      $('#file-name').html(this.files[0].name);
      $('#file-name').css('padding-right', '15px');
      $('#delete-file').css('display','inline-block');
      $('#select_img').css('display', 'block');
   });
   $('#delete-file').on('click',function(){
      $('#attach-file').val('');
      $('#file-name').html('');
      $('#file-name').css('padding-right', '0px');
      $('#delete-file').css('display','none');
      $('#select_img').css('display', 'none');
   });
}); /* 첨부파일 이름 표시 */

function necessary() {
	   var need = true;
	   $('.need').each(function(){
	      if($(this).val()==''){
	         alert($(this).attr('title') + '을 입력하세요!'); /* title을 입력하세요 */
	         $(this).focus(); /* 빈칸 커서 이동*/
	         need = false;
	         return need;
	      }
	   });
	   return need;
	} // necessary()