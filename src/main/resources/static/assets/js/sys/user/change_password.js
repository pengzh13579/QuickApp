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
    submitHandler: function (form) {
      var loadIndex = layer.load(0, {shade: [0.3, '#C6C2B6']});
      if ($('#newPwd').val() == $('#againPwd').val()) {
        $.ajax({
          type: "POST",
          dataType: "json",
          url: "/systemUserController/changePassword",
          data: $(form).serialize(),
          success: function (data) {
            layer.msg(data.msg);
            if (data.success) {
              window.location.href = "/login";
            }
          },
          complete: function(){
            parent.layer.close(loadIndex);
          }
        });
      } else {
      }
    }
  });
});
