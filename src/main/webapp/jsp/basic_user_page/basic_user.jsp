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
    <link rel="shortcut icon" href="favicon.ico">
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
                <a class="codrops-icon codrops-icon--drop" href="user" title="Back to the article"><span>Back to the article</span></a>
            </div>
            <h1><span>Inspiration for</span> Articles</h1>
        </header>
        <section class="pricing-section bg-11">
            <h2 class="pricing-section__title">Our Articles</h2>
            <div class="pricing pricing--tenzin">
                <c:forEach var="article" items="${requestScope.articles}">
                    <div class="pricing__item">
                        <h3 class="pricing__title">${article.articleName}</h3>
                        <div class="pricing__price"><span class="pricing__currency">$</span>
                            <c:if test="${article.price == 0}">
                                Free
                            </c:if>
                        </div>
                        <p class="pricing__sentence">${article.articleSection}</p>
                        <ul class="pricing__feature-list">
                            <li class="pricing__feature">${article.publishDate}</li>
                        </ul>
                        <button class="pricing__action">Read Article</button>
                    </div>
                </c:forEach>
                <%--<div class="pricing__item">
                    <h3 class="pricing__title">Freelancer</h3>
                    <div class="pricing__price"><span class="pricing__currency">$</span>Free</div>
                    <p class="pricing__sentence">Perfect for single freelancers who work by themselves</p>
                    <ul class="pricing__feature-list">
                        <li class="pricing__feature">Support forum</li>
                        <li class="pricing__feature">Free hosting</li>
                        <li class="pricing__feature">40MB of storage space</li>
                    </ul>
                    <button class="pricing__action">Read Article</button>
                </div>
                <div class="pricing__item">
                    <h3 class="pricing__title">Small business</h3>
                    <div class="pricing__price"><span class="pricing__currency">$</span>Free</div>
                    <p class="pricing__sentence">Suitable for small businesses with up to 5 employees</p>
                    <ul class="pricing__feature-list">
                        <li class="pricing__feature">Unlimited calls</li>
                        <li class="pricing__feature">Free hosting</li>
                        <li class="pricing__feature">10 hours of support</li>
                        <li class="pricing__feature">Social media integration</li>
                        <li class="pricing__feature">1GB of storage space</li>
                    </ul>
                    <button class="pricing__action">Read Article</button>
                </div>
                <div class="pricing__item">
                    <h3 class="pricing__title">Larger business</h3>
                    <div class="pricing__price"><span class="pricing__currency">$</span>Free</div>
                    <p class="pricing__sentence">Great for large businesses with more than 5 employees</p>
                    <ul class="pricing__feature-list">
                        <li class="pricing__feature">Unlimited calls</li>
                        <li class="pricing__feature">Free hosting</li>
                        <li class="pricing__feature">Unlimited hours of support</li>
                        <li class="pricing__feature">Social media integration</li>
                        <li class="pricing__feature">Anaylitcs integration</li>
                        <li class="pricing__feature">Unlimited storage space</li>
                    </ul>
                    <button class="pricing__action">Read Article</button>
                </div>--%>
            </div>
        </section>
    </div>
    <!-- /container -->
</body>

</html>
