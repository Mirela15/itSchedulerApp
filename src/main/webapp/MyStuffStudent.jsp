<html>
<head>
  <meta charset="UTF-8">
  <title>My Stuff Student</title>
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

Hello Student, <b><%=email%></b>


<h2>ItScheduler_App!</h2>


<div id="readstudent">
  <table border="1">
    <thead>
    <tr>

        <th>Trainer Name</th>
      <th>Email</th>
        <th>Course</th>
        <th>Course Date</th>

       <!-- <th>Has Knowledge</th>!-->


    </tr>
    </thead>
    <tbody id="obiect">

    </tbody>


  </table>

</div>

<script>
  TrainerList();
</script>


<a href ="logout.jsp">Logout</a>
<input type="button" id="delete" value="Delete Registration" onClick="deleteName()">
</body>
</html>
