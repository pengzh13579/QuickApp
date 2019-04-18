$(function () {
  $("#frm").validate({
    rules: {
      ID: {
        required: true
      },
      AREAId: {
        required: true,
        maxlength: 20
      },
      PAGETitle: {
        required: true,
        maxlength: 255
      },
      PAGEContent: {
        required: true,
        maxlength: 65535
      },
      RELEASEDate: {
        required: true
      },
      INDUSTRYInfo: {
        maxlength: 100
      }
    },
    messages: {},
    submitHandler: function (form) {
      var url = "/infoPageController/addPage";
      if ($('#id').val() != "") {
        var url = "/infoPageController/editPage";
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
