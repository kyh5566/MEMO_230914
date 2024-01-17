<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글쓰기</h1>
		
		<input type="text" id="subject" class="form-control" placeholder="제목을 입력하세요">
		<textarea id="content" class="form-control" placeholder="내용을 입력하세요" rows="10"></textarea>
		<div class="d-flex justify-content-end my-3">
			<input type="file" id="file" accept=".jpg, .png, .gif">
		</div>
		
		<div class="d-flex justify-content-between">
			<button type="button" id="postListBtn" class="btn btn-dark">목록</button>
			
			<div>
				<button type="button" id="clearBtn" class="btn btn-secondary">모두지우기</button>
				<button type="button" id="saveBtn" class="btn btn-info">저장</button>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 목록 버튼 클릭 => 목록화면이동
		$("#postListBtn").on("click",function() {
			location.href = "/post/post-list-view";
		});
		
		// 모두 지우기 버튼 클릭
		$("#clearBtn").on("click",function() {
			//alert("클릭");
			$("#subject").val("");
			$("#content").val("");
		});
		
		// 저장 버튼
		$("#saveBtn").on("click",function() {
			//alert("저장버튼");
			let subject = $("#subject").val().trim();
			let content = $("#content").val();
			let fileName = $("#file").val();
			//alert(fileName); C:\fakepath\img1.jpg
			
			// validation
			if (subject == "") {
				alert("제목을 입력하세요");
				return;
			}
			if (content == "") {
				alert("내용을 입력하세요");
				return;
			}
			//파일이 업로드 된 경우에만 확장자 체크
			if (fileName) {
				//alert("파일");
				//C:\fakepath\img1.jpg
				// 확장자만 뽑은후 소문자로 변경해서 검사한다.
				let extension = fileName.split(".").pop().toLowerCase();
				//alert(extension);
				
				if ($.inArray(extension, ['jpg','png','gif','jpeg']) == -1) {
					alert("이미지 파일만 업로드 할수있습니다");
					$("#file").val(""); // 파일을 비운다
					return;
				}
			}
			
			// form 태그를 js에서 만든다.
			// 이미지 업로드할때는 반드시 form 태그가 있어야한다.
			let formData = new FormData();
			formData.append("subject",subject);
			// key 는 name 속성과 같다 Request Parameter 명
			formData.append("content",content);
			formData.append("file", $("#file")[0].files[0]);
			// AJAX
			$.ajax({
				//request
				type:"POST"
				,url:"/post/create"
				,data:formData
				,enctype:"multipart/form-data" // 파일업로드를 위한 추가 설정
				,processData:false // 파일업로드를 위한 추가 설정
				,contentType:false // 파일업로드를 위한 추가 설정
				//response
				,success:function(data) {
					if (data.code == 200) {
						alert("저장 완료");
						location.href="/post/post-list-view";
					} else {
						alert(data.error_message);
					}
				}
			,error:function(request,status,error) {
				alert("글을 저장하는데 실패했습니다");
			}
			});
		});
	});
</script>