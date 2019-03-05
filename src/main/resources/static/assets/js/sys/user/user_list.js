$(function () {
  //外部js调用
  laydate({
    elem: '#createDateStart', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
    event: 'focus' //响应事件。如果没有传入event，则按照默认的click
  });
  //外部js调用
  laydate({
    elem: '#createDateEnd', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
    event: 'focus' //响应事件。如果没有传入event，则按照默认的click
  });
  //初始化表格,动态从服务器加载数据
  $("#table_list").bootstrapTable({
    //使用get请求到服务器获取数据
    method: "POST",
    //必须设置，不然request.getParameter获取不到请求参数
    contentType: "application/x-www-form-urlencoded",
    //获取数据的Servlet地址
    url: "/systemUserController/listUsers",
    //表格显示条纹
    striped: true,
    singleSelect: true,
    //启动分页
    pagination: true,
    //每页显示的记录数
    pageSize: 10,
    //当前第几页
    pageNumber: 1,
    singleSelect: true,
    clickToSelect: true,
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
      field: "userName",
      sortable: true
    }, {
      title: "姓名",
      field: "realName",
      sortable: true
    }, {
      title: "性别",
      field: "sex",
      sortable: true,
      formatter: function (value, row, index) {
        if (value == '1') {
          return '<span class="label label-warning">女</span>';
        }
        return '<span class="label label-primary">男</span>';
      }
    }, {
      title: "出生日期",
      field: "birthday",
      sortable: true,
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
    }, {
      title: "操作",
      field: "empty",
      formatter: function (value, row, index) {
        var operateHtml = '<button class="btn btn-primary btn-xs" type="button" onclick="edit(\''
            + row.userName
            + '\')"><i class="fa fa-edit"></i>&nbsp;修改</button> &nbsp;';
        operateHtml = operateHtml
            + '<button class="btn btn-danger btn-xs" type="button" onclick="del(\''
            + row.userName
            + '\')"><i class="fa fa-remove"></i>&nbsp;删除</button> &nbsp;';
        operateHtml = operateHtml
            + '<button class="btn btn-info btn-xs" type="button" onclick="related_role(\''
            + row.id + '\')"><i class="fa fa-arrows"></i>&nbsp;关联角色</button>';
        return operateHtml;
      }
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
  $("#searchbtn").click(function () {
    $('#table_list').bootstrapTable('refresh');
  });
});

function edit(userName) {
  layer.open({
    type: 2,
    title: '修改用户',
    shadeClose: true,
    shade: false,
    area: ['893px', '600px'],
    content: '/systemUserController/edit/' + userName,
    end: function (index) {
      $('#table_list').bootstrapTable("refresh");
      layer.close(index);
    }
  });
}

function add() {
  layer.open({
    type: 2,
    title: '添加用户',
    shadeClose: true,
    shade: false,
    area: ['893px', '600px'],
    content: '/systemUserController/add',
    end: function (index) {
      $('#table_list').bootstrapTable("refresh");
      layer.close(index);
    }
  });
}

function related_role(id) {
  layer.open({
    type: 2,
    title: '关联角色',
    shadeClose: true,
    shade: false,
    area: ['893px', '600px'],
    content: '/systemUserController/relatedRole/' + id,
    end: function (index) {
      $('#table_list').bootstrapTable("refresh");
      layer.close(index);
    }
  });
}

function del(userName) {
  layer.confirm('确定删除吗?', {icon: 3, title: '提示'}, function (index) {
    $.ajax({
      type: "POST",
      dataType: "json",
      url: "/systemUserController/deleteUser",
      data: {userName : userName},
      success: function (data) {
        layer.msg(data.msg, {time: 2000}, function () {
          $('#table_list').bootstrapTable("refresh");
          layer.close(index);
        });
      }
    });
  });
}

function lockUser() {
  //获取选中数据
  var selections = $("#table_list").bootstrapTable('getSelections');

  if (selections.length > 1) {
    layer.msg("只能选择一行进行锁定");
    return;
  }
  if (selections.length <= 0) {
    layer.msg("请选择一条数据");
    return;
  }
  layer.confirm('确定锁定' + selections[0].realName + '吗?', {icon: 3, title: '提示'},
    function (index) {
      $.ajax({
        type: "POST",
        dataType: "json",
        url: "/systemUserController/lockUser",
        data: {userName : selections[0].userName},
        success: function (data) {
          layer.msg(data.msg);
          $('#table_list').bootstrapTable("refresh");
        }
      });
    }
  );
}

