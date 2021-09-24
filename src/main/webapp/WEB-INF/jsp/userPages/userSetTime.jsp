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
        <input type="hidden" name="command" value="userSetTimeCommand">
        <div class="form-group">
            <label for="exampleInputEmail1">edit a new category name</label>
            <input type="time" name="setTime" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="enter a new category name" required="required">
        </div>
        <button type="submit" class="btn btn-primary">set time</button>
    </form>
</div>
</body>
</html>

