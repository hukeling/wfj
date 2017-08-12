<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>加盟王府井</title>
<link rel="stylesheet" type="text/css" href="../../../css/main.css" />
<link rel="stylesheet" type="text/css" href="../../../css/wfj.css" />
<link href="../../../css/css.css" rel="stylesheet" type="text/css"
	media="all">

<script type="text/javascript" src="../../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="../../../js/koala.min.1.5.js"></script>
</head>
<body>
	<%@ include file="../include/header.jsp"%>


	<div class="wraper">

		<div class="container">
			<div id="TimeLine">
				<div class="lines">&nbsp;</div>
				<div class="item">
					<div class="item-wraper">
						<strong>公司简介</strong>
						<div class="aa">
							<span>北京华赢智创科技有限公司</span>成立于2015年，为华赢集团旗下子公司之一。公司依托多年来积累的行业资源和管理经验，建设了区域性综合商业体O2O模式的电子商务平台
							⎯⎯即智慧王府井电子商务平台，该平台为北京市东城区政府信息化立项项目，得到官方授权。
						</div>
						<img src="jiameng/jianjie.jpg" />
					</div>
				</div>

				<div class="item">
					<div class="item-wraper">
						<!-- 代码 开始 -->
						<div id="fsD1" class="focus">
							<div id="D1pic1" class="fPic">
								<div class="fcon" style="display: none;">
									<a target="_blank" href=""><img src="jiameng/jianjie-2.jpg"
										style="opacity: 1;"></a>
								</div>

								<div class="fcon" style="display: none;">
									<a target="_blank" href=""><img src="jiameng/jianjie-3.jpg"
										style="opacity: 1;"></a>
								</div>
							</div>
							<div class="fbg">
								<div class="D1fBt" id="D1fBt">
									<a href="javascript:void(0)" hidefocus="true" target="_self"
										class=""><i>1</i></a> <a href="javascript:void(0)"
										hidefocus="true" target="_self" class=""><i>2</i></a>

								</div>
							</div>
							<!--<span class="prev"></span>-->
							<!--<span class="next"></span>-->
						</div>
						<script type="text/javascript">
							Qfast.add('widgets', {
								path : "js/terminator2.2.min.js",
								type : "js",
								requires : [ 'fx' ]
							});
							Qfast(false, 'widgets', function() {
								K.tabs({
									id : 'fsD1', //焦点图包裹id
									conId : "D1pic1", //** 大图域包裹id
									tabId : "D1fBt",
									tabTn : "a",
									conCn : '.fcon', //** 大图域配置class
									auto : 1, //自动播放 1或0
									effect : 'fade', //效果配置
									eType : 'click', //** 鼠标事件
									pageBt : true,//是否有按钮切换页码
									bns : [ '.prev', '.next' ],//** 前后按钮配置class
									interval : 3000
								//** 停顿时间
								})
							})
						</script>
						<!-- 代码 结束 -->
					</div>
				</div>


				<div class="item">
					<div class="item-wraper">
						<strong>关于平台</strong>
						<div class="aa">
							智慧王府井电子商务平台为消费者提供一条龙的区域化本地服务。最终实现区域独立、全国互联、品牌共享，同时可以在全国形成商业综合体联盟，占领全国的区域型商业综合体的互联网市场，
							实现区域型商业综合体的招商、广告推广、线上线下交易及金融服务。</div>
						<img src="jiameng/jianjie-7.jpg" /> <img
							src="jiameng/jianjie-8.jpg" />
					</div>
				</div>

				<div class="item">
					<div class="item-wraper">
						<strong>智慧王府井O2O电商模式</strong>
						<div class="aa">
							智慧王府井电子商务平台充分结合O2O电子商务成熟模型与城市综合商业体模式，平台依托王府井本身的品牌优势及政府的强力支持，结合区域综合商务体模式及区域本地化特点，
							是集旅游、商务、娱乐、休闲、餐饮等为一体的综合性的电商平台，并以王府井自身品牌优势，迅速占领互联网O2O市场，满足中高端电商消费者的消费需求，并推动商家从传统行业走向互联网营销的突破，
							扩大销售途径，增加销售手段、扩大销售份额，抢占市场先机。</div>
						<!-- 代码 开始 -->
						<div id="fsD2" class="focus">
							<div id="D1pic2" class="fPic">
								<div class="fcon" style="display: none;">
									<a target="_blank" href=""><img src="jiameng/jianjie-4.jpg"
										style="opacity: 1;"></a>
								</div>

								<div class="fcon" style="display: none;">
									<a target="_blank" href=""><img src="jiameng/jianjie-5.jpg"
										style="opacity: 1;"></a>
								</div>

								<div class="fcon" style="display: none;">
									<a target="_blank" href=""><img src="jiameng/jianjie-6.jpg"
										style="opacity: 1;"></a>
								</div>
							</div>
							<div class="fbg">
								<div class="D1fBt" id="D1fBt2" style="display: none;">
									<a href="javascript:void(0)" hidefocus="true" target="_self"
										class="current"><i>1</i></a> <a href="javascript:void(0)"
										hidefocus="true" target="_self" class=""><i>2</i></a> <a
										href="javascript:void(0)" hidefocus="true" target="_self"
										class=""><i>3</i></a> <a href="javascript:void(0)"
										hidefocus="true" target="_self" class=""><i>4</i></a> <a
										href="javascript:void(0)" hidefocus="true" target="_self"
										class=""><i>5</i></a>
								</div>
							</div>
							<span class="prev"></span> <span class="next"></span>

						</div>
						<script type="text/javascript">
							Qfast.add('widgets', {
								path : "js/terminator2.2.min.js",
								type : "js",
								requires : [ 'fx' ]
							});
							Qfast(false, 'widgets', function() {
								K.tabs({
									id : 'fsD2', //焦点图包裹id
									conId : "D1pic2", //** 大图域包裹id
									tabId : "D1fBt2",
									tabTn : "a",
									conCn : '.fcon', //** 大图域配置class
									auto : 1, //自动播放 1或0
									effect : 'fade', //效果配置
									eType : 'click', //** 鼠标事件
									pageBt : true,//是否有按钮切换页码
									bns : [ '.prev', '.next' ],//** 前后按钮配置class
									interval : 3000
								//** 停顿时间
								})
							})
						</script>
						<!-- 代码 结束 -->
					</div>
				</div>


				<div class="item">
					<div class="item-wraper">
						<strong>平台特点</strong>
						<div class="aa " style="text-indent: 0">
							<span>※联合成功的商业品牌共同打造最具实效的商业模式</span><br />
							<p>通过互联网新模式将系统解决王府井众多商家“各自为战”的商业模式，共同打造互联网商业品牌，发挥各自的商业特色，为消费者提供同一品牌的消费服务。</p>
							<span>※打造社交型商业综合体</span><br />
							<p>集商务、旅游、娱乐、休闲、餐饮等于一体，为消费者提供一条龙的区域化本地服务。是
								一个全国互通、区域独立、各行业商业活动相互结合的综合电商平台;
								最终实现区域独立、全国互联、品牌共享，同时形成商业综合体联盟，占领全国的区域型商业综合体的互联网市场，实现区域型商业综合体的招商、广告推广、互联网营销、线上线下交易及金融服务。
								将所有商家、消费信息、消费者聚合，使得商家、消费者、产品服务都实现在同城，形成区域化的生态链和商务环境。</p>
							<span>※打造区域化（王府井）线下与线上相结合的网络销售</span><br /> <span>※打造商旅结合，形成以旅游为主线的区域化店连店的商业新模式</span><br />
							<span>※互联网金融与商业相结合</span><br />
							<p>第三方支付提供网络支付结算服务，为客户提供方便快捷，随时随地、多种手段 的互联网、移动支付手段及结算服务；
								电商金融服务以信用证贷为基础，为消费者或商家提供资金小额贷款服务；
								提供更灵活的个人金融服务，促进消费。比如：先付费再消费、先消费后付款、分期购买、项目融资、基金等服务；
								以金融服务为保障，开展适合中高端客户的私人订制的商务活动或其他金融理财 服务</p>
							<span>※辅助商家经营管理，为商家提供科学、高效的网络营销工具</span><br /> <img
								src="jiameng/jianjie-10.jpg" />
						</div>
					</div>
				</div>


				<div class="item">
					<div class="item-wraper">
						<strong>功能亮点</strong> <img src="jiameng/jianjie-9.jpg" />
					</div>
				</div>


				<div class="item">
					<div class="item-wraper">
						<strong>平台优势</strong> <img src="jiameng/jianjie-11.jpg" />
					</div>
				</div>


				<div class="item">
					<div class="item-wraper">
						<strong>招商加盟</strong> <img src="jiameng/jianjie-12.jpg" />
					</div>
				</div>

				<div class="item">
					<div class="item-wraper">
						<strong>展望未来</strong>
						<p>智慧王府井电商服务平台充分结合O2O电子商务成熟模型与城市综合商业体模式，依托北京市政府的强力支持及王府井本身的品牌优势，
							必将以星火燎原之势将王府井电子商务平台推广到全国及国际市场，成为区域化生活服务电商的领头军。</p>
						<p>智慧王府井电商服务平台以全新的商业综合体模式运营，打破传统电商运营模式，通过互联网+商业地产，以实现全国范围的商业地产商业增容为目标，真诚与各商业伙伴共创未来。</p>
						<img src="jiameng/jianjie-13.jpg" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="../../../js/jquery.min.js"></script>
	<script type="text/javascript" src="../../../js/smohan.timeline.min.js"></script>
	<script type="text/javascript">
		$(window).load(function() {
			$(window).bind('resize', function() {
				$('#TimeLine').smohanTimeline({});
			}).resize();
		})
	</script>

	<!--尾部-->
	<div class="tail_box">
		<ul class="tail">
			<li><img src="images/6_27.png" />

				<p>
					100%<span>正品保证</span>
				</p></li>
			<li><img src="images/6_29.png" />

				<p>
					10天<span>退换货</span>
				</p></li>
			<li><img src="images/6_31.png" />

				<p>
					10天调价<span>补差额</span>
				</p></li>
			<li><img src="images/6_34.png" />

				<p>
					7X24小时<span>在线客服</span>
				</p></li>
		</ul>
		<div class="link_box">
			<ul class="bangzhu">
				<li>
					<p>
						<a href="">购物流程</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">关于注册</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">注册协议</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">关于登录</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">购物流程指南</a>
					</p>
				</li>
			</ul>
			<ul class="bangzhu">
				<li>
					<p>
						<a href="">物流配送</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">配送方式</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">配送查询</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">运费说明</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">签收与拒收</a>
					</p>
				</li>
			</ul>
			<ul class="bangzhu">
				<li>
					<p>
						<a href="">支付方式</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">关于支付</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">个人在线支付</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">线下汇款</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">信用支付</a>
					</p>
				</li>
			</ul>
			<ul class="bangzhu">
				<li>
					<p>
						<a href="">售后服务</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">订单取消</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">退换货政策</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">发票制度</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">保修及维修</a>
					</p>
				</li>
			</ul>
			<ul class="bangzhu">
				<li>
					<p>
						<a href="">会员服务</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">会员说明</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">招募供应商</a>
					</p>
				</li>
				<li>
					<p>
						<a href="">供应商登录</a>
					</p>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>