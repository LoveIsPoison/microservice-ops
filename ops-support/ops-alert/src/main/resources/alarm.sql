CREATE TABLE `alert_info` (
	`id` INT (11) NOT NULL AUTO_INCREMENT COMMENT '报警信息id',
	`alert_detail` VARCHAR (10000) DEFAULT NULL COMMENT '详细报警信息，完整log',
	`group_id` INT (11) DEFAULT NULL COMMENT '规则组ID',
	`status` VARCHAR (10) DEFAULT NULL COMMENT '报警信息状态{Trigger:"已触发",Notice:"已通知",Handle:"处理中",Finished:"已解除"}',
	`create_date` datetime DEFAULT NULL COMMENT '创建时间',
	`update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`create_user` VARCHAR (255) DEFAULT NULL,
	`update_user` VARCHAR (255) DEFAULT NULL,
	`app_name` VARCHAR (50) DEFAULT NULL COMMENT '服务名称，或者服务IP',
	`alert_desc` VARCHAR (255) DEFAULT NULL COMMENT '报警信息描述{如：关键字：exception 5分钟 出现3次 log地址：/log}',
	PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

CREATE TABLE `rule_group` (
	`id` INT (11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR (30) DEFAULT NULL COMMENT '规则组名称',
	`descr` VARCHAR (255) DEFAULT NULL COMMENT '规则组描述',
	`alarm_type` VARCHAR (10) DEFAULT NULL COMMENT '报警邮件类型',
	`mail_title` VARCHAR (30) DEFAULT NULL COMMENT '报警邮件标题',
	`mail_content` VARCHAR (255) DEFAULT NULL COMMENT '报警邮件内容',
	`status` TINYINT (10) DEFAULT NULL COMMENT '规则组状态：{0:禁用;1:启用}',
	PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 109 DEFAULT CHARSET = utf8;

CREATE TABLE `rule_info` (
	`id` INT (11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR (50) DEFAULT NULL COMMENT '规则名称',
	`rule_desc` VARCHAR (255) DEFAULT NULL COMMENT '规则描述',
	`keyword` VARCHAR (255) DEFAULT NULL COMMENT '关键字',
	`time` INT (10) DEFAULT NULL COMMENT '持续时间',
	`count` INT (11) DEFAULT NULL COMMENT '出现次数',
	`group_id` INT (11) DEFAULT NULL COMMENT '规则组ID',
	`status` TINYINT (4) DEFAULT NULL COMMENT '规则状态',
	PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 73 DEFAULT CHARSET = utf8;

CREATE TABLE `rule_scope` (
	`id` INT (11) NOT NULL AUTO_INCREMENT,
	`type` VARCHAR (10) DEFAULT NULL COMMENT '服务类型',
	`ip_app` VARCHAR (30) DEFAULT NULL COMMENT '服务名称或IP',
	`group_id` INT (11) DEFAULT NULL COMMENT '规则组ID',
	PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 126 DEFAULT CHARSET = utf8;

-- CREATE TABLE `user_group` (
-- 	`id` VARCHAR (255) DEFAULT NULL,
-- 	`name` VARCHAR (255) DEFAULT NULL COMMENT '用户组ID',
-- 	`desc` VARCHAR (255) DEFAULT NULL COMMENT '用户组描述'
-- ) ENGINE = INNODB DEFAULT CHARSET = utf8;
-- 
CREATE TABLE `user_group_alert` (
	`id` INT (11) NOT NULL AUTO_INCREMENT,
	`user_group_id` INT (11) DEFAULT NULL COMMENT '用户组ID',
	`rule_group__id` INT (11) DEFAULT NULL COMMENT '规则组ID',
	PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 117 DEFAULT CHARSET = utf8;

