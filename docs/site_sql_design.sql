desc site;
select * from site;
insert into site 
values(null, 'MySite', 
			'안녕하세요. 강광은의 mysite에 오신 것을 환영합니다.', 
			'/assets/images/study.gif', 
            '이 사이트는 웹 프로그램밍 실습과제 예제 사이트입니다. 메뉴는 사이트 소개, 방명록, 게시판이 있구요. Java 수업 + 데이터베이스 수업 + 웹프로그래밍 수업 배운 거 있는거 없는 거 다 합쳐서 만들어 놓은 사이트 입니다.'
            );
            
update site
set title = "YourSite"
where no = 1;

update site
set title = 'YourSite', 
	welcome = '안녕하세요. 강광은의 mysite에 오신 것을 환영합니다.', 
    profile = '/assets/images/study.gif', 
    description = '이 사이트는 웹 프로그램밍 실습과제 예제 사이트입니다.\n
      메뉴는 사이트 소개, 방명록, 게시판이 있구요. Java 수업 + 데이터베이스 수업\n
      + 웹프로그래밍 수업 배운 거 있는거 없는 거 다 합쳐서 만들어 놓은 사이트 입니다.'
where no = 1;