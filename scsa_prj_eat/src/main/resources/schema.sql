-- ssafy_board 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS ssafy_board;
USE ssafy_board;

-- 트랙 정보를 저장하는 curriculum 테이블
CREATE TABLE curriculum (
    code INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- 사용자 정보를 저장하는 users 테이블
CREATE TABLE users (
    id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50) NOT NULL,
    curriculum_code INT,
    FOREIGN KEY (curriculum_code) REFERENCES curriculum(code)
);

-- 맛집 정보를 저장하는 board 테이블
CREATE TABLE board (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    region VARCHAR(200),
    category VARCHAR(200),
    price INT
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

-- 초기 데이터 삽입
-- curriculum 테이블 데이터
INSERT INTO curriculum (code, name) VALUES 
(100, 'Python'),
(200, 'Java'),
(300, 'Embedded'),
(400, 'Mobile');

-- 관리자 계정 생성
INSERT INTO users (id, password, name, curriculum_code) VALUES 
('admin', 'admin', '관리자', 200);

-- 샘플 맛집 데이터
INSERT INTO board (title, region, category, price) VALUES 
('청진동 해장국', '서울 종로구', '한식', 15000),
('이탈리안 레스토랑', '서울 강남구', '양식', 35000),
('홍콩반점', '서울 서초구', '중식', 20000),
('스시코우지', '서울 송파구', '일식', 45000),
('태국음식점', '서울 마포구', '아시안', 25000),
('술집 나무', '서울 용산구', '술집', 30000);