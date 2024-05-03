<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Registration</title>
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
                <label for="firstName">First Name:</label>
                <input type="text" id="firstName" name="custFirstName" required>
                
                <label for="lastName">Last Name:</label>
                <input type="text" id="lastName" name="custLastName" required>
                
                <label for="address">Address:</label>
                <input type="text" id="address" name="custAddress" required>
                
                <label for="city">City:</label>
                <input type="text" id="city" name="custCity" required>
                
                <label for="province">Province:</label>
                <select id="province" name="custProv" required>
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
                <input type="text" id="postal" name="custPostal" required>
                
                <label for="country">Country:</label>
                <input type="text" id="country" name="custCountry" required>
                
                <label for="homePhone">Home Phone:</label>
                <input type="text" id="homePhone" name="custHomePhone" required>
                
                <label for="busPhone">Business Phone:</label>
                <input type="text" id="busPhone" name="custBusPhone">
                
                <label for="email">Email:</label>
                <input type="email" id="email" name="custEmail" required>
                
                <label for="password">Password:</label>
                <input type="password" id="password" name="custPassword" required>
                
                <button type="submit">Register</button>
            </form>
        </div>
    </div>

    <script>
        document.getElementById('registerForm').addEventListener('submit', function(event) {
            event.preventDefault();

            var formData = new FormData(this);

            var jsonObject = {};
            formData.forEach(function(value, key){
                jsonObject[key] = value;
            });
            var jsonString = JSON.stringify(jsonObject);

            var xhr = new XMLHttpRequest();
            xhr.open('PUT', '/customer/putCustomer', true); 
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    var response = JSON.parse(xhr.responseText);
                    if (xhr.status === 200) {
                        alert('User registered successfully.');
                        window.location.href = 'profile.jsp'; // Redirect to profile page after successful registration
                    } else {
                        alert('Failed to register user. Please try again later: ' + response.message);
                    }
                }
            };
            xhr.send(jsonString);
        });
    </script>
</body>
</html>
