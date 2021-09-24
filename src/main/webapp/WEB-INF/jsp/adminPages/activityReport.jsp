<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Time accounting</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Open+Sans">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Time accounting</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="Controller?command=userCommand">Users</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Controller?command=categoryCommand">Activity categories</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Controller?command=activityCommand">Activity</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Controller?command=pinActivityCommand">Pin activity</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Controller?command=activityReportCommand">Activity Report</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Controller?command=usersReportCommand">User Report</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Controller?command=logoutCommand">Logout</a>
            </li>
        </ul>
    </div>
</nav>
<br><br>
<div class="container">
    <form action="Controller" method="get">
        <input type="hidden" name="command" value="activityReportCommand">
        <label class="mr-sm-2" for="inlineFormCustomSelect">Choose category</label>
        <select name="categoryName" class="custom-select mr-sm-2" id="inlineFormCustomSelect">
            <option value="">${categoryId}</option>
            <c:forEach var="category" items="${categories}">
                <option value="${category.id}">${category.categoryName}</option>
            </c:forEach>
        </select>
        <br><br>
        <button type="submit" class="btn btn-primary">add an activity</button>
    </form>
</div>
<br><br>
<div class="container">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>nameActivity <a href="Controller?command=activityReportCommand&sort=nameActivityDESC&categoryId=${sessionScope.categoryId}"><i class="bi bi-sort-down"></i></a>
                <a href="Controller?command=activityReportCommand&sort=nameActivityASC&categoryId=${sessionScope.categoryId}"><i class="bi bi-sort-up"></i></a></th>
            <th>categoryName <a href="Controller?command=activityReportCommand&sort=categoryNameDESC&categoryId=${sessionScope.categoryId}"><i class="bi bi-sort-down"></i></a>
                <a href="Controller?command=activityReportCommand&sort=categoryNameASC&categoryId=${sessionScope.categoryId}"><i class="bi bi-sort-up"></i></a></th>
            <th>userCount <a href="Controller?command=activityReportCommand&sort=userCountDESC&categoryId=${sessionScope.categoryId}"><i class="bi bi-sort-down"></i></a>
                <a href="Controller?command=activityReportCommand&sort=userCountASC&categoryId=${sessionScope.categoryId}"><i class="bi bi-sort-up"></i></a></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="activity" items="${activitiesReport}">
            <tr>
                <td><c:out value="${activity.nameActivity}" /></td>
                <td><c:out value="${activity.categoryName}" /></td>
                <td><c:out value="${activity.userCount}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
