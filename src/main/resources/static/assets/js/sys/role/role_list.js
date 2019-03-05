var zTreeObj;
var setting = {
  view: {
    showIcon: false, //设置是否显示节点图标
    showLine: true, //设置是否显示节点与节点之间的连线
    showTitle: true //设置是否显示节点的title提示信息
  },
  data: {
    simpleData: {
      enable: true, //设置是否启用简单数据格式（zTree支持标准数据格式跟简单数据格式，上面例子中是标准数据格式）
      idKey: "id", //设置启用简单数据格式时id对应的属性名称
      pidKey: "pid", //设置启用简单数据格式时parentId对应的属性名称,ztree根据id及pid层级关系构建树结构
      rootPid: 0
    }
  },
  check: {
    enable: true,  //设置是否显示checkbox复选框
    chkStyle: "checkbox",
    chkboxType: { "Y": "p", "N": "p" }
  },
  callback: {
    // onClick: setPidInfo,  //定义节点单击事件回调函数
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
  //初始化表格,动态从服务器加载数据
  $("#table_list").bootstrapTable({
    //使用get请求到服务器获取数据
    method: "POST",
    //必须设置，不然request.getParameter获取不到请求参数
    contentType: "application/x-www-form-urlencoded",
    //获取数据的Servlet地址
    url: "/systemRoleController/listRoles",
    //表格显示条纹
    striped: true,
    singleSelect: true,
    clickToSelect: true,
    //启动分页
    pagination: true,
    //每页显示的记录数
    pageSize: 10,
    //当前第几页
    pageNumber: 1,
    //记录数可选列表
    pageList: [5, 10, 15, 20, 25],
    //表示服务端请求
    sidePagination: "server",
    //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
    //设置为limit可以获取limit, offset, search, sort, order
    queryParamsType: "undefined",
    queryParams: function queryParams(params) {
      var param = {
        pageNumber: params.pageNumber,
        pageSize: params.pageSize,
        sortName: params.sortName,
        sortOrder: params.sortOrder
      };
      return param;
    },
    //json数据解析
    responseHandler: function (res) {
      return {
        "rows": res.rows,
        "total": res.total
      };
    },
    formatNoMatches: function () {
      return "没有相关的匹配结果";
    },
    formatLoadingMessage: function () {
      return "请稍等，正在加载中。。。";
    },
    //数据列
    columns: [{
      checkbox: true
    }, {
      title: "id",
      field: "id",
      visible: false
    }, {
      title: "角色编号",
      field: "code",
      sortable: true
    }, {
      title: "角色名",
      field: "roleName"
    }, {
      title: "排序",
      field: "num",
      sortable: true
    }, {
      title: "备注",
      field: "tips"
    }, {
      title: "更新时间",
      field: "updateDate",
      formatter: function (value, row, index) {
        return changeDateFormat(value)
      },
      sortable: true
    }, {
      title: "操作",
      field: "empty",
      formatter: function (value, row, index) {
        var operateHtml = '<button class="btn btn-primary btn-xs" type="button" onclick="edit(\''
            + row.id
            + '\')"><i class="fa fa-edit"></i>&nbsp;修改</button> &nbsp;';
        operateHtml = operateHtml
            + '<button class="btn btn-danger btn-xs" type="button" onclick="del(\''
            + row.id + '\')"><i class="fa fa-remove"></i>&nbsp;删除</button>';
        operateHtml = operateHtml
            + '<button class="btn btn-info btn-xs" type="button" onclick="related_menu(\''
            + row.id + '\')"><i class="fa fa-arrows"></i>&nbsp;关联菜单</button>';
        return operateHtml;
      }
    }],
    onDblClickRow: function (row) {
      layer.open({
        type: 2,
        title: '角色信息',
        shadeClose: true,
        shade: false,
        area: ['793px', '300px'],
        content: '/systemRoleController/info/' + row.id,
        end: function (index) {
        }
      });
    },
    onClickRow: function (row) {
      $.ajax({
        type: "POST",
        dataType: "json",
        url: "/systemMenuController/getMenuIdByRoleId",
        data: { roleId : row.id },
        success: function (data) {
          $.post("/systemMenuController/selectMenuTreeList", {}, function (menus) {  //id=3是初始输入，确立根节点的id=3
            zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, eval(menus));
            for (var item in data.obj) {
              zTreeObj.checkNode(zTreeObj.getNodeByParam("id", data.obj[item]), true, true);
            }
          });
        }
      });
    }
  });
});

function relatedMenus() {
  //获取选中数据
  var selections = $("#table_list").bootstrapTable('getSelections');

  if (selections.length > 1) {
    layer.msg("只能选择一行关联菜单");
    return;
  }
  if (selections.length <= 0) {
    layer.msg("请选择一个角色关联菜单");
    return;
  }
  var menuIds = [];
  checked_list = zTreeObj.getCheckedNodes(true);
  for (var item in checked_list) {
    menuIds.push(checked_list[item].id);
  }
  $.ajax({
    type: "POST",
    dataType: "json",
    url: "/systemUserController/userSimpleList",
    data: {
      roleId: selections[0].id,
      menuIds: menuIds
    },
    success: function (data) {
      layer.msg(data.msg);
    }
  });
}

function edit(id) {
  layer.open({
    type: 2,
    title: '修改角色',
    shadeClose: true,
    shade: false,
    area: ['793px', '300px'],
    content: '/systemRoleController/edit/' + id,
    end: function (index) {
      $('#table_list').bootstrapTable("refresh");
    }
  });
}

function add() {
  layer.open({
    type: 2,
    title: '添加角色',
    shadeClose: true,
    shade: false,
    area: ['793px', '300px'],
    content: '/systemRoleController/add',
    end: function (index) {
      $('#table_list').bootstrapTable("refresh");
    }
  });
}

function del(id) {
  layer.confirm('确定删除吗?', {icon: 3, title: '提示'}, function (index) {
    $.ajax({
      type: "POST",
      dataType: "json",
      url: "/systemRoleController/delete",
      data: {id : id},
      success: function (msg) {
        layer.msg(msg.message, {time: 2000}, function () {
          $('#table_list').bootstrapTable("refresh");
          layer.close(index);
        });
      }
    });
  });
}
