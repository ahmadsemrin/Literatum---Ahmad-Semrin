<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en" class="no-js">

<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>SAGE Journals</title>
    <meta name="description" content="Various styles and inspiration for responsive, flexbox-based HTML pricing tables" />
    <meta name="keywords" content="pricing table, inspiration, ui, modern, responsive, flexbox, html, component" />
    <meta name="author" content="Codrops" />
    <link rel="shortcut icon" href="<c:url value="/jsp/basic_user_page/favicon.ico"/>">
    <link href='https://fonts.googleapis.com/css?family=Homemade+Apple' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Sahitya:400,700' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Roboto:400,700' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Playfair+Display:900' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Alegreya+Sans:400,700,800' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Roboto+Condensed:400,300,700' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Nunito:400,300,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="<c:url value="/jsp/basic_user_page/css/normalize.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/jsp/basic_user_page/css/demo.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/jsp/basic_user_page/css/icons.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/jsp/basic_user_page/css/component.css"/>" />
    <!--[if IE]>
  		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
</head>

<body>
    <div class="container">
        <header class="codrops-header">
            <div class="codrops-links">
                <a class="codrops-icon codrops-icon--prev" href="login" title="Log out"><span>Log out</span></a>
                <a class="codrops-icon codrops-icon--drop" href="basic" title="Back to the article"><span>Back to the article</span></a>
            </div>
            <h1><span>Inspiration for</span> Articles</h1>
        </header>
        <section class="pricing-section bg-11">
            <h2 class="pricing-section__title">Our Articles</h2>
            <div class="pricing pricing--tenzin">
                <c:if test="${requestScope.articles == null}">
                    <h3 style="color:red">There are no articles yet!</h3>
                </c:if>
                <c:forEach var="article" items="${requestScope.articles}">
                    <div class="pricing__item">
                        <h3 class="pricing__title">${article.articleName}</h3>
                        <div class="pricing__price"><span class="pricing__currency">$</span>
                            <c:if test="${article.price == 0}">
                                Free
                            </c:if>
                        </div>
                        <p class="pricing__sentence">${article.title}</p>
                        <ul class="pricing__feature-list">
                            <li class="pricing__feature">${article.publishDate}</li>
                        </ul>
                        <form method="post" action="article">
                            <button class="pricing__action">Read Article
                                <input name="articleName" value="${article.articleName}" type="hidden">
                                <input type="submit" style="color: transparent; background-color: transparent; border-color: transparent; cursor: default;">
                            </button>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </section>
    </div>
    <!-- /container -->
</body>

</html>
