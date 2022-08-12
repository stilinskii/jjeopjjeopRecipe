DROP TABLE Recipe;

CREATE TABLE Recipe (
	rcp_seq	NUMBER		NOT NULL,
	rcp_name	VARCHAR2(50)		NULL,
	user_id	VARCHAR2(10)		NOT NULL,
	filename	VARCHAR2(150)		NULL,
	filepath	VARCHAR2(300)		NULL,
	time	VARCHAR2(10)		NULL,
	difficulty	VARCHAR2(10)		NULL,
	amount	VARCHAR2(20)		NULL,
	summary	VARCHAR2(3000)		NULL,
	hash_tag	VARCHAR2(2000)		NULL,
	rcp_date	DATE		NULL,
	scrap	NUMBER	DEFAULT 0	NULL,
	report	NUMBER	DEFAULT 0	NULL
);

COMMENT ON COLUMN Recipe.user_id IS '아이디';

DROP TABLE User_j;

CREATE TABLE User_j (
	user_id	VARCHAR2(10)		NOT NULL,
	password	VARCHAR2(20)		NULL,
	usertype	NUMBER		NULL,
	username	VARCHAR2(20)		NULL,
	nickname	VARCHAR2(20)		NULL,
	email	VARCHAR2(50)		NULL,
	phone	VARCHAR2(20)		NULL,
	birthday	DATE		NULL,
	gender	NUMBER		NULL,
	postno	NUMBER		NOT NULL,
	address	VARCHAR2(100)		NULL
);

COMMENT ON COLUMN User_j.user_id IS '아이디';

COMMENT ON COLUMN User_j.password IS '비밀번호';

COMMENT ON COLUMN User_j.usertype IS '개인/판매자여부
1:개인|2:판매자';

COMMENT ON COLUMN User_j.username IS '이름';

COMMENT ON COLUMN User_j.nickname IS '별명';

COMMENT ON COLUMN User_j.email IS '이메일';

COMMENT ON COLUMN User_j.phone IS '휴대폰번호';

COMMENT ON COLUMN User_j.birthday IS '생년월일';

COMMENT ON COLUMN User_j.gender IS '성별
1:남|2:여';

COMMENT ON COLUMN User_j.postno IS '우편번호';

COMMENT ON COLUMN User_j.address IS '주소';

DROP TABLE Seller;

CREATE TABLE Seller (
	user_id	VARCHAR2(10)		NOT NULL,
	business_name	VARCHAR2(30)		NOT NULL,
	registration_number	NUMBER		NOT NULL,
	seller_phone	NUMBER		NOT NULL,
	seller_address	VARCHAR2(100)		NOT NULL,
	approval	NUMBER	DEFAULT 0	NOT NULL
);

COMMENT ON COLUMN Seller.user_id IS '아이디';

COMMENT ON COLUMN Seller.business_name IS '상호명';

COMMENT ON COLUMN Seller.registration_number IS '사업자 등록 번호';

COMMENT ON COLUMN Seller.seller_phone IS '사업장 전화번호';

COMMENT ON COLUMN Seller.seller_address IS '사업장 주소';

COMMENT ON COLUMN Seller.approval IS '승인여부';

DROP TABLE Category;

CREATE TABLE Category (
	cate_seq	NUMBER		NOT NULL,
	cate_name	VARCHAR2(50)		NULL
);

DROP TABLE Cate_rcp;

CREATE TABLE Cate_rcp (
	cate_seq	NUMBER		NOT NULL,
	rcp_seq	NUMBER		NOT NULL,
    CONSTRAINT PK_CATE_RCP PRIMARY KEY (
	cate_seq,
    rcp_seq
)
);

DROP TABLE Produce_board;

CREATE TABLE Produce_board (
	produce_num	NUMBER		NOT NULL,
	user_id	VARCHAR2(10)		NOT NULL,
	produce_type	NUMBER		NOT NULL,
	produce_name	VARCHAR2(30)		NOT NULL,
	price	NUMBER		NOT NULL,
	produce_image	VARCHAR2(300)		NOT NULL,
	produce_image_path	VARCHAR2(300)		NOT NULL,
	produce_description	VARCHAR2(200)		NOT NULL
);

COMMENT ON COLUMN Produce_board.produce_num IS '게시글 일련번호';

COMMENT ON COLUMN Produce_board.user_id IS '아이디';

COMMENT ON COLUMN Produce_board.produce_type IS '상품종류(1은 채소, 2는 과일, 3은 곡물, 4는 기타)';

COMMENT ON COLUMN Produce_board.produce_name IS '상품명(판매단위 같이 쓰기)';

COMMENT ON COLUMN Produce_board.price IS '금액';

COMMENT ON COLUMN Produce_board.produce_image IS '상품이미지';

COMMENT ON COLUMN Produce_board.produce_image_path IS '상품이미지저장된 경로';

COMMENT ON COLUMN Produce_board.produce_description IS '상품 상세설명';

DROP TABLE User_rcp;

CREATE TABLE User_rcp (
	user_id	VARCHAR2(10)		NOT NULL,
	rcp_seq	NUMBER		NOT NULL,
	scr_date	DATE		NULL,
    CONSTRAINT PK_USER_RCP PRIMARY KEY (
	user_id,
	rcp_seq
)
);

COMMENT ON COLUMN User_rcp.user_id IS '아이디';

DROP TABLE Comment_rcp;

CREATE TABLE Comment_rcp (
	co_rcp_seq	NUMBER		NOT NULL,
	rcp_seq	NUMBER		NOT NULL,
	user_id	VARCHAR2(10)		NOT NULL,
	co_date	DATE		NULL,
	reports	NUMBER	DEFAULT 0	NULL,
	re_co_seq	NUMBER		NOT NULL,
	re_step	NUMBER	DEFAULT 0	NULL,
	filename	VARCHAR2(150)		NULL,
	filepath	VARCHAR2(300)		NULL
);

COMMENT ON COLUMN Comment_rcp.user_id IS '아이디';

DROP TABLE Produce_review;

CREATE TABLE Produce_review (
	pay_num	NUMBER		NOT NULL,
	produce_name	VARCHAR2(30)		NOT NULL,
	rating	NUMBER		NOT NULL,
	content	VARCHAR2(200)		NOT NULL,
	created_date	DATE		NOT NULL
);

COMMENT ON COLUMN Produce_review.pay_num IS '구매 일련번호';

COMMENT ON COLUMN Produce_review.produce_name IS '상품명(판매단위 같이 쓰기)';

COMMENT ON COLUMN Produce_review.rating IS '0점이상, 5점이하로 0.5점단위로 평가';

COMMENT ON COLUMN Produce_review.content IS '리뷰내용';

COMMENT ON COLUMN Produce_review.created_date IS '리뷰작성일';

DROP TABLE Manual;

CREATE TABLE Manual (
	manual_no	NUMBER		NOT NULL,
	rcp_seq	NUMBER		NOT NULL,
	manual_txt	VARCHAR2(3000)		NULL,
	filename	VARCHAR2(150)		NULL,
	filepath	VARCHAR2(300)		NULL,
    
        CONSTRAINT PK_MANUAL  PRIMARY KEY (manual_no, rcp_seq)
);

DROP TABLE Pay_history;

CREATE TABLE Pay_history (
	pay_num	NUMBER		NOT NULL,
	user_id	VARCHAR2(10)		NOT NULL,
	produce_num	NUMBER		NOT NULL,
	quantity	NUMBER		NOT NULL,
	pay	NUMBER	DEFAULT 0	NOT NULL,
	total_price	NUMBER		NOT NULL
);

COMMENT ON COLUMN Pay_history.pay_num IS '구매 일련번호';

COMMENT ON COLUMN Pay_history.user_id IS '아이디';

COMMENT ON COLUMN Pay_history.produce_num IS '게시글 일련번호';

COMMENT ON COLUMN Pay_history.quantity IS '수량';

COMMENT ON COLUMN Pay_history.pay IS '샀으면 true(1), 아니면 false(0)';

COMMENT ON COLUMN Pay_history.total_price IS '총금액';

DROP TABLE CommunityBoard;

CREATE TABLE CommunityBoard (
	id	NUMBER		NOT NULL,
	user_id	VARCHAR2(10)		NOT NULL,
	rcp_seq	NUMBER		NULL,
	category	NUMBER		NULL,
	title	VARCHAR2(20)		NULL,
	content	VARCHAR2(4000)		NULL,
	created_at	TIMESTAMP		NULL,
	updated_at	TIMESTAMP		NULL,
	report	NUMBER		NULL
);

COMMENT ON COLUMN CommunityBoard.user_id IS '아이디';

COMMENT ON COLUMN CommunityBoard.category IS '0 자유글 1 레시피후기';

DROP TABLE Image;

CREATE TABLE Image (
	id	NUMBER		NOT NULL,
	board_id	NUMBER		NOT NULL,
	filename	VARCHAR2(150)		NULL,
	filepath	VARCHAR2(300)		NULL
);

DROP TABLE Comment_board;

CREATE TABLE Comment_board (
	id	NUMBER		NOT NULL,
	board_id	NUMBER		NOT NULL,
	user_id	VARCHAR2(10)		NOT NULL,
	content	VARCHAR2(1000)		NULL,
	created_at	TIMESTAMP		NULL,
	updated_at	TIMESTAMP		NULL,
	report	NUMBER		NULL
);

COMMENT ON COLUMN Comment_board.user_id IS '아이디';

ALTER TABLE Recipe ADD CONSTRAINT PK_RECIPE PRIMARY KEY (
	rcp_seq
);

ALTER TABLE User_j ADD CONSTRAINT PK_USER PRIMARY KEY (
	user_id
);

ALTER TABLE Seller ADD CONSTRAINT PK_SELLER PRIMARY KEY (
	user_id
);

ALTER TABLE Category ADD CONSTRAINT PK_CATEGORY PRIMARY KEY (
	cate_seq
);

--ALTER TABLE Cate_rcp ADD CONSTRAINT PK_CATE_RCP PRIMARY KEY (
--	cate_seq,
--	rcp_seq
--);

ALTER TABLE Produce_board ADD CONSTRAINT PK_PRODUCE_BOARD PRIMARY KEY (
	produce_num
);

--ALTER TABLE User_rcp ADD CONSTRAINT PK_USER_RCP PRIMARY KEY (
--	user_id,
--	rcp_seq
--);

ALTER TABLE Comment_rcp ADD CONSTRAINT PK_COMMENT_RCP PRIMARY KEY (
	co_rcp_seq
);

ALTER TABLE Produce_review ADD CONSTRAINT PK_PRODUCE_REVIEW PRIMARY KEY (
	pay_num
);

--ALTER TABLE Manual ADD CONSTRAINT PK_MANUAL PRIMARY KEY (
--	manual_no,
--	rcp_seq
--    
--
--);

ALTER TABLE Pay_history ADD CONSTRAINT PK_PAY_HISTORY PRIMARY KEY (
	pay_num
);

ALTER TABLE CommunityBoard ADD CONSTRAINT PK_COMMUNITYBOARD PRIMARY KEY (
	id
);

ALTER TABLE Image ADD CONSTRAINT PK_IMAGE PRIMARY KEY (
	id,
	board_id
);

ALTER TABLE Comment_board ADD CONSTRAINT PK_COMMENT_BOARD PRIMARY KEY (
	id
);

ALTER TABLE Recipe ADD CONSTRAINT FK_User_j_TO_Recipe_1 FOREIGN KEY (
	user_id
)
REFERENCES User_j (
	user_id
);

ALTER TABLE Seller ADD CONSTRAINT FK_User_j_TO_Seller_1 FOREIGN KEY (
	user_id
)
REFERENCES User_j (
	user_id
);

ALTER TABLE Cate_rcp ADD CONSTRAINT FK_Category_TO_Cate_rcp_1 FOREIGN KEY (
	cate_seq
)
REFERENCES Category (
	cate_seq
);

ALTER TABLE Cate_rcp ADD CONSTRAINT FK_Recipe_TO_Cate_rcp_1 FOREIGN KEY (
	rcp_seq
)
REFERENCES Recipe (
	rcp_seq
);

ALTER TABLE Produce_board ADD CONSTRAINT FK_Seller_TO_Produce_board_1 FOREIGN KEY (
	user_id
)
REFERENCES Seller (
	user_id
);

ALTER TABLE User_rcp ADD CONSTRAINT FK_User_j_TO_User_rcp_1 FOREIGN KEY (
	user_id
)
REFERENCES User_j (
	user_id
);

ALTER TABLE User_rcp ADD CONSTRAINT FK_Recipe_TO_User_rcp_1 FOREIGN KEY (
	rcp_seq
)
REFERENCES Recipe (
	rcp_seq
);

ALTER TABLE Comment_rcp ADD CONSTRAINT FK_Recipe_TO_Comment_rcp_1 FOREIGN KEY (
	rcp_seq
)
REFERENCES Recipe (
	rcp_seq
);

ALTER TABLE Comment_rcp ADD CONSTRAINT FK_User_j_TO_Comment_rcp_1 FOREIGN KEY (
	user_id
)
REFERENCES User_j (
	user_id
);

ALTER TABLE Comment_rcp ADD CONSTRAINT FK_Comment_TO_Comment_ FOREIGN KEY (
	re_co_seq
)
REFERENCES Comment_rcp (
	co_rcp_seq
);

ALTER TABLE Produce_review ADD CONSTRAINT FK_Pay_history_TO_review_1 FOREIGN KEY (
	pay_num
)
REFERENCES Pay_history (
	pay_num
);

ALTER TABLE Manual ADD CONSTRAINT FK_Recipe_TO_Manual_1 FOREIGN KEY (
	rcp_seq
)
REFERENCES Recipe (
	rcp_seq
);

ALTER TABLE Pay_history ADD CONSTRAINT FK_User_j_TO_Pay_history_1 FOREIGN KEY (
	user_id
)
REFERENCES User_j (
	user_id
);

ALTER TABLE Pay_history ADD CONSTRAINT FK_board_TO_history FOREIGN KEY (
	produce_num
)
REFERENCES Produce_board (
	produce_num
);

ALTER TABLE CommunityBoard ADD CONSTRAINT FK_User_j_TO_CommunityBoard_1 FOREIGN KEY (
	user_id
)
REFERENCES User_j (
	user_id
);

ALTER TABLE CommunityBoard ADD CONSTRAINT FK_Recipe_TO_CommunityBoard_1 FOREIGN KEY (
	rcp_seq
)
REFERENCES Recipe (
	rcp_seq
);

ALTER TABLE Image ADD CONSTRAINT FK_CommunityBoard_TO_Image_1 FOREIGN KEY (
	board_id
)
REFERENCES CommunityBoard (
	id
);

ALTER TABLE Comment_board ADD CONSTRAINT FK_CommunityBoard_TO_board_1 FOREIGN KEY (
	board_id
)
REFERENCES CommunityBoard (
	id
);

ALTER TABLE Comment_board ADD CONSTRAINT FK_User_j_TO_Comment_board_1 FOREIGN KEY (
	user_id
)
REFERENCES User_j (
	user_id
);

--category의 컬럼인 cate_seq의 시퀀스
CREATE SEQUENCE cate_seq_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

--recipe의 컬럼인 rcp_seq의 시퀀스
CREATE SEQUENCE rcp_seq_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

--Comment_rcp의 컬럼인 co_rcp_seq의 시퀀스
CREATE SEQUENCE co_rcp_seq_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

--produce_board의 컬럼인 produce_num의 시퀀스
CREATE SEQUENCE produce_num_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

--produce_hitory의 컬럼인 pay_num의 시퀀스
CREATE SEQUENCE pay_num_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;