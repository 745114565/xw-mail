$(function() {
	buildMultiSelectBox();
	validateRule();
});

/**
 * 创建多选框
 *
 * @returns {Boolean}
 */
function buildMultiSelectBox(){
	var optionData = [];
	var url = '/mail/biEmailExcelSheetConfig/list2option?excelId=' + $('#id').val();
	$.get(url,function(resp){
		if(resp && resp.code == 0   && resp.list)
			optionData = buildOptionData(resp.list);
		if(resp.ess){
			resp.ess.forEach(function(item,i){
				var option = optionData.filter(o => o.value == item.sheetId)[0] || '';
				if(option)
					option.selected = true;
			});
		}
		initOptions('sheets',optionData);
	});
}

function buildOptionData(list){
	if(!list)
		return [];
	var _arr = [];
	list.forEach(function(item,index){
// {label: 'Option 1', title: 'OptionSS 1', value: '1', selected: true,disabled:true},
		var obj = {};
		obj.label = item.sheetName;
		obj.title = item.sheetName;
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
		   nonSelectedText: '请选择Sheet', // 默认展示文本
		   selectAllText: '全选',  // 全选文本
		   allSelectedText: '全选',
		   onChange: function(option,checked){
		     multSelectOnChange(option,checked);
		   }
		}).multiselect('dataprovider', optionData);
}

function multSelectOnChange(option,checked){
    var splitStr = '\u0009';
	var str = $('#fileDesc').text();
	var cLabel = splitStr + ($(option)[0].label || '') + splitStr;
	if(checked){
		str += cLabel;
	}else{
		str = str.replace(cLabel,'');
	}
    $('#fileDesc').text(str);
}

$.validator.setDefaults({
	submitHandler : function() {
		submit();
	}
});

/**
 * 提交数据
 */
function submit() {

	$.ajax({
		cache : true,
		type : "GET",
		url : "/mail/biEmailExcelConfig/update",
		data : $('#signupForm').serialize(),
		async : false,
		error : function(request) {
			laryer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("保存成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
			} else {
				layer.alert(data.msg)
			}
		}
	});
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			},
			fileDesc : {
				required: true
			}
		},
		messages : {
			name : {
				required : icon + "请输入Excel名"
			},
			fileDesc : {
				required : icon + "请输入文件描述"
			}
		}
	})
}
