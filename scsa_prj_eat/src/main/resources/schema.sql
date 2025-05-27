-- 기존 데이터베이스 삭제
DROP DATABASE IF EXISTS ssafy_board;

-- ssafy_board 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS ssafy_board;
USE ssafy_board;

-- 회사 정보를 저장하는 company 테이블
CREATE TABLE company (
    code INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL CHECK (name IN ('dx', 'ds', 'sds'))
);

-- 사용자 정보를 저장하는 users 테이블
CREATE TABLE users (
    id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50) NOT NULL,
    class int ,
    phone VARCHAR(20),
    company_code INT,
    FOREIGN KEY (company_code) REFERENCES company(code)
);

-- 맛집 정보를 저장하는 board 테이블
CREATE TABLE board (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    region VARCHAR(200),
    category VARCHAR(200),
    price INT,
    content TEXT,
    address VARCHAR(500),
    user_id VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 첨부 파일 정보를 저장하는 board_file 테이블
CREATE TABLE board_file (
    file_id INT AUTO_INCREMENT PRIMARY KEY,
    id INT,
    file_path VARCHAR(255),
    ori_name VARCHAR(255),
    system_name VARCHAR(255),
    FOREIGN KEY (id) REFERENCES board(id) ON DELETE CASCADE
);

CREATE TABLE diary (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    date DATE,
    context TEXT,
    user_id VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 좋아요 정보를 저장하는 Users_Board_likes 테이블
CREATE TABLE Users_Board_likes (
    board_id INT,
    user_id VARCHAR(50),
    liked BOOLEAN DEFAULT true,
    PRIMARY KEY (board_id, user_id),
    FOREIGN KEY (board_id) REFERENCES board(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 초기 데이터 삽입
-- company 테이블 데이터
INSERT INTO company (code, name) VALUES 
(100, 'dx'),
(200, 'ds'),
(300, 'sds');

-- 관리자 계정 생성
INSERT INTO users (id, password, name, class, phone, company_code) VALUES 
('admin', 'admin', '관리자', 24, '010-1234-5678', 200);

-- 샘플 맛집 데이터
INSERT INTO board (title, region, category, price, user_id) VALUES 
('청진동 해장국', '서울 종로구', '한식', 15000, 'admin'),
('이탈리안 레스토랑', '서울 강남구', '양식', 35000, 'admin'),
('홍콩반점', '서울 서초구', '중식', 20000, 'admin'),
('스시코우지', '서울 송파구', '일식', 45000, 'admin'),
('태국음식점', '서울 마포구', '아시안', 25000, 'admin'),
('술집 나무', '서울 용산구', '술집', 30000, 'admin');