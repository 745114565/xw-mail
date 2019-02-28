var prefix = "/sys/user";

//指定一些特殊的菜单排序在前面，可以用也可以不用，这里做了简单的处理
var sortArr = [
               {
            	   id: 1
            	   ,sortIndex: 1
               }
               ];
var sortArrL = sortArr.length;

$(function () {
    laydate({
        elem : '#birth'
    });
    initIndexPageTable();
});


function initIndexPageTable(){
	var $table = $('#indexPage');
	$table.bootstrapTable({
		method : 'get', // 服务器数据的请求方式 get or post
		url : '/sys/indexPageConfig/listAll', // 服务器数据的加载地址
		striped : true, // 设置为true会有隔行变色效果
		dataType : "json", // 服务器返回的数据类型
		pagination : true, // 设置为true会在底部显示分页条
		singleSelect : true, // 设置为true将禁止多选
		iconSize : 'outline',
//		toolbar : '#exampleToolbar',
		pageSize : 5, // 如果设置了分页，每页数据条数
		pageNumber : 1, // 如果设置了分布，首页页码
		search : true, // 是否显示搜索框
		showColumns : false, // 是否显示内容下拉框（选择显示的列）
		sidePagination : "client", // 设置在哪里进行分页，可选值为"client" 或者 "server"
		queryParams : function(params) {
			return  {};
		},
		columns : [
				   {
		        	   title : '页面模块',
		        	   field : 'modulName'
		           },
		           {
		        	   field : 'pageName',
		        	   title : '页面名称'
		           },
		           {
		        	   field : 'checked',
		        	   title : '设置首页',
//		        	   sortable: true,
//		        	   order: 'desc',
		        	   formatter : function(value, row, index) {
		        	        var hiddenC =  value ? 'hidden' : '';
		        	        var hiddenD =  !value ? 'hidden' : '';
							var c = '<a class="btn btn-primary btn-sm  ' + hiddenC + ' " href="#" title="设为首页"  mce_href="#" onclick="setting(\''
								+ row.pageUrl
								+ '\')"  >设为首页</a> ';
							var d = '<button class="btn btn-danger  btn-sm  ' + hiddenD + '" href="#" title="取消设置"  mce_href="#" onclick="remove(\''
								+ row.pageUrl
								+ '\')" >取消设置</button> ';
							return c + d ;
						}
		           }

				],
		onLoadSuccess:function(data){
			if(data.code=='0'){
				var res = data.res;
				if(!res) return ;
				res.forEach(function(item,index){
					var id = item.pageUrl;

					var oArr = sortArr.filter(o=>o.id == id);
					if(oArr && oArr.length > 0)
						item.sortIndex = oArr[0].sortIndex;
					else
						item.sortIndex = sortArrL + 1 +  index;
				});

				res.sort((a,b)=>{return (a.sortIndex > b.sortIndex) ? 1 : (a.sortIndex < b.sortIndex) ? -1 : 0});
				res.sort((a,b)=> b.checked - a.checked);

				$table.bootstrapTable('load',res);

			}
		}
	});
}


function setting(pageId){
	$.ajax({
		url: '/sys/indexPageConfig/setting',
		type:'put',
		dataType:'json',
		data:{pageId:pageId},
		success:function(r){
			if (r.code === 0) {
				parent.layer.msg("操作成功");
				reLoad();
			} else {
				layer.msg(r.msg);
			}
        }
	});
}

function remove(pageId){
	$.ajax({
		url: '/sys/indexPageConfig/setting',
		type:'delete',
		dataType:'json',
		data:{pageId:pageId},
		success:function(r){
			if (r.code === 0) {
				parent.layer.msg("操作成功");
				reLoad();
			} else {
				layer.msg(r.msg);
			}
        }
	});
}

function reLoad(){
	$('#indexPage').bootstrapTable('refresh', {});
}

// ===================
/**
 * 基本信息提交
 */
$("#base_save").click(function () {
    var hobbyStr = getHobbyStr();
    $("#hobby").val(hobbyStr);
    if($("#basicInfoForm").valid()){
            $.ajax({
                cache : true,
                type : "POST",
                url :"/sys/user/updatePeronal",
                data : $('#basicInfoForm').serialize(),
                async : false,
                error : function(request) {
                    laryer.alert("Connection error");
                },
                success : function(data) {
                    if (data.code == 0) {
                        parent.layer.msg("更新成功");
                    } else {
                        parent.layer.alert(data.msg)
                    }
                }
            });
        }

});
$("#pwd_save").click(function () {
    if($("#modifyPwd").valid()){
        $.ajax({
            cache : true,
            type : "POST",
            url :"/sys/user/resetPwd",
            data : $('#modifyPwd').serialize(),
            async : false,
            error : function(request) {
                parent.laryer.alert("Connection error");
            },
            success : function(data) {
                if (data.code == 0) {
                    parent.layer.alert("更新密码成功");
                    $("#photo_info").click();
                } else {
                    parent.layer.alert(data.msg)
                }
            }
        });
    }
});
function getHobbyStr(){
    var hobbyStr ="";
    $(".hobby").each(function () {
        if($(this).is(":checked")){
            hobbyStr+=$(this).val()+";";
        }
    });
   return hobbyStr;
}