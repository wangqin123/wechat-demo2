require.config({
	//利用 urlArgs 解决 RequireJS 的缓存问题 随机数
	urlArgs: function( config, moduleName, url ) {
		if ( moduleName.indexOf( "/app/" ) != -1 ) {
			//return "2.250-snapshots";
			return "version="+Math.random() * 10;
		}
		if ( moduleName.indexOf( "/views/" ) != -1 ) {
			return "version="+Math.random() * 10;
		}
		return "";
		//return Math.random() * 10;
	},
	
		paths: {
			
			    "...": "..."
				//require-css 加载css
				, "require.css/css": ["assets/component/require-css/0.1.8/css.min"]

				//require-css-extend 扩展加载css
				, "require.css.extend/css.extend": "assets/component/require-css-extend/0.1.8/css.extend.min"
				
				, "jquery": "assets/component/jquery/1.11.3/jquery.min"
				, "jweixin": [ "http://res.wx.qq.com/open/js/jweixin-1.0.0", "assets/component/jweixin/jweixin-1.0.0" ]
				, "base": "assets/component/app/base"
					
		},
		//映射
		map: {
			"*": { "css2": "require.css.extend/css.extend" }
		},
		
		//依赖
		shim: {
			//css 依赖加载器
			"require.css.extend/css.extend": {  deps: [ "require.css/css" ] }
		}
		});
	
	
	( function() {
		require(["jquery", "base","require.css/css","require.css.extend/css.extend"], function($){
			$(function(){
				 });
		});
			 
		
	})();
	
		  
	
