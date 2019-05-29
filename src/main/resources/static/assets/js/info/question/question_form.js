$(function () {
  //外部js调用
  laydate({
    elem: '#startDate',
    event: 'focus'
  });
  laydate({
    elem: '#endDate',
    event: 'focus'
  });
  $("#frm").validate({
    rules: {
      questionCode: {
        required: true,
        maxlength: 50
      },
      questionName: {
        required: true,
        maxlength: 300
      },
      startDate: {
        date: true,
        required: true
      },
      endDate: {
        date: true,
        required: true
      },
      questionDescribed: {
        maxlength: 100
      }
    },
    messages: {},
    submitHandler: function (form) {
      var loadIndex = layer.load(0, {shade: [0.3, '#C6C2B6']});
      var url = "/infoQuestionController/addQuestion";
      if ($('#id').val() != "") {
        var url = "/infoQuestionController/editQuestion";
      }
      $.ajax({
        type: "POST",
        dataType: "json",
        url: url,
        data: $(form).serialize(),
        success: function (data) {
          data = $.parseJSON(data);
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
