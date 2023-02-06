desc board;
select * from board;

-- findAll()
select a.no, a.title, a.contents, a.hit, a.reg_date, a.g_no, a.o_no, a.depth, a.user_no, b.name
from board a join user b
	on a.user_no = b.no
where a.contents like "%노하라%"
	 -- or a.contents like '%노하라%' 
order by a.g_no desc, a.o_no
limit 0, 10;

select a.no, a.title, a.contents, a.hit, a.reg_date, a.g_no, a.o_no, a.depth, a.user_no, b.name
from board a join user b
	on a.user_no = b.no
where a.title like "%%"
	or a.contents like '%%'
order by a.g_no desc, a.o_no
limit 10
offset 0;



-- findNo()
select a.no, a.title, a.contents, a.hit, a.reg_date, a.g_no, a.o_no, a.depth, a.user_no, b.name
from board a join user b
	on a.user_no = b.no
where a.no = 2;

-- maxPage()
select count(no) 
from board 
where title like '%%'
	or contents like '%%';

-- insert()

select * from board order by no desc;

insert into board
select null, "제목10", "제목10", 0, now(), ifnull(max(g_no)+1, 0), 1, 0, 1
from board;

select null, "저메추aa", "오늘 저녁 메뉴 추천 좀!", 0, now(), ifnull(max(g_no)+1, 0), 1, 0, 2
from board;

-- modify()
update board set title = "테테", contents = "스트스트" where no = 2;

-- hitUp()
update board set hit = hit + 1 where no = 2;


-- delete()
delete from guestbook where no = ? and password = ?;

-- 답글 추가
update board
set o_no = o_no + 1
where g_no = (
				select g_no
                from(
						select g_no
						from board
						where no = 12
					)a
			)
		and o_no > (
					select o_no
					from(
						select o_no
						from board
						where no = 12
						)b
					);                    

