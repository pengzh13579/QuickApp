$(function () {
  var itemNum = 0;
  //外部js调用
  laydate({
    elem: '#birthday', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
    event: 'focus' //响应事件。如果没有传入event，则按照默认的click
  });
  $("#add_contact_btn").click(function () {
    var item = "<div  id=\"contact_div[" + itemNum
        + "]\" class=\"form-group\">\n" +
        "           <label class=\"col-sm-1 control-label\"></label>" +
        "           <label class=\"col-sm-2 control-label\" style=\"margin-top:-7px\">\n"
        +
        "               <select id=\"contactTypeSelect" + itemNum
        + "\" class=\"form-control\" onchange=\"contact_change(" + itemNum
        + ")\">\n" +
        "                   <option value=\"1\"  >邮箱</option>\n" +
        "                   <option value=\"2\"  >电话</option>\n" +
        "                   <option value=\"3\"  >QQ</option>\n" +
        "                   <option value=\"4\"  >微信</option>\n" +
        "                   <option value=\"5\"  >地址</option>\n" +
        "               </select>\n" +
        "           </label>\n" +
        "           <div class=\"col-sm-6\">\n" +
        "               <input type=\"hidden\" id=\"contactType" + itemNum
        + "\" name=\"contacts[" + itemNum
        + "].contactType\" class=\"form-control\" value=\"1\" />\n" +
        "               <input type=\"email\" id=\"contactInfo" + itemNum
        + "\" name=\"contacts[" + itemNum
        + "].contactInfo\" class=\"form-control\" value=\"\"/>\n" +
        "           </div>\n"
        + "         <div class=\"col-sm-2\">\n"
        + "              <button id=\"contact_del_btn" + itemNum
        + "\" class=\"btn btn-primary\" type=\"button\" onclick=\"del_contact(this)\">删除</button>\n"
        + "         </div>" +
        "       </div>"
    itemNum = itemNum + 1;
    $("#control_info").after(item);
  });
  $("#frm").validate({
    rules: {
      userName: {
        required: true,
        minlength: 4,
        maxlength: 10
      },
      nickName: {
        required: true,
        minlength: 4,
        maxlength: 10
      },
      sex: {
        required: true
      },
      birthday: {
        date: true,
        required: true
      }
    },
    messages: {},
    submitHandler: function (form) {
      var url = "/systemUserController/addUser";
      if ($('#id').val() != "") {
        var url = "/systemUserController/editUser";
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
  $("#provinceId").change(function () {
    $.get("/provinceAreaController/getCityByProvinceId/" + $(
        "#provinceId").val(), function (data) {
      if (data.success) {
        var result = "<option>选择城市</option>";
        $.each(data.obj, function (n, value) {
          result += "<option value='" + value.cityId + "'>" + value.city
              + "</option>";
        });
        $("#cityId").html('');
        $("#cityId").append(result);
      }
    }, "json");
  });
  $("#cityId").change(function () {
    $.get("/provinceAreaController/getAreaByCityId/" + $("#cityId").val(),
        function (data) {
          if (data.success) {
            var result = "<option>选择区域</option>";
            $.each(data.obj, function (n, value) {
              result += "<option value='" + value.areaId + "'>" + value.area
                  + "</option>";
            });
            $("#areaId").html('');
            $("#areaId").append(result);
          }
        }, "json");
  });
  $("#provinceIdAddr").change(function () {
    $.get("/provinceAreaController/getCityByProvinceId/" + $(
        "#provinceIdAddr").val(), function (data) {
      if (data.success) {
        var result = "<option>选择城市</option>";
        $.each(data.obj, function (n, value) {
          result += "<option value='" + value.cityId + "'>" + value.city
              + "</option>";
        });
        $("#cityIdAddr").html('');
        $("#cityIdAddr").append(result);
      }
    }, "json");
  });
  $("#cityIdAddr").change(function () {
    $.get("/provinceAreaController/getAreaByCityId/" + $("#cityIdAddr").val(),
        function (data) {
          if (data.success) {
            var result = "<option>选择区域</option>";
            $.each(data.obj, function (n, value) {
              result += "<option value='" + value.areaId + "'>" + value.area
                  + "</option>";
            });
            $("#areaIdAddr").html('');
            $("#areaIdAddr").append(result);
          }
        }, "json");
  });
});

function contact_change(index) {
  $('#contactType' + index).val($('#contactTypeSelect' + index).val());
  $('#contactInfo' + index).attr('type', 'text');
  switch ($('#contactType' + index).val()) {
    case '1':
      $('#contactInfo' + index).attr('type', 'email');
      break;
    case '2':
    case '3':
  }
}

function del_contact(tag) {
  $('#' + $(tag).attr('id').replace('del_btn', 'div')).remove();
}
