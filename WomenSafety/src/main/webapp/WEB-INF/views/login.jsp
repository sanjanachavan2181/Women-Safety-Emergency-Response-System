<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="_header.jspf" %>
<div class="row justify-content-center"><div class="col-md-5">
 <div class="card shadow-sm"><div class="card-body">
  <h3 class="mb-3">User Login</h3>
  <form method="post" action="${pageContext.request.contextPath}/login">
    <div class="mb-3"><label class="form-label">Email or Mobile</label>
      <input class="form-control" name="identifier" required></div>
    <div class="mb-3"><label class="form-label">Password</label>
      <input type="password" class="form-control" name="password" required></div>
    <button class="btn btn-danger w-100">Login</button>
  </form>
  <p class="mt-3 mb-0 text-center">New user? <a href="${pageContext.request.contextPath}/register">Register</a></p>
 </div></div>
</div></div>
<%@ include file="_footer.jspf" %>
