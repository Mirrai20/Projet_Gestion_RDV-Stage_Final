<?php

    spl_autoload_register(function ($class_name) {
        include '../Classes/'. $class_name . '.php';
    });

    session_start();

    if(!isset($_SESSION['CIN'])) {
        header('Location: /CHUO/');
        die();
    }

    if($_POST['Demande_accepté']) {

        $user = User::findBy($_GET['code']);
        if(User::AcceptUser($user)) {
            header('Location: /CHUO/home.php');
            die();

        } else {
            //demande Not accepted | Server Issues
            header('Location: /CHUO/home.php/?'. sha1('exc_problem'));
            die();
        }

    } else {

            //Supprimer USER
            if($_POST["Del_demande"]) {
                $user = User::findBy($_GET['code']);
              if(User::DELUser($user)) {
                  //user DELITE
                  header('Location: /CHUO/home.php?'.sha1('user_del'));
                  die();

              } else {

                  //User Not Delite | Server Issues
                  header('Location: /CHUO/home.php?'.sha1('exc_problem'));
                  die();
              }
            } else {

              header('Location: /CHUO/');
              die();

            }

    }

?>
