<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login to Literatum</title>
</head>
<body>
    <form method="post" action="login">
        <h1>Login to Literatum</h1>
        <table>
            <tr>
                <td>
                    <span>Username: </span>
                </td>
                <td>
                    <input id="username" type="text" name="username" placeholder="username"/>
                </td>
            </tr>
            <tr>
                <td>
                    <span>Password: </span>
                </td>
                <td>
                    <input id="password" type="password" name="password" placeholder="password"/>
                </td>
            </tr>
            <tr>
                <td>
                    <span>&nbsp;</span>
                </td>
                <td>
                    <input type="submit" value="Login"/>
                </td>
                <td>
                    <a href="LoginPage.jsp">Not a user? Register to Literatum</a>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
