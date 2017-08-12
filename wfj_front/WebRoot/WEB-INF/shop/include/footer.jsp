<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="clear"></div>

<!--尾部-->
<div class="tail_box">
	<ul class="tail">
		<li><img
			src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/images/6_27.png" />
			<p>
				100%<span>正品保证</span>
			</p></li>
		<li><img
			src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/images/6_29.png" />
			<p>
				10天<span>退换货</span>
			</p></li>
		<li><img
			src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/images/6_31.png" />
			<p>
				10天调价<span>补差额</span>
			</p></li>
		<li><img
			src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/images/6_34.png" />
			<p>
				7X24小时<span>在线客服</span>
			</p></li>
	</ul>
	<div class="link_box">
		<ul class="bangzhu">
			<li>
				<h3>
					<a href="">购物流程</a>
				</h3>
			</li>
			<s:iterator value="#request.bottomNewsLink" var="a">
				<s:if test="#a.categoryId==93">
					<li>
						<p>
							<a href=""><s:property value="#a.title" /></a>
						</p>
					</li>
				</s:if>
			</s:iterator>
		</ul>
		<ul class="bangzhu">
			<li>
				<h3>
					<a href="">物流配送</a>
				</h3>
			</li>
			<s:iterator value="#request.bottomNewsLink" var="a">
				<s:if test="#a.categoryId==94">
					<li>
						<p>
							<a href=""><s:property value="#a.title" /></a>
						</p>
					</li>
				</s:if>
			</s:iterator>
		</ul>
		<ul class="bangzhu">
			<li>
				<h3>
					<a href="">支付方式</a>
				</h3>
			</li>
			<s:iterator value="#request.bottomNewsLink" var="a">
				<s:if test="#a.categoryId==95">
					<li>
						<p>
							<a href=""><s:property value="#a.title" /></a>
						</p>
					</li>
				</s:if>
			</s:iterator>
		</ul>
		<ul class="bangzhu">
			<li>
				<h3>
					<a href="">售后服务</a>
				</h3>
			</li>
			<s:iterator value="#request.bottomNewsLink" var="a">
				<s:if test="#a.categoryId==96">
					<li>
						<p>
							<a href=""><s:property value="#a.title" /></a>
						</p>
					</li>
				</s:if>
			</s:iterator>
		</ul>
		<ul class="bangzhu">
			<li>
				<h3>
					<a href="">会员服务</a>
				</h3>
			</li>
			<s:iterator value="#request.bottomNewsLink" var="a">
				<s:if test="#a.categoryId==97">
					<li>
						<p>
							<a href=""><s:property value="#a.title" /></a>
						</p>
					</li>
				</s:if>
			</s:iterator>
		</ul>
	</div>
	<div>
		<a href="www.miitbeian.gov.cn" target="_blank">京ICP备15040906号</a>
	</div>