$(function () {
  $("#frm").validate({
    rules: {
      questionId: {
        required: true
      },
      itemQuestion: {
        required: true,
        maxlength: 300
      },
      itemType: {
        required: true,
        maxlength: 1
      },
      itemSort: {
        required: true,
        maxlength: 3
      },
      itemMore: {
        required: true,
        maxlength: 1
      }
    },
    messages: {},
    submitHandler: function (form) {
      var loadIndex = layer.load(0, {shade: [0.3, '#C6C2B6']});
      var url = "/infoQuestionItemController/addQuestionItem";
      if ($('#id').val() != "") {
        var url = "/infoQuestionItemController/editQuestionItem";
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
