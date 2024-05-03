<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome Page</title>
    <style>
        @keyframes gradientBG {
            0% {
                background-position: 0% 50%;
            }
            50% {
                background-position: 100% 50%;
            }
            100% {
                background-position: 0% 50%;
            }
        }

        body, html {
            height: 100%;
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(270deg, white, #acd7d7);
            background-size: 400% 400%;
            animation: gradientBG 5s ease infinite;
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
            background-color: white;
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

        .logout-button {
            padding: 10px 20px;
            border: none;
            border-radius: 6px;
            background-color: #FF6347;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s ease;
            width: 73%;
            margin-top: 20px;
        }

        .logout-button:hover {
            background-color: #FF7F7F;
        }

    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>
<div class="container">
    <div class="welcome-box">
        <div class="welcome-message">
            Hello, <span id="welcomeFirstName"></span>!
        </div>
        <form class="info-form" id="infoForm">
            <input type="text" id="firstName" name="firstName" class="info-field" placeholder="First Name" disabled><br>
            <input type="text" id="lastName" name="lastName" class="info-field" placeholder="Last Name" disabled><br>
            <input type="text" id="address" name="address" class="info-field" placeholder="Address" disabled><br>
            <input type="text" id="city" name="city" class="info-field" placeholder="City" disabled><br>
            <input type="text" id="prov" name="prov" class="info-field" placeholder="Province" disabled><br>
            <input type="text" id="postal" name="postal" class="info-field" placeholder="Postal Code" disabled><br>
            <input type="text" id="country" name="country" class="info-field" placeholder="Country" disabled><br>
            <input type="text" id="homePhone" name="homePhone" class="info-field" placeholder="Home Phone" disabled><br>
            <input type="text" id="busPhone" name="busPhone" class="info-field" placeholder="Business Phone" disabled><br>
            <input type="text" id="email" name="email" class="info-field" placeholder="Email" disabled><br>
            <input type="password" id="password" name="password" class="info-field" placeholder="Password" disabled><br>

            <div class="button-container">
                <button type="button" class="edit-button" onclick="enableEdit()">Edit</button>
                <button type="button" class="save-button" onclick="saveChanges()">Save</button>
                <br><br>
            </div>
            <br>
        </form>
        <button class="logout-button" onclick="logout()">Logout</button>
        <br><br>
    </div>
</div>
</body>
<script>

    var userData = sessionStorage.getItem('userData');
    if (userData) {

        userData = JSON.parse(userData);


        document.getElementById('welcomeFirstName').textContent = userData.custFirstName;

        document.getElementById('firstName').value = userData.custFirstName;
        document.getElementById('lastName').value = userData.custLastName;
        document.getElementById('address').value = userData.custAddress;
        document.getElementById('city').value = userData.custCity;
        document.getElementById('prov').value = userData.custProv;
        document.getElementById('postal').value = userData.custPostal;
        document.getElementById('country').value = userData.custCountry;
        document.getElementById('homePhone').value = userData.custHomePhone;
        document.getElementById('busPhone').value = userData.custBusPhone;
        document.getElementById('email').value = userData.custEmail;
        document.getElementById('password').value = userData.custPassword;
    }

    function enableEdit() {
        var fields = document.getElementsByClassName("info-field");
        for (var i = 0; i < fields.length; i++) {
            fields[i].disabled = false;
        }
    }

    function saveChanges() {
        var customerId = userData.customerId;
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
        var agentId = userData.agentId;

        var data = {
            customerId: customerId,
            custFirstName: firstName,
            custLastName: lastName,
            custAddress: address,
            custCity: city,
            custProv: prov,
            custPostal: postal,
            custCountry: country,
            custHomePhone: homePhone,
            custBusPhone: busPhone,
            custEmail: email,
            custPassword: password,
            agentId: agentId
        };

        var dataJSON = JSON.stringify(data);
        console.log(dataJSON);


        $.ajax({
            url: 'http://localhost:8080/Workshop_7_REST-1.0-SNAPSHOT/api/customer/postagent',
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: dataJSON,
            success: function(response) {
                alert('Changes saved successfully!');
                sessionStorage.setItem('userData', JSON.stringify(data));
                location.reload();
            },
            error: function(xhr, status, error) {

                if (error.name === "SyntaxError") {
                    console.log('Non-critical parse error ignored.');
                    alert('Changes saved successfully!');
                    sessionStorage.setItem('userData', JSON.stringify(data));
                    location.reload();
                } else {
                    console.error('Error saving changes:', error);
                    alert('Failed to save changes. Please try again later.');
                }
            }
        });
    }

    function logout() {
        sessionStorage.removeItem('userData');
        window.location.href = 'login.jsp';
    }
</script>
</html>
