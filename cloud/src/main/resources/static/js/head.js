var iw = -44;

setLineLeft(fact($(".nav ul li.active")));
$(".nav ul li").mouseover(function(){
	setLineLeft(fact(this));
});
$(".nav ul li").mouseout(function(){
	setLineLeft(fact($(".nav ul li.active")));
});

function fact(tag){
	var l = $(tag).position().left;
	var w = $(tag).width();
	var j = l+(w/2);
	var left = l+(w-iw)/2;
	return left;
}

function setLineLeft(num){
	num += 'px';
	$(".nav-line").stop().animate({"left":num},700,null);
}