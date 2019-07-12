$(function () {
    $('#department_tree').on('changed.jstree', function (e, data) {
        for (var i = 0; i < data.selected.length; i++) {
            var department = $(data.instance.get_node(data.selected[i]).text)
            $.ajax({
                type: "POST",
                dataType: "json",
                data: {
                    code: department.attr("id"),
                },
                url: "/systemDepartmentController/getDepartmentInfo",
                success: function (data) {
                    $('#code').attr('readonly', true);
                    $('#departCode').attr('readonly', true);
                    $('#departParentName').attr('readonly', true);
                    bindFiledToControl(data);
                    departmentUserListRefresh();
                }
            });

        }
    }).jstree({
        'core': {
            'check_callback': true
        },
        'plugins': ['types', 'none']
    });

    $('#pcode').change(function () {
        $.ajax({
            type: "POST",
            dataType: "json",
            data: {
                code: $('#pcode').val(),
            },
            url: "/systemDepartmentController/getDepartmentInfo",
            success: function (data) {
                $('#departParentName').val(data.departSimpleName);
            }
        });
    });

    $("#frm").validate({
        rules: {
            code: {
                required: true,
                maxlength: 10
            },
            departCode: {
                required: true,
                maxlength: 50
            },
            pcode: {
                required: true,
                maxlength: 10
            },
            departSimpleName: {
                required: true,
                maxlength: 25
            },
            departFullName: {
                maxlength: 100
            },
            departLeader: {
                maxlength: 20
            },
            departTel: {
                maxlength: 20
            },
            tips: {
                maxlength: 255
            },
            temp: {
                maxlength: 50
            },
            num: {
                required: true
            }
        },
        messages: {},
        submitHandler: function (form) {
            var loadIndex = layer.load(0, {shade: [0.3, '#C6C2B6']})
            var url = "/systemDepartmentController/addDepartment";
            if ($('#id').val() != "") {
                var url = "/systemDepartmentController/editDepartment";
            }
            $.ajax({
                type: "POST",
                dataType: "json",
                url: url,
                data: $(form).serialize(),
                success: function (data) {
                    $('#department_tree').jstree(true).refresh();
                    layer.msg(data.msg);
                },
                complete: function(){
                    parent.layer.close(loadIndex);
                }
            });
        }
    });
});

function delUsers() {
    var nodes = $('#department_tree').jstree(true).get_selected(true);
    if (nodes.length != 1) {
        layer.msg("请在左侧部门树中选择一个部门进行添加删除关联用户！");
        return;
    }
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/systemDepartmentController/deleteRelatedUsers",
        data: {
            id: $('#id').val(),
            userIds: getBootstrapTableIds("department_user_list")
        },
        success: function (data) {
            layer.msg(data.msg);
            departmentUserListRefresh();
        }
    });
}

function addLowerDepartment() {
    var nodes = $('#department_tree').jstree(true).get_selected(true);
    if (nodes.length != 1) {
        layer.msg("请在左侧部门树中选择一个部门进行添加下级部门！");
        return;
    }
    document.getElementById("frm").reset();
    $('#code').removeAttr('readonly');
    $('#departCode').removeAttr('readonly');
    $('#pcode').val($(nodes[0].text).attr('id')).change();
}

function addSameDepartment() {
    var nodes = $('#department_tree').jstree(true).get_selected(true);
    if (nodes.length != 1) {
        layer.msg("请在左侧部门树中选择一个部门添加同级部门！");
        return;
    }
    var node = $('#department_tree').jstree("get_node",
        $('#department_tree').jstree("get_parent", nodes[0]))
    document.getElementById("frm").reset();
    $('#code').removeAttr('readonly');
    $('#departCode').removeAttr('readonly');
    $('#pcode').val($(node.text).attr('id')).change();
}

function addUsers() {
    var nodes = $('#department_tree').jstree(true).get_selected(true);
    if (nodes.length != 1) {
        layer.msg("请在左侧部门树中选择一个部门进行添加删除关联用户！");
        return;
    }
    openUserSimpleList("关联用户", "/systemDepartmentController/relatedUsers",
        {id: $('#id').val()});
}

function del() {
    var nodes = $('#department_tree').jstree(true).get_selected(true);
    if (nodes.length != 1) {
        layer.msg("请在左侧部门树中选择一个部门进行删除操作！");
        return;
    }
    layer.confirm('确定删除吗?', {icon: 3, title: '提示'}, function (index) {
        var code = $($('#department_tree').jstree(true).get_selected(true)[0].text).attr('id');
        $.ajax({
            type: "POST",
            dataType: "json",
            data: {
                code: code
            },
            url: "/systemDepartmentController/delete",
            success: function (data) {
                layer.msg(data.msg);
                $('#department_tree').jstree(true).refresh();
            }
        });
    });
}

function departmentUserListRefresh(){
    $("#department_user_list").bootstrapTable('destroy').bootstrapTable({
        //使用get请求到服务器获取数据
        method: "POST",
        //必须设置，不然request.getParameter获取不到请求参数
        contentType: "application/x-www-form-urlencoded",
        //获取数据的Servlet地址
        url: "/systemUserController/listDepartmentUsers/" + $('#code').val(),
        //表格显示条纹
        striped: true,
        singleSelect: false,
        clickToSelect: true,
        //表示服务端请求
        sidePagination: "server",
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        //设置为limit可以获取limit, offset, search, sort, order
        queryParamsType: "undefined",
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
            title: "用户名",
            field: "realName"
        }]
    });
}

function parentCallbackFunc(){
    departmentUserListRefresh();
}
