$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/mail/biEmailSenderConfig/update",
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
    			emailName : {
    				required : true
    			},
    			emailAddr : {
    				required: true,
    		        email: true
    			},
    			emailPwd : {
    				required : true
    			},
    			mailTransportProtocol : {
    				required : true
    			},
    			mailSmtpHost : {
    				required : true
    			}
    		},
    		messages : {
    			emailName : {
    				required : icon + "请输入邮箱名称"
    			},
    			emailAddr : {
    				required : icon + "请输入正确的邮箱地址"
    			},
    			emailPwd : {
    				required : icon + "请输入邮箱登录密码"
    			},
    			mailTransportProtocol : {
    				required : icon + "请输入邮箱协议"
    			},
    			mailSmtpHost : {
    				required : icon + "请输入邮箱服务器"
    			}
    		}
    	})
}