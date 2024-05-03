<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome Page</title>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            font-family: Arial, sans-serif;
        }

        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100%;
        }

        .welcome-box {
            width: 50%;
            height: auto; 
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            position: relative;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.5);
            border-radius: 12px;
            overflow: hidden;
            padding: 20px;
        }

        .welcome-message {
            font-size: 24px;
            color: #4FA8A8;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .info-form {
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .info-field {
            margin-bottom: 10px;
            width: 70%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        .button-container {
            display: flex;
            justify-content: space-between;
            width: 73%;
            margin-top: 10px;
        }

        .edit-button, .save-button {
            padding: 10px 20px;
            border: none;
            border-radius: 6px;
            background-color: #4FA8A8;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s ease;
            width: 48%;
        }

        .edit-button:hover, .save-button:hover {
            background-color: #81D6D6;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="welcome-box">
            <div class="welcome-message">
    
                Hello, <%= session.getAttribute("customerFirstName") %>!
            </div>
            <form class="info-form" id="infoForm">
                <input type="text" id="firstName" name="firstName" class="info-field" placeholder="First Name" value="<%= session.getAttribute("customerFirstName") %>" disabled><br>
                <input type="text" id="lastName" name="lastName" class="info-field" placeholder="Last Name" value="<%= session.getAttribute("customerLastName") %>" disabled><br>
                <input type="text" id="address" name="address" class="info-field" placeholder="Address" value="<%= session.getAttribute("customerAddress") %>" disabled><br>
                <input type="text" id="city" name="city" class="info-field" placeholder="City" value="<%= session.getAttribute("customerCity") %>" disabled><br>
                <input type="text" id="prov" name="prov" class="info-field" placeholder="Province" value="<%= session.getAttribute("customerProvince") %>" disabled><br>
                <input type="text" id="postal" name="postal" class="info-field" placeholder="Postal Code" value="<%= session.getAttribute("customerPostalCode") %>" disabled><br>
                <input type="text" id="country" name="country" class="info-field" placeholder="Country" value="<%= session.getAttribute("customerCountry") %>" disabled><br>
                <input type="text" id="homePhone" name="homePhone" class="info-field" placeholder="Home Phone" value="<%= session.getAttribute("customerHomePhone") %>" disabled><br>
                <input type="text" id="busPhone" name="busPhone" class="info-field" placeholder="Business Phone" value="<%= session.getAttribute("customerBusinessPhone") %>" disabled><br>
                <input type="text" id="email" name="email" class="info-field" placeholder="Email" value="<%= session.getAttribute("customerEmail") %>" disabled><br>
                <input type="text" id="password" name="password" class="info-field" placeholder="Password" value="<%= session.getAttribute("customerPassword") %>" disabled><br>

                <div class="button-container">
                    <button type="button" class="edit-button" onclick="enableEdit()">Edit</button>
                    <button type="button" class="save-button" onclick="saveChanges()">Save</button>
                    <br><br>
                </div>
                <br><br><br>
            </form>
        </div>
    </div>
</body>
<script>
    function enableEdit() {
        var fields = document.getElementsByClassName("info-field");
        for (var i = 0; i < fields.length; i++) {
            fields[i].disabled = false;
        }
    }

    function saveChanges() {
        var firstName = document.getElementById('firstName').value.trim();
        var lastName = document.getElementById('lastName').value.trim();
        var address = document.getElementById('address').value.trim();
        var city = document.getElementById('city').value.trim();
        var prov = document.getElementById('prov').value.trim();
        var postal = document.getElementById('postal').value.trim();
        var country = document.getElementById('country').value.trim();
        var homePhone = document.getElementById('homePhone').value.trim();
        var busPhone = document.getElementById('busPhone').value.trim();
        var email = document.getElementById('email').value.trim();
        var password = document.getElementById('password').value.trim();


        var data = {
            "custFirstName": firstName,
            "custLastName": lastName,
            "custAddress": address,
            "custCity": city,
            "custProv": prov,
            "custPostal": postal,
            "custCountry": country,
            "custHomePhone": homePhone,
            "custBusPhone": busPhone,
            "custEmail": email,
            "custPassword": password
        };


        var jsonString = JSON.stringify(data);


        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/customer/postagent', true); 
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    
                    alert('Changes saved successfully.');
                  
                    var fields = document.getElementsByClassName("info-field");
                    for (var i = 0; i < fields.length; i++) {
                        fields[i].disabled = true;
                    }
                } else {
                  
                    alert('Failed to save changes. Please try again later.');
                }
            }
        };
        xhr.send(jsonString);
    }
</script>
</html>
