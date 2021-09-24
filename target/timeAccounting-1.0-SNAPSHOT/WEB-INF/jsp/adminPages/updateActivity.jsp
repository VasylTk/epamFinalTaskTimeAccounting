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
<br><br><br>
<div class="container">
    <form action="Controller" method="post">
        <input type="hidden" name="command" value="activityCommand">
        <div class="form-group">
            <label for="exampleInputEmail1">Activity name</label>
            <input name="activityNameUpdate" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter an activity" required="required">
        </div>
        <label class="mr-sm-2" for="inlineFormCustomSelect">Category name</label>
        <select name="categoryIdUpdate" class="custom-select mr-sm-2" id="inlineFormCustomSelect">
            <c:forEach var="category" items="${sessionScope.categories}">
                <option value="${category.id}">${category.categoryName}</option>
            </c:forEach>
        </select>
        <button type="submit" class="btn btn-primary">add an activity</button>
    </form>
</div>
</body>
</html>
