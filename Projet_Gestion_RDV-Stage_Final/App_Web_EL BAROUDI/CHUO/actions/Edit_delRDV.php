<?php

    spl_autoload_register(function ($class_name) {
        include '../Classes/'. $class_name . '.php';
    });

    session_start();

    if(!isset($_SESSION['CIN'])) {
        header('Location: /CHUO/');
        die();
    }

    //modifier RDV
    if($_POST["edit_rdv"]) {
      $d=$_POST["EDIT_jourRdv"]." ".$_POST["EDIT_dateRdv"];
      //echo "<script> alert('".$d."'); </script>";

        $RDV = RDV::findBy($_GET['idrdv']);
        $user = User::findBy($RDV->getCINP());

        if(RDV::findRDV($RDV->getNuMedecin(),$d, 'date_rdv') && ($RDV->getDateRdv() != $d)) {
            //Ce RDV a été déja reserver par un autre patient
            header('Location: /CHUO/home.php?'.sha1('RDV_used'));
            die();

        }

        $RDV->setDateRdv($d);
        $RDV->setEtat("Valider <Modifier>");

        if(RDV::updateRDV($RDV)) {

                  if(RDV::sendEmail($user,$RDV)) {

                    //Date DRV Updated
                    header('Location: /CHUO/home.php?'.sha1('RDV_edit'));
                    die();

                  } else {

                      //date RDV Not updated | Server Issues
                      header('Location: /CHUO/home.php?'.sha1('exc_problem'));
                      die();
                  }

        } else {

            //date RDV Not updated | Server Issues
            header('Location: /CHUO/home.php?'.sha1('exc_problem'));
            die();
        }



    } else {
      //Supprimer USER
      if($_POST["del_rdv"]) {
        $RDV = RDV::findBy($_GET['idrdv']);
          $RDV->setEtat("Annuler");
          $RDV->setDateRdv(null);
          $user = User::findBy($RDV->getCINP());

          if(RDV::Anurdv($RDV)) {
            if(RDV::sendEmail($user,$RDV)) {
                //RDV DELITE
                header('Location: /CHUO/home.php?'.sha1('rdv_del'));
                die();

            } else {

                //RDV Not Delite | Server Issues
                header('Location: /CHUO/home.php?'.sha1('exc_problem'));
                die();
            }

          } else {

              //RDV Not Delite | Server Issues
              header('Location: /CHUO/home.php?'.sha1('exc_problem'));
              die();
          }
      } else {

        header('Location: /CHUO/');
        die();

      }

    }



?>
