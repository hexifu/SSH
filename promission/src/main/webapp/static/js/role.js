$(function () {
    /*角色数据列表*/
    $("#role_dg").datagrid({
        url:"getRoles",
        columns:[[
            {field:'rnum',title:'角色编号',width:100,align:'center'},
            {field:'rname',title:'角色名称',width:100,align:'center'}
        ]],
        fit:true,
        fitColumns:true,
        rownumbers:true,
        pagination:true,
        singleSelect:true,
        striped:true,
        toolbar:"#toolbar"
    });


    /*添加编辑对话框*/
    $("#dialog").dialog({
        width:600,
        height:600,
        buttons:[{
            text:'保存',
            handler:function(){
                /*提交表单*/

                /*判断当前时保存操作还是编辑操作*/
                var rid = $("[name='rid']").val();
                var url;
                if(rid){
                    /*编辑*/
                    url="updateRole";
                }else{
                    /*保存*/
                    url="saveRole"
                }
                $("#myform").form("submit",{
                    url:url,
                    onSubmit:function(param){   /*传递额外参数   已选择的权限*/
                        /*获取已选择的权限*/
                        var allRows = $("#role_data2").datagrid("getRows");
                        /*遍历出每一个权限*/
                        for(var i=0 ;i<allRows.length;i++){
                            /*取出每一个权限*/
                            var row = allRows[i];
                            /*给他封装到集合中*/
                            param["permissions["+i+"].pid"] = row.pid;
                        }

                    },
                    success:function (data) {
                        data = $.parseJSON(data);
                        if (data.success){
                            $.messager.alert("温馨提示",data.msg);
                            /*关闭对话框 */
                            $("#dialog").dialog("close");
                            /*重新加载数据表格*/
                            $("#role_dg").datagrid("reload");
                        } else {
                            $.messager.alert("温馨提示",data.msg);
                        }
                    }
                })


            }
        },{
            text:'关闭',
            handler:function(){
                $("#dialog").dialog("close");
            }
        }],
        closed:true
    });

    /*添加角色*/
    $("#add").click(function () {
        $("#dialog").dialog("open");
        /*清空表单*/
        $("#myform").form("clear");
        /*清空已选权限*/
        $("#role_data2").datagrid("loadData",{rows:[]});
        /*设置标题*/
        $("#dialog").dialog("setTitle","添加角色");

    });

    /*权限列表*/
    $("#role_data1").datagrid({
        title:"所有权限",
        width:250,
        height:400,
        fitColumns:true,
        singleSelect:true,
        url:'permissionList',
        columns:[[
            {field:'pname',title:'权限名称',width:100,align:'center'}
        ]],
        onClickRow:function (rowIndex,rowData) {   /*点击一行时，回调*/


            /*判断是否已经存在该权限*/
            /*去除所有已选权限*/
            var allRows = $("#role_data2").datagrid("getRows");
            /*取出每一个进行判断*/
            for(var i = 0;i<allRows.length;i++){
                var row = allRows[i];  /*取出每一行*/
                if(rowData.pid==row.pid){  /*已经存在该权限*/
                    /*让已经存在的权限成为选中状态*/
                    /*获取已经成为选中状态的当前角标*/
                 var index = $("#role_data2").datagrid("getRowIndex",row);
                 /*让该行成为选中状态*/
                    $("#role_data2").datagrid("selectRow",index);
                    return;
                }
            }
            /*把当前选中的行，添加到已选权限*/
            $("#role_data2").datagrid("appendRow",rowData);
        }
    });

    /*选中权限列表*/
    $("#role_data2").datagrid({
        title:"已选权限",
        width:250,
        height:400,
        singleSelect:true,
        fitColumns:true,
        columns:[[
            {field:'pname',title:'权限名称',width:100,align:'center'}
        ]],
        onClickRow:function (rowIndex,rowData){
            /*删除当前选中的一行*/
            $("#role_data2").datagrid("deleteRow",rowIndex);
        }

    });

    /*监听编辑点击*/
    $("#edit").click(function () {

        /*获取当前选中的行*/
        var rowData = $("#role_dg").datagrid("getSelected");

        console.log(rowData);
        if(!rowData){                                              //为空
            $.messager.alert("提示","请选择一行进行编辑");
            return;
        }

        /*加载当前角色下的权限*/
        var options = $("#role_data2").datagrid("options");
        options.url="getPermissionByRid?rid="+rowData.rid;
        /*重新加载数据*/
        $("#role_data2").datagrid("load");

        $("#dialog").dialog("setTitle","编辑角色");
        $("#dialog").dialog("open");
        /*选中数据回显*/
        $("#myform").form("load",rowData);


    });
    
    /*监听删除点击*/
    $("#remove").click(function () {
        /*获取当前选中的行*/
        var rowData = $("#role_dg").datagrid("getSelected");

        console.log(rowData);
        if(!rowData){                                              //为空
            $.messager.alert("提示","请选择一行进行删除");
            return;
        }
        $.get("deleteRole?rid="+rowData.rid,function (data) {
            if (data.success){
                $.messager.alert("温馨提示",data.msg);
                /*重新加载数据表格*/
                $("#role_dg").datagrid("reload");
            } else {
                $.messager.alert("温馨提示",data.msg);
            }
        });
    });

});