<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="_header.jspf" %>
<div class="text-center">
  <h3 class="mb-3">Emergency SOS</h3>

  <c:if test="${not empty alertStatus}">
    <div class="alert alert-info">
      <strong>Alert status:</strong> <c:out value="${alertStatus}"/><br>
      <c:choose>
        <c:when test="${hasLocation}">Your location was attached to this alert.</c:when>
        <c:otherwise>Location permission was not granted - alert saved without coordinates.</c:otherwise>
      </c:choose>
      <br><strong>If you are in danger, call 112 now.</strong>
    </div>
  </c:if>

  <p class="text-muted">Pressing the button asks for confirmation, then requests your location (only if you allow it).</p>

  <form id="sosForm" method="post" action="${pageContext.request.contextPath}/user/sos">
    <input type="hidden" name="confirm" id="confirm" value="no">
    <input type="hidden" name="latitude" id="lat">
    <input type="hidden" name="longitude" id="lng">
    <button type="button" id="sosBtn" class="btn btn-danger sos-btn my-4">SOS</button>
  </form>
  <p id="locMsg" class="text-muted small"></p>

  <div class="alert alert-warning text-start">
    An SOS alert is not guaranteed to reach emergency services. Delivery depends on a configured notification
    service and network access. Helplines: <strong>112</strong> (Emergency), <strong>1091</strong> (Women Helpline).
  </div>
</div>

<script>
document.getElementById('sosBtn').addEventListener('click', function () {
  if (!confirm('Send an emergency SOS alert to your trusted contacts?')) return;   // user can cancel
  var form = document.getElementById('sosForm');
  var msg = document.getElementById('locMsg');
  document.getElementById('confirm').value = 'yes';
  this.disabled = true;

  if (!navigator.geolocation) { msg.textContent = 'Location not supported - sending without it.'; form.submit(); return; }
  msg.textContent = 'Requesting location permission...';
  navigator.geolocation.getCurrentPosition(
    function (pos) {
      document.getElementById('lat').value = pos.coords.latitude;
      document.getElementById('lng').value = pos.coords.longitude;
      form.submit();
    },
    function () { msg.textContent = 'Location permission not granted - sending without location.'; form.submit(); },
    { enableHighAccuracy: true, timeout: 8000, maximumAge: 0 }
  );
});
</script>
<%@ include file="_footer.jspf" %>
