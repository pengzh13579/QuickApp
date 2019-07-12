$(function () {
    //初始化表格,动态从服务器加载数据
    $("#table_list").bootstrapTable({
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
              content: '/infoQuestionItemController/info/' + row.id,
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
            title: "主键ID",
            field: "id"
        },{
            title: "问卷ID",
            field: "questionId"
        },{
            title: "题目名",
            field: "itemQuestion"
        },{
            title: "题目类型",
            field: "itemType"
        },{
            title: "题目排序",
            field: "itemSort"
        },{
            title: "是否多值（0：否；1：是）",
            field: "itemMore"
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
        content: '/infoQuestionItemController/edit/' + id,
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
        content: '/infoQuestionItemController/add',
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
            url: "/infoQuestionItemController/delete",
            data: {id : id},
            success: function(data){
                layer.msg(data.msg);
                $('#table_list').bootstrapTable("refresh");
            }
        });
    });
}

