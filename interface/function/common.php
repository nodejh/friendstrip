<?php


/**
 * Class Tool 工具类
 */
class Tool {

    public $res; // 返回给客户端的结果
    public $is_null;


    /**
     * 数组所有内容都不能为空
     * @param $array
     * @return bool
     */
    public function all_not_null($array) {
        if (is_array($array)) {
            $this->is_null = true;
            foreach($array as $key => $v) {
                if ($v == null) {
                    $this->is_null = false;
                }
            }
            return $this->is_null;
        } else {
            return false;
        }
    }


    /**
     * 至少一项不为空
     * @param $array
     * @return bool
     */
    public function one_not_null($array) {
        if (is_array($array)) {
            $this->is_null = false;
            foreach($array as $key => $v) {
                if ($v != null) {
                    $this->is_null = true;
                }
            }
            return $this->is_null;
        } else {
            return false;
        }
    }


    public function encrypt($string) {
        return $string;
        //return md5(md5($string));
    }

    /**
     * 验证手机号
     * @param $string
     * @return bool
     */
    public function reg_exp_phone($string) {
    $pattern =  '/^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/';
    if (preg_match($pattern, $string)) {
        return true;
    } else {
        return false;
    }
  }


    /**
     * 验证邮箱
     * @param $string
     * @return bool
     */
    public function reg_exp_email($string) {
        $pattern = '/^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/';
        if(preg_match($pattern, $string)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 验证密码，6-20为不含引号字符串
     * @param $string
     * @return bool
     */
    public function reg_exp_password($string) {
        $pattern = '/^[^\'\"]{6,20}$/';
        if (preg_match($pattern, $string)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @param $array
     */
    public function die_json($array) {
        die(json_encode($array));
    }


}