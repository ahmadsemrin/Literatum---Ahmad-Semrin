<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login to Literatum</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--===============================================================================================-->
    <link rel="icon" href="<c:url value="/jsp/basic_user_page/favicon.ico"/>" type="image/icon"/>
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="jsp/login_page/vendor/bootstrap/css/bootstrap.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="jsp/login_page/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="jsp/login_page/fonts/iconic/css/material-design-iconic-font.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="jsp/login_page/vendor/animate/animate.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="jsp/login_page/vendor/css-hamburgers/hamburgers.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="jsp/login_page/vendor/animsition/css/animsition.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="jsp/login_page/vendor/select2/select2.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="jsp/login_page/vendor/daterangepicker/daterangepicker.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="jsp/login_page/css/util.css">
    <link rel="stylesheet" type="text/css" href="jsp/login_page/css/main.css">
    <!--===============================================================================================-->
</head>
<body>

<div class="limiter">
    <div class="container-login100">
        <div class="wrap-login100">
            <form class="login100-form validate-form" method="post" action="loggedIn">
					<span class="login100-form-title p-b-26">
						Welcome
					</span>
                <span class="login100-form-title p-b-48">
                    <img src="jsp/login_page/images/icons/atypon_logo.jpg" width="60" height="60" style="border-radius:35%">
                </span>

                <div class="wrap-input100 validate-input" data-validate = "Valid email is: a@b.c">
                    <input class="input100" type="text" name="email"/>
                    <span class="focus-input100" data-placeholder="Email"></span>
                </div>

                <div class="wrap-input100 validate-input" data-validate="Enter password">
						<span class="btn-show-pass">
							<i class="zmdi zmdi-eye"></i>
						</span>
                    <input class="input100" type="password" name="password"/>
                    <span class="focus-input100" data-placeholder="Password"></span>
                </div>

                <div class="container-login100-form-btn">
                    <div class="wrap-login100-form-btn">
                        <div class="login100-form-bgbtn"></div>
                        <button class="login100-form-btn" type="submit">
                            Login
                        </button>
                    </div>
                </div>

                <div class="text-center p-t-115">
                    <span class="txt1" id="txt" style="color: red">
                        <%=(request.getAttribute("hiddenFieldLogin") != null)?request.getAttribute("hiddenFieldLogin"):""%>
                    </span>
                </div>

                <div class="text-center p-t-115">
						<span class="txt1">
							Don&apos;t have an account?
						</span>

                    <a class="txt2" href="sign-up">
                        Sign Up
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>


<div id="dropDownSelect1"></div>

<!--===============================================================================================-->
<script src="jsp/login_page/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="jsp/login_page/vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
<script src="jsp/login_page/vendor/bootstrap/js/popper.js"></script>
<script src="jsp/login_page/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
<script src="jsp/login_page/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
<script src="jsp/login_page/vendor/daterangepicker/moment.min.js"></script>
<script src="jsp/login_page/vendor/daterangepicker/daterangepicker.js"></script>
<!--===============================================================================================-->
<script src="jsp/login_page/vendor/countdowntime/countdowntime.js"></script>
<!--===============================================================================================-->
<script src="jsp/login_page/js/main.js"></script>

</body>
</html>