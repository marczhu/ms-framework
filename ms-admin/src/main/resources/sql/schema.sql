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
  salt             VARCHAR(24) NOT NULL,
  status           INTEGER     NOT NULL DEFAULT 1,
  create_time      DATETIME    NOT NULL DEFAULT NOW(),
  last_update_time DATETIME    NOT NULL DEFAULT NOW(),
  deleted          INTEGER     NOT NULL DEFAULT -1,
  PRIMARY KEY (id)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =UTF8;
