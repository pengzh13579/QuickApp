$(function () {
  $("#frm").validate({
    rules: {
      scheduleName: {
        required: true,
        maxlength: 50
      },
      scheduleNameCn: {
        required: true,
        maxlength: 50
      },
      scheduleCron: {
        required: true,
        maxlength: 50
      },
      scheduleCode: {
        maxlength: 50
      },
      scheduleParam: {
        required: true,
        maxlength: 255
      }
    },
    messages: {},
    submitHandler: function (form) {
      var loadIndex = layer.load(0, {shade: [0.3, '#C6C2B6']});
      var url = "/systemScheduleController/addSchedule";
      if ($('#id').val() != "") {
        var url = "/systemScheduleController/editSchedule";
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
        },
        complete: function(){
          parent.layer.close(loadIndex);
        }
      });
    }
  });
});
