
$(function(){						//通栏左侧导航
	$(".nav_zoom").hide();
	$(".all_nav_z").hover(function(){
			x=$(".all_nav_z").index(this)
			$(".nav_zoom").eq(x).show();
		},function(){
			$(".nav_zoom").hide();
			});
})

$(function(){						//通栏左侧导航
	$(".every").hover(function(){
			x=$(".every").index(this)
			$(".every p").eq(x).animate({left:6},100);
        $(".every h4").eq(x).animate({left:6},100);
		},function(){
			$(".every p").eq(x).animate({left:0},100);
        $(".every h4").eq(x).animate({left:0},100);
			});
})

$(function(){
	/*整合图片，初始化*/
	var n=0;
	$(".banner img").hide();
	$(".banner img:first").show();
	/*鼠标指向按钮*/
	$(".ba_2").mouseover(function(){
		/*当前项添加whiteLi类*/
		$(".ba_2").removeClass("whiteLi");
		$(this).addClass("whiteLi");
		/*找到当前按钮索引值*/
		n = $(".ba_2").fadeIn(1000).index(this);	
		/*指定显示哪张图片*/
		$(".banner img").hide();
		$(".banner img:eq("+n+")").fadeIn(1000).show();
		/*指定显示哪个文本*/
	})
	/*动画的变化函数*/
	function changeImg(){
		if(n<$(".banner img").length-1){
			n=n+1;
		}else{
			n=0;
		}
		$(".banner img").hide();
		$(".banner img:eq("+n+")").fadeIn(1000).show();
		
		$(".ba_2").removeClass("whiteLi");
		$(".ba_2:eq("+n+")").addClass("whiteLi");
	}
	var timer = setInterval(changeImg,5000);
	/*控制定时器*/
	$(".banner").mouseover(function(){
		clearInterval(timer);	
	}).mouseout(function(){
		timer = setInterval(changeImg,5000);	
	})
})
$(function(){
	$(".ba_2").eq(0).addClass("whiteLi");
})

$(function(){								//css调用
	$(".fa_3").eq(0).addClass("fa_3T");
	$(".fa_3").mouseover(function(){
		xBtn=$(".fa_3").index(this);
		$(".fa_3").removeClass("fa_3T");
		$(this).addClass("fa_3T");
		})
})

$(function(){							//点击显示
	$(".recharge").hide();
	$(".recharge").eq(0).show();
	$(".fa_3").mouseover(function(){
		x=$(".fa_3").index(this);
		$(".recharge").hide();
		$(".recharge").eq(x).show();
		});
})

$(function(){						//通栏左侧导航
	$(".grid").hover(function(){
			x=$(".grid").index(this)
			$(".gr_3 img").eq(x).animate({left:-6},200);
		},function(){
			$(".gr_3 img").eq(x).animate({left:0},200);
			});
})

$(function(){             //楼层banner切换
	//b1
	$(".b1 img").hide();  
	$(".b1 img:first").show();
	var oSimg1 = 0;
	function imgoSimg1(){
		if(oSimg1<$(".b1 img").length-1){
			oSimg1=oSimg1+1;
			}else{
			oSimg1=0;	
			}
			 
		$(".b1 img").hide();
		$(".b1 img:eq("+oSimg1+")").fadeIn(444);
		$(".b1 .storey_index_li").removeClass("storey_index_li_red")
		$(".b1 .storey_index_li").eq(+oSimg1).addClass("storey_index_li_red")
		}
	
	var oSimg_time1 = setInterval(imgoSimg1,2000);
	$(".b1").mouseover(function(){
		clearInterval(oSimg_time1);
			}).mouseout(function(){
			oSimg_time1=setInterval(imgoSimg1,2000)	
	})
	
	$(".b1 .storey_index_li").eq(0).addClass("storey_index_li_red")
	$(".b1 .storey_index_li").mouseover(function(){
		oSimg1=$(".b1 .storey_index_li").index(this)
		$(".b1 img").hide();
		$(".b1 img:eq("+oSimg1+")").fadeIn(444);
		$(".b1 .storey_index_li").removeClass("storey_index_li_red")
		$(".b1 .storey_index_li").eq(+oSimg1).addClass("storey_index_li_red")
	})    
	
	//b2
	$(".b2 img").hide();  
	$(".b2 img:first").show();
	var oSimg2 = 0;
	function imgoSimg2(){
		if(oSimg2<$(".b2 img").length-1){
			oSimg2=oSimg2+1;
			}else{
			oSimg2=0;	
			}
			 
		$(".b2 img").hide();
		$(".b2 img:eq("+oSimg2+")").fadeIn(444);
		$(".b2 .storey_index_li").removeClass("storey_index_li_red")
		$(".b2 .storey_index_li").eq(+oSimg2).addClass("storey_index_li_red")
		}
	
	var oSimg_time2 = setInterval(imgoSimg2,2200);
	$(".b2").mouseover(function(){
		clearInterval(oSimg_time2);
			}).mouseout(function(){
			oSimg_time2=setInterval(imgoSimg2,2200)	
	})
	
	$(".b2 .storey_index_li").eq(0).addClass("storey_index_li_red")
	$(".b2 .storey_index_li").mouseover(function(){
		oSimg2=$(".b2 .storey_index_li").index(this)
		$(".b2 img").hide();
		$(".b2 img:eq("+oSimg2+")").fadeIn(444);
		$(".b2 .storey_index_li").removeClass("storey_index_li_red")
		$(".b2 .storey_index_li").eq(+oSimg2).addClass("storey_index_li_red")
	})    
	
	//b3
	$(".b3 img").hide();  
	$(".b3 img:first").show();
	var oSimg3 = 0;
	function imgoSimg3(){
		if(oSimg3<$(".b3 img").length-1){
			oSimg3=oSimg3+1;
			}else{
			oSimg3=0;	
			}
			 
		$(".b3 img").hide();
		$(".b3 img:eq("+oSimg3+")").fadeIn(444);
		$(".b3 .storey_index_li").removeClass("storey_index_li_red")
		$(".b3 .storey_index_li").eq(+oSimg3).addClass("storey_index_li_red")
		}
	
	var oSimg_time3 = setInterval(imgoSimg3,2100);
	$(".b3").mouseover(function(){
		clearInterval(oSimg_time3);
			}).mouseout(function(){
			oSimg_time3=setInterval(imgoSimg3,2100)	
	})
	
	$(".b3 .storey_index_li").eq(0).addClass("storey_index_li_red")
	$(".b3 .storey_index_li").mouseover(function(){
		oSimg3=$(".b3 .storey_index_li").index(this)
		$(".b3 img").hide();
		$(".b3 img:eq("+oSimg3+")").fadeIn(444);
		$(".b3 .storey_index_li").removeClass("storey_index_li_red")
		$(".b3 .storey_index_li").eq(+oSimg3).addClass("storey_index_li_red")
	})    
	
})