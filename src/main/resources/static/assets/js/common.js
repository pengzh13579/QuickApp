var dictionary;

$.fn.serializeObject = function () {
  var o = {};
  var a = this.serializeArray();
  $.each(a, function () {
    if (o[this.name] !== undefined) {
      if (!o[this.name].push) {
        o[this.name] = [o[this.name]];
      }
      o[this.name].push(this.value || '');
    } else {
      o[this.name] = this.value || '';
    }
  });
  return o;
};

function bindFiledToControl(data) {
  for (var key in data) {
    $('#' + key).val(data[key]).change();
  }
}

function openUserSimpleList(title, url, jsonStr) {
  layer.open({
    type: 2,
    title: title,
    shadeClose: true,
    shade: false,
    area: ['900px', '500px'],
    btn: ['确定', '关闭'],
    content: "/systemUserController/userSimpleList",
    yes: function (index) {
      var res = window["layui-layer-iframe" + index].callbackdata();
      jsonStr["userIds"] = res["userIds"];
      $.ajax({
        type: "POST",
        dataType: "json",
        url: url,
        data: jsonStr,
        success: function (data) {
          layer.msg(data.msg, {time: 1000}, function () {
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            if (data.success) {
              parentCallbackFunc();
              parent.layer.close(index);
            }
          });
        }
      });
      layer.close(index);
    },
    cancel: function () {
      //右上角关闭回调
    }
  });
}

//修改——转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
  if (cellval != null) {
    var date = new Date(parseInt(cellval));
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1)
        : date.getMonth() + 1) + '-';
    var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
    return Y + M + D;
  }
}

function getBootstrapTableIds(tableId) {

    var ids = [];
    var select_list = $("#" + tableId).bootstrapTable('getSelections');
    for (var item in select_list) {
        ids.push(select_list[item].id);
    }
    return ids;
}

function loadSelectData(ele) {
    var code = $(ele).attr("dictionary-code");
    if(typeof(code) == "undefined"){
        return;
    }
    if(dictionary == undefined){
        dictionary = {};
    }
    var nameOpt = "";
    if(!dictionary.hasOwnProperty(code)){
        $.ajax({
            url: "/fixedDictionaryController/getDictionarys/" + code,
            type: "get",
            async: false,
            success: function(data){
                if(data != ""){
                    data = JSON.parse(data);
                    dictionary[code] = data.list;
                    if(data.empty == 1){
                        nameOpt += "<option value='' selected='selected'>--请选择--</option>";
                    }
                }
            },
            error: function(){}
        });
    }
    if(dictionary.hasOwnProperty(code)) {
        for (var i = 0; i < dictionary[code].length; i++) {
            if ($(ele).attr("value") == dictionary[code][i].dictionaryValue) {
                nameOpt += "<option value='" + dictionary[code][i].dictionaryValue + "' selected='selected'>" + dictionary[code][i].dictionaryName + "</option>";
            } else {
                nameOpt += "<option value='" + dictionary[code][i].dictionaryValue + "' >" + dictionary[code][i].dictionaryName + "</option>"
            }
        }
        $(ele).html(nameOpt);
    }
}
