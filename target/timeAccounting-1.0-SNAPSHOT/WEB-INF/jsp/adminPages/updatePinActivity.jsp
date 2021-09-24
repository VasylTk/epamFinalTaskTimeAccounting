<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Time accounting</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Open+Sans">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>
<br><br><br>
<div class="container">
    <form action="Controller" method="post">
        <input type="hidden" name="command" value="pinActivityCommand">
        <label class="mr-sm-2" for="inlineFormCustomSelect">User name</label>
        <select name="userLoginUpdate" class="custom-select mr-sm-2" id="inlineFormCustomSelect">
            <c:forEach var="user" items="${sessionScope.users}">
                <option value="${user.id}">${user.userLogin}</option>
            </c:forEach>
        </select>
        <label class="mr-sm-2" for="inlineFormCustomSelect2">Activity name</label>
        <select name="nameActivityUpdate" class="custom-select mr-sm-2" id="inlineFormCustomSelect2">
            <c:forEach var="activity" items="${sessionScope.activities}">
                <option value="${activity.id}">${activity.nameActivity}</option>
            </c:forEach>
        </select>
        <br><br>
        <button type="submit" class="btn btn-primary">add a new category</button>
    </form>
</div>
</body>
</html>
