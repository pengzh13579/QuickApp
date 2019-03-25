$(function () {
  $("#frm").validate({
    rules: {
      accesstoken: {
        required: true,
        maxlength: 300
      },
      openid: {
        required: true,
        maxlength: 300
      },
      nickname: {
        maxlength: 100
      },
      headimg: {
        maxlength: 300
      },
      createtime: {
        maxlength: 20
      },
      provice: {
        maxlength: 50
      },
      city: {
        maxlength: 50
      },
      money: {
      }
    },
    messages: {},
    submitHandler: function (form) {
      var url = "/wxUserController/addUser";
      if ($('#id').val() != "") {
        var url = "/wxUserController/editUser";
      }
      $.ajax({
        type: "POST",
        dataType: "json",
        url: url,
        data: $(form).serialize(),
        success: function (data) {
          layer.msg(data.msg, {time: 2000}, function () {
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            if (data.success) {
              parent.layer.close(index);
            }
          });
        }
      });
    }
  });
});
