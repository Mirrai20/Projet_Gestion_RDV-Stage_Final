<?php

    spl_autoload_register(function ($class_name) {
        include '../Classes/'. $class_name . '.php';
    });

    session_start();

    if(!isset($_SESSION['CIN'])) {
        header('Location: /CHUO/');
        die();
    }
    //modifier USER
    if($_POST['EDIT_submit']) {

        $user = User::findBy($_GET['code']);
        if(User::findBy($_POST['EDIT_email'], 'email') && ($user->getEmail() != $_POST['EDIT_email'])) {

            //New Email already Used by another account
            header('Location: /CHUO/home.php?'.sha1('email_used'));
            die();

        }
        if(User::findBy($_POST['EDIT_code'], 'CIN') && ($user->getCIN() != $_POST['EDIT_code'])) {

            //New CIN already Used by another account
            header('Location: /CHUO/home.php?'.sha1('code_used'));
            die();

        }
        if(User::findBy($_POST['EDIT_tele'], 'telephone') && ($user->getTelephone() != $_POST['EDIT_tele'])) {
            //New CIN already Used by another account
            header('Location: /CHUO/home.php?'.sha1('Tele_used'));
            die();
        }
       if($user->getCIN() != $_POST['EDIT_code']){
            $user->setCIN($_POST['EDIT_code']);
            if(!(User::updateCIN($user))){
              //User Not updated | Server Issues
              header('Location: /CHUO/home.php?'.sha1('exc_problem'));
              die();
            }
         }
        if($user->getNom() != $_POST['EDIT_name']) $user->setNom($_POST['EDIT_name']);
        if($user->getPrenom() != $_POST['EDIT_prenom']) $user->setPrenom($_POST['EDIT_prenom']);
        if($user->getDateN() != $_POST['EDIT_date']) $user->setDateN($_POST['EDIT_date']);
        if($user->getEmail() != $_POST['EDIT_email']) $user->setEmail($_POST['EDIT_email']);
        if($user->getTelephone() != $_POST['EDIT_tele']) $user->setTelephone($_POST['EDIT_tele']);
        if($user->getPassword() != $_POST['EDIT_password']) $user->setPassword($_POST['EDIT_password']);


        if( $user->getDescription() == 'Patient' ){
            if($user->getSex() != $_POST['EDIT_sx'] ) $user->setSex($_POST['EDIT_sx']);
        }else{
          if( $user->getDescription() == 'Medecin' ){
            if(User::findBy($_POST['EDIT_Numedecin'], 'Nu_medecin',$user->getDescription()) && ($user->getNuMedecin() != $_POST['EDIT_Numedecin'])) {
                //New Number Medecin already Used by another account
                header('Location: /CHUO/home.php?'.sha1('Nu_medecin_used'));
                die();
            }
            if($user->getNuMedecin() != $_POST['EDIT_Numedecin'] ){
              $user->setNuMedecin($_POST['EDIT_Numedecin']);
              if(!(User::updateNuMedecin($user))){
                //User Not updated | Server Issues
                header('Location: /CHUO/home.php?'.sha1('exc_problem'));
                die();
              }
              }
              if($user->getSpecialite() != $_POST['EDIT_sp'] ) $user->setSpecialite($_POST['EDIT_sp']);
              if($user->getConge() != $_POST['EDIT_cg'] ) $user->setConge($_POST['EDIT_cg']);


          }
        }

        if(User::updateUser($user)) {

            //user Updated
            header('Location: /CHUO/home.php?'.sha1('user_edit'));
            die();

        } else {

            //User Not updated | Server Issues
            header('Location: /CHUO/home.php?'.sha1('exc_problem'));
            die();
        }

    } else {

      //Supprimer USER
      if($_POST["Del_user"]) {
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
