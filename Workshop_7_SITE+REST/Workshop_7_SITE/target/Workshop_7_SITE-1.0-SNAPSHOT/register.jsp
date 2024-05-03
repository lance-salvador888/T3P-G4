<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Registration</title>
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

        .register-box {
            width: 50%;
            height: auto;
            display: flex;
            flex-direction: column;
            align-items: center;
            position: relative;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.5);
            border-radius: 12px;
            overflow: hidden;
            padding: 20px;
            background-color: white;
        }

        .register-form {
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .register-form label {
            margin-bottom: 10px;
        }

        .register-form input,
        .register-form select {
            width: 70%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            margin-bottom: 10px;
        }

        .register-form button {
            padding: 10px 20px;
            border: none;
            border-radius: 6px;
            background-color: #4FA8A8;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .register-form button:hover {
            background-color: #81D6D6;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="register-box">
        <h2>User Registration</h2>
        <form class="register-form" id="registerForm">
<%--            <input hidden type="number" id="userId" name="customerId" value=0>--%>
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="custFirstName" required maxlength="25">

            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="custLastName" required maxlength="25">

            <label for="address">Address:</label>
            <input type="text" id="address" name="custAddress" required maxlength="75">

            <label for="city">City:</label>
            <input type="text" id="city" name="custCity" required maxlength="50">

            <label for="province">Province:</label>
            <select id="province" name="custProv" required>
                <option value="">Select a Province</option>
                <option value="AB">Alberta</option>
                <option value="BC">British Columbia</option>
                <option value="MB">Manitoba</option>
                <option value="NB">New Brunswick</option>
                <option value="NL">Newfoundland and Labrador</option>
                <option value="NS">Nova Scotia</option>
                <option value="ON">Ontario</option>
                <option value="PE">Prince Edward Island</option>
                <option value="QC">Quebec</option>
                <option value="SK">Saskatchewan</option>
                <option value="NT">Northwest Territories</option>
                <option value="NU">Nunavut</option>
                <option value="YT">Yukon</option>
            </select>

            <label for="postal">Postal Code:</label>
            <input type="text" id="postal" name="custPostal" required maxlength="7">

            <label for="country">Country:</label>
            <input type="text" id="country" name="custCountry" maxlength="25">

            <label for="homePhone">Home Phone:</label>
            <input type="text" id="homePhone" name="custHomePhone" maxlength="20">

            <label for="busPhone">Business Phone:</label>
            <input type="text" id="busPhone" name="custBusPhone" required maxlength="20">

            <label for="email">Email:</label>
            <input type="email" id="email" name="custEmail" required maxlength="50">

            <label for="password">Password:</label>
            <input type="password" id="password" name="custPassword" maxlength="25">
<%--            <input hidden type="number" id="agentId" name="agentId" value=0>--%>
            <button type="submit">Register</button>
        </form>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    document.getElementById('registerForm').addEventListener('submit', function(event) {
        event.preventDefault();
        var formData = new FormData(document.getElementById("registerForm"));
        var object = {customerId: 0};

        formData.forEach((value, key) => object[key] = value);
        var objectJSON = JSON.stringify(object);
        objectJSON = objectJSON.substring(0, (objectJSON.length-1)) + ',"agentId":2}';
        console.log(objectJSON);
        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080/Workshop_7_REST-1.0-SNAPSHOT/api/customer/putCustomer',
            data: objectJSON,
            accept: "application/json",
            contentType: 'application/json',
            dataType: 'json',
            success: function(response) {
                alert('User registered successfully.');
                window.location.href = 'login.jsp'; // Redirect after successful registration
            },
            error: function(xhr, status, error) {
                // Check if the error is a parse error
                if (error.name === "SyntaxError") {
                    console.log('Non-critical parse error ignored.');
                    alert('User registered successfully.');
                    window.location.href = 'login.jsp'; // Redirect after successful registration
                } else {
                    console.error('Error saving changes:', error);
                    alert('Failed to register user. Please try again later.');
                }
            }
        });
    });
</script>

</body>
</html>
