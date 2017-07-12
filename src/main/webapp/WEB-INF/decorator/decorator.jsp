<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
	if(basePath == null || basePath.equals("")) 
		basePath = "/"; 
	pageContext.setAttribute("basePath", basePath);
	pageContext.setAttribute("version", new java.util.Date().getTime());
%>

<!DOCTYPE html>
  <html>
 <head>
 <base href="${basePath }" />
 
  <meta charset="UTF-8" />
  <meta content="text/html"/>
	<meta name="msapplication-tap-highlight" content="no" /><!--  去掉Windows Phone点击时的高亮效果 -->
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
	<meta content="yes" name="apple-mobile-web-app-capable" />
	<meta content="black" name="apple-mobile-web-app-status-bar-style" /> 
	<meta content="telephone=no" name="format-detection" />
	<link type="image/x-icon" rel="shortcut icon" href="/assets/images/favicon.ico" />
	<script type="text/javascript" src="/assets/component/jweixin/jweixin-1.0.0.js" ></script> 
	<script type="text/javascript" src="assets/component/requirejs/2.1.11/require.js" data-main=""></script>
	<script type="text/javascript" src="assets/config.js?v=${version }"></script>
	
	<%-- <script type="text/javascript" src="/assets/component/jquery/1.11.3/jquery.min.js" ></script>
	<script type="text/javascript" src="/assets/component/app/base.js?v=${version }" ></script>--%>

	
	<title><sitemesh:write property='title' /></title>
	<sitemesh:write property='head' />
  </head>
  <body>
  
  
     <sitemesh:write property='body' />
 </body>
 </html>