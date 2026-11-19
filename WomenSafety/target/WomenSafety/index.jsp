<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/_header.jspf" %>
<div class="text-center py-5">
  <h1 class="display-5 fw-bold">Women Safety &amp; Emergency Response System</h1>
  <p class="lead">Save trusted contacts, trigger an SOS alert and share your location - with your permission.</p>
  <a class="btn btn-danger btn-lg me-2" href="${pageContext.request.contextPath}/register">Get Started</a>
  <a class="btn btn-outline-dark btn-lg" href="${pageContext.request.contextPath}/login">Login</a>
</div>
<div class="alert alert-warning">
  <strong>Important:</strong> An SOS alert is not guaranteed to reach emergency services. In danger, always call <strong>112</strong>.
</div>
<%@ include file="/WEB-INF/views/_footer.jspf" %>
