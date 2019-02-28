$().ready(function() {
	validateRule();
	geneCodeEvent();
});
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
		type : "POST",
		url : "/mail/biEmailSheetConfig/update",
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
			sheetName : {
				required : true
			},
			titles : {
				required: true
			},
			coloums : {
				required: true
			},
			qSql : {
				required: true
			},
			dataSource : {
				required: true
			}
		},
		messages : {
			sheetName : {
				required : icon + "请输入Sheet名"
			},
			titles : {
				required : icon + "sheet表头"
			},
			coloums : {
				required : icon + "请输入数据库列名"
			},
			qSql : {
				required : icon + "请填入查询的SQL"
			},
			dataSource : {
				required : icon + "请选择数据源"
			}
		}
	})
}
function geneCodeEvent(){
    $('#geneCodeBtn').on('click',function(e){
        $('#geneCodeBlock').removeClass('hidden');
        buildMultiSelectBox();
    });
}

function hiddenGeneCodeBtn(){
    $('#geneCodeBlock').addClass('hidden');
}

function geneCodeAction(){
    var tableId = $('#tables').val();
    var url = '/mail/biEmailSheetConfig/geneSheetConfigs';
    $.get(url,{tableName:tableId},function(resp){
        if(resp.code == 0){
            var sheet = resp.sheet;
            $('#sheetName').val(sheet.sheetName);
            $('#titles').val(sheet.titles);
            $('#coloums').val(sheet.coloums);
            $('#qSql').val(sheet.qsql);
        }
    });
}

/**
 * 创建多选框
 *
 * @returns {Boolean}
 */
function buildMultiSelectBox(){
	var optionData = [];
	var url = '/common/generator/list';
	$.get(url,{},function(resp){
		optionData = buildOptionData(resp || []);
		initOptions('tables',optionData);
	});
}

function buildOptionData(list){
	if(!list)
		return [];
	var _arr = [];
	list.forEach(function(item,index){
// {label: 'Option 1', title: 'OptionSS 1', value: '1', selected: true,disabled:true},
		var obj = {};
		obj.label = item.tableComment;
		obj.title = item.tableComment;
		obj.value = item.tableName;
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
		   nonSelectedText: '请选择表', // 默认展示文本
//		   selectAllText: '全选',  // 全选文本
//		   allSelectedText: '全选'
		}).multiselect('dataprovider', optionData);
}


function tryOut(){
    var sheetName = $('#sheetName').val();
    var titles = $('#titles').val();
    var coloums = $('#coloums').val();
    var qsql = $('#qSql').val();
    if(!sheetName){
        layer.alert("sheet名不能为空")
        return ;
    }
    if(!titles){
        layer.alert("sheet表头不能为空")
        return ;
    }
    if(!coloums){
        layer.alert("列名不能为空")
        return ;
    }
    if(!qsql){
        layer.alert("SQL不能为空")
        return ;
    }
    var colArr = coloums.split(',');
    var titleArr = titles.split(',');
    if(titleArr.length != colArr.length){
        layer.alert('sheet表头与列名个数不一致');
        return ;
    }

    var columnsConfig = [];
    for(var i = 0; i < titleArr.length;i++ ){
        var obj = {};
        obj.field = colArr[i];
        obj.title = titleArr[i];
        columnsConfig.push(obj);
    }
    initTable(columnsConfig);


    var url = '/mail/biEmailSheetConfig/tryOut';
    $.get(url,{sql:qsql},function(res){
        if(res.code == 0){
           var list = res.list || [];
           $('#exampleTable').bootstrapTable('load',  list);
        }
    })


}

function initTable(columnsConfig){
$('#exampleTable').bootstrapTable('destroy')
    .bootstrapTable({
            iconSize : 'outline',
            toolbar : '#exampleToolbar',
            striped : true, // 设置为true会有隔行变色效果
            dataType : "json", // 服务器返回的数据类型
            pagination : true, // 设置为true会在底部显示分页条
            singleSelect : false, // 设置为true将禁止多选
            pageSize : 10, // 如果设置了分页，每页数据条数
            pageNumber : 1, // 如果设置了分布，首页页码
            showRefresh : false, //显示刷新按钮
            showColumns : false, // 是否显示内容下拉框（选择显示的列）
            sidePagination : "client", // 设置在哪里进行分页，可选值为"client" 或者 "server"
             columns : columnsConfig,
             data: []
     })
}