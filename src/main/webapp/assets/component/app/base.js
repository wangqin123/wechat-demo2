$(function(){
	
	var location1 = (window.location+'').split('/');
	var basePath = location1[0]+'//'+location1[2];
	$.ajax({
		   type:"post",
		   url:basePath+"/js/jssdk/config",
		   data:{"url":location.href.split('#')[0]},
		   dataType:"json",
		   success:function(data){
			   
			   console.log(data);
			   wx.config({
				    debug: true,
				    appId: data.appId ,
				    timestamp: data.timestamp ,
				    nonceStr: data.nonceStr,
				    signature: data.signature,
				    jsApiList: [
				      'checkJsApi',
				      'onMenuShareTimeline',
				      'onMenuShareAppMessage',
				      'onMenuShareQQ',
				      'onMenuShareWeibo',
				      'hideMenuItems',
				      'showMenuItems',
				      'hideAllNonBaseMenuItem',
				      'showAllNonBaseMenuItem',
				      'translateVoice',
				      'startRecord',
				      'stopRecord',
				      'onRecordEnd',
				      'playVoice',
				      'pauseVoice',
				      'stopVoice',
				      'uploadVoice',
				      'downloadVoice',
				      'chooseImage',
				      'previewImage',
				      'uploadImage',
				      'downloadImage',
				      'getNetworkType',
				      'openLocation',
				      'getLocation',
				      'hideOptionMenu',
				      'showOptionMenu',
				      'closeWindow',
				      'scanQRCode',
				      'chooseWXPay',
				      'openProductSpecificView',
				      'addCard',
				      'chooseCard',
				      'openCard'
				    ]
				});
		   }
	});

	
	

	
	
	
});