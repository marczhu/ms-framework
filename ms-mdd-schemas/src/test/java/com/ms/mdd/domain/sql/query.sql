--roles
select
  ur.id,ur.user_id,ur.role_id,r.id,r.name
from t_users_roles ur
inner join t_role r on ur.role_id = r.id
where ur.user_id=1

--t_permission 1
select
  rp.id,rp.permission_id,rp.role_id,p.id,p.name,p.expression,p.type
from t_roles_permissions rp
inner join t_permission p on rp.permission_id = p.id
where rp.role_id in(
  select role_id from t_users_roles ur where ur.user_id=1
)

--t_permission 2
select
  rp.id,rp.permission_id,rp.role_id,p.id,p.name,p.expression,p.type
from t_roles_permissions rp
inner join t_permission p on rp.permission_id = p.id
where exists(select role_id from t_users_roles where ur.user_id=1 and role_id=rp.role_id)

--select permissions by user
select p.type,p.resource_id,p.expression from t_permission p where p.id in(select rp.permission_id from t_roles_permissions rp where rp.role_id in(select  ur.id from t_users_roles ur where ur.user_id=1));

--select permissions by user2 ?
select
  rp.id,rp.permission_id,rp.role_id,p.type,p.resource_id,p.expression
from t_roles_permissions rp
inner join t_permission p on rp.permission_id = p.id
where exists(select role_id from t_users_roles where ur.user_id=1 and role_id=rp.role_id)

