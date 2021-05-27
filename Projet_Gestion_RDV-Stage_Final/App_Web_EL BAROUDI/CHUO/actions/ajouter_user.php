<?php

    spl_autoload_register(function ($class_name) {
        include '../Classes/'. $class_name . '.php';
    });


    //Récupération des valeurs entrant
    $CIN = $_POST['signup_code'];
    $nom = $_POST['signup_name'];
    $prenom = $_POST['signup_prenom'];
    $dateN = $_POST['signup_date'];
    $tele = $_POST['signup_tele'];
    $email = $_POST['signup_email'];
    $password = $_POST['signup_password'];
    $description= $_POST['signup_description'];
    if($description=="Medecin"){
      $specialite = $_POST['signup_sp'];
      $NuMedecin = $_POST['signup_Numedecin'];

    }else {
      $sex  = $_POST['signup_sx'];
    }

    if(User::findBy($email, 'email') == null) {
          if(User::findBy($CIN) == null){
                if(User::findBy($tele, 'telephone') == null){
                  if($description=="Medecin"){
                    if(User::findBy($NuMedecin, 'Nu_medecin', $description) != null){
                      //Number medecin already used by another account
                      header('Location: /CHUO/home.php?'.sha1('Nu_medecin_used'));
                      die();
                    }
                    $user =  new Medecin();
                    $user->setSpecialite($specialite);
                    $user->setNuMedecin($NuMedecin);
                    $user->setConge('Non');
                  }else{
                    if($description=="Patient"){
                      $user=new Patient();
                      $user->setSex($sex);
                    }
                  }
                  $user->setCIN($CIN);
                  $user->setNom($nom);
                  $user->setPrenom($prenom);
                  $user->setDateN($dateN);
                  $user->setTelephone($tele);
                  $user->setEmail($email);
                  $user->setPassword($password);
                  $user->setAcceptation(1);
                  $user->setDescription($description);

                  if(User::addUser($user)) {
                      //user added successfully
                      header('Location: /CHUO/home.php?'. sha1('user_added'));
                      die();
                  } else {
                      //User not added
                      header('Location: /CHUO/home.php?'.sha1('exc_problem'));
                      die();

                  }
                }else{
                  //Number phone already used by another account
                  header('Location: /CHUO/home.php?'.sha1('Tele_used'));
                  die();
                }
            }else {
              //CIN already used by another account
              header('Location: /CHUO/home.php?'.sha1('code_used'));
              die();

            }

    } else {

        //Email already used by another account
        header('Location: /CHUO/home.php?'.sha1('email_used'));
        die();


    }


?>
