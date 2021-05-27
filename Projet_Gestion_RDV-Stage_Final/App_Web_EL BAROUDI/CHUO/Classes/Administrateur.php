<?php

    class Administrateur extends User {

        //CONSTRUCTORS

        public function __construct()
        {
            parent::__construct();
            $this->setDescription('Administrateur');
        }


        //METHODS///

        //Les nouveaux utilisateurs
        public function getNewUser() {

            $allUser = User::findAll();
            $newsUser= array();

            if($allUser == null) return $newsUser;

            foreach($allUser as $User) {
                if($User->getAcceptation()==0) {
                    array_push($newsUser, $User);
                }
            }

            return $newsUser;

        }
        // Afficher les utilisateurs
        public function getALLuser() {

            $allUser = User::findAll();
            $afficher_user= array();

            if($allUser == null) return $afficher_user;

            foreach($allUser as $User) {
                if($User->getDescription()!="Administrateur" && $User->getAcceptation()!=0){
                    array_push($afficher_user, $User);

                    }
            }

            return $afficher_user;

        }
        // Afficher les reclamations
        public function getALLReclamation() {

            $allRecl = Reclamation::findAll();
            $afficher_recl= array();

            if($allRecl == null) return $afficher_recl;

            foreach($allRecl as $Recl) {
                if($Recl->getStatut()=="Encoure"){
                    array_push($afficher_recl, $Recl);
                  }
            }

            return $afficher_recl;

        }
        //Corriger la reclamation
      /*  public static function CorrigerRecl($user) : bool {
            $query = "update reclamation set Statut ='Corriger' where CIN = ?";
            $params = [
               $user->getCIN()
            ];
            $UPDToRecl = Model::submitData($query, $params);

           return $UPDToRecl;
        }*/

        // Afficher tous les médecins
        public function getALLmedecin() {

            $allUser = User::findAll();
            $afficher_medecin= array();

            if($allUser == null) return $afficher_medecin;

            foreach($allUser as $User) {
                if($User->getDescription()=="Medecin"){
                    array_push($afficher_medecin, $User);

                    }
            }

            return $afficher_medecin;

        }
        // Afficher tous les RDV
        public function getALLRdv($idmedecin) {

            $allRdv = rdv::findAll();
            $afficher_rdv= array();

            if($allRdv == null) return $afficher_rdv;

            foreach($allRdv as $Rdv) {
                if($Rdv->getEtat()!="Annuler" && $Rdv->getNuMedecin()==$idmedecin && $Rdv->getDateRdv()>=date("Y-m-d")){
                    array_push($afficher_rdv, $Rdv);
                  }
            }

            return $afficher_rdv;

        }
        // Afficher tous les specialité
        public function getALLSP() {

            $allsp = specialite::findAll();
            $afficher_specialite= array();

            if($allsp == null) return $afficher_specialite;

            foreach($allsp as $sp) {
                    array_push($afficher_specialite, $sp);

            }

            return $afficher_specialite;

        }


    }

?>
