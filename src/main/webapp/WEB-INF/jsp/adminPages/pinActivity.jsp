<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "ex" uri = "/WEB-INF/jsp/tags/customTags.tld"%>
<html>
<head>
    <title>Time accounting</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Open+Sans">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
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

<div class="container">
    <form action="Controller" method="post">
        <input type="hidden" name="command" value="pinActivityCommand">
        <label class="mr-sm-2" for="inlineFormCustomSelect">User name</label>
        <select name="userLogin" class="custom-select mr-sm-2" id="inlineFormCustomSelect">
            <c:forEach var="user" items="${sessionScope.users}">
                <option value="${user.id}">${user.userLogin}</option>
            </c:forEach>
        </select>
        <label class="mr-sm-2" for="inlineFormCustomSelect2">Activity name</label>
        <select name="nameActivity" class="custom-select mr-sm-2" id="inlineFormCustomSelect2">
            <c:forEach var="activity" items="${sessionScope.activities}">
                <option value="${activity.id}">${activity.nameActivity}</option>
            </c:forEach>
        </select>
        <br><br><br>
        <button type="submit" class="btn btn-primary">add a new category</button>
    </form>
</div>
<br>
<div class="container">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th colspan="4">UserName</th>
            <th colspan="2">ActivityName</th>
            <th colspan="2">State</th>
            <th colspan="2">On delete</th>
            <th>Activate</th>
            <th colspan="2">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="userActivity" items="${userActivities}">
            <tr>
                <td colspan="4"><c:out value="${userActivity.userLogin}" /></td>
                <td colspan="2"><c:out value="${userActivity.activityName}" /></td>
                <td colspan="2"><ex:UserActivityStateTag state="${userActivity.state}"/></td>
                <td colspan="2"><ex:OnDeleteTag onDelete="${userActivity.onDelete}"/></td>
                <td><a href="Controller?command=pinActivityCommand&makeActiveId=${userActivity.id}">make activity active</a></td>
                <td colspan="2">
                    <a class="edit" href="Controller?command=pinActivityCommand&updateId=${userActivity.id}" title="Edit" data-toggle="tooltip"><i class="material-icons"></i></a>
                    <a class="delete" href="Controller?command=pinActivityCommand&id=${userActivity.id}" title="Delete" data-toggle="tooltip"><i class="material-icons"></i></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="container">
    <nav>
        <ul class="pagination">
            <c:forEach begin="1" end="${countOfPages}" var="i">
                <li class="page-item"><a class="page-link" href="Controller?command=pinActivityCommand&page=${i}">${i}</a></li>
            </c:forEach>
        </ul>
    </nav>
</div>
</body>
</html>
