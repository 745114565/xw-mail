var prefix = "/mail/biEmailJobConfig"
$(function() {
	initDateBox();
	var initV = moment().subtract('days',1).format('YYYYMMDD');
	$('#send_time').val(initV);
});


/**
 * 提交数据
 */
function sendByManual() {
	var id = $('#id').val();
	var date = $('#send_time').val();
	if(!date){
		parent.layer.msg('发送时间不能为空！');
		return false;
	}
	$.ajax({
		cache : true,
		type : "GET",
		url : prefix +  '/sendByManual?id=' + id + '&date=' + date,
		async : false,
		error : function(request) {
			laryer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.alert(data.msg);
			} else {
				parent.layer.alert(data.msg)
			}
		}
	});
}

function initDateBox(){
	$('.form_date').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  true,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0,
		format: 'yyyymmdd'
    }); 

}
