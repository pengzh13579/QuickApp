$.fn.serializeObject = function()
{
  var o = {};
  var a = this.serializeArray();
  $.each(a, function() {
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

function bindFiledToControl(data){
  for(var key in data){
    $('#' + key).val(data[key]).change();
  }
}


function openUserSimpleList(title, url, jsonStr){
  layer.open({
    type: 2,
    title: title,
    shadeClose: true,
    shade: false,
    area: ['893px', '600px'],
    btn: ['确定','关闭'],
    content: "/systemUserController/userSimpleList",
    yes: function(index){
      var res = window["layui-layer-iframe" + index].callbackdata();
      jsonStr["userIds"]=res["userIds"];
      $.ajax({
        type: "POST",
        dataType: "json",
        url: url,
        data: jsonStr,
        success: function (data) {
          layer.msg(data.msg, {time: 2000}, function () {
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            if (data.success) {
              parent.layer.close(index);
            }
          });
        }
      });
      layer.close(index);
    },
    cancel: function(){
      //右上角关闭回调
    }
  });
}

