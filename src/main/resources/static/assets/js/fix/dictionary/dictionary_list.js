$(function () {
    //初始化表格,动态从服务器加载数据
    $("#parent_table_list").bootstrapTable({
        //使用get请求到服务器获取数据
        method: "POST",
        //必须设置，不然request.getParameter获取不到请求参数
        contentType: "application/x-www-form-urlencoded",
        //获取数据的Servlet地址
        url: "/fixedDictionaryController/listDictionarys",
        //表格显示条纹
        striped: true,
        //启动分页
        pagination: true,
        singleSelect: true,
        clickToSelect: true,
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
        queryParams: function queryParams(params){
            var param = {
                pageNumber: params.pageNumber,
                pageSize: params.pageSize,
                sortName: params.sortName,
                sortOrder: params.sortOrder,
                pid: 0
            };
            return param;
        },
        //json数据解析
        responseHandler: function(res) {
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
        },{
            title: "数据字典编码",
            sortable: true,
            field: "dictionaryCode"
        },{
            title: "数据字典名",
            sortable: true,
            field: "dictionaryName"
        },{
            title: "允许空值",
            field: "hasEmpty",
            sortable: true,
            formatter: function (value, row, index) {
                if (value == '1') {
                    return '<span class="label label-warning">允许</span>';
                }
                return '<span class="label label-primary">不允许</span>';
            }
        },{
          title: "更新时间",
          field: "updateDate",
          sortable: true,
          formatter: function (value, row, index) {
            return changeDateFormat(value)
          },
          sortable: true
        },{
            title: "操作",
            field: "empty",
            formatter: function (value, row, index) {
                var operateHtml = '<button class="btn btn-primary btn-xs" type="button" onclick="edit(\''+row.id+'\')"><i class="fa fa-edit"></i>&nbsp;修改</button> &nbsp;';
                operateHtml = operateHtml + '<button class="btn btn-danger btn-xs" type="button" onclick="del(\''+row.id+'\')"><i class="fa fa-remove"></i>&nbsp;删除</button>';
                return operateHtml;
            }
        }],
        onDblClickRow: function (row) {
            layer.open({
              type: 2,
              title: '数据字典信息',
              shadeClose: true,
              shade: false,
              area: ['420px', '300px'],
              content: '/fixedDictionaryController/info/' + row.id,
              end: function(index){
              }
            });
        },
        onClickRow: function (row) {
          $("#table_list").bootstrapTable('destroy').bootstrapTable({
            //使用get请求到服务器获取数据
            method: "POST",
            //必须设置，不然request.getParameter获取不到请求参数
            contentType: "application/x-www-form-urlencoded",
            //获取数据的Servlet地址
            url: "/fixedDictionaryController/listDictionarys",
            //表格显示条纹
            striped: true,
            //启动分页
            pagination: true,
            singleSelect: true,
            clickToSelect: true,
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
            queryParams: function queryParams(params){
              var param = {
                pageNumber: params.pageNumber,
                pageSize: params.pageSize,
                sortName: params.sortName,
                sortOrder: params.sortOrder,
                pid: row.id
              };
              return param;
            },
            //json数据解析
            responseHandler: function(res) {
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
            },{
              title: "数据字典名",
              sortable: true,
              field: "dictionaryName"
            },{
              title: "数据字典值",
              sortable: true,
              field: "dictionaryValue"
            },{
              title: "操作",
              field: "empty",
              formatter: function (value, row, index) {
                var operateHtml = '<button class="btn btn-primary btn-xs" type="button" onclick="edit(\''+row.id+'\')"><i class="fa fa-edit"></i>&nbsp;修改</button> &nbsp;';
                operateHtml = operateHtml + '<button class="btn btn-danger btn-xs" type="button" onclick="del(\''+row.id+'\')"><i class="fa fa-remove"></i>&nbsp;删除</button>';
                return operateHtml;
              }
            }],
            onDblClickRow: function (row) {
              layer.open({
                type: 2,
                title: '数据字典信息',
                shadeClose: true,
                shade: false,
                area: ['420px', '320px'],
                content: '/fixedDictionaryController/info/' + row.id,
                end: function(index){
                }
              });
            }
          });
        }
    });
});

function edit(id){
    layer.open({
        type: 2,
        title: '数据字典修改',
        shadeClose: true,
        shade: false,
        area: ['420px', '320px'],
        content: '/fixedDictionaryController/edit/' + id,
        end: function(index){
            $('#parent_table_list').bootstrapTable("refresh");
            $('#table_list').bootstrapTable("refresh");
        }
    });
}

function parentAdd(){
    layer.open({
        type: 2,
        title: '父级字典添加',
        shadeClose: true,
        shade: false,
        area: ['420px', '300px'],
        content: '/fixedDictionaryController/add/' + 0,
        end: function(index){
            $('#parent_table_list').bootstrapTable("refresh");
        }
    });
}

function add(){//获取选中数据
  var selections = $("#parent_table_list").bootstrapTable('getSelections');

  if (selections.length > 1) {
    layer.msg("只能选择一行父数据字典项");
    return;
  }
  if (selections.length <= 0) {
    layer.msg("请选择一个父数据字典项");
    return;
  }
  layer.open({
    type: 2,
    title: '数据字典添加',
    shadeClose: true,
    shade: false,
    area: ['420px', '320px'],
    content: '/fixedDictionaryController/add/' + selections[0].id,
    end: function(index){
      $('#table_list').bootstrapTable("refresh");
    }
  });
}

function del(id){
    layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/fixedDictionaryController/delete",
            data: {id : id},
            success: function(data){
                layer.msg(data.msg);
                $('#table_list').bootstrapTable("refresh");
            }
        });
    });
}

