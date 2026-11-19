<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="_header.jspf" %>
<div class="row justify-content-center"><div class="col-md-4">
 <div class="card shadow-sm"><div class="card-body">
  <h3 class="mb-3">Admin Login</h3>
  <form method="post" action="${pageContext.request.contextPath}/admin/login">
    <div class="mb-3"><label class="form-label">Username</label><input class="form-control" name="username" required></div>
    <div class="mb-3"><label class="form-label">Password</label><input type="password" class="form-control" name="password" required></div>
    <button class="btn btn-dark w-100">Login</button>
  </form>
 </div></div>
</div></div>
<%@ include file="_footer.jspf" %>
