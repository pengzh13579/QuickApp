$(function () {
  $("#frm").validate({
    rules: {
      code: {
        required: true,
        minlength: 1,
        maxlength: 20
      },
      roleName: {
        required: true,
        minlength: 1,
        maxlength: 25
      },
      num: {
        required: true
      },
      tips: {
      }
    },
    messages: {},
    submitHandler: function (form) {
      var url = "/systemRoleController/addRole";
      if ($('#id').val() != "") {
        var url = "/systemRoleController/editRole";
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
