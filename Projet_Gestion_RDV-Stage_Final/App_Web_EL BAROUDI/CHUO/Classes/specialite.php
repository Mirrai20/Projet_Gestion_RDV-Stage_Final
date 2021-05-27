<?php

    class specialite extends User {

        //ATTRIBUTES
        protected $NuMedecin;
        protected $specialite;


        //CONSTRUCTORS

        public function __construct()
        {
            parent::__construct();
            $this->specialite = null;
            $this->NuMedecin = null;

        }

        //SETTERS
        public function setSpecialite($specialite) : void { $this->specialite = $specialite; }
        public function setNuMedecin($NuMedecin) : void { $this->NuMedecin = $NuMedecin; }


        //GETTERS
        public function getSpecialite() : string { return $this->specialite; }
        public function getNuMedecin() : string { return $this->NuMedecin; }

        //Get all specialité
        public static function findAll($table_name = 'specialite') {
            $data = Model::findAll($table_name);
            if($data != null) {

                $recls = array(count($data));

                for($i = 0; $i < count($data); $i++) {

                    $info = $data[$i];
                    $SPL = new specialite();
                    $SPL->setSpecialite($info['specialite']);
                    $SPL->setNuMedecin($info['Nu_medecin']);

                    $SPLs[$i] = $SPL;
                }

                return $SPLs;

            } else {
                return null;
            }
        }


    }

?>
