$(function () {
  //初始化表格,动态从服务器加载数据
  $("#table_list").bootstrapTable({
    //使用get请求到服务器获取数据
    method: "POST",
    //必须设置，不然request.getParameter获取不到请求参数
    contentType: "application/x-www-form-urlencoded",
    //获取数据的Servlet地址
    url: "/systemMenuController/listMenus",
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
    toolbar: '#toolbar',
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
        sortOrder: params.sortOrder,
        code: $("#codeSearch").val(),
        menuRealName: $("#menuRealNameSearch").val()
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
    }, {
      title: "操作",
      field: "empty",
      formatter: function (value, row, index) {
        var operateHtml = '<button class="btn btn-primary btn-xs" type="button" onclick="edit(\''
            + row.code
            + '\')"><i class="fa fa-edit"></i>&nbsp;修改</button> &nbsp;';
        operateHtml = operateHtml
            + '<button class="btn btn-danger btn-xs" type="button" onclick="del(\''
            + row.id + '\')"><i class="fa fa-remove"></i>&nbsp;删除</button>';
        return operateHtml;
      }
    }]
  });
  $("#searchBtn").click(function () {
    $('#table_list').bootstrapTable('refresh');
  });
});

function edit(code) {
  layer.open({
    type: 2,
    title: '修改菜单',
    shadeClose: true,
    shade: false,
    area: ['500px', '610px'],
    content: '/systemMenuController/edit/' + code,
    end: function (index) {
      $('#table_list').bootstrapTable("refresh");
    }
  });
  $("#searchbtn").click(function () {
      $('#table_list').bootstrapTable('refresh');
  });
}

function add() {
  layer.open({
    type: 2,
    title: '添加菜单',
    shadeClose: true,
    shade: false,
    area: ['500px', '610px'],
    content: '/systemMenuController/add',
    end: function (index) {
      $('#table_list').bootstrapTable("refresh");
    }
  });
}

function del(code) {
  layer.confirm('确定删除吗?', {icon: 3, title: '提示'}, function (index) {
    $.ajax({
      type: "POST",
      dataType: "json",
      url: "/systemMenuController/delete",
      data: {code : code},
      success: function (data) {
        layer.msg(data.message);
        $('#table_list').bootstrapTable("refresh");
      }
    });
  });
}

