/*首页banner*/
jQuery(".banner").slide({
	titCell: ".hd ul",
	mainCell: ".bd ul",
	effect: "fold",
	autoPlay: true,
	autoPage: true,
	trigger: "click"
});
//产品图片滚动效果
	jQuery(".proscroll").slide({ mainCell:".prolist", effect:"leftLoop",vis:5, autoPlay:true});
//商品详情切换
	jQuery(".pro-detail").slide({ titCell:".tab-hd li",trigger:"click", mainCell:".tab-bd",delayTime:0 });
//专场详情图片切换
	jQuery(".spdetail").slide({ mainCell:".bd ul",effect:"fold",autoPlay:true,delayTime:200 });

