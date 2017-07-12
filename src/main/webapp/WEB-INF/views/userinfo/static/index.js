define(['css2!/views/userinfo/static/demo.css'],function(){

	var location1 = (window.location+'').split('/');
	var basePath = location1[0]+'//'+location1[2];
	
	wx.ready(function(){
		 wx.onMenuShareTimeline({
			    title: "${share.title}", // 分享标题
			    link: "${share.link}", // 分享链接
			    imgUrl: "${share.imgurl}", // 分享图标
			    success: function () { 
			        // 用户确认分享后执行的回调函数
			        alert(11111);
			    },
			    cancel: function () { 
			        // 用户取消分享后执行的回调函数
			    	  alert(22222);
			    },
			    fail: function (res) {
	                 alert("sdsds"+JSON.stringify(res));
	              }
		   });
		   wx.onMenuShareAppMessage({
			    title: "${share.title}", // 分享标题
			    desc: "${share.desc}", // 分享描述
			    link: "${share.link}", // 分享链接
			    imgUrl: "${share.imgurl}", // 分享图标
			    type: '', // 分享类型,music、video或link，不填默认为link
			    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
			    success: function () { 
			        // 用户确认分享后执行的回调函数
			    	  alert(33333);
			    },
			    cancel: function () { 
			        // 用户取消分享后执行的回调函数
			    	  alert(4444+"${share.title}");
			    },
			    fail: function (res) {
	                 alert("sdsdggg"+JSON.stringify(res));
	              }
			});	
		
	});
	
	function getParam(name){ 
		 var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
		 var r = window.location.search.substr(1).match(reg); 
		 if (r != null) return unescape(r[2]); return null; 
	}
	

	
	
	
	$("#share").click(function(){
		var returnUrl = decodeURIComponent(getParam("returnUrl")).replace("|", "=");
		returnUrl= returnUrl.replace(":80", "").replace("$", "?").replace(",", "&").replace("|", "=");
		
		alert(returnUrl);
		location.href = returnUrl;
	});
	
	
});