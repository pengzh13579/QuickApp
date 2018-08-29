var zTreeObj;
var setting = {
  view: {
    selectedMulti: true, //设置是否能够同时选中多个节点
    showIcon: true, //设置是否显示节点图标
    showLine: true, //设置是否显示节点与节点之间的连线
    showTitle: true, //设置是否显示节点的title提示信息
  },
  data: {
    simpleData: {
      enable: true, //设置是否启用简单数据格式（zTree支持标准数据格式跟简单数据格式，上面例子中是标准数据格式）
      idKey: "id", //设置启用简单数据格式时id对应的属性名称
      pidKey: "pId", //设置启用简单数据格式时parentId对应的属性名称,ztree根据id及pid层级关系构建树结构
      rootPid: 0
    }
  },
  check: {
    enable: true  //设置是否显示checkbox复选框
  },
  callback: {
    // onClick: onClick,  //定义节点单击事件回调函数
    // onRightClick: OnRightClick, //定义节点右键单击事件回调函数
    // beforeRename: beforeRename, //定义节点重新编辑成功前回调函数，一般用于节点编辑时判断输入的节点名称是否合法
    // onDblClick: onDblClick, //定义节点双击事件回调函数
    // onCheck: onCheck  //定义节点复选框选中或取消选中事件的回调函数
  },
  async: {
    enable: true,   //设置启用异步加载
    type: "get",   //异步加载类型:post和get
    contentType: "application/json", //定义ajax提交参数的参数类型，一般为json格式
    url: "/systemMenuController/selectMenuTreeList",  //定义数据请求路径
    autoParam: ["id=id", "name=name"] //定义提交时参数的名称，=号前面标识节点属性，后面标识提交时json数据中参数的名称
  }
};

$(function () {
  $.post("/systemMenuController/selectMenuTreeList", {}, function (data) {  //id=3是初始输入，确立根节点的id=3
    zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, eval(data));
  });
  $("#frm").validate({
    rules: {
      code: {
        required: true,
        minlength: 4,
        maxlength: 50
      },
      name: {
        required: true,
        minlength: 4,
        maxlength: 50
      },
      num: {
        required: true
      }
    },
    messages: {},
    submitHandler: function (form) {
      debugger;
      $('#pId').val(zTreeObj.getSelectedNodes()[0].id);
      var url = "/systemMenuController/addMenu";
      if ($('#id').val() != "") {
        var url = "/systemUserController/editUser";
      }
      $.ajax({
        type: "POST",
        dataType: "json",
        url: url,
        data: $(form).serialize(),
        success: function (data) {
          debugger;
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
