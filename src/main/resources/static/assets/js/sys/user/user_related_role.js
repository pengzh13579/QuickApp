$(function () {
  //初始化表格,动态从服务器加载数据
  $("#role_table_list").bootstrapTable({
    //使用get请求到服务器获取数据
    method: "POST",
    //必须设置，不然request.getParameter获取不到请求参数
    contentType: "application/x-www-form-urlencoded",
    //获取数据的Servlet地址
    url: "/systemRoleController/listRoles",
    //表格显示条纹
    striped: true,
    //启动分页
    pagination: true,
    //每页显示的记录数
    pageSize: 10,
    //当前第几页
    pageNumber: 1,
    //记录数可选列表
    pageList: [5, 10, 15, 20, 25],
    clickToSelect: true,
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
    }]
  });
});

function relatedRole() {
  var loadIndex = layer.load(0, {shade: [0.3, '#C6C2B6']});
  //使用getSelections即可获得，row是json格式的数据
  var rows = $.map($('#role_table_list').bootstrapTable('getSelections'),
      function (row) {
        return row;
      });
  if (rows.length <= 0) {
    layer.msg("请至少选择一条数据");
    return;
  }
  var roleIds = "";
  for (var i = 0; i < rows.length; i++) {
    roleIds += rows[i].id + ",";
  }
  $.ajax({
    type: "POST",
    dataType: "json",
    url: "/systemUserController/relatedRoleInfo",
    data: {
      roleIds: roleIds.substring(0, roleIds.length - 1),
      userName: $('#userName').val()
    },
    success: function (data) {
      var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
      if (data.success) {
        parent.layer.close(index);
      }
    },
    complete: function(){
      parent.layer.close(loadIndex);
    }
  });
}
