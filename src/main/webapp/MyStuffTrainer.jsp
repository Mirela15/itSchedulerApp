<html>
<head>
    <meta charset="UTF-8">
    <title>My Stuff Trainer</title>
    <script src="actiuni.js" type="text/javascript" ></script>
    <script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
</head>
<body>
<%

    HttpSession s = request.getSession();
    Object o = s.getAttribute("id");
    Object email = s.getAttribute("email");
    if(o==null)
    {
        response.sendRedirect("login.html");
    }
%>

Hello Trainer,  <b><%=email%></b>


<h2>ItScheduler_App!</h2>

<input type="text" placeholder="Search" onkeyup="search(this.value)">

<!--<input type="text" id="studentname" placeholder="Add my students" />
<input type="button" id="add" value="New" onClick="addStudents()" />!-->

<div id="readtrainer">
    <table border="1">
        <thead>
        <tr>
            <%--            <th onclick="sorteazaNume(this)">Obiect &dArr;</th>--%>
            <th>Student Name</th>
            <th>Email</th>
            <th>Course</th>
            <th>Has Knowledge</th>
             <!--   <th>Course Date</th>-->

        </tr>
        </thead>
        <tbody id="obiect">

        </tbody>


    </table>

</div>

<script>
    StudentList();
</script>


<a href ="logout.jsp">Logout</a>

</body>
</html>
