-- user
desc user;
select * from user;

-- join
insert into user values(null, '강광은', 'hhnn@email.com', password('123123'), 'male', now());

-- login
select no, name, email, password, gender, join_date from user where email='shin@email.com' and password = password('123123');

-- findByNo
select no, name, email, password, gender, join_date from user where no = 2;

-- update
update user set name = '신노스케', password = password(123456), gender = 'female' where no = 2;
update user set name = '신짱구', password = password(123123), gender = 'male' where no = 2;
update user set name = '신짱구', gender = 'male' where no = 2;