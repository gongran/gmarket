var myChart = echarts.init(document.getElementById('main'));
option = {
	title : {
		text : '比特币现金'
	},
	tooltip : {
		trigger : 'axis',
		formatter : function(params) {
			var res ="";
			res += '开盘 : ' + params[0].value[1] + '  最高 : '
					+ params[0].value[4]+"<br/>";
			res += '收盘 : ' + params[0].value[2] + '  最低 : '
					+ params[0].value[3];
			return res;
		}
	},
	legend : {
		data : [ '上证指数' ]
	},
	toolbox : {
		show : true,
		feature : {
			mark : {
				show : true
			},
			dataZoom : {
				show : true
			},
			dataView : {
				show : true,
				readOnly : false
			},
			magicType : {
				show : true,
				type : [ 'line', 'bar' ]
			},
			restore : {
				show : true
			},
			saveAsImage : {
				show : true
			}
		}
	},
	dataZoom : {
		show : true,
		realtime : true,
		start : 50,
		end : 100
	},
	xAxis : [ {
		type : 'category',
		boundaryGap : true,
		axisTick : {
			onGap : false
		},
		splitLine : {
			show : false
		},
		data : xAxis
	} ],
	yAxis : [ {
		type : 'value',
		scale : true,
		boundaryGap : [ 0.01, 0.01 ]
	} ],
	series : [ {
		name : '上证指数',
		type : 'k',
		data : yAxis
	} ]
};
myChart.setOption(option);