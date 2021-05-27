<?php

    class Model {


        public static function findBy($value, $column, $table_name) {

            $conn = Database::getConnection();
            $query = "select distinct * from ". $table_name ." where $column = '$value'";
            $stmt = $conn->prepare($query);
            $stmt->execute();
            $data = $stmt->fetchAll();

            return count($data) ? $data[0] : null;
        }

        public static function findAll($table_name) {
            $conn = Database::getConnection();
            $query = "select * from ". $table_name;
            $stmt = $conn->prepare($query);
            $stmt->execute();
            $data = $stmt->fetchAll();

            return count($data) ? $data : null;
        }
        public static function findAllRDV($table_name) {
            $conn = Database::getConnection();
            $query = "select * from ". $table_name." ORDER BY date_rdv ASC ";
            $stmt = $conn->prepare($query);
            $stmt->execute();
            $data = $stmt->fetchAll();

            return count($data) ? $data : null;
        }
        public static function findAllRecl($table_name) {
            $conn = Database::getConnection();
            $query = "select * from ". $table_name." ORDER BY date_reclamation ASC ";
            $stmt = $conn->prepare($query);
            $stmt->execute();
            $data = $stmt->fetchAll();

            return count($data) ? $data : null;
        }
        public static function findAllSP($table_name) {
            $conn = Database::getConnection();
            $query = "select distinct specialite from ". $table_name;
            $stmt = $conn->prepare($query);
            $stmt->execute();
            $data = $stmt->fetchAll();

            return count($data) ? $data : null;
        }

        public static function submitData($query, $params) : bool {
            $conn = Database::getConnection();
            $stmt = $conn->prepare($query);

            if($stmt->execute($params)) return true;
            else return false;
        }

    }

?>
