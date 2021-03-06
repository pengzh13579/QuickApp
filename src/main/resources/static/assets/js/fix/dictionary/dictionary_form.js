$(function () {
  $("#frm").validate({
    rules: {
      pid: {
        required: true
      },
      dictionaryCode: {
        required: true,
        maxlength: 50
      },
      dictionaryName: {
        required: true,
        maxlength: 50
      },
      dictionaryValue: {
      },
      sort: {
        required: true
      },
      described: {
        maxlength: 50
      }
    },
    messages: {},
    submitHandler: function (form) {
      var loadIndex = layer.load(0, {shade: [0.3, '#C6C2B6']});
      var url = "/fixedDictionaryController/addDictionary";
      if ($('#id').val() != "") {
        var url = "/fixedDictionaryController/editDictionary";
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
