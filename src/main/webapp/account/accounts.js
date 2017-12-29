$(document).ready(function() {
	getBlock(true);

	$("input[type='checkbox']").change(function() {
		if (this.checked) {
			getBlock(false);
		} else {
			getBlock(true);
		}

	});
	function getBlock(con) {
		$.ajax({
			type : "post",
			url : "/account02",
			data : {
				isAll : con
			},
			success : function(data) {
				dispaly(data);
			}
		});
	}

	function dispaly(data) {
		var html = "";
		for ( var acc in data) {
			html += '<div class="col-lg-6 col-md-6 col-xs-6 col-sm-6">';
			html += '<div class="row">';
			html += '<h2>';
			if (data[acc].type == "spot") {
				html += '币币交易账户';
			} else if (data[acc].type == "margin") {
				html += '杠杆交易账户';
			} else if (data[acc].type == "otc") {
				html += '法币交易账户';
			}
			html += '</h2>';
			html += '</div>';
			html += '<div class="row">';
			html += '<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">';
			html += '<p>';
			html += '<b>账户类型:</b>';
			if (data[acc].type == "spot") {
				html += '币币';
			} else if (data[acc].type == "margin") {
				html += '杠杆';
			} else if (data[acc].type == "otc") {
				html += '法币';
			}
			html += '</p>';
			html += '</div>';
			html += '<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">';
			html += '<b>状态:</b>' + data[acc].state;
			html += '</div>';
			html += '<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">';
			html += '';
			html += '</div>';
			html += '</div><br />';
			for ( var i in data[acc].list) {
				var bizhong = data[acc].list[i];
				html += '<div class="row">';
				html += '<div class="col-lg-2">';
				html += '<p><b>' + bizhong.currency + '</b></p>';
				html += '</div>';
				html += '<div class="col-lg-5">';
				html += '<p>可用数量:' + bizhong.v_trade + '</p>';
				html += '</div>';
				html += '<div class="col-lg-5">';
				html += '<p>冻结数量:' + bizhong.v_frozen + '</p>';
				html += '</div>';
				html += '</div>';
				html += '<div class="row">';
				html += '<div class="col-lg-2">';
				html += '</div>';
				html += '<div class="col-lg-5">';
				html += '<p>cny:' + bizhong.cny_trade + '</p>';
				html += '</div>';
				html += '<div class="col-lg-5">';
				html += '<p>cny:' + bizhong.cny_frozen + '</p>';
				html += '</div>';
				html += '</div>';
			}
			html += '</div>';
		}
		$("#accdiv").html(html);
	}
});