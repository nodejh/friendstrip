<?php

class DB {

    public $db;
    protected $dsn = "mysql:host=localhost;dbname=ft";
    protected $db_user = 'root';
    protected $db_password = 'root';

    function __construct() {

        try {

            $this->db = new PDO($this->dsn, $this->db_user, $this->db_password);
            $this->db->query('set names "utf8"');
            $this->db->setAttribute(PDO::ATTR_CASE, PDO::CASE_NATURAL);

        } catch (PDOException $error){
            echo '数据库连接失败：'.$error->getMessage();
            exit();
        }
    }

}