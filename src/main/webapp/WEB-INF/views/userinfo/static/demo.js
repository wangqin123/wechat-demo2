/*$(function(){

	var location1 = (window.location+'').split('/');
	var basePath = location1[0]+'//'+location1[2];
	wx.ready(function(){
		wx.chooseImage({
		    count: 1, // 默认9
		    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
		    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
		    success: function (res) {
		        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
		    }
		});
		
	});
	
	
	$(".title li").click(function(){
		$(".title li").removeClass("active");
		$(this).addClass("active");
		alert(3333);
		 //window.location.href = basePath+'/center/medicalnsuranceDetails.html?returnUrl='+basePath+'/center/userinfo.html';
		
		
	$.ajax({
			type:"post",
			url:basePath+"/center/addInfo/v1",
			data:{},
			dataType:"json",
			success:function(data){
				alert(111);
				console.log(data);
			}
		});
		
		
		
	});
});

*/

define(['css2!/views/userinfo/static/demo.css'],function(){
    var location1 = (window.location+'').split('/');
	var basePath = location1[0]+'//'+location1[2];
	wx.ready(function(){
		wx.chooseImage({
		    count: 1, // 默认9
		    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
		    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
		    success: function (res) {
		        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
		    }
		});
		
	});
	
	
	$(".title li").click(function(){
		$(".title li").removeClass("active");
		$(this).addClass("active");
		//alert(3333);
		 window.location.href = basePath+'/center/medicalnsuranceDetails.html?returnUrl='+basePath+'/center/userinfo.html';
		
		
	$.ajax({
			type:"post",
			url:basePath+"/center/addInfo/v1",
			data:{},
			dataType:"json",
			success:function(data){
				alert(111);
				console.log(data);
			}
		});
		
		
		
	});
	
	
   
});
