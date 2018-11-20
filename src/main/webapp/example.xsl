<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" version="4.0" cdata-section-elements="yes" encoding="UTF-8" indent="yes" />

    <xsl:variable name="publicationType">
        <xsl:choose>
            <xsl:when test="/component/@type = 'serialArticle'">
                <xsl:text>Article</xsl:text>
            </xsl:when>
            <xsl:when test="/component/@type = 'serialChapter'">
                <xsl:text>Chapter</xsl:text>
            </xsl:when>
            <xsl:otherwise>
                <xsl:text>Other</xsl:text>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:template match="/">
        <xsl:value-of select="$publicationType"/>
        <xsl:copy-of select="title"/>
    </xsl:template>

    <xsl:template match="title">
        <h1><xsl:apply-templates/></h1>
    </xsl:template>

    <xsl:template match="/">
        <xsl:apply-templates select="//title"/>
    </xsl:template>

    <xsl:template match="component">
        <xsl:apply-templates select="header/contentMeta/titleGroup/title[@type = 'main']"/>
    </xsl:template>

    <xsl:template match="title">
        <xsl:if test="@type = 'main'">
            <xsl:apply-templates/>
        </xsl:if>
    </xsl:template>

    <xsl:template name="publicationHeader">
    </xsl:template>

    <xsl:template name="authors">
        <xsl:text>Authors: </xsl:text>
        <xsl:apply-templates select="header/contentMeta/creators"/>
    </xsl:template>

    <xsl:template match="creator">
        <xsl:if test="@creatorRole = 'author'">
            <xsl:value-of select="personName/firstName"/>
            <xsl:text> </xsl:text>
            <xsl:value-of select="personName/secondName"/>
        </xsl:if>
    </xsl:template>

    <xsl:template>
        <xsl:call-template name="authors"/>
    </xsl:template>

    <xsl:template match="creator2">
        <xsl:variable name="numberOfAuthors" select="count(../creators[@creatorRole = 'author'])"/>
        <xsl:if test="@creatorRole = 'author'">
            <xsl:value-of select="personName/firstName"/>
            <xsl:text> </xsl:text>
            <xsl:value-of select="personName/secondName"/>
            <xsl:if test="position() != $numberOfAuthors">
                <xsl:text>, </xsl:text>
            </xsl:if>
        </xsl:if>
    </xsl:template>

    <xsl:template match="creator3">
        <xsl:for-each select="creators[@creatorRole = 'author']">
            Position: <xsl:value-of select="position()"/>
            Last: <xsl:value-of select="last()"/>

            <xsl:choose>
                <xsl:when test="contactDetails/email">
                    <!--<a href="mailto:$[contactDetails/email">

                    </a>-->
                    <xsl:attribute name="href">
                        <xsl:text>mailto:</xsl:text>
                        <xsl:value-of select="contactDetails/email"/>
                    </xsl:attribute>
                    <xsl:value-of select="personName/firstName"/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="personName/secondName"/>
                </xsl:when>
                <xsl:otherwise>

                </xsl:otherwise>
            </xsl:choose>
            <xsl:if test="position() != $numberOfAuthors">
                <xsl:text>, </xsl:text>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="abstract">
        <h2>Abstract: </h2>
        <xsl:apply-templates select="header/contentMeta/abstractData"/>
    </xsl:template>

    <xsl:template name="fulltext">
        <h2>Fulltext: </h2>
        <xsl:apply-templates select="body"/>
    </xsl:template>

    <xsl:template match="section/title">
        <h2><xsl:apply-templates/></h2>
    </xsl:template>

    <xsl:template match="title" mode="section">
        <h2><xsl:apply-templates/></h2>
    </xsl:template>

    <xsl:template match="p">
        <p>
            <xsl:attribute name="style">
                <xsl:text>color:red</xsl:text>
            </xsl:attribute>
            <xsl:apply-templates/>
        </p>
    </xsl:template>

</xsl:stylesheet>