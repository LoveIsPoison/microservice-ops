ALTER TABLE `gate_ignore_uri`
ADD COLUMN `service_id`  varchar(200) NULL AFTER `update_date`;

CREATE TABLE `gate_routes_version` (
  `id` int(11) DEFAULT NULL,
  `service_id` varchar(200) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;