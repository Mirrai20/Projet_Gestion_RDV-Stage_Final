<?php

    class Patient extends User {

        //ATTRIBUTES
        protected $sex;

        //CONSTRUCTORS

        public function __construct()
        {
            parent::__construct();
            $this->setDescription('Patient');
            $this->sex = null;

        }

        //SETTERS
        public function setSex($sex) : void { $this->sex = $sex; }

        //GETTERS
        public function getSex() : string { return $this->sex; }


    }

?>
