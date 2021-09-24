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
        <input type="hidden" name="command" value="categoryCommand">
        <div class="form-group">
            <label for="exampleInputEmail1">Category name</label>
            <input name="categoryName" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter category" required="required">
        </div>
        <button type="submit" class="btn btn-primary">add a new category</button>
    </form>
</div>
<br>
<div class="container">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>categoryName</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="category" items="${categories}">
            <tr>
                <td><c:out value="${category.categoryName}" /></td>
                <td>
                    <a class="edit" href="Controller?command=categoryCommand&updateId=${category.id}" title="Edit" data-toggle="tooltip"><i class="material-icons"></i></a>
                    <a class="delete" href="Controller?command=categoryCommand&id=${category.id}" title="Delete" data-toggle="tooltip"><i class="material-icons"></i></a>
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
                <li class="page-item"><a class="page-link" href="Controller?command=categoryCommand&page=${i}">${i}</a></li>
            </c:forEach>
        </ul>
    </nav>
</div>
</body>
</html>
