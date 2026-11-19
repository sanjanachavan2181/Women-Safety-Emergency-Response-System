<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="_header.jspf" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<h3 class="mb-3">Admin Dashboard</h3>
<div class="row g-4">
 <div class="col-lg-7">
  <div class="card shadow-sm"><div class="card-body">
   <h5>Registered Users</h5>
   <div class="table-responsive"><table class="table table-sm align-middle">
    <thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Mobile</th><th></th></tr></thead>
    <tbody>
    <c:forEach var="u" items="${users}">
     <tr>
      <td>${u.userId}</td><td><c:out value="${u.name}"/></td><td><c:out value="${u.email}"/></td><td><c:out value="${u.mobile}"/></td>
      <td><form method="post" action="${ctx}/admin/dashboard" onsubmit="return confirm('Delete this user and all their data?');">
        <input type="hidden" name="action" value="deleteUser"><input type="hidden" name="id" value="${u.userId}">
        <button class="btn btn-sm btn-outline-danger">Delete</button></form></td>
     </tr>
    </c:forEach>
    </tbody>
   </table></div>
  </div></div>
 </div>
 <div class="col-lg-5">
  <div class="card shadow-sm mb-3"><div class="card-body">
   <h5>Publish Safety Tip</h5>
   <form method="post" action="${ctx}/admin/dashboard">
    <input type="hidden" name="action" value="addTip">
    <div class="mb-2"><input class="form-control" name="title" placeholder="Title" required maxlength="150"></div>
    <div class="mb-2"><textarea class="form-control" name="description" rows="3" placeholder="Description" required></textarea></div>
    <button class="btn btn-primary">Publish</button>
   </form>
  </div></div>
  <div class="card shadow-sm"><div class="card-body">
   <h5>Active Tips</h5>
   <c:forEach var="t" items="${tips}">
    <div class="d-flex justify-content-between align-items-start border-bottom py-2">
      <div><strong><c:out value="${t.title}"/></strong><br><small class="text-muted"><c:out value="${t.description}"/></small></div>
      <form method="post" action="${ctx}/admin/dashboard" onsubmit="return confirm('Remove this tip?');">
        <input type="hidden" name="action" value="deleteTip"><input type="hidden" name="id" value="${t.tipId}">
        <button class="btn btn-sm btn-outline-danger">X</button></form>
    </div>
   </c:forEach>
  </div></div>
 </div>
</div>
<%@ include file="_footer.jspf" %>
