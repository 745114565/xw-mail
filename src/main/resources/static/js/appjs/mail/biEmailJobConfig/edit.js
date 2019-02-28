$().ready(function() {
	validateRule();
	buildMultiSelectBox();
	buildMultiSelectBox2();
	jobNameEvent();
    $("#cronExpression").cronGen({
        direction : 'left'
     });
});
function jobNameEvent(){
	$('#jobName').on('change',function(e){
		var  $this = $(this);
		var JobValue = $this.val();
		$('#jobDesc').val(JobValue);
		$('#subject').val(JobValue);
	});
}

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});

function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/mail/biEmailJobConfig/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			sendTo : {
				required : true
			},
			subject : {
				required: true
			},
			emailContentId : {
				required: true
			},
			cronExpression : {
				required : true
			},
			emailSenderId : {
				required : true
			},
			jobName : {
				required : true
			}
		},
		messages : {
			sendTo : {
				required : icon + "请输入收件人邮箱地址"
			},
			subject : {
				required : icon + "请输入邮件主题"
			},
			emailContentId : {
				required : icon + "请选择邮箱内容"
			},
			cronExpression : {
				required : icon + "请输入发送周期"
			},
			emailSenderId : {
				required : icon + "请选择发件人邮箱"
			},
			jobName : {
				required : icon + "请输入任务名称"
			}
		}
	})
}

/**
 * 创建多选框
 *
 * @returns {Boolean}
 */
function buildMultiSelectBox(){
	var optionData = [];
	var url = '/mail/biEmailReceiverGroupConfig/listAll';
	$.get(url,{},function(resp){
		if(resp && resp.code == 0   && resp.groups)
			optionData = buildOptionData(resp.groups);

		var sendToStr = $('#sendToHidenId').val() || '';
		var ccStr = $('#ccHidenId').val() || '';
		var bccStr = $('#bccHidenId').val() || '';
		var sendToArr = sendToStr.split(',');
		var ccArr = ccStr.split(',');
		var bccArr = bccStr.split(',');

		initOptions('sendTo',cnvOptionData(optionData,sendToArr));
		initOptions('cc',cnvOptionData(optionData,ccArr));
		initOptions('bcc',cnvOptionData(optionData,bccArr));
	});
}

function cnvOptionData(optionData,arr){
    return optionData.map(item=>{
        item.selected = false;
        if(arr.includes(item.value))
          item.selected = true;
        return item;
    }).sort((a,b) => b.selected - a.selected );
}

/**
 * 创建多选框
 *
 * @returns {Boolean}
 */
function buildMultiSelectBox2(){
	var textOptionData = [];
	var excelOptionData = [];
	var url ='/mail/biEmailJobConfig/content';
	var jobId = $('#id').val();
	if(jobId)
		url += '?jobId='+ jobId;

	if(!url) return false;
	$.get(url,function(resp){
		if(resp && resp.code == 0){
			if(resp.textList)
				textOptionData = buildOptionData(resp.textList);
			if(resp.exceLList)
				excelOptionData = buildOptionData(resp.exceLList);
		}

		if(resp.contents){
			resp.contents.forEach(function(item,i){
				var option = textOptionData.filter(o => o.value == item.contentId)[0] || '';
				if(option)
					option.selected = true;
				var option1 = excelOptionData.filter(o => o.value == item.contentId)[0] || '';
				if(option1)
					option1.selected = true;
			});
		}

		initOptions('textEmailContentId',textOptionData);
		initOptions('excelEmailContentId',excelOptionData);
	});
}


function buildOptionData(list){
	if(!list)
		return [];
	var _arr = [];
	list.forEach(function(item,index){
// {label: 'Option 1', title: 'OptionSS 1', value: '1', selected: true,disabled:true},
		var obj = {};
		obj.label = item.name;
		obj.title = item.name;
		obj.value = item.id + '';
		obj.selected = false;
		obj.disabled = false;
		_arr.push(obj);
	});

	return _arr;
}
function initOptions(id,optionData){
	$('#'+id).multiselect({
		   maxHeight: 160,
		   buttonWidth:260,  // 选择框的大小
		   numberDisplayed:6,
		   optionClass: function(element) {
		       var value = $(element).parent().find($(element)).index();
		       if (value%2 == 0) {
		           return 'even';
			   } else {
			       return 'odd';
			       }
			   },
		   enableCaseInsensitiveFiltering: true,// 开启模糊搜索
		   includeSelectAllOption: true,// 是否现实全选
		   nonSelectedText: '请选择', // 默认展示文本
		   selectAllText: '全选',  // 全选文本
		   allSelectedText: '全选'
		}).multiselect('dataprovider', optionData);
}

