<?php

    session_start();

    spl_autoload_register(function ($class_name) {
        include '../Classes/'. $class_name . '.php';
    });

    if(isset($_SESSION['CIN'])) {
        if(User::findBy($_SESSION['CIN'])) {
            header('Location: /CHUO/');
            die();
        }
    }

    if(!isset($_POST['signin_email']) && !isset($_POST['signin_password'])) {
        header('Location: /CHUO/');
        die();
    }

    $email = $_POST['signin_email'];
    $password = $_POST['signin_password'];

    if(($user = User::findBy($email, 'email')) != null) {

      if($password == $user->getPassword()) {

        $_SESSION['CIN'] = $user->getCIN();
        $_SESSION['nom'] = $user->getNom();
        $_SESSION['email'] = $user->getEmail();
        header('Location: /CHUO/home.php');

      } else {

        //wrong password
        header('Location: /CHUO/?'. sha1('wrong_password'));

      }

    } else {

        //Email not found
        header('Location: /CHUO/?'. sha1('not_found'));

    }

?>
