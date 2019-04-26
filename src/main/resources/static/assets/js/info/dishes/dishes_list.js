$(function () {
    //初始化表格,动态从服务器加载数据
    $("#table_list").bootstrapTable({
        //使用get请求到服务器获取数据
        method: "POST",
        //必须设置，不然request.getParameter获取不到请求参数
        contentType: "application/x-www-form-urlencoded",
        //获取数据的Servlet地址
        url: "/infoDishesController/dishesList",
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
                sortOrder: params.sortOrder
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
        onDblClickRow: function (row) {
            layer.open({
              type: 2,
              title: '信息',
              shadeClose: true,
              shade: false,
              area: ['420px', '280px'],
              content: '/infoDishesController/info/' + row.id,
              end: function(index){
              }
            });
        },
        //数据列
        columns: [{
          title: "id",
          field: "id",
          visible: false
        },{
            title: "菜品ID",
            field: "id"
        },{
            title: "菜品分类",
            field: "dishesType"
        },{
            title: "菜品名称",
            field: "dishesName"
        },{
            title: "菜品金额",
            field: "dishesPrice"
        },{
            title: "菜品是否折扣：0不折扣，1折扣",
            field: "dishesDiscount"
        },{
            title: "折扣后金额",
            field: "dishesPay"
        },{
            title: "菜品图片",
            field: "dishesImage"
        },{
            title: "菜品口味",
            field: "dishesTaste"
        },{
            title: "菜品口味程度",
            field: "dishesLevel"
        },{
            title: "删除标记：0：有效1：无效",
            field: "disFlag"
        },{
            title: "创建日期",
            field: "createDate"
        },{
            title: "更新日期",
            field: "updateDate"
        },{
            title: "创建者",
            field: "createUser"
        },{
            title: "更新者",
            field: "updateUser"
        },{
          title: "更新时间",
          field: "updateDate",
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
        }]
    });
});


function edit(id){
    layer.open({
        type: 2,
        title: '修改',
        shadeClose: true,
        shade: false,
        area: ['420px', '280px'],
        content: '/infoDishesController/edit/' + id,
        end: function(index){
            $('#table_list').bootstrapTable("refresh");
        }
    });
}

function add(){
    layer.open({
        type: 2,
        title: '添加',
        shadeClose: true,
        shade: false,
        area: ['420px', '280px'],
        content: '/infoDishesController/add',
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
            url: "/infoDishesController/delete",
            data: {id : id},
            success: function(data){
                layer.msg(data.message);
                $('#table_list').bootstrapTable("refresh");
            }
        });
    });
}

