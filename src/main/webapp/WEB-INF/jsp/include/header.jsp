<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="h-100 d-flex justify-content-between align-items-center">
	<%-- 로고 --%>
	<div>
		<h2>메모 게시판</h2>
	</div>
	
	<%-- 로그인/아웃 --%>
	<div>
		<span>${userName}님 안녕하세요</span>
		<a href="/user/sign-out">로그아웃</a>
	</div>
</div>