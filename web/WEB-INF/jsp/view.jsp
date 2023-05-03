<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.AbstractSection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.CompanySection" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %><%--
  Created by IntelliJ IDEA.
  User: mbuhalov
  Date: 17.04.2023
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${ sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
        <c:choose>
        <c:when test="${type =='PERSONAL' || type =='OBJECTIVE'}">
    <h3><%=sectionEntry.getKey().getTitle()%>
    </h3><br>
    <%=((TextSection) section).getValue()%><br>
    </c:when>
    <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
        <h3><%=sectionEntry.getKey().getTitle()%>
        </h3><br>
        <tr>
            <td colspan="2">
                <ul>
                    <c:forEach var="item" items="<%=((ListSection) section).getValues()%>">
                        <li>${item}</li>
                    </c:forEach>
                </ul>
            </td>
        </tr>
    </c:when>
    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
        <c:forEach var="org" items="<%=((CompanySection) section).getCompanies()%>">
            <tr>
                <td colspan="2">
                    <h3>${org.name}</h3>
                    <p>
                </td>
            </tr>
            <c:forEach var="position" items="${org.periods}">
                <jsp:useBean id="position" type="com.urise.webapp.model.Period"/>
                <tr>
                    <td width="15%" style="vertical-align: top"><%=HtmlUtil.formatDates(position)%>
                    </td>
                    <td><b>${position.name}</b><br>${position.description}</td>
                    <p>
                </tr>
            </c:forEach>
        </c:forEach>
    </c:when>
    </c:choose>
    <p>
        </c:forEach>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
