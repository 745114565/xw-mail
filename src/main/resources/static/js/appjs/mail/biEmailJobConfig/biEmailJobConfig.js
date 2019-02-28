var prefix = "/mail/biEmailJobConfig"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
					//	showRefresh : true,
					//	showToggle : true,
					//	showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						// queryParamsType : "limit",
						// //设置为limit则会发送符合RESTFull格式的参数
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						showRefresh : false, //显示刷新按钮
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset,
								searchName: $('#searchName').val(),
					           // name:$('#searchName').val(),
					           // username:$('#searchName').val()
							};
						},
						columns : [
                                {
                                     title : '序号',
                                     formatter: function (value, row, index) {
                                         return index+1;
                                     }
                                },
                                {
                                    field : 'jobName',
                                    title : '任务名称'
                                },
                                {
                                    field : 'jobDesc',
                                    title : '任务描述'
                                },
//								{
//									field : 'sendTo',
//									title : '收件人邮箱地址'
//								},
//																{
//									field : 'cc',
//									title : '抄送人邮箱地址'
//								},
//																{
//									field : 'bcc',
//									title : '密送人邮箱地址'
//								},
                                {
									field : 'subject', 
									title : '邮件主题' 
								},
								{
									field : 'cronExpression',
									title : '发送周期'
								},
								{
									field : 'jobStatus', 
									title : '任务状态',
                                    formatter:function(value, row, index){
                                        var res_v = (value == 1?'已开启':'已停止');
                                        var res_calss = (value == 1?'class="btn btn-primary"':'class="btn btn-warning"');
                                        return '<button  '+ res_calss +'  readonly >' + res_v + '</button>'
                                    }
								},
//								{
//									field : 'isconcurrent',
//									visible: false,
//									title : '任务是否有状态：0 没有 ，1 有'
//								},
								{
									field : 'createManStr',
									title : '创建人'
								},
								{
									field : 'createTime', 
									title : '创建时间' 
								},
								{
									field : 'lastOptionManStr',
									title : '最后一次操作人' 
								},
								{
									field : 'lastOptionTime',
									title : '最后一次修改时间'
								},
								{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
                                        var a = '<a class="btn btn-primary btn-sm  '+ s_send_h
                                             +' " href="#" mce_href="#" title="手动发送" onclick="toSendMail(\''
                                             + row.id + '\',\'' + row.jobStatus
                                             + '\')">手动发送<i class="fa fa-edit"></i></a> ';

                                        var e = '<a class="btn btn-primary btn-sm  '+ s_edit_h +' " href="#" mce_href="#" title="编辑" onclick="edit(\''
                                                + row.id + '\',\'' + row.jobStatus
                                                + '\')">编辑<i class="fa fa-edit"></i></a> ';
                                        var d = '<a class="btn btn-danger  btn-sm  '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                                + row.id + '\',\'' + row.jobStatus
                                                + '\')">删除<i class="fa fa-remove"></i></a> ';
                                        var s1 = '<a class="btn btn-warning btn-sm '+ (row.jobStatus== 1?'':'hidden' )+' " href="#" title="停用"  mce_href="#" onclick="stop(\''
                                                + row.id + ',' + row.jobStatus
                                                + '\')" >停用<i class="fa fa-ban"></i></a> ';
                                        var s2 = '<a class="btn btn-info btn-sm '+ (row.jobStatus== 0?'':'hidden') + ' " href="#" title="启用"  mce_href="#" onclick="stop(\''
                                                + row.id + ',' + row.jobStatus
                                                + '\')">启用<i class="fa fa-ban"></i></a> ';
                                        var s = '<span class="'+ s_st_h +'">' + s1 + s2 + '</span>';

										return  a + e + d + s;
									}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
}
function edit(id,status) {
	if(status == 1){
		layer.alert('请先停用该任务！');
		return false;
	}
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
function remove(id,status){
	if(status == 1){
		layer.alert('请先停用该任务！');
		return false;
	}

	layer.confirm('确认删除？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code === 0) {
					layer.msg("删除成功");
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})
}
function toSendMail(id,status){
    layer.open({
        type: 2,
        title: '手动发送邮件',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '420px'],
        content: prefix + '/toSendMail/'+id // iframe的url
    });
}
function stop(id_stop) {
	var id_stop_arr = id_stop.split(',');
	var id = id_stop_arr[0];
	var jobStatus = id_stop_arr[1];

	var smg = '';
	if(jobStatus == 0)
		smg = '确定启用该任务？';
	if(jobStatus == 1)
		smg = '确定停用该任务？';
	layer.confirm(smg, {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/jobStatus",
			type : "post",
			data : {
				'id' : id,
				'jobStatus':jobStatus,
			},
			success : function(r) {
				if (r.code === 0) {
					layer.msg("操作成功");
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})

}