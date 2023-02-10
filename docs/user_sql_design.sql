desc user;
select * from user order by no desc;
alter table user add column role enum("ADMIN", "USER") default "USER" after gender;

insert into user values (null, '관리자', 'admin@mysite.com', password('123123'), 'male', "ADMIN", now());