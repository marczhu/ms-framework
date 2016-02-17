-- ALTER TABLE t_user DROP INDEX idx_user_login_name;
-- ALTER TABLE t_user DROP INDEX idx_user_password;
-- ALTER TABLE t_user DROP INDEX idx_user_status;
-- ALTER TABLE t_role DROP INDEX idx_role_name;
-- ALTER TABLE t_users_roles DROP FOREIGN KEY fk_users_roles_user_id;
-- ALTER TABLE t_users_roles DROP FOREIGN KEY fk_users_roles_role_id;
-- ALTER TABLE t_users_roles DROP INDEX idx_users_roles_user_id;
-- ALTER TABLE t_users_roles DROP INDEX idx_users_roles_role_id;
-- ALTER TABLE t_permission DROP INDEX idx_permission_resource_id;
-- ALTER TABLE t_roles_permissions DROP FOREIGN KEY fk_roles_permissions_role_id;
-- ALTER TABLE t_roles_permissions DROP FOREIGN KEY fk_roles_permissions_permission_id;
-- ALTER TABLE t_roles_permissions DROP INDEX idx_roles_permissions_role_id;
-- ALTER TABLE t_roles_permissions DROP INDEX idx_roles_permissions_permission_id;
-- ALTER TABLE t_menu_resources DROP INDEX idx_menu_resources_resource_code;
-- ALTER TABLE t_menu_resources DROP INDEX idx_menu_resources_level;

DROP TABLE IF EXISTS t_user;
DROP TABLE IF EXISTS t_role;
DROP TABLE IF EXISTS t_users_roles;
DROP TABLE IF EXISTS t_permission;
DROP TABLE IF EXISTS t_roles_permissions;
DROP TABLE IF EXISTS t_menu_resources;

CREATE TABLE t_user (
  id               BIGINT      NOT NULL AUTO_INCREMENT,
  login_name       VARCHAR(30) NOT NULL UNIQUE,
  email            VARCHAR(50) NOT NULL DEFAULT '',
  mobile           VARCHAR(20) NOT NULL DEFAULT '',
  password         VARCHAR(32) NOT NULL,
  salt             VARCHAR(16) NOT NULL,
  status           INTEGER     NOT NULL DEFAULT 1,
  create_time      DATETIME    NOT NULL DEFAULT NOW(),
  last_update_time DATETIME    NOT NULL DEFAULT NOW(),
  deleted          INTEGER     NOT NULL DEFAULT -1,
  PRIMARY KEY (id)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =UTF8;

ALTER TABLE t_user ADD INDEX idx_user_login_name (login_name);
ALTER TABLE t_user ADD INDEX idx_user_password (password);
ALTER TABLE t_user ADD INDEX idx_user_status (status);


CREATE TABLE t_role (
  id               INTEGER     NOT NULL AUTO_INCREMENT,
  name             VARCHAR(30) NOT NULL UNIQUE,
  remark           VARCHAR(50),
  create_time      DATETIME    NOT NULL DEFAULT NOW(),
  last_update_time DATETIME    NOT NULL DEFAULT NOW(),
  deleted          INTEGER     NOT NULL DEFAULT -1,
  PRIMARY KEY (id)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =UTF8;

ALTER TABLE t_role ADD INDEX idx_role_name (name);

CREATE TABLE t_users_roles (
  id               BIGINT   NOT NULL AUTO_INCREMENT,
  user_id          BIGINT   NOT NULL,
  role_id          INTEGER  NOT NULL,
  create_time      DATETIME NOT NULL DEFAULT NOW(),
  last_update_time DATETIME NOT NULL DEFAULT NOW(),
  deleted          INTEGER  NOT NULL DEFAULT -1,
  PRIMARY KEY (id)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =UTF8;

ALTER TABLE t_users_roles ADD CONSTRAINT fk_users_roles_user_id FOREIGN KEY (user_id) REFERENCES t_user (id);
ALTER TABLE t_users_roles ADD CONSTRAINT fk_users_roles_role_id FOREIGN KEY (role_id) REFERENCES t_role (id);
ALTER TABLE t_users_roles ADD INDEX idx_users_roles_user_id (user_id);
ALTER TABLE t_users_roles ADD INDEX idx_users_roles_role_id (role_id);


CREATE TABLE t_permission (
  id               INTEGER      NOT NULL AUTO_INCREMENT,
  type             INTEGER      NOT NULL,
  resource_id      INTEGER      NOT NULL,
  expression       VARCHAR(255) NOT NULL,
  remark             VARCHAR(50)  NOT NULL UNIQUE,
  create_time      DATETIME     NOT NULL DEFAULT NOW(),
  last_update_time DATETIME     NOT NULL DEFAULT NOW(),
  deleted          INTEGER      NOT NULL DEFAULT -1,
  PRIMARY KEY (id)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =UTF8;

ALTER TABLE t_permission ADD INDEX idx_permission_resource_id (resource_id);


CREATE TABLE t_roles_permissions (
  id               INTEGER  NOT NULL AUTO_INCREMENT,
  role_id          INTEGER  NOT NULL,
  permission_id    INTEGER  NOT NULL,
  create_time      DATETIME NOT NULL DEFAULT NOW(),
  last_update_time DATETIME NOT NULL DEFAULT NOW(),
  deleted          INTEGER  NOT NULL DEFAULT -1,
  PRIMARY KEY (id)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =UTF8;

ALTER TABLE t_roles_permissions ADD CONSTRAINT fk_roles_permissions_role_id FOREIGN KEY (role_id) REFERENCES t_role (id);
ALTER TABLE t_roles_permissions ADD CONSTRAINT fk_roles_permissions_permission_id FOREIGN KEY (permission_id) REFERENCES t_permission (id);
ALTER TABLE t_roles_permissions ADD INDEX idx_roles_permissions_role_id (role_id);
ALTER TABLE t_roles_permissions ADD INDEX idx_roles_permissions_permission_id (permission_id);

CREATE TABLE t_menu_resources (
  id               INTEGER     NOT NULL AUTO_INCREMENT,
  label_text       VARCHAR(30),
  url              VARCHAR(100),
--  resource_code    VARCHAR(30) NOT NULL UNIQUE,
  remark           VARCHAR(50),
  menu_level       INTEGER     NOT NULL,
  sequence_order   INTEGER     NOT NULL,
  create_time      DATETIME    NOT NULL DEFAULT NOW(),
  last_update_time DATETIME    NOT NULL DEFAULT NOW(),
  deleted          INTEGER     NOT NULL DEFAULT -1,
  PRIMARY KEY (id)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =UTF8;

ALTER TABLE t_menu_resources ADD INDEX idx_menu_resources_level (menu_level);

