<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="_header.jspf" %>
<div class="row justify-content-center"><div class="col-md-6">
 <div class="card shadow-sm"><div class="card-body">
  <h3 class="mb-3">My Profile</h3>
  <form method="post" action="${pageContext.request.contextPath}/user/profile">
    <div class="mb-3"><label class="form-label">Full Name</label>
      <input class="form-control" name="name" required maxlength="100" value="<c:out value='${user.name}'/>"></div>
    <div class="mb-3"><label class="form-label">Email</label>
      <input type="email" class="form-control" name="email" required value="<c:out value='${user.email}'/>"></div>
    <div class="mb-3"><label class="form-label">Mobile</label>
      <input class="form-control" name="mobile" required pattern="[6-9][0-9]{9}" maxlength="10" value="<c:out value='${user.mobile}'/>"></div>
    <button class="btn btn-primary">Update Profile</button>
  </form>
 </div></div>
</div></div>
<%@ include file="_footer.jspf" %>
