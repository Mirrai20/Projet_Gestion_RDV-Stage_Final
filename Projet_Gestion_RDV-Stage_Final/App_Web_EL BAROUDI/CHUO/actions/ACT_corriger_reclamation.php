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
    if($_POST['Corriger_submit']) {
      $recl = Reclamation::findRecl($_GET['NuRecl']);
        $user = User::findBy($recl->getID());
        if(User::findBy($_POST['Corriger_code'], 'CIN') && ($user->getCIN() != $_POST['Corriger_code'])) {

            //New CIN already Used by another account
            header('Location: /CHUO/home.php?'.sha1('code_used'));
            die();

        }

       if($user->getCIN() != $_POST['Corriger_code']){
            $user->setCIN($_POST['Corriger_code']);
            if(!(User::updateCIN($user))){
              //User Not updated | Server Issues
              header('Location: /CHUO/home.php?'.sha1('exc_problem'));
              die();
            }
         }

        if($user->getNom() != $_POST['Corriger_name']) $user->setNom($_POST['Corriger_name']);
        if($user->getPrenom() != $_POST['Corriger_prenom']) $user->setPrenom($_POST['Corriger_prenom']);
        if($user->getDateN() != $_POST['Corriger_date']) $user->setDateN($_POST['Corriger_date']);



        if(User::updateUser($user)) {
          $recl->setStatut("Corriger");

          if(Reclamation::UpdRecl($recl)) {
            if(Reclamation::sendEmail($user,$recl)){
              //user Updated
              header('Location: /CHUO/home.php?'.sha1('Recl_Co'));
              die();
            }else{
              //User Not updated | Server Issues
              header('Location: /CHUO/home.php?'.sha1('exc_problem'));
              die();
            }


          } else {

              //User Not updated | Server Issues
              header('Location: /CHUO/home.php?'.sha1('exc_problem'));
              die();
          }

        } else {

            //User Not updated | Server Issues
            header('Location: /CHUO/home.php?'.sha1('exc_problem'));
            die();
        }

    } else {
      //Rejeter une reclamation
      if($_POST["del_recl"]) {
          $recl = Reclamation::findRecl($_GET['NuRecl']);
          $recl->setStatut("Rejeter");
          $user = User::findBy($recl->getID());
        if(Reclamation::UpdRecl($recl)) {
            if(Reclamation::sendEmail($user,$recl)){
              //Reclamation rejeter
              header('Location: /CHUO/home.php?'.sha1('Recl_Rej'));
              die();
            }else{
              // Server Issues
              header('Location: /CHUO/home.php?'.sha1('exc_problem'));
              die();
            }


        } else {

            // Server Issues
            header('Location: /CHUO/home.php?'.sha1('exc_problem'));
            die();
        }
      } else {

        header('Location: /CHUO/');
        die();

      }

    }



?>
