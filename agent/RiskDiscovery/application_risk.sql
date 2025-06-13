-- 1. 应用风险检测规则表
CREATE TABLE `application_risk_rules` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `risk_name` varchar(255) NOT NULL COMMENT '风险名称',
  `risk_desc` text COMMENT '风险描述',
  `risk_level` int(11) NOT NULL COMMENT '风险等级：1-低风险，2-中风险，3-高风险，4-严重风险',
  `risk_type` varchar(100) NOT NULL COMMENT '风险类型：权限配置、信息泄露、服务配置、认证绕过等',
  `target_service` varchar(100) NOT NULL COMMENT '目标服务：tomcat、ftp、nginx、apache等',
  `detection_method` varchar(50) NOT NULL COMMENT '检测方法：HTTP、HTTPS、PORT、SERVICE',
  `detection_path` varchar(500) DEFAULT NULL COMMENT '检测路径',
  `detection_payload` text COMMENT '检测载荷',
  `risk_flag` text COMMENT '风险标识：用于判断是否存在风险的关键字',
  `remediation_advice` text COMMENT '修复建议',
  `status` enum('active','inactive') DEFAULT 'active' COMMENT '规则状态',
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_risk_type` (`risk_type`),
  KEY `idx_target_service` (`target_service`),
  KEY `idx_risk_level` (`risk_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用风险检测规则表';

-- 2. 应用风险检测结果表
CREATE TABLE `application_risk_results` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `rule_id` int(11) NOT NULL COMMENT '规则ID',
  `risk_name` varchar(255) NOT NULL COMMENT '风险名称',
  `risk_type` varchar(100) NOT NULL COMMENT '风险类型',
  `risk_level` int(11) NOT NULL COMMENT '风险等级',
  `target_host` varchar(255) NOT NULL COMMENT '目标主机',
  `target_url` varchar(500) DEFAULT NULL COMMENT '目标URL',
  `detection_time` datetime NOT NULL COMMENT '检测时间',
  `is_risky` tinyint(1) DEFAULT 0 COMMENT '是否存在风险：0-否，1-是',
  `risk_detail` text COMMENT '风险详情',
  `response_content` text COMMENT '响应内容（截取前1000字符）',
  `error_message` text COMMENT '错误信息',
  `remediation_advice` text COMMENT '修复建议',
  `status` enum('pending','confirmed','fixed','false_positive') DEFAULT 'pending' COMMENT '处理状态',
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_rule_id` (`rule_id`),
  KEY `idx_target_host` (`target_host`),
  KEY `idx_detection_time` (`detection_time`),
  KEY `idx_is_risky` (`is_risky`),
  FOREIGN KEY (`rule_id`) REFERENCES `application_risk_rules` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用风险检测结果表';

-- 3. 插入示例风险检测规则数据
INSERT INTO `application_risk_rules` (
    `risk_name`, `risk_desc`, `risk_level`, `risk_type`,
    `target_service`, `detection_method`, `detection_path`,
    `detection_payload`, `risk_flag`, `remediation_advice`
) VALUES
(
    'Tomcat管理页面未授权访问',
    'Tomcat管理控制台可以被未经授权的用户访问，存在严重安全风险',
    4,
    '认证绕过',
    'tomcat',
    'HTTP',
    '/manager/html',
    '',
    'Apache Tomcat',
    '1. 配置manager-gui角色的用户认证\n2. 限制访问IP范围\n3. 修改默认访问路径'
),
(
    'Tomcat 404错误页面信息泄露',
    'Tomcat默认404错误页面可能泄露服务器版本信息',
    2,
    '信息泄露',
    'tomcat',
    'HTTP',
    '/nonexistent-page-test-404',
    '',
    'Apache Tomcat',
    '1. 自定义404错误页面\n2. 隐藏服务器版本信息\n3. 配置server.xml中的Server标签'
),
(
    'FTP匿名登录检测',
    '检测FTP服务是否允许匿名用户登录',
    3,
    '权限配置',
    'ftp',
    'PORT',
    '',
    '21',
    '',
    '1. 禁用匿名FTP访问\n2. 配置强密码策略\n3. 启用FTP over SSL/TLS'
),
(
    'MySQL数据库端口暴露检测',
    '检测MySQL数据库端口是否对外开放',
    3,
    '服务配置',
    'mysql',
    'PORT',
    '',
    '3306',
    '',
    '1. 配置防火墙规则限制访问\n2. 修改默认端口\n3. 配置bind-address参数'
),
(
    'Redis未授权访问检测',
    '检测Redis服务是否存在未授权访问漏洞',
    4,
    '认证绕过',
    'redis',
    'PORT',
    '',
    '6379',
    '',
    '1. 设置Redis访问密码\n2. 配置防火墙规则\n3. 修改默认端口\n4. 禁用危险命令'
),
(
    'Nginx状态页面检测',
    '检测Nginx状态监控页面是否可以被未授权访问',
    2,
    '信息泄露',
    'nginx',
    'HTTP',
    '/nginx_status',
    '',
    'Active connections',
    '1. 限制状态页面访问IP\n2. 配置HTTP认证\n3. 关闭不必要的状态页面'
),
(
    'Apache服务器状态页面检测',
    '检测Apache服务器状态页面是否可以被未授权访问',
    2,
    '信息泄露',
    'apache',
    'HTTP',
    '/server-status',
    '',
    'Apache Server Status',
    '1. 限制状态页面访问\n2. 配置访问控制\n3. 关闭ExtendedStatus'
),
(
    'Tomcat AJP端口检测',
    '检测Tomcat AJP连接器端口是否暴露',
    3,
    '服务配置',
    'tomcat',
    'PORT',
    '',
    '8009',
    '',
    '1. 关闭不必要的AJP连接器\n2. 配置访问限制\n3. 修改默认端口'
),
(
    'PhpMyAdmin未授权访问检测',
    '检测PhpMyAdmin管理界面是否可以被未授权访问',
    3,
    '认证绕过',
    'phpmyadmin',
    'HTTP',
    '/phpmyadmin/',
    '',
    'phpMyAdmin',
    '1. 配置HTTP认证\n2. 限制访问IP范围\n3. 修改默认访问路径\n4. 更新到最新版本'
),
(
    'MongoDB未授权访问检测',
    '检测MongoDB数据库是否存在未授权访问',
    4,
    '认证绕过',
    'mongodb',
    'PORT',
    '',
    '27017',
    '',
    '1. 启用MongoDB认证\n2. 配置防火墙规则\n3. 修改默认端口\n4. 配置访问控制'
);