<?php

    spl_autoload_register(function ($class_name) {
        include 'Classes/'. $class_name . '.php';
    });

    session_start();

    if(isset($_SESSION['CIN'])) {
		if(User::findBy($_SESSION['CIN']) != null) {
			header('Location: /CHUO/home.php');
			die();
		}
    }

    $error = '';
    $success = '';

    //ERRORS
    if(isset($_GET[sha1('not_found')])) $error = 'Email ou Le mot de passe est incorrect!';
    if(isset($_GET[sha1('wrong_password')])) $error = 'Password donné est incorrect, try again!';

?>
<html>
	<head>
		<title>CHU Oujda</title>
		<link rel="stylesheet" type="text/css" href="css/index.css">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<!-- Erreurs -->
			<div class="global-errors" id="global-errors"  onclick="location.href='index.php'">
				<?php
				if($error != "") {
					echo "<p>$error</p>";
				}
				?>
			</div>


			<!-- Login  -->
		<div class="container">
			<div class="img">
					<img src="img/wave.png">
			</div>
			<div class="login-content">
				<form action="actions/signin.php" method="post">
					<img src="img/admin.png">
					<h2 class="title">Welcome</h2>
	           		<div class="input-div one">
	           		   <div class="i">
	           		   		<i class="fas fa-user"></i>
	           		   </div>
	           		   <div class="div">
	           		   		<h5>Email</h5>
	           		   		<input type="text" name="signin_email" id="signin_email" class="input" required>
	           		   </div>
	           		</div>
	           		<div class="input-div pass">
	           		   <div class="i">
	           		    	<i class="fas fa-lock"></i>
	           		   </div>
	           		   <div class="div">
	           		    	<h5>Password</h5>
	           		    	<input type="password" name="signin_password" id="signin_password"class="input" required>
	            	   </div>
	            	</div>
	            	<input type="submit" class="btn" value="Login" onclick="checkInputs(this,'in')">
                <p class="errors" id="signin_errors"><br></p>
	            </form>
	        </div>
	    </div>
	    <script type="text/javascript" src="js/main.js"></script>
      <!-- CE SCRIPT VA GERER LES INPUT DANS LE CAS D'AJOUTS ou DE modifications -->
      <script src="js/login.js"></script>
	</body>
</html>
