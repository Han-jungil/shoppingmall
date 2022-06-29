<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- css 링크 -->
<link href="https://fonts.googleapis.com/css?family=Work+Sans:300,400,700" rel="stylesheet">
<link rel="stylesheet" href="assets/css/bootstrap/bootstrap.css">
<link rel="stylesheet" href="assets/css/animate.css">
<link rel="stylesheet" href="assets/fonts/ionicons/css/ionicons.min.css">
<link rel="stylesheet" href="assets/css/owl.carousel.min.css">
<link rel="stylesheet" href="assets/fonts/flaticon/font/flaticon.css">
<link rel="stylesheet" href="assets/fonts/fontawesome/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/css/select2.css">
<link rel="stylesheet" href="assets/css/helpers.css">
<link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/view/mainNav.jsp" />
    <section class="probootstrap-cover overflow-hidden relative" style="background-image: url('assets/images/busanTour.jpg');"data-stellar-background-ratio="0.5" id="section-home">
        <div class="overlay"></div>
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md probootstrap-animate" >
                    <form method="post" class="probootstrap-form" action ="${pageContext.request.contextPath}/InsertReviewController">
                        <div class="row mb-2">
                            <div class="col-md">
                                <div class="form-group">
                                    <table class="probootstrap-date-wrap table table-striped " style="color: black;">
                                        <input type="text" id="reviewNo" name="reviewNo" hidden="hidden">
 
                                        <tr>
	                                        <th>city</th>
								            <th>
								                <select class="js-example-basic-single js-states form-control" id="id_label_single2" name="city">
								                    <c:forEach var="c" items="${cityList}">
								                        <option value="${c.city }">${c.city}</option>
								                    </c:forEach>
								                </select>
								            </th>
                                        </tr>
                                        <tr>
								            <th>review</th>
								            <th><input type="text" id="review" name="review" value=""></th>
								        </tr>
								            <input type="text" id="tourdiyNo" name="tourdiyNo" hidden="hidden">
								            <input type="text" id="estimateNo" name="estimateNo" hidden="hidden">
                                    </table>
                                    <button type="button" id="signup" class="btn btn-primary">등록</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md">
                <h2 class="heading mb-2 display-4 font-light probootstrap-animate">INSERT REVIEW</h2>
                <p class="lead mb-5 probootstrap-animate">
                </div>
            </div>
        </div>
    </section>
     
     <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/popper.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script src="assets/js/owl.carousel.min.js"></script>
    <script src="assets/js/bootstrap-datepicker.js"></script>
    <script src="assets/js/jquery.waypoints.min.js"></script>
    <script src="assets/js/jquery.easing.1.3.js"></script>
    <script src="assets/js/select2.min.js"></script>
    <script src="assets/js/main.js"></script>
</body>
</html>