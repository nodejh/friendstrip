<?php

set_time_limit(0);

include '../../interface/database/dbConfigure.php';
include 'phpQuery.php';


get_all_province();


// 爬首页所有省份的 url
function get_all_province() {
    $page_province = 'http://www.lvmama.com/lvyou/';
    phpQuery::newDocumentFile($page_province);

    // 省份所在的节点组合
    $dom_province = pq('.wy_area_list:eq(0)').find('a');

    foreach ($dom_province as $key => $v) {
        $url_province[$key] = $v->attr('href');
    }
    echo $url_province;
}
