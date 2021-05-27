<?php

    class User extends Model {

        //ATTRIBUTES

        protected $CIN;
        protected $nom;
        protected $prenom;
        protected $dateN;
        protected $telephone;
        protected $email;
        protected $password;
        protected $description;
        protected $acceptation;

        /////////////////////////////////////////////////////////////////////////////////////
        //CONSTRUCTORS

        public function __construct()
        {
            $this->CIN = null;
            $this->nom = null;
            $this->prenom = null;
            $this->dateN = null;
            $this->telephone = null;
            $this->email = null;
            $this->password = null;
            $this->description=null;
            $this->acceptation = 0;
        }

        /////////////////////////////////////////////////////////////////////////////////////
        //SETTERS

        public function setCIN($CIN) : void { $this->CIN = $CIN; }
        public function setNom($nom) : void { $this->nom = $nom; }
        public function setPrenom($prenom) : void { $this->prenom = $prenom; }
        public function setDateN($dateN) : void { $this->dateN = $dateN; }
        public function setTelephone($telephone) : void { $this->telephone = $telephone; }
        public function setEmail($email) : void { $this->email = $email; }
        public function setPassword($password) : void { $this->password = $password; }
        public function setAcceptation($acceptation) : void { $this->acceptation = $acceptation; }
        public function setDescription($description) : void { $this->description = $description; }

        /////////////////////////////////////////////////////////////////////////////////////
        //GETTERS

        public function getCIN() : string { return $this->CIN; }
        public function getNom() : string { return $this->nom; }
        public function getPrenom() : string { return $this->prenom; }
        public function getDateN() : string { return $this->dateN; }
        public function getTelephone() : string { return $this->telephone; }
        public function getEmail() : string { return $this->email; }
        public function getPassword() : string { return $this->password; }
        public function getAcceptation() : int { return $this->acceptation; }
        public function getDescription() : string { return $this->description; }

        /////////////////////////////////////////////////////////////////////////////////////
        //METHODS

        //Get User By id
        public static function findBy($value, $column = 'CIN', $table_name = 'user') {
            $data = parent::findBy($value, $column, $table_name);

              if($data != null){
                if($table_name=="user") {

                  switch($data['description']) {
                    case 'Medecin':
                      $user = new Medecin();
                      $FT = parent::findBy($data['CIN'],'CIN',$data['description']);
                      $user->setSpecialite($FT['specialite']);
                      $user->setNuMedecin($FT['Nu_medecin']);
                      $user->setConge($FT['en_conge']);
                      break;
                    case 'Patient':
                      $user = new Patient();
                      $FT = parent::findBy($data['CIN'],'CIN',$data['description']);
                      $user->setSex($FT['sex']);
                      break;
                    case 'Administrateur':
                      $user = new Administrateur();
                      break;
                  }

                  $user->setCIN($data['CIN']);
                  $user->setNom($data['Nom']);
                  $user->setPrenom($data['Prenom']);
                  $user->setDateN($data['Date_naissance']);
                  $user->setTelephone($data['telephone']);
                  $user->setEmail($data['Email']);
                  $user->setPassword($data['Password']);
                  $user->setAcceptation($data['acceptation']);
                    return $user;
              }else{
                if($table_name=="Medecin"){
                  $user = new Medecin();
                  $user->setCIN($data['CIN']);
                  $user->setSpecialite($data['specialite']);
                  $user->setNuMedecin($data['Nu_medecin']);
                  $user->setConge($data['en_conge']);
                    return $user;

                }else{
                  if($table_name=="Patient"){
                      $user = new Patient();
                    $user->setCIN($data['CIN']);
                    $user->setSex($data['sex']);
                      return $user;
                  }
                  }
                }
              }
              return null;
          }

        //Get all users
        public static function findAll($table_name = 'user') {
            $data = parent::findAll($table_name);

            if($data != null) {

                $users = array(count($data));

                for($i = 0; $i < count($data); $i++) {

                    $info = $data[$i];
                    switch($info['description']) {
                        case 'Medecin':
                          $user = new Medecin();
                          $FT = parent::findBy($info['CIN'],'CIN',$info['description']);
                          $user->setSpecialite($FT['specialite']);
                          $user->setNuMedecin($FT['Nu_medecin']);
                          $user->setConge($FT['en_conge']);
                          break;
                        case 'Patient':
                          $user = new Patient();
                          $FT = parent::findBy($info['CIN'],'CIN','Patient');
                          $user->setSex($FT["sex"]);
                          break;
                        case 'Administrateur':
                          $user = new Administrateur();
                          break;
                    }

                    $user->setCIN($info['CIN']);
                    $user->setNom($info['Nom']);
                    $user->setPrenom($info['Prenom']);
                    $user->setDateN($info['Date_naissance']);
                    $user->setTelephone($info['telephone']);
                    $user->setEmail($info['Email']);
                    $user->setPassword($info['Password']);
                    $user->setAcceptation($info['acceptation']);

                    $users[$i] = $user;
                }

                return $users;

            } else {
                return null;
            }
        }

        //Accepter demandes
        public static function AcceptUser($user) : bool {
            $query = "update user set acceptation = 1 where CIN = ?";
            $params = [
               $user->getCIN()
            ];
            $UPDToUser = parent::submitData($query, $params);

           return $UPDToUser;
        }


        //Add new user
        public static function addUser($user) : bool {
            $query = 'insert into user (CIN,Nom,Prenom,Date_naissance,telephone,Email,Password,acceptation,description) values (?, ?, ?, ?, ?, ?, ?, ?, ?)';
            $params = [
                $user->getCIN(),
                $user->getNom(),
                $user->getPrenom(),
                $user->getDateN(),
                $user->getTelephone(),
                $user->getEmail(),
                $user->getPassword(),
                $user->getAcceptation(),
                $user->getDescription()
            ];

            $addToUser = parent::submitData($query, $params);
            if($user->getDescription() == 'Medecin') {

                //Insert user in Medecin table
                $addToObject = parent::submitData(
                  'insert into Medecin (Nu_medecin,CIN,specialite,en_conge) values (?,?,?,?)',
                  [
                      $user->getNuMedecin(),
                      $user->getCIN(),
                      $user->getSpecialite(),
                      $user->getConge()
                  ]
                );

                //Insert Medecin in table spécialité
                $addToObjectSP = parent::submitData(
                  'insert into specialite (Nu_medecin,specialite) values (?,?)',
                  [
                      $user->getNuMedecin(),
                      $user->getSpecialite()

                  ]
                );
                  return $addToUser && $addToObject && $addToObjectSP;

            } else {

                //Insert user in Patient
                if($user->getDescription() == 'Patient'){
                  $addToObject = parent::submitData(
                      'insert into Patient (CIN,sex) values (?,?)',
                      [
                        $user->getCIN(),
                        $user->getSex(),
                      ]
                  );
              }

            }
            return $addToUser && $addToObject;

        }


        //Update User
       public static function updateUser($user) : bool {
           $query = "update user set Nom = ?, Prenom = ?, Date_naissance = ? , Email = ?, Password = ?, telephone = ? where CIN = ?";
           $params = [
               $user->getNom(),
               $user->getPrenom(),
               $user->getDateN(),
               $user->getEmail(),
               $user->getPassword(),
               $user->getTelephone(),
               $user->getCIN()
           ];
           $UPDToUser = parent::submitData($query, $params);
           if($user->getDescription()=="Patient"){
             $UPDObject = parent::submitData(
                 'update Patient set sex = ? where CIN = ?',
                 [
                     $user->getSex(),
                     $user->getCIN()

                 ]
             );
             return $UPDToUser && $UPDObject;
           }else{
             if($user->getDescription()=="Medecin"){
               $UPDObject = parent::submitData(
                   'update Medecin set CIN = ? , specialite = ? , en_conge = ? where Nu_medecin = ?',
                   [
                       $user->getCIN(),
                       $user->getSpecialite(),
                       $user->getConge(),
                       $user->getNuMedecin()

                   ]
               );
               $UPDObjectSP = parent::submitData(
                   'update specialite set specialite = ? where Nu_medecin = ?',
                   [
                       $user->getSpecialite(),
                       $user->getNuMedecin()

                   ]
               );
               return $UPDToUser && $UPDObject && $UPDObjectSP;
             }
           }
          return $UPDToUser;
       }

       //Update user CIN
      public static function updateCIN($user) : bool {
        //UPDATE CIN user from all table "update on cascade"
        $query = "update user set CIN = ? where Nom = ? AND Prenom = ? AND Date_naissance = ? AND Email = ? AND Password = ? AND telephone = ? ";
        $params = [
            $user->getCIN(),
            $user->getNom(),
            $user->getPrenom(),
            $user->getDateN(),
            $user->getEmail(),
            $user->getPassword(),
            $user->getTelephone()

        ];
        $UPDCIN = parent::submitData($query, $params);

         return $UPDCIN;
      }
      //Update number Medecin
     public static function updateNuMedecin($user) : bool {
       //UPDATE Nu_medecin  from all table "update on cascade"
       $query = "update Medecin set Nu_medecin= ? where CIN = ?";
       $params = [
          $user->getNuMedecin(),
           $user->getCIN()
       ];
       $UPDNuMedecin = parent::submitData($query, $params);

        return $UPDNuMedecin;
     }


      //Delete User
     public static function DELUser($user) : bool {
       //delite user from ALL table "on delite cascade"
       $query = "delete from user where CIN = ?";
       $params = [
           $user->getCIN()
       ];
       $DELToUser = parent::submitData($query, $params);
       return $DELToUser;

     }
/*
     //Annuaire Simple
     public function AnnuaireSimple($email,$tele) {

         $allUser = self::findAll();
         $afficher_user= array();

         if($allUser == null) return $afficher_user;

         foreach($allUser as $User) {
           if($User->getDescription()!='Administrateur'){
             $User->setEmail(strtoupper($User->getEmail()));
                 if(!empty($email) && !empty($tele)){
                   if($User->getEmail()==$email && $User->getTelephone()==$tele) {
                       array_push($afficher_user, $User);
                   }
                 }else{
                   if(!empty($email)){
                     if($User->getEmail()==$email) {
                         array_push($afficher_user, $User);
                     }
                   }else{
                     if($User->getTelephone()==$tele) {
                         array_push($afficher_user, $User);
                     }
                   }
                 }
              }
         }
         return $afficher_user;
     }
     */
}
 ?>
