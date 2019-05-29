$(function () {
    //初始化表格,动态从服务器加载数据
    $("#table_list").bootstrapTable({
        //使用get请求到服务器获取数据
        method: "POST",
        //必须设置，不然request.getParameter获取不到请求参数
        contentType: "application/x-www-form-urlencoded",
        //获取数据的Servlet地址
        url: "/infoQuestionController/questionList",
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
              title: '问卷信息',
              shadeClose: true,
              shade: false,
              area: ['420px', '280px'],
              content: '/infoQuestionController/info/' + row.id,
              end: function(index){
              }
            });
        },
        //数据列
        columns: [{
            checkbox: true
        }, {
          title: "id",
          field: "id",
          visible: false
        },{
            title: "问卷编号",
            field: "questionCode",
            sortable: true
        },{
            title: "问卷名",
            field: "questionName",
            sortable: true
        },{
            title: "问卷开始时间",
            field: "startDate",
            formatter: function (value, row, index) {
                return changeDateFormat(value)
            },
            sortable: true
        },{
            title: "问卷结束时间",
            field: "endDate",
            formatter: function (value, row, index) {
                return changeDateFormat(value)
            },
            sortable: true
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
        }],
        onClickRow: function (row) {
            $("#item_table_list").bootstrapTable('destroy').bootstrapTable({
                //使用get请求到服务器获取数据
                method: "POST",
                //必须设置，不然request.getParameter获取不到请求参数
                contentType: "application/x-www-form-urlencoded",
                //获取数据的Servlet地址
                url: "/infoQuestionItemController/questionItemList",
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
                        questionId: row.id
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
                    title: "题目名",
                    field: "itemQuestion",
                    sortable: true
                },{
                    title: "题目类型",
                    field: "itemType",
                    sortable: true
                },{
                    title: "题目排序",
                    field: "itemSort",
                    sortable: true
                },{
                    title: "是否多值",
                    field: "itemMore",
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == '1') {
                            return '<span class="label label-warning">是</span>';
                        }
                        return '<span class="label label-primary">否</span>';
                    }
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
                        var operateHtml = '<button class="btn btn-primary btn-xs" type="button" onclick="editItem(\''+row.id+'\')"><i class="fa fa-edit"></i>&nbsp;修改</button> &nbsp;';
                        operateHtml = operateHtml + '<button class="btn btn-danger btn-xs" type="button" onclick="delItem(\''+row.id+'\')"><i class="fa fa-remove"></i>&nbsp;删除</button>';
                        return operateHtml;
                    }
                }],
                onDblClickRow: function (row) {
                    layer.open({
                        type: 2,
                        title: '题目信息',
                        shadeClose: true,
                        shade: false,
                        area: ['420px', '320px'],
                        content: '/infoQuestionItemController/info/' + row.id,
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
        title: '修改问卷',
        shadeClose: true,
        shade: false,
        area: ['420px', '280px'],
        content: '/infoQuestionController/edit/' + id,
        end: function(index){
            $('#table_list').bootstrapTable("refresh");
        }
    });
}

function add(){
    layer.open({
        type: 2,
        title: '添加问卷',
        shadeClose: true,
        shade: false,
        area: ['420px', '280px'],
        content: '/infoQuestionController/add',
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
            url: "/infoQuestionController/delete",
            data: {id : id},
            success: function(data){
                layer.msg(data.message);
                $('#table_list').bootstrapTable("refresh");
            }
        });
    });
}

function relatedItem(){
    var selections = $("#table_list").bootstrapTable('getSelections');

    if (selections.length > 1) {
        layer.msg("只能选择一行问卷项");
        return;
    }
    if (selections.length <= 0) {
        layer.msg("请选择一个问卷项");
        return;
    }
    layer.open({
        type: 2,
        title: '添加题目',
        shadeClose: true,
        shade: false,
        area: ['420px', '280px'],
        content: '/infoQuestionItemController/add/' + selections[0].id,
        end: function(index){
            $('#item_table_list').bootstrapTable("refresh");
        }
    });
}

function editItem(id){
    layer.open({
        type: 2,
        title: '修改题目',
        shadeClose: true,
        shade: false,
        area: ['420px', '280px'],
        content: '/infoQuestionItemController/edit/' + id,
        end: function(index){
            $('#item_table_list').bootstrapTable("refresh");
        }
    });
}

function delItem(id){
    layer.confirm('确定删除该题目吗?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/infoQuestionItemController/delete",
            data: {id : id},
            success: function(data){
                layer.msg(data.message);
                $('#item_table_list').bootstrapTable("refresh");
            }
        });
    });
}
