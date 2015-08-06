<?php

if ($_POST) {

	var_dump($_POST);
	$a = $_POST;
	echo json_encode($a);
	
} elseif($_GET) {

	$b = $_GET;
	echo json_encode($b);

}

?>
