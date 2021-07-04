<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>노드 뽑기</title>
<!-- js -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>

<!-- css -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css" />


<!-- js process -->
<script>

$(function () {
	$("#demo1").jstree({ 
		"plugins" : [ "themes", "html_data", "ui", "cookies" ]
	});
	$("#demo2").jstree({ 
		"plugins" : [ "themes", "html_data", "ui", "cookies" ]
	});
  $('#demo1,#demo2').bind("select_node.jstree",function(e,data) {
	  var level = data.node.parents.length;
	    var text  = data.node.text;
	    var id    = data.node.id;
      console.log(name,id,level);
  });
    
});
</script>
</head>
<body>
<h1>트리구조 확인</h1>

<div id="demo1" class="demo">
	<ul>
		<li id="phtml_1">
			<a href="#">Root node 1</a>
			<ul>
				<li id="phtml_2">
					<a href="#">Child node 1</a>

				</li>
				<li id="phtml_3">
					<a href="#">Child node 2</a>
				</li>
			</ul>
		</li>
		<li id="phtml_4">
			<a href="#">Root node 2</a>

		</li>
	</ul>
</div>
  Without id-s:
<div id="demo2" class="demo">
	<ul>
		<li>
			<a href="#">Root node 1</a>
			<ul>
				<li>
					<a href="#">Child node 1</a>                   

				</li>
				<li>
					<a href="#">Child node 2</a>
				</li>
			</ul>
		</li>
		<li>
			<a href="#">Root node 2</a>

		</li>
	</ul>
</div>
</body>
</html>