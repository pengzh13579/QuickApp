$(function () {

});

function edit(userName){
    layer.open({
        type: 2,
        title: '用户修改',
        shadeClose: true,
        shade: false,
        area: ['893px', '600px'],
        content: '/systemUserController/edit/' + userName,
        end: function(index){
            $('#table_list').bootstrapTable("refresh");
        }
    });
}
function add(){
    layer.open({
        type: 2,
        title: '用户添加',
        shadeClose: true,
        shade: false,
        area: ['893px', '600px'],
        content: '/systemMenuController/add',
        end: function(index){
            $('#table_list').bootstrapTable("refresh");
        }
    });
}
function grant(id){
    layer.open({
        type: 2,
        title: '关联角色',
        shadeClose: true,
        shade: false,
        area: ['893px', '600px'],
        content: '${ctx!}/admin/user/grant/'  + id,
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
            url: "systemUserController/delete/" + id,
            success: function(msg){
                layer.msg(msg.message, {time: 2000},function(){
                    $('#table_list').bootstrapTable("refresh");
                    layer.close(index);
                });
            }
        });
    });
}

function detailFormatter(index, row) {
    var html = [];
    html.push('<p><b>描述:</b> ' + row.description + '</p>');
    return html.join('');
}

//修改——转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
    if (cellval != null) {
        var date = new Date(parseInt(cellval));
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        var D = date.getDate() + ' ';
        return Y+M+D;
    }
}
