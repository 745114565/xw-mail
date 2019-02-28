
var prefix = "/mail/biEmailJobRunDiary"
$(function() {
    initDateBox();
	loadData();
	copyText();
});

function loadData() {
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
						pageSize : 50, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							var p = getParams();
                            $.extend(true,params,p);
                            return params;
						},
						columns : columns,
                        onLoadSuccess:function(resp){
                            if(resp.code == 0){
                                var page = resp.page;
                                var rows = page.rows;
                                var total = page.total;
                                if(!rows){
                                    page.rows = [];
                                    page.total = 0;
                                }
                                $('#exampleTable').bootstrapTable('load',page);
                            }else{
                                layer.msg(resp.msg)
                            }
                        }

					});
}

function getParams(){
	return {
	    startTimeStr: $('#startTime').val() || '',
        endTimeStr: $('#endTime').val() || '',
        runState: $('#runState').val() || '',
        searchValue: $('#searchName').val()|| ''
    };
}

var columns =
[
    {
        checkbox : true
    },
      {
           title : '序号',
           formatter: function (value, row, index) {
               return index+1;
           }
      },
    {
        field : 'jobName',
        title : '任务名称',
          formatter: function (value, row, index) {
               return '<i title="复制任务ID" class="fa fa-file-code-o copy-btn text-info"  data-clipboard-text="'+row.jobId+'"></i>  ' + value;
          }
    },
    {
        field : 'startTime',
        title : '任务开始时间'
    },
    {
        field : 'endTime',
        title : '任务结束时间'
    },
    {
        field : 'runState',
        title : '运行状态 ' ,
        formatter: function (value, row, index) {
            var res = (value == 0 ? { class: 'btn-info',text:'正在执行'}
                    : (value == 1 ? { class: 'btn-danger',text: '失败'}
                    : (value == 2 ? { class: 'btn-primary',text: '成功'}
                    : { class: 'btn-success',text: '未知'})));
            return '<button class="btn '+ res.class+'" readonly> ' + res.text + '</button>';
        }
    },
    {
        field : 'failedType',
        title : '失败类型' ,
          formatter: function (value, row, index) {
              return value == 1 ? '程序异常' : (value == 2 ? '程序异常' : '');
          }
    },
      {
          field : 'failedMsg',
          title : '失败信息'
      },
    {
        field : 'retryTimes',
        title : '重试次数'
    },
    {
        field : 'retried',
        title : '是否重试',
        formatter: function(value,row,index){
            return value == 0 ? '未重试' : (value == 1 ? '已经重试' : '');
        }
    }
];


function reLoad() {
	$('#exampleTable').bootstrapTable('refresh',{});
}
//复制文本
function copyText(){
	var clipboard = new ClipboardJS('.copy-btn');
    clipboard.on('success', function(e) {
    	 parent.layer.msg("复制成功！");
    	 e.clearSelection();
    });
    clipboard.on('error', function(e) {
    	 parent.layer.msg("复制失败！");
    });
}

function batchSendEmail() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择邮件");
		return;
	}
	layer.prompt({
		  formType: 0,
		  value: moment().subtract('days',1).format('YYYYMMDD'),
		  title: '请输入日期',
		  area: ['60px', '100px'] //自定义文本域宽高
		}, function(value, index, elem){
			$.each(rows, function(i, row) {
				var id  = row['jobId'];
				if(!value){
					parent.layer.msg('发送时间不能为空！');
					return false;
				}
				$.ajax({
					cache : true,
					type : "GET",
					url : prefix +  '/sendByManual?id=' + id + '&date=' + value,
					async : false,
					error : function(request) {
						laryer.alert("Connection error");
					},
					success : function(data) {

						//刷新页面
						reLoad();
//						if (data.code == 0) {
//							parent.layer.alert(data.msg);
//						} else {
//							parent.layer.alert(data.msg)
//						}
					}
				});
			});
		  layer.close(index);
		});

//	layer.confirm("确认要重发选中的'" + rows.length + "'个邮件吗?", {
//		btn : [ '确定', '取消' ]
//	// 按钮
//	}, function() {
//
//	}, function() {});
}

/**
 * 初始化日期插件
 */
function initDateBox(){
	$('.form_date').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		format: 'yyyy-mm-dd',
		forceParse: 0
    });

}