package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;

public class BoardDao {
	// 전체 게시글 리스트
	public List<BoardVo> findAll(String searchWord, Long limit, Long offset) {
		List<BoardVo> result = new ArrayList<>();
	
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql ="select a.no, a.title, a.contents, a.hit, a.reg_date, a.g_no, a.o_no, a.depth, a.user_no, b.name"
					+ " from board a join user b"
					+ "		on a.user_no = b.no"
					+ "	where a.title like ?"					
					+ "		or a.contents like ?"					
					+ " order by a.g_no desc, a.o_no"
					+ " limit ?" 
					+ " offset ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%"+searchWord+"%");
			pstmt.setString(2, "%"+searchWord+"%");
			pstmt.setLong(3, limit);
			pstmt.setLong(4, offset);
			
			

			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setHit(rs.getLong(4));
				vo.setRegDate(rs.getString(5));
				vo.setGroupNo(rs.getLong(6));
				vo.setOrderNo(rs.getLong(7));
				vo.setDepth(rs.getLong(8));
				vo.setUserNo(rs.getLong(9));
				vo.setUserName(rs.getString(10));
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	// 게시글 하나
	public BoardVo findNo(Long no) {
		BoardVo result = new BoardVo();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql =
					  "select a.no, a.title, a.contents, a.hit, a.reg_date, a.g_no, a.o_no, a.depth, a.user_no, b.name"
					  + " from board a join user b"
					  + "	on a.user_no = b.no"
					  + " where a.no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.setNo(rs.getLong(1));
				result.setTitle(rs.getString(2));
				result.setContents(rs.getString(3));
				result.setHit(rs.getLong(4));
				result.setRegDate(rs.getString(5));
				result.setGroupNo(rs.getLong(6));
				result.setOrderNo(rs.getLong(7));
				result.setDepth(rs.getLong(8));
				result.setUserNo(rs.getLong(9));
				result.setUserName(rs.getString(10));
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	// 마지막 페이지 (페이지 사이즈)
	public int findMaxPage(String searchWord) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql ="select count(no)"
					+ " from board"
					+ " where title like ?"
					+ " 	or contents like ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%"+searchWord+"%");
			pstmt.setString(2, "%"+searchWord+"%");

			rs = pstmt.executeQuery();
			while(rs.next()) {
				result = (int) rs.getLong(1);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	// 게시글 입력
	public void insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = 
					"insert into board " + 
					"select null, ?, ?, 0, now(), ifnull(max(g_no)+1, 0), 1, 0, ? " + 
					"from board";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserNo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 답글 달기
	public void Reply(BoardVo vo) {
		updateReply(vo);
		insertReply(vo);
	}
	
	// 답글달기 - 답글 남기려는 글 이후의 orderNo + 1
	public void updateReply(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = 
					  " update board"
					  + " set o_no = o_no + 1"
					  + " where g_no = ("
					  + "				select g_no"
					  + "                from("
					  + "						select g_no"
					  + "						from board"
					  + "						where no = ?"
					  + "					)a"
					  + "			)"
					  + "		and o_no > ("
					  + "					select o_no"
					  + "                	from("
					  + "							select o_no"
					  + "							from board"
					  + "							where no = ?"
					  + "						)b"
					  + "					)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getReplyNo());
			pstmt.setLong(2, vo.getReplyNo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	// 답글달기 - 답글 남기려는  글 다음 자리에 답글 추가
	public void insertReply(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = 
					  "insert into board"
					  + " select null, ?, ?, 0, now(), g_no, o_no + 1, depth + 1, ?"
					  + " from board"
					  + " where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserNo());
			pstmt.setLong(4, vo.getReplyNo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 게시글 수정
	public void modify(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = 
					  "update board set title = ?, contents = ? where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	// 조회수 업데이트
	public void hitUp(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = 
					  "update board set hit = hit + 1 where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}	
	
	// 게시글 삭제
	public void deleteByNo(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "delete from board where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);			
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.10.116:3307/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
		
		return conn;
	}
}
