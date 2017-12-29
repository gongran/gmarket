<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/index.css" rel="stylesheet">
<script src="/js/jquery-3.2.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/echarts.js"></script>

<link rel="icon" href="/img/favicon.ico">
<title>accounts</title>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Market${name }</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">Home</a></li>
				<li><a href="#about">About</a></li>
				<li><a href="#contact">Contact</a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
	</nav>

	<div class="container">
		<!-- 	<div class="starter-template">
			<h1>Block Chain</h1>
			<p class="lead">111</p>
		</div> -->
		<div class="row">
			<div class="col-lg-6 col-md-6 col-xs-6 col-sm-6">
				<label> <input type="checkbox"/>隐藏零资产
				</label>
			</div>
		</div>
		<div class="row" id="accdiv">
			<%-- <c:forEach items="${result}" var="item">
				<div class="col-lg-6 col-md-6 col-xs-6 col-sm-6">
					<div class="row">
						<h2>
							<c:choose>
								<c:when test="${item.type=='spot'}">  
								币币交易账户
							</c:when>
								<c:when test="${item.type=='margin'}">
								杠杆交易账户
						</c:when>
								<c:when test="${item.type=='otc'}">
								法币交易账户
							</c:when>
							</c:choose>
						</h2>
					</div>
					<div class="row">
						<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
							<p>
								<b>账户类型:</b>
								<c:choose>
									<c:when test="${item.type=='spot'}">  
								币币交易
							</c:when>
									<c:when test="${item.type=='margin'}">
								杠杆
							</c:when>
									<c:when test="${item.type=='otc'}">
								法币
							</c:when>
								</c:choose>
							</p>
						</div>
						<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
							<b>状态:</b>${item.state }

						</div>
						<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
							<label> <input type="checkbox">隐藏零资产
							</label>
						</div>
					</div>
					<br />
					<c:forEach items="${item.list }" var="litem">
						<div class="row">
							<div class="col-lg-2">
								<p>
									<b>${litem.currency }</b>
								</p>
							</div>
							<div class="col-lg-5">
								<p>可用金额：${litem.v_trade }</p>
							</div>
							<div class="col-lg-5">
								<p>冻结金额：${litem.v_frozen }</p>
							</div>
						</div>
					</c:forEach>
					<!-- <a class="btn btn-secondary" href="#" role="button">View
							details &raquo;</a> -->

				</div>
			</c:forEach> --%>
		</div>
	</div>
	<script src="account/accounts.js"></script>
</body>
</html>