CREATE DATABASE IF NOT EXISTS config DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

USE config;

CREATE USER 'config'@'%' IDENTIFIED BY 'config';

CREATE USER 'config'@'localhost' IDENTIFIED BY 'config';

GRANT ALL PRIVILEGES ON config.* TO 'config'@'%';
GRANT ALL PRIVILEGES ON config.* TO 'config'@'localhost';

/*------------------------------------------------------------------------*/
/*--		app															  */
/*------------------------------------------------------------------------*/
CREATE TABLE app (
	id BIGINT(22) PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(128) NOT NULL UNIQUE,
	`desc` TEXT NOT NULL,
	creator VARCHAR(32) NOT NULL,
	mender VARCHAR(32) NOT NULL,
	create_time DATETIME NOT NULL,
	last_modified_time DATETIME NOT NULL
) Engine=innodb DEFAULT CHARSET=utf8 comment="应用";

/*------------------------------------------------------------------------*/
/*--		app host													  */
/*------------------------------------------------------------------------*/
CREATE TABLE app_host (
	id BIGINT(22) PRIMARY KEY AUTO_INCREMENT,
	app_id BIGINT(22) NOT NULL,
	name VARCHAR(128) NOT NULL UNIQUE,
	ip VARCHAR(140) NOT NULL UNIQUE,
	config TEXT NOT NULL,
	status VARCHAR(64) NOT NULL,
	creator VARCHAR(32) NOT NULL,
	mender VARCHAR(32) NOT NULL,
	create_time DATETIME NOT NULL,
	last_modified_time DATETIME NOT NULL,
	CONSTRAINT fk_host_app_id FOREIGN KEY(app_id) REFERENCES app(id)
) Engine=innodb DEFAULT CHARSET=utf8 comment="应用的机器";

/*------------------------------------------------------------------------*/
/*--		app staff													  */
/*------------------------------------------------------------------------*/
CREATE TABLE app_staff (
	id BIGINT(22) PRIMARY KEY AUTO_INCREMENT,
	app_id BIGINT(22) NOT NULL,
	name VARCHAR(128) NOT NULL,
	role VARCHAR(32) NOT NULL,
	creator VARCHAR(32) NOT NULL,
	mender VARCHAR(32) NOT NULL,
	create_time DATETIME NOT NULL,
	last_modified_time DATETIME NOT NULL,
	CONSTRAINT uq_name_role UNIQUE(name, role),
	CONSTRAINT fk_staff_app_id FOREIGN KEY(app_id) REFERENCES app(id)
) Engine=innodb DEFAULT CHARSET=utf8 comment="应用人员";


/*------------------------------------------------------------------------*/
/*--		app	config													  */
/*------------------------------------------------------------------------*/
CREATE TABLE app_config (
	id BIGINT(22) PRIMARY KEY AUTO_INCREMENT,
	app_id BIGINT(22) NOT NULL,
	`key` VARCHAR(128) NOT NULL,
	value TEXT NOT NULL,
	version BIGINT(22) NOT NULL,
	creator VARCHAR(32) NOT NULL,
	mender VARCHAR(32) NOT NULL,
	create_time DATETIME NOT NULL,
	last_modified_time DATETIME NOT NULL,
	CONSTRAINT uq_app_id_key UNIQUE(app_id, `key`),
	CONSTRAINT fk_config_app_id FOREIGN KEY(app_id) REFERENCES app(id)
) Engine=innodb DEFAULT CHARSET=utf8 comment="应用的配置";

/*------------------------------------------------------------------------*/
/*--		config publish												  */
/*------------------------------------------------------------------------*/
CREATE TABLE config_publish (
	id BIGINT(22) PRIMARY KEY AUTO_INCREMENT,
	config_id BIGINT(22) NOT NULL,
	value TEXT NOT NULL,
	version BIGINT(22) NOT NULL,
	reason TEXT NOT NULL,
	creator VARCHAR(32) NOT NULL,
	mender VARCHAR(32) NOT NULL,
	create_time DATETIME NOT NULL,
	last_modified_time DATETIME NOT NULL,
	CONSTRAINT fk_config_id FOREIGN KEY(config_id) REFERENCES app_config(id)
) Engine=innodb DEFAULT CHARSET=utf8 comment="配置的发布记录";

/*------------------------------------------------------------------------*/
/*--		config publish host											  */
/*------------------------------------------------------------------------*/
CREATE TABLE config_publish_host (
	id BIGINT(22) PRIMARY KEY AUTO_INCREMENT,
	publish_id BIGINT(22) NOT NULL,
	host_id BIGINT(22) NOT NULL,
	creator VARCHAR(32) NOT NULL,
	mender VARCHAR(32) NOT NULL,
	create_time DATETIME NOT NULL,
	last_modified_time DATETIME NOT NULL,
	CONSTRAINT fk_publish_id FOREIGN KEY(publish_id) REFERENCES config_publish(id),
	CONSTRAINT fk_host_id FOREIGN KEY(host_id) REFERENCES app_host(id),
	CONSTRAINT uq_name_role UNIQUE(publish_id, host_id)
) Engine=innodb DEFAULT CHARSET=utf8 comment="配置发布的机器";