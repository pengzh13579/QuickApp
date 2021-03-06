$(function () {
  $("#frm").validate({
    rules: {
      itemId: {
        required: true
      },
      answerInfo: {
        required: true,
        maxlength: 500
      },
      disFlag: {
        required: true
      },
      createDate: {
        required: true
      },
      updateDate: {
        required: true
      },
      createUser: {
        required: true,
        maxlength: 20
      },
      updateUser: {
        required: true,
        maxlength: 20
      }
    },
    messages: {},
    submitHandler: function (form) {
      var loadIndex = layer.load(0, {shade: [0.3, '#C6C2B6']});
      var url = "/infoQuestionAnswerController/addQuestionAnswer";
      if ($('#id').val() != "") {
        var url = "/infoQuestionAnswerController/editQuestionAnswer";
      }
      $.ajax({
        type: "POST",
        dataType: "json",
        url: url,
        data: $(form).serialize(),
        success: function (data) {
          // 解决返回值带<pre style="word-wrap: break-word; white-space: pre-wrap;">的问题
          data = $.parseJSON(data.replace(/<.*?>/ig, ""));
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
