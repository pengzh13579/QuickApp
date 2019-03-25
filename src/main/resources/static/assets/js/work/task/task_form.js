$(function () {
  $("#frm").validate({
    rules: {
      taskName: {
        required: true,
        maxlength: 50
      },
      taskContent: {
        maxlength: 255
      },
      taskStatus: {
        required: true
      }
    },
    messages: {},
    submitHandler: function (form) {
      var url = "/workTaskController/addTask";
      if ($('#id').val() != "") {
        var url = "/workTaskController/editTask";
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
