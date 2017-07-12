<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; if(basePath == null || basePath.equals("")) basePath = "/"; pageContext.setAttribute("basePath", basePath);%>
<!DOCTYPE>
<html>
<head>
    <title>信息页面</title>
    <base href="${basePath}" />
    <meta charset="UTF-8" /><meta content="text/html"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
	<meta content="yes" name="apple-mobile-web-app-capable" />
	<meta content="black" name="apple-mobile-web-app-status-bar-style" /> 
	<meta content="telephone=no" name="format-detection" />
	<link type="image/x-icon" rel="shortcut icon" href="favicon.ico" />
    <link type="text/css" rel="stylesheet" href="assets/component/frozenui/css/basic.css"/>
	<link type="text/css" rel="stylesheet" href="assets/component/frozenui/css/frozen.css"/>
	<script type="text/javascript">
		Global( function() {
			var $ = require( "jquery" );
			setTimeout( function() {
				$( ".app-page-html" ).show();
				$( "#globalLoading" ).fadeOut();
				$( ".app-page-body" ).show();
			}, 500 );
		} );
	</script>
</head>
<body style="background: #f5f5f5 url('assets/component/app/img/paper.jpg') left top repeat; color: ">
<section class="">
    <p style="line-height: 85px; text-align: center; font-size: 18px; font-family: 微软雅黑;">请在微信扫描关注</p>
    <div class="ui-placehold-img" style="padding-top: 0;">
	    <img alt="微信二维码关注" src="wap/qrcode" />
    </div>
</section>
</body>
</html>