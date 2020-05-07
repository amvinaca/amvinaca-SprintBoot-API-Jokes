<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Jokes from Chuck Norris</title>
    <style>
        table {
            font-family: arial, sans-serif;

            width: 100%;
        }
        td, th {

            text-align: left;
            padding: 8px;
            width: 300px;
        }
        tr:nth-child(even) {
            background-color: lightblue;
        }
        img
        {
            height: 25px;
            width: 25px;
            border-bottom-color: black;
        }
        body
        {
            background-color: whitesmoke;
        }
    </style>
</head>
<body>

<h2>Homework 4 </h2>

<textarea rows="10" cols="30" >${complete}</textarea><img src="../../img/Norris.jpg" style="width:180px;height: 160px";>

<form method="post" action="/save">
    <input type="submit" value="Save to DB">
    <input type="hidden" name="id" value="">
    <input type="hidden" name="value">
    <input type="hidden" name="url" >
</form>

<form method="post" action="/new">
    <input type="submit" value="Load new API">
</form>

<h2>Jokes Database </h2>
<table>
    <tr>
        <th>Joke</th>

        <th>URL</th>
        <th>Delete</th>
    </tr>
    <c:forEach var = "listitem" items = "${jokes}">
        <tr>
            <td>${listitem.getValue()}</td>
            <td>${listitem.getUrl()}</td>
            <td>
                <a href="/delete/${listitem.getId()}" action="/delete"> <img src="../../img/delete.png"  id="delete_image" ></a>
                <a href="/view/${listitem.getId()}">View</a>
            </td>
        </tr>
    </c:forEach>

</table>

<form method="post" action="/edit/">
    <input type="hidden" name="id" value="${selectedItem.getId()}">
    <br>First Name: <br>
    <input type="text" name="value" value="${selectedItem.getValue()}">
    <br>Last name:<br>
    <input type="text" name="url" value="${selectedItem.getUrl()}">
    <br><br>
    <input type="submit" value="Submit">
</form>

</body>
</html>
