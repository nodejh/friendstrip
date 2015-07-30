-- Host: localhost
-- Generation Time: 2015-07-30 22:36:16
-- 服务器版本： 5.7.7-rc
-- PHP Version: 5.3.29
-- 
-- MySQL 导入数据库命令：
--    mysql -u root -p -e 'create database friendstrip;'
--    mysql -u root -p friendstrip < friendstrip.sql


SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";
SET GLOBAL character_set_database=utf8;
SET GLOBAL character_set_server=utf8;
--
-- Database: `friendstrip`
--

-- --------------------------------------------------------

--
-- 表的结构 `Approval` 赞
--

CREATE TABLE IF NOT EXISTS `Approval` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `approval_id` int(11) NOT NULL COMMENT '景点/游记的 id',
  `data` int(11) NOT NULL,
  `type` tinyint(1) NOT NULL COMMENT '1表示景点的赞，2表示游记的赞，3表示评论的赞',
  `author_id` int(11) NOT NULL 
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- -------------------------------------------------------

--
-- 表的结构 `Attention` 关注出行纪录
--

CREATE TABLE IF NOT EXISTS `Attention` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_id` int(11) NOT NULL,
  `trips_id` int(11) NOT NULL,
  `date` int(11) NOT NULL COMMENT '关注时间'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `City` 城市
--

CREATE TABLE IF NOT EXISTS `City` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(200) NOT NULL,
  `country_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
-- --------------------------------------

--
-- 表的结构 `Collection` 用户收藏
--

CREATE TABLE IF NOT EXISTS `Collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_id` int(11) NOT NULL COMMENT '收藏该内容的用户 id',
  `collection__id` int(11) NOT NULL COMMENT '收藏的景点/评论的 id',
  `type` tinyint(1) NOT NULL COMMENT '1景点，2游记'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `Comment` 用户评论
--

CREATE TABLE IF NOT EXISTS `Comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `comment_id` int(11) NOT NULL COMMENT '景点/游记/评论的 id',
  `type` tinyint(1) NOT NULL COMMENT '1表示景点的评论，2表示游记的评论，3表示评论的评论',
  `content` varchar(2000) NOT NULL COMMENT '评论时间',
  `date` int(11) NOT NULL COMMENT '评论日期',
  `author_id` int(11) NOT NULL COMMENT '评论者',
  `parent_id` int(11) NULL COMMENT '父评论'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `Country` 省份
--

CREATE TABLE IF NOT EXISTS `Country` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(200) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `Scenery` 景点
--

CREATE TABLE IF NOT EXISTS `Scenery` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '景点名称',
  `city_id` int(11) NOT NULL COMMENT '所处城市 id',
  `country_id` int(11) NOT NULL COMMENT '所处省份 id',
  `instruction` varchar(10000) NOT NULL COMMENT '景点简介',
  `picture` varchar(200) NOT NULL COMMENT '景点图片',
  `price` varchar(200) NOT NULL COMMENT '门票价格',
  `score` int(11) NOT NULL COMMENT '综合评分'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `School` 学校
--

CREATE TABLE IF NOT EXISTS `School` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(200) NOT NULL,
  `campus` varchar(200) NOT NULL COMMENT '校区',
  `city_id` int(11) NOT NULL COMMENT '所处城市'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `Share` 用户分享
--

CREATE TABLE IF NOT EXISTS `Share` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `share_id` int(11) NOT NULL COMMENT '景点/游记的 id',
  `type` tinyint(1) NOT NULL COMMENT '1表示景点的分享，2表示游记的分享',
  `author_id` int(11) NOT NULL COMMENT '用户 id',
  `data` int(11) NOT NULL COMMENT '分享的时间'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `Travels` 游记
--

CREATE TABLE IF NOT EXISTS `Travels` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` varchar(200) NOT NULL,
  `content` varchar(10000) NOT NULL COMMENT '游记内容',
  `author_id` int(11) NOT NULL COMMENT '游记作者',
  `data` int(11) NOT NULL COMMENT '游记发布时间'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `Trips` 出行信息
--

CREATE TABLE IF NOT EXISTS `Trips` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` varchar(200) NOT NULL COMMENT '标题',
  `content` varchar(10000) NOT NULL COMMENT '内容',
  `city_id` int(11) NOT NULL COMMENT '即将出行的城市',
  `location` int(11) NOT NULL COMMENT '出行的具体地点',
  `data` int(11) NOT NULL COMMENT '出行时间'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(200) NOT NULL,
  `sex` tinyint(1) NOT NULL COMMENT '1男，2女，0未知',
  `phone` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `avatar` varchar(255) NOT NULL COMMENT '用户头像',
  `school_id` int(11) NOT NULL COMMENT '学校id，关联School表'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `Views` 浏览量
--

CREATE TABLE IF NOT EXISTS `Views` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `views_id` int(11) NOT NULL COMMENT '景点/游记的 id',
  `type` tinyint(1) NOT NULL COMMENT '1表示景点，2表示游记'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

