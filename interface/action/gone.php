<?php


/*

{
      "type":"1";                     // string 1景点; 2景区
      "spotId":"10",                   // string
      //"token":"10101",                   // string
    }



 */

include 'common.class.php';


class Gone extends Common {

    public $data;


    function __construct() {

        parent::__construct();
    }


    public function index() {


        $post_data = file_get_contents("php://input");
        $get_data = json_decode($post_data, true);

        //$get_data = $_POST;

        if ($get_data['type'] == 1) {

            $sql = "SELECT want_number FROM ft_view WHERE view_id = :view_id";

            $stmt = $this->db->prepare($sql);
            $stmt->execute(array(':view_id' => $get_data['spotId']));
            $stmt->setFetchMode(PDO::FETCH_ASSOC);
            $current_number = $stmt->fetch();

            $new_number = $current_number['want_number'] + 1;

            $sql_update = "UPDATE ft_view SET want_number = :want_number WHERE view_id = :view_id";
            $stmt_update = $this->db->prepare($sql_update);
            $stmt_update->bindValue(':want_number', $new_number);
            $stmt_update->bindValue(':view_id', $get_data['spotId']);

            $res = $stmt_update->execute();
            if ($res) {
                $this->res['code'] = 0;
                $this->res['message'] = 'success';
            } else {
                $this->res['code'] = 1037;
                $this->res['message'] = '更新数据库失败';
            }


        } elseif ($get_data['type'] == 2) {



        } else {
            $this->res['code'] = 1036;
            $this->res['message'] = '结果为空';
        }

    }


    function __destruct() {
        $this->die_json($this->res);
    }
}


$obj = new Gone();
$obj->index();












