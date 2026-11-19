<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="_header.jspf" %>
<div class="row justify-content-center"><div class="col-md-6">
 <div class="card shadow-sm"><div class="card-body">
  <h3 class="mb-3">Create Account</h3>
  <form method="post" action="${pageContext.request.contextPath}/register">
    <div class="mb-3"><label class="form-label">Full Name</label>
      <input class="form-control" name="name" required maxlength="100" value="<c:out value='${name}'/>"></div>
    <div class="mb-3"><label class="form-label">Email</label>
      <input type="email" class="form-control" name="email" required value="<c:out value='${email}'/>"></div>
    <div class="mb-3"><label class="form-label">Mobile (10 digits)</label>
      <input class="form-control" name="mobile" required pattern="[6-9][0-9]{9}" maxlength="10" value="<c:out value='${mobile}'/>"></div>
    <div class="mb-3"><label class="form-label">Password (min 8 characters)</label>
      <input type="password" class="form-control" name="password" required minlength="8"></div>
    <div class="mb-3"><label class="form-label">Confirm Password</label>
      <input type="password" class="form-control" name="confirm" required minlength="8"></div>
    <button class="btn btn-danger w-100">Register</button>
  </form>
  <p class="mt-3 mb-0 text-center">Already registered? <a href="${pageContext.request.contextPath}/login">Login</a></p>
 </div></div>
</div></div>
<%@ include file="_footer.jspf" %>
