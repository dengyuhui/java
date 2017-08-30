$(".redu-btn").click(function(){
	inputNumFactory($(this).next(),false);
});

$(".add-btn").click(function(){
	inputNumFactory($(this).prev(),true);
});

function inputNumFactory(tag,b){
	var limit = $(tag).attr("data-limit");
	var v = $(tag).val();
	if(b){
		v++;
		if(v <= limit){
			$(tag).val(v);
		}
	}else{
		v--;
		if(v>0){
			$(tag).val(v);
		}
	}
}

$(".text-num").blur(function(){
	var limit = $(this).attr("data-limit");
	var v = $(this).val();
	var reg = /^\d*$/
	limit = parseInt(limit);
	if(reg.test(v) && v > 0 && v <= limit){
		$(this).val(v);
	}else{
		$(this).val(1);
	}
});

$(".btn-sel-box input[type='button']").click(function(){
	var inputs = $(this).siblings("input[type='button']");
	$(inputs).removeClass("active");
	$(this).addClass("active");
});
$(".input-num-kd").blur(function(){
	var v = $(this).val();
	v = v > 100 ? 100 : v < 1 ? 1: v;
	$(this).val(v);
	var vStr = v+"%";
	$(".jdt-bg-color").animate({"width":vStr},1000,null);
	$(".jdt-btn").animate({"left":vStr},1000,null);
});

var scale = function (btn,bar,title){
	this.btn=document.getElementById(btn);
	this.bar=document.getElementById(bar);
	this.title=document.getElementById(title);
	this.step=this.bar.getElementsByTagName("div")[0];
	this.init();
};
scale.prototype={
	init:function (){
		var f=this,g=document,b=window,m=Math;
		f.btn.onmousedown=function (e){
			var x=(e||b.event).clientX;
			var l=this.offsetLeft;
			/*var max=f.bar.offsetWidth-this.offsetWidth;*/
			var max=f.bar.offsetWidth;
			g.onmousemove=function (e){
				var thisX=(e||b.event).clientX;
				var to=m.min(max,m.max(-2,l+(thisX-x)));
				f.btn.style.left=to+'px';
				f.ondrag(m.round(m.max(0,to/max)*100),to);
				b.getSelection ? b.getSelection().removeAllRanges() : g.selection.empty();
			};
			g.onmouseup=new Function('this.onmousemove=null');
		};
	},
	ondrag:function (pos,x){
		if(pos < 1){
			this.step.style.width='1%';
			this.btn.style.left="1%";
			this.title.value = 1;
		}else{
			this.step.style.width=Math.max(0,x)+'px';
			this.title.value = pos;
		}
	}
}
new scale('jdt-btn','jdt-box','input-num-kd');