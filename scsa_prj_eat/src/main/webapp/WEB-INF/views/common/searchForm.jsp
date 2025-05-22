<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form action="search" method="GET" class="row lh-base">
	<div class="col-2">
		<label class="form-label">검색기준</label> 
		<select class="form-select" name="key">
			<option value="titleNregion" selected="selected">식당명+지역</option>
			<option value="title">식당명</option>
			<option value="region">지역</option>
			<option value="category">카테고리</option>
			<option value="price">가격대</option>
		</select>
	</div>
	<div class="col-5">
		<label class="form-label">검색 내용</label> 
		<input type="text" name="word" class="form-control">
	</div>
	<div class="col-2">
		<label class="form-label">정렬기준</label> 
		<select class="form-select" name="orderBy">
			<option value="title">식당명</option>
			<option value="region">지역</option>
			<option value="category">카테고리</option>
			<option value="price">가격대</option>
		</select>
	</div>
	<div class="col-2">
		<label class="form-label">정렬방향</label> 
		<select class="form-select" name="orderByDir">
			<option value="asc">오름차순</option>
			<option value="desc">내림차순</option>
		</select>
	</div>
	<div class="col-1 d-flex align-items-end">
		<input type="submit" value="검색" class="btn btn-primary">
	</div>
</form>