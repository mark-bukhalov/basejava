<%--
  Created by IntelliJ IDEA.
  User: mbuhalov
  Date: 17.04.2023
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
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
    <c:if test="${error != null}">
        <c:forEach var="valueerror" items="${error}">
            <p style="background-color:tomato;">${valueerror}.</p>
        </c:forEach>
    </c:if>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:!!!</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.name}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>

        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:catch var="errorFlag">
                <c:set var="section" value="${resume.getSectionExc(type)}"/>

            </c:catch>
            <c:if test="${empty errorFlag}">
                <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
                <h2><a>${type.title}</a></h2>
                <c:choose>
                    <c:when test="${type=='OBJECTIVE'}">
                        <input type='text' name='${type}' size=75 value='<%=((TextSection) section).getValue()%>'>
                    </c:when>
                    <c:when test="${type=='PERSONAL'}">
                        <textarea name='${type}' cols=75 rows=5><%=((TextSection) section).getValue()%></textarea>
                    </c:when>
                    <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <textarea name='${type}' cols=75
                              rows=5><%=String.join("\n", ((ListSection) section).getValues())%></textarea>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                        <c:forEach var="org" items="<%=((CompanySection) section).getCompanies()%>"
                                   varStatus="counter">
                            <dl>
                                <dt>Название учереждения:</dt>
                                <dd><input type="text" name='${type}' size=100 value="${org.name}"></dd>
                            </dl>
                            <dl>
                                <dt>Сайт учереждения:</dt>
                                <dd><input type="text" name='${type}url' size=100 value="${org.url}"></dd>
                                </dd>
                            </dl>
                            <br>

                            <div style="margin-left: 30px">
                                <c:forEach var="pos" items="${org.periods}">
                                    <jsp:useBean id="pos" type="com.urise.webapp.model.Period"/>
                                    <dl>
                                        <dt>Начальная дата:</dt>
                                        <dd>
                                            <input type="text" name="${type.name()}${counter.index}startDate" size=10
                                                   value="<%=DateUtil.format(pos.getBeginDate())%>"
                                                   placeholder="MM/yyyy">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>Конечная дата:</dt>
                                        <dd>
                                            <input type="text" name="${type.name()}${counter.index}endDate" size=10
                                                   value="<%=DateUtil.format(pos.getEndDate())%>" placeholder="MM/yyyy">
                                    </dl>
                                    <dl>
                                        <dt>Должность:</dt>
                                        <dd><input type="text" name='${type.name()}${counter.index}title' size=75
                                                   value="${pos.name}">
                                    </dl>
                                    <dl>
                                        <dt>Описание:</dt>
                                        <dd><textarea name="${type.name()}${counter.index}description" rows=5
                                                      cols=75>${pos.description}</textarea></dd>
                                    </dl>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:if>
        </c:forEach>


        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
