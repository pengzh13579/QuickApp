$(function () {
  //初始化表格,动态从服务器加载数据
  $("#user_simple_list").bootstrapTable({
    //使用get请求到服务器获取数据
    method: "POST",
    //必须设置，不然request.getParameter获取不到请求参数
    contentType: "application/x-www-form-urlencoded",
    //获取数据的Servlet地址
    url: "/systemUserController/listUsers",
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
        sortOrder: params.sortOrder,
        disFlag: $("#disFlagSearch").val(),
        userName: $("#userNameSearch").val(),
        realName: $("#realNameSearch").val(),
        sex: $("#sexSearch").val(),
        createDateStart: $("#createDateStart").val(),
        createDateEnd: $("#createDateEnd").val()
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
      title: "ID",
      field: "id",
      visible: false
    }, {
      title: "登录名",
      field: "userName"
    }, {
      title: "姓名",
      field: "realName"
    }, {
      title: "性别",
      field: "sex",
      formatter: function (value, row, index) {
        if (value == '1') {
          return '<span class="label label-warning">女</span>';
        }
        return '<span class="label label-primary">男</span>';
      }
    }, {
      title: "出生日期",
      field: "birthday",
      formatter: function (value, row, index) {
        return changeDateFormat(value)
      }
    }, {
      title: "所属角色",
      field: "roleName"
    }, {
      title: "状态",
      sortable: true,
      field: "disFlag",
      formatter: function (value, row, index) {
        if (value == '0') {
          return '<span class="label label-info">激活</span>';
        }
        if (value == '2') {
          return '<span class="label label-danger">锁定</span>';
        }
        return '<span class="label label-danger">删除</span>';
      }
    }, {
      title: "创建时间",
      field: "createDate",
      formatter: function (value, row, index) {
        return changeDateFormat(value)
      },
      sortable: true
    }, {
      title: "更新时间",
      field: "updateDate",
      formatter: function (value, row, index) {
        return changeDateFormat(value)
      },
      sortable: true
    }],
    onDblClickRow: function (row) {
      layer.open({
        type: 2,
        title: '用户信息',
        shadeClose: true,
        shade: false,
        area: ['893px', '600px'],
        content: '/systemUserController/userInfo/' + row.userName,
        end: function (index) {
        }
      });
    }
  });
});

var callbackdata = function () {
  var userIds = [];
  select_list = $("#user_simple_list").bootstrapTable('getSelections');
  for (var item in select_list) {
    userIds.push(select_list[item].id);
  }
  var data = {
    userIds: userIds
  };
  return data;
}

