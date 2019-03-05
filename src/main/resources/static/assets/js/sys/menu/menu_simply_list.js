$(function () {
  //初始化表格,动态从服务器加载数据
  $("#menu_simple_list").bootstrapTable({
    //使用get请求到服务器获取数据
    method: "POST",
    //必须设置，不然request.getParameter获取不到请求参数
    contentType: "application/x-www-form-urlencoded",
    //获取数据的Servlet地址
    url: "/systemMenuController/listMenus",
    //表格显示条纹
    striped: true,
    singleSelect: false,
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
    //工具按钮用哪个容器
    toolbar: '#toolbar',
    //是否启用排序
    sortable: true,
    //排序方式
    sortOrder: "asc",
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
    //数据列
    columns: [{
      title: "ID",
      field: "id",
      visible: false
    }, {
      title: "菜单编号",
      field: "code"
    }, {
      title: "菜单名称",
      field: "menuRealName"
    }, {
      title: "资源URL",
      field: "url"
    }, {
      title: "排序",
      field: "num",
      sortable: true
    }, {
      title: "图标",
      field: "icon"
    }, {
      title: "是否菜单",
      sortable: true,
      field: "menuFlag",
      formatter: function (value, row, index) {
        if (value == 0) {
          return '<span class="label label-danger">否</span>';
        } else if (value == 1) {
          return '<span class="label label-info">是</span>';
        }
      }
    }, {
      title: "是否展开",
      sortable: true,
      field: "openFlag",
      formatter: function (value, row, index) {
        if (value == 0) {
          return '<span class="label label-danger">否</span>';
        } else if (value == 1) {
          return '<span class="label label-info">是</span>';
        }
      }
    }]
  });
});

var callbackdata = function () {
  var menuIds = [];
  select_list = $("#menu_simple_list").bootstrapTable('getSelections');
  for (var item in select_list) {
    menuIds.push(select_list[item].id);
  }
  var data = {
    menuIds: menuIds
  };
  return data;
}

