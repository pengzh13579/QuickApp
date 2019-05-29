$(function () {
  $("#frm").validate({
    rules: {
      itemId: {
        required: true
      },
      optionName: {
        required: true,
        maxlength: 100
      },
      optionCd: {
        required: true
      }
    },
    messages: {},
    submitHandler: function (form) {
      var loadIndex = layer.load(0, {shade: [0.3, '#C6C2B6']});
      var url = "/infoQuestionOptionController/addQuestionOption";
      if ($('#id').val() != "") {
        var url = "/infoQuestionOptionController/editQuestionOption";
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
