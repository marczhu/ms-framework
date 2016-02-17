select 1 ;
insert into t_user (id,login_name,email,mobile,password,salt,status,create_time,last_update_time,deleted ) values (1,'admin','','','yL8R5+pcMviPQe41UjqccdRR+0FT2/Zc','2T7kqByIhsPgJIwL',1,sysdate(),sysdate(),-1);
select * from t_user;