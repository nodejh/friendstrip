## 数据库
	
```
用户信息
User 
user_id int(11)
name varchar(200)
sex tinyint(1) -- 1男2女0未知
phone int(11)
passsword varchar(64)
avatar varchar(220) -- 头像
school_id int(11) --学校

景点信息
Scenery
scenery_id int(11) 
name varchar(200)
city_id int(11) --城市id
country_id int(11) --省份id
instruction varchar(1000) --简介
picture varchar(200) --景点图片
price varchar(200) --票价
score int(11) --评分

游记
Travels
travels_id int(11)
title varchar(200)
content varchar(10000)
author_id int(10) --关联User表中的user_id
date int(11)--发布日期

城市
City
city_id int(11)
name varchar(200)
country_id int(11)

省份
Country
country_id int(11)
name varchar(200)

学校
School
school_id int(10)
name varchar(100)
city_id int(11)
country_id int(11)

评论
Comment
comment_id int(11) --景点/游记/评论的 id
type int(11) --1表示景点的评论，2表示游记的评论，3表示评论的评论
content text
date int(11)
author_id int(11) --评论者

赞
Approval
approval_id int(11)--景点/游记的 id
date int(11)
type int(11) --1表示景点的赞，2表示游记的赞，3表示评论的赞
author_id int(11)

分享
Share
share_id int(11) --景点/游记的 id
type int(11) --1表示景点的分享，2表示游记的分享
author_id int(11) --分享者
date int(11)

浏览量
Views
views_id int(11) --景点/游记的 id
type int(11) --1表示景点，2表示游记

出行信息
Trips
trips_id int(11)
title varchar(200)
content text
city_id int(11)
country_id int(11)
data 

收藏
Collection
user_id int(11) --收藏该内容的用户 id
collection_id int(11) --收藏的景点/评论的 id
type tinyint(1) -- 1景点，2游记


关注出行纪录表
Attention
id
user_id
trips_id
date 关注时间



```

		
##开发

1. 客户端java，服务端php	
	
2. 存储在本地的数据：

	+ 用户设置（日模式/夜间模式）
	
	+ 图片缓存
	

