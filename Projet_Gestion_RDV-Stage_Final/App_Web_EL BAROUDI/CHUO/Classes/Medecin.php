<?php

    class Medecin extends User {

        //ATTRIBUTES
        protected $specialite;
        protected $NuMedecin;
        protected $conge;
        //CONSTRUCTORS

        public function __construct()
        {
            parent::__construct();
            $this->setDescription('Medecin');
            $this->specialite = null;
            $this->NuMedecin = null;
            $this->conge = null;


        }

        //SETTERS
        public function setSpecialite($specialite) : void { $this->specialite = $specialite; }
        public function setNuMedecin($NuMedecin) : void { $this->NuMedecin = $NuMedecin; }
        public function setConge($conge) : void { $this->conge = $conge; }

        //GETTERS
        public function getSpecialite() : string { return $this->specialite; }
        public function getNuMedecin() : string { return $this->NuMedecin; }
        public function getConge() : string { return $this->conge; }





    }

?>
