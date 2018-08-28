$(function(){
    //菜单点击
    J_iframe
    $(".J_menuItem").on('click',function(){
        var url = $(this).attr('href');
        $("#J_iframe").attr('src',url);
        return false;
    });
});
function changePassword(){
  layer.open({
    type: 2,
    title: '修改密码',
    shadeClose: true,
    shade: false,
    area: ['400px', '400px'],
    content: '/systemUserController/changePasswordForm',
    end: function(index){
    }
  });
}
