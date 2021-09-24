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
                <a class="nav-link" href="Controller?command=userCurrentActivitiesCommand">Current activities</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Controller?command=userSetActivityCommand">Request on adding activity</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Controller?command=userDeleteActivityCommand">Request on deleting activity</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Controller?command=logoutCommand">Logout</a>
            </li>
        </ul>
    </div>
</nav>
<br><br><br>
<div class="container">
    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-8"><h2>Activities on deleting</h2></div>
            </div>
        </div>
        <br><br><br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Activity name</th>
                <th>On delete</th>
                <th>Set on delete</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="activity" items="${activities}">
                <tr>
                    <td><c:out value="${activity.activityName}"/></td>
                    <td><ex:OnDeleteTag onDelete="${activity.onDelete}"/></td>
                    <td>
                        <a class="edit" title="Edit" href="Controller?command=userDeleteActivityCommand&onDelete=${activity.id}" data-toggle="tooltip"><i class="material-icons"></i></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div class="container">
    <nav>
        <ul class="pagination">
            <c:forEach begin="1" end="${countOfPages}" var="i">
                <li class="page-item"><a class="page-link" href="Controller?command=userDeleteActivityCommand&page=${i}">${i}</a></li>
            </c:forEach>
        </ul>
    </nav>
</div>
</body>
</html>

