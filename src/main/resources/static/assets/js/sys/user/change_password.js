$(function () {
  $("#frm").validate({
    rules: {
      oldPwd: {
        required: true,
        minlength: 6,
        maxlength: 20
      },
      newPwd: {
        required: true,
        minlength: 6,
        maxlength: 20
      },
      againPwd: {
        required: true,
        minlength: 6,
        maxlength: 20
      }
    },
    messages: {},
    submitHandler:function(form){
      debugger;
      if($('#newPwd').val() == $('#againPwd').val()){
        $.ajax({
          type: "POST",
          dataType: "json",
          url: "/systemUserController/changePassword",
          data: $(form).serialize(),
          success: function(data){
            alert(data.msg);
            if(data.success){
              window.location.href="/login";
            }
          }
        });
      }else{

      }
    }
  });
});
