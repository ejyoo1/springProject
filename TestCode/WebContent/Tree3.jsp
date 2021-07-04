<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tree1,2 통합 연</title>
<!-- js -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>

<!-- css -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css" />


<!-- js process -->
<script>

$(function () {
	
	var test = [
		{ "id" : "ajson1", "parent" : "#", "text" : "Simple root node" },
		{ "id" : "ajson2", "parent" : "#", "text" : "Root node 2" },
		{ "id" : "ajson3", "parent" : "ajson2", "text" : "Child 1" },
		{ "id" : "ajson4", "parent" : "ajson2", "text" : "Child 2" },
	];
	
	$('#tree2').jstree({ 
		'core' : {
			'data' : test,
			'check_callback' : true
		},	
		'plugins' : ["wholerow","contextmenu","cookies"],
		'contextmenu' : {
			"items" : {
				"test" : { //사실상 "test"라는 이름은 변수에 가깝기 때문에 뭐든 상관없다 생각한다.
	        		"separator_before" : false,
					"separator_after" : true,
					"label" : "신규메뉴",
					"action" : function(obj){alert('메뉴테스트')}
				},
				"test1" : {
					"separator_before" : false,
					"separator_after" : true,
					"label" : "신규메뉴2",
					"action" : function(obj){alert('메뉴테스트2')}
				}
			}
		}
	});
  $('#tree2').bind("select_node.jstree",function(e,data) {
	  var level = data.node.parents.length;
	    var text  = data.node.text;
	    var id    = data.node.id;
      console.log(text+","+id+","+level);
  });
    
});
</script>
</head>
<body>
<h1>트리구조 확인</h1>

<div id="tree2">
	<input type="text" id="text"value="cherry">
</div>
<button type="button" onclick="fn_extends()" >Extends</button>
<button type="button" onclick="fn_collapse()" >collapse</button>

<script>
function fn_extends(){
	alert("fn_extends");
	$("#tree2").jstree("open_all");
}
function fn_collapse(){
	alert("fn_collapse");
	$("#tree2").jstree("close_all");
}
</script>
</body>
</html>