<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Open+Sans">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <title>Time accounting</title>
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
    <br>
    <div class="container">
        <form action="Controller" method="post">
            <input type="hidden" name="command" value="userCommand">
            <div class="form-group">
                <label for="exampleInputEmail1">Email address</label>
                <input type="email" name="email" class="form-control" required="required" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Password</label>
                <input type="password" name="password" required="required" class="form-control" id="exampleInputPassword1" placeholder="Password">
                <p>${sessionScope.passwordError}</p>
            </div>
            <label class="mr-sm-2" for="inlineFormCustomSelect">Role name</label>
            <select name="role" class="custom-select mr-sm-2" id="inlineFormCustomSelect">
                <c:forEach var="userRole" items="${userRoles}">
                    <option value="${userRole.id}">${userRole.roleName}</option>
                </c:forEach>
            </select>
            <br>
            <br>
            <button type="submit" class="btn btn-primary">Register</button>
        </form>
    </div>

    <div class="container">
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-8"><h2>Employee <b>Details</b></h2></div>
                </div>
            </div>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>E-mail</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="account" items="${accounts}">
                    <tr>
                        <td><c:out value="${account.userLogin}" /></td>
                        <td><c:out value="${account.userRoleName}" /></td>
                        <td>
                            <a class="edit" title="Edit" data-toggle="tooltip"><i class="material-icons"></i></a>
                            <a class="delete" href="Controller?command=userCommand&id=${account.id}" title="Delete" data-toggle="tooltip"><i class="material-icons"></i></a>
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
                    <li class="page-item"><a class="page-link" href="Controller?command=userCommand&page=${i}">${i}</a></li>
                </c:forEach>
            </ul>
        </nav>
    </div>
</body>
</html>
