<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:bundle basename="app">
	<html lang="${language}">

<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="css/navbar.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="signup.signup" /></title>
</head>
<body class="text-center bg-mint center-body">
	<div class="form-signup">
		<form class="" action="signupServlet" method="post">
			<img class="mb-1" src="./Assets/Logo.png" height="200px"
				width="175px">
			<h3 class="mb-3 font-weight-normal">
				<fmt:message key="signup.registeraccount" />
			</h3>
			<div class="form-group">
				<c:if test="${sessionScope.failedSignup == \"true\"}">
					<div class="alert alert-danger" role="alert">
						<strong>Passwords do not match</strong>
					</div>
				</c:if>
				<input class="form-control" type="text" name="username"
					id="username" placeholder="<fmt:message key="signup.userid" />"
					required autofocus /> <input class="form-control" type="email"
					name="email" id="email"
					placeholder="<fmt:message key="signup.email" />" required /> <br>
				<br>
				<div id="locationField">
					<input id="autocomplete" class="form-control" type="text" 
						placeholder="Enter your address" onFocus="geolocate()" type="text"></input>
				</div>
				<input class="form-control" type="text" name="city" id="locality"
					placeholder="<fmt:message key="signup.city" />" required="required" readonly ></input>
					
				<input class="form-control" name="country" id="country"
					placeholder="<fmt:message key="signup.country" />" required="required" readonly ></input>
				<input class="form-control" type="password" name="password"
					id="password" placeholder="<fmt:message key="signup.password" />"
					required /> <input class="form-control" type="password"
					name="repassword" id="repassword"
					placeholder="<fmt:message key="signup.repassword" />" required />
			</div>
			<input type="submit" class="btn btn-dark btn-lg btn-block mb-3"
				value="<fmt:message key="signup.signup"/>" />
		</form>
		<form action="setLanguageServlet" method="get">
			<div class="btn-group">
				<a class="btn btn-small btn-outline-dark btn-block"
					href="${pageContext.request.contextPath}/index.jsp"><fmt:message
						key="signup.haveAccount" /></a>


				<div class="btn-group" role="group">
					<select class="btn btn-outline-dark dropdown-toggle"
						aria-labelledby="btnGroupDrop1" id="language" name="language"
						onchange="submit()">
						<option class="dropdown-item" value="en"
							${language == 'en' ? 'selected' : ''}>English</option>
						<option class="dropdown-item" value="fr"
							${language == 'fr' ? 'selected' : ''}>Français</option>
					</select>
				</div>
			</div>
		</form>
	</div>
</body>
</fmt:bundle>
</html>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>


<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCxVRd_gr9JqDif3V0Yl-QljBYlEsPoyaQ
&libraries=places&callback=initAutocomplete"
	async defer></script>


<script>
      // This example displays an address form, using the autocomplete feature
      // of the Google Places API to help users fill in the information.

      // This example requires the Places library. Include the libraries=places
      // parameter when you first load the API. For example:
      // <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=places">

      var placeSearch, autocomplete;
      var componentForm = {
        //street_number: 'short_name',
       // route: 'long_name',
        locality: 'long_name',
        //administrative_area_level_1: 'short_name',
        country: 'long_name',
       // postal_code: 'short_name'
      };

      function initAutocomplete() {
        // Create the autocomplete object, restricting the search to geographical
        // location types.
        autocomplete = new google.maps.places.Autocomplete(
            /** @type {!HTMLInputElement} */(document.getElementById('autocomplete')),
            {types: ['geocode']});

        // When the user selects an address from the dropdown, populate the address
        // fields in the form.
        autocomplete.addListener('place_changed', fillInAddress);
      }

      function fillInAddress() {
        // Get the place details from the autocomplete object.
        var place = autocomplete.getPlace();

        for (var component in componentForm) {
          document.getElementById(component).value = '';
          document.getElementById(component).disabled = false;
        }

        // Get each component of the address from the place details
        // and fill the corresponding field on the form.
        for (var i = 0; i < place.address_components.length; i++) {
          var addressType = place.address_components[i].types[0];
          if (componentForm[addressType]) {
            var val = place.address_components[i][componentForm[addressType]];
            document.getElementById(addressType).value = val;
          }
        }
      }

      // Bias the autocomplete object to the user's geographical location,
      // as supplied by the browser's 'navigator.geolocation' object.
      function geolocate() {
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(function(position) {
            var geolocation = {
              lat: position.coords.latitude,
              lng: position.coords.longitude
            };
            var circle = new google.maps.Circle({
              center: geolocation,
              radius: position.coords.accuracy
            });
            autocomplete.setBounds(circle.getBounds());
          });
        }
      }
    </script>