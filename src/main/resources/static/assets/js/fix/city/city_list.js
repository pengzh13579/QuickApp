$(function () {
    //初始化表格,动态从服务器加载数据
    $("#province_table_list").bootstrapTable({
        //使用get请求到服务器获取数据
        method: "POST",
        //必须设置，不然request.getParameter获取不到请求参数
        contentType: "application/x-www-form-urlencoded",
        //获取数据的Servlet地址
        url: "/provinceAreaController/provinceList",
        //表格显示条纹
        striped: true,
        //启动分页
        pagination: false,
        singleSelect: true,
        clickToSelect: true,
        //表示服务端请求
        sidePagination: "server",
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        //设置为limit可以获取limit, offset, search, sort, order
        queryParamsType: "undefined",
        //json数据解析
        responseHandler: function(res) {
            return {
                "rows": res.rows,
                "total": res.total
            };
        },
        //数据列
        columns: [{
          title: "省自治区级代码",
          field: "provinceId"
        },{
            title: "省自治区级名",
            field: "provinceName"
        }],
      onClickRow: function (proRow) {
        $("#city_table_list").bootstrapTable('destroy').bootstrapTable({
          //使用get请求到服务器获取数据
          method: "POST",
          //必须设置，不然request.getParameter获取不到请求参数
          contentType: "application/x-www-form-urlencoded",
          //获取数据的Servlet地址
          url: "/provinceAreaController/countyList/" + proRow.provinceId,
          //表格显示条纹
          striped: true,
          //启动分页
          pagination: false,
          singleSelect: true,
          clickToSelect: true,
          //表示服务端请求
          sidePagination: "server",
          //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
          //设置为limit可以获取limit, offset, search, sort, order
          queryParamsType: "undefined",
          //json数据解析
          responseHandler: function(res) {
            return {
              "rows": res.rows,
              "total": res.total
            };
          },
          //数据列
          columns: [{
            title: "地市级代码",
            field: "countyId"
          },{
            title: "地市级名",
            field: "countyName"
          }],
          onClickRow: function (cityRow) {
            $("#area_table_list").bootstrapTable('destroy').bootstrapTable({
              //使用get请求到服务器获取数据
              method: "POST",
              //必须设置，不然request.getParameter获取不到请求参数
              contentType: "application/x-www-form-urlencoded",
              //获取数据的Servlet地址
              url: "/provinceAreaController/districtList/" + cityRow.countyId,
              //表格显示条纹
              striped: true,
              //启动分页
              pagination: false,
              singleSelect: true,
              clickToSelect: true,
              //表示服务端请求
              sidePagination: "server",
              //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
              //设置为limit可以获取limit, offset, search, sort, order
              queryParamsType: "undefined",
              //json数据解析
              responseHandler: function(res) {
                return {
                  "rows": res.rows,
                  "total": res.total
                };
              },
              //数据列
              columns: [{
                title: "区县级代号",
                field: "districtId"
              },{
                title: "区县级名",
                field: "districtName"
              }]
            });
          }
        });
      }
    });
});


