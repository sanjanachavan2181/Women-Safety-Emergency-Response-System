<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="_header.jspf" %>
<h3 class="mb-4">Welcome, <c:out value="${sessionScope.userName}"/></h3>
<div class="row g-3">
  <div class="col-md-4"><a class="card-link" href="${pageContext.request.contextPath}/user/sos"><div class="card border-danger h-100"><div class="card-body text-center">
    <h1>&#128680;</h1><h5>SOS Alert</h5><p class="text-muted mb-0">Send an emergency alert</p></div></div></a></div>
  <div class="col-md-4"><a class="card-link" href="${pageContext.request.contextPath}/user/contacts"><div class="card h-100"><div class="card-body text-center">
    <h1>&#128222;</h1><h5>Emergency Contacts</h5><p class="text-muted mb-0">Add, edit or delete</p></div></div></a></div>
  <div class="col-md-4"><a class="card-link" href="${pageContext.request.contextPath}/user/history"><div class="card h-100"><div class="card-body text-center">
    <h1>&#128337;</h1><h5>Alert History</h5><p class="text-muted mb-0">Review previous alerts</p></div></div></a></div>
  <div class="col-md-6"><a class="card-link" href="${pageContext.request.contextPath}/user/profile"><div class="card h-100"><div class="card-body text-center">
    <h1>&#128100;</h1><h5>My Profile</h5></div></div></a></div>
  <div class="col-md-6"><a class="card-link" href="${pageContext.request.contextPath}/tips"><div class="card h-100"><div class="card-body text-center">
    <h1>&#128161;</h1><h5>Safety Tips &amp; Helplines</h5></div></div></a></div>
</div>
<%@ include file="_footer.jspf" %>
