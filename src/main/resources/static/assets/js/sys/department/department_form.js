$(function () {
  $("#frm").validate({
    rules: {
      code: {
        required: true,
        maxlength: 10
      },
      departCode: {
        required: true,
        maxlength: 50
      },
      pcode: {
        required: true,
        maxlength: 10
      },
      departSimpleName: {
        required: true,
        maxlength: 25
      },
      departFullName: {
        required: true,
        maxlength: 100
      },
      departLeader: {
        required: true,
        maxlength: 20
      },
      departTel: {
        required: true,
        maxlength: 20
      },
      tips: {
        maxlength: 255
      },
      temp: {
        maxlength: 50
      },
      num: {
        required: true
      }
    },
    messages: {},
    submitHandler: function (form) {
      var url = "/systemDepartmentController/addDepartment";
      if ($('#id').val() != "") {
        var url = "/systemDepartmentController/editDepartment";
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
