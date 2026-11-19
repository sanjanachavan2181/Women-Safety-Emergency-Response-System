<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="_header.jspf" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="row g-4">
 <div class="col-md-4">
  <div class="card shadow-sm"><div class="card-body">
   <h5><c:out value="${empty editContact ? 'Add Contact' : 'Edit Contact'}"/></h5>
   <form method="post" action="${ctx}/user/contacts">
     <input type="hidden" name="action" value="${empty editContact ? 'add' : 'edit'}">
     <c:if test="${not empty editContact}"><input type="hidden" name="contactId" value="${editContact.contactId}"></c:if>
     <div class="mb-3"><label class="form-label">Name</label>
       <input class="form-control" name="contactName" required maxlength="100" value="<c:out value='${editContact.contactName}'/>"></div>
     <div class="mb-3"><label class="form-label">Mobile (10 digits)</label>
       <input class="form-control" name="mobile" required pattern="[6-9][0-9]{9}" maxlength="10" value="<c:out value='${editContact.mobile}'/>"></div>
     <div class="mb-3"><label class="form-label">Relation</label>
       <input class="form-control" name="relation" maxlength="50" placeholder="Mother, Friend..." value="<c:out value='${editContact.relation}'/>"></div>
     <button class="btn btn-primary"><c:out value="${empty editContact ? 'Add' : 'Save'}"/></button>
     <c:if test="${not empty editContact}"><a class="btn btn-secondary" href="${ctx}/user/contacts">Cancel</a></c:if>
   </form>
  </div></div>
 </div>
 <div class="col-md-8">
  <div class="card shadow-sm"><div class="card-body">
   <h5>Trusted Contacts</h5>
   <c:choose>
    <c:when test="${empty contacts}"><p class="text-muted mb-0">No contacts saved yet. Add at least one so SOS alerts have someone to notify.</p></c:when>
    <c:otherwise>
     <div class="table-responsive"><table class="table align-middle">
      <thead><tr><th>Name</th><th>Mobile</th><th>Relation</th><th></th></tr></thead>
      <tbody>
      <c:forEach var="c" items="${contacts}">
       <tr>
        <td><c:out value="${c.contactName}"/></td>
        <td><c:out value="${c.mobile}"/></td>
        <td><c:out value="${c.relation}"/></td>
        <td class="text-end">
          <a class="btn btn-sm btn-outline-primary" href="${ctx}/user/contacts?edit=${c.contactId}">Edit</a>
          <form class="d-inline" method="post" action="${ctx}/user/contacts" onsubmit="return confirm('Delete this contact?');">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="contactId" value="${c.contactId}">
            <button class="btn btn-sm btn-outline-danger">Delete</button>
          </form>
        </td>
       </tr>
      </c:forEach>
      </tbody>
     </table></div>
    </c:otherwise>
   </c:choose>
  </div></div>
 </div>
</div>
<%@ include file="_footer.jspf" %>
