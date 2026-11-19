<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="_header.jspf" %>
<h3 class="mb-3">Safety Tips &amp; Helplines</h3>
<div class="row g-3 mb-4">
  <div class="col-6 col-md-3"><div class="card text-center border-danger"><div class="card-body"><h3>112</h3>National Emergency</div></div></div>
  <div class="col-6 col-md-3"><div class="card text-center"><div class="card-body"><h3>100</h3>Police</div></div></div>
  <div class="col-6 col-md-3"><div class="card text-center"><div class="card-body"><h3>1091</h3>Women Helpline</div></div></div>
  <div class="col-6 col-md-3"><div class="card text-center"><div class="card-body"><h3>181</h3>Mahila Helpline</div></div></div>
</div>
<c:forEach var="t" items="${tips}">
  <div class="card mb-2"><div class="card-body">
    <h6 class="card-title"><c:out value="${t.title}"/></h6>
    <p class="card-text mb-0"><c:out value="${t.description}"/></p>
  </div></div>
</c:forEach>
<c:if test="${empty tips and empty error}"><p class="text-muted">No safety tips published yet.</p></c:if>
<%@ include file="_footer.jspf" %>
