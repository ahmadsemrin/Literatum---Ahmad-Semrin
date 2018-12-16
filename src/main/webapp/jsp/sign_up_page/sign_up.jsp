<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>Register to Atypon</title>
  <link rel="icon" href="<c:url value="/jsp/basic_user_page/favicon.ico"/>" type="image/icon"/>
  <link rel="stylesheet" href="jsp/sign_up_page/css/style.css">
</head>
<body>
  <div id="login-box">
  <div class="left">
    <form method="post" action="signed">
      <h1>Sign up</h1>
      <input type="text" name="email" placeholder="E-mail" />
      <input type="password" name="password" placeholder="Password" />
      <input type="password" name="password2" placeholder="Retype password" />
      <span class="txt1" id="txt" style="color: red">
        <%=(request.getAttribute("hiddenFieldSignUp") != null)?request.getAttribute("hiddenFieldSignUp"):""%>
      </span>
      <input type="submit" name="signup_submit" value="Sign me up" />
    </form>
  </div>
  <div class="right">
    <span class="loginwith">Sign in with<br />social network</span>
    <button class="social-signin facebook">Log in with facebook</button>
    <button class="social-signin twitter">Log in with Twitter</button>
    <button class="social-signin google">Log in with Google+</button>
  </div>
  <div class="or">OR</div>
  </div>
</body>
</html>
