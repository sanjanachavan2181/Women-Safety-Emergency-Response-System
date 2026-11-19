<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="_header.jspf" %>
<h3 class="mb-3">Alert History</h3>
<c:choose>
 <c:when test="${empty alerts}"><div class="alert alert-secondary">You have not created any alerts yet.</div></c:when>
 <c:otherwise>
  <div class="table-responsive"><table class="table table-striped">
   <thead><tr><th>#</th><th>Date</th><th>Time</th><th>Location</th><th>Status</th></tr></thead>
   <tbody>
   <c:forEach var="a" items="${alerts}">
    <tr>
     <td>${a.alertId}</td>
     <td><fmt:formatDate value="${a.alertTime}" pattern="dd MMM yyyy"/></td>
     <td><fmt:formatDate value="${a.alertTime}" pattern="hh:mm a"/></td>
     <td>
      <c:choose>
       <c:when test="${not empty a.latitude and not empty a.longitude}">
        <a target="_blank" rel="noopener" href="https://maps.google.com/?q=${a.latitude},${a.longitude}">${a.latitude}, ${a.longitude}</a>
       </c:when>
       <c:otherwise><span class="text-muted">Not shared</span></c:otherwise>
      </c:choose>
     </td>
     <td><c:out value="${a.status}"/></td>
    </tr>
   </c:forEach>
   </tbody>
  </table></div>
 </c:otherwise>
</c:choose>
<%@ include file="_footer.jspf" %>
