<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
<link rel="stylesheet" href="/css/ratchet.min.css"/>
<link rel="stylesheet" href="/css/docs.min.css"/>
<script type="text/javascript">
	var _gaq = _gaq || [];
	_gaq.push([ '_setAccount', 'UA-36050008-1' ]);
	_gaq.push([ '_trackPageview' ]);

	(function() {
		var ga = document.createElement('script');
		ga.type = 'text/javascript';
		ga.async = true;
		ga.src = ('https:' == document.location.protocol ? 'https://ssl'
				: 'http://www')
				+ '.google-analytics.com/ga.js';
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(ga, s);
	})();
</script>
</head>
<body ontouchstart="">
	<div class="docs-section">
		<h2>Page setup</h2>
		<p class="lead">Three simple rules for structuring your Ratchet
			pages</p>
		<h3>1. Fixed bars come first</h3>
		<p>
			All fixed bars (
			<code>.bar</code>
			) should always be the first thing in the
			<code>&lt;body&gt;</code>
			of the page. This is really important!
		</p>

		<h3>
			2. Everything else goes in
			<code>.content</code>
		</h3>
		<p>
			Anything that's not a
			<code>.bar</code>
			should be put in a div with the class
			<code>.content</code>
			. Put this div after the bars in the
			<code>&lt;body&gt;</code>
			tag. The
			<code>.content</code>
			div is what actually scrolls in a Ratchet prototype.
		</p>

		<h3>3. Don't forget your meta tags</h3>
		<p>They're included in the template.html page included in the
			download, but make sure they stay in the page. They are important for
			Ratchet to work just right.</p>
	</div>
</body>
</html>