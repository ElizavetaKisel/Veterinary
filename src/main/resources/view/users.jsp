<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>

<h2> All Users </h2>

<table cellpadding="5", border="1", cellspacing="0">
<thead>
<tr>
<th id ></th>
<th login ></th>
<th email ></th>
</tr>
</thead>
    <tbody>
<c:forEach items="${users}" var="user">
    <tr>
    <td> ${user.user_id}</td>
    <td> ${user.login}</td>
    <td> ${user.email}</td>
    <td> ${user.role}</td>

    </tr>
</c:forEach>
    </tbody>
</table>
</body>
</html>