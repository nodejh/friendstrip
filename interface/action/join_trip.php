<?php


// 6、加入出行（拼车）
//
// release.php


/*



var data = {
  "user_id": 2, // 加入者ID
  "trip_id": 1, //trip id
  "total_people": 1, //当前总人数，即用户当前看到的总人数
};


$.post('action/join_trip.php', data, function (res) {
console.log(res);
});


{"join_id":"10","code":0,"message":"success"}


 */

include 'common.class.php';


class Join extends Common {

    public $data;


    function __construct() {
        parent::__construct();
    }


    public function index() {

        $get_data = $_POST;

        // 判断 trip 是否存在
        $sql_is_trip = "SELECT * FROM ft_join WHERE trip_id=:trip_id";
        $stmt_is_trip = $this->db->prepare($sql_is_trip);
        $stmt_is_trip->execute(array(':trip_id'=>$get_data['tripId']));
        $stmt_is_trip->setFetchMode(PDO::FETCH_ASSOC);
        $res_is_trip = $stmt_is_trip->fetch();

        if ($res_is_trip) {
            // 判断是否已经加入过
            $sql_is_join = "SELECT * FROM ft_join WHERE user_id=:user_id AND trip_id=:trip_id";
            $stmt_is_join = $this->db->prepare($sql_is_join);
            $stmt_is_join->execute(array(':user_id' => $get_data['uid'], ':trip_id'=>$get_data['tripId']));
            $stmt_is_join->setFetchMode(PDO::FETCH_ASSOC);
            $res_is_join = $stmt_is_join->fetch();

            if ($res_is_join) {

                $this->res['code'] = 1022;
                $this->res['message'] = '已经加入过';

            } else {
                // 记录出行
                $sql_insert = "INSERT INTO ft_join(user_id, trip_id, time) VALUES (:user_id, :trip_id, :time)";
                $stmt_insert = $this->db->prepare($sql_insert);
                $time = time();
                $stmt_insert->bindparam(':user_id', $get_data['uid']);
                $stmt_insert->bindparam(':trip_id', $get_data['tripId']);
                $stmt_insert->bindparam(':time', $time);

                //$join_id = $stmt_insert->execute();

                if ($stmt_insert->execute()) {

                    $this->res['tripId'] = $get_data['tripId'];

                    // 更新出行表信息
                    $sql_update = "UPDATE ft_trip SET people_hadnum = :people_hadnum WHERE trip_id = :trip_id";

                    $people_hadnum = $get_data['total_people'] + 1;

                    $stmt_update = $this->db->prepare($sql_update);
                    $stmt_update->bindparam(':trip_id', $get_data['tripId']);
                    $stmt_update->bindparam(':people_hadnum', $people_hadnum);

                    if($stmt_update->execute()){
                        $this->res['code'] = 0;
                        $this->res['message'] = 'success';

                    }else{
                        $this->db->errorInfo();
                        $this->res['code'] = 1023;
                        $this->res['message'] = '更新出行表信息';
                    }

                }  else {
                    $this->db->errorInfo();
                    $this->res['code'] = 1024;
                    $this->res['message'] = '记录出行信息失败';
                }
            }
        } else {
            $this->res['code'] = 1025;
            $this->res['message'] = 'trip 不存在';
        }
        
    }


    function __destruct() {
        $this->die_json($this->res);
    }
}


$join = new Join();
$join->index();



