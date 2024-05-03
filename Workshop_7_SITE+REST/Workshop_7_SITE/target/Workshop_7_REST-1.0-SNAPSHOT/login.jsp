<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
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

        .login-box {
            width: 85%;
            max-width: 500px;
            height: auto;
            display: grid;
            grid-template-columns: 1fr;
            position: relative;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.5);
            border-radius: 12px;
            overflow: hidden;
            padding: 20px;
        }

        .LoginForm {
            background: white;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            width: 100%;
        }

            .LoginForm h2 {
                font-size: 36px;
                color: #4FA8A8;
                font-weight: 700;
                margin-bottom: 20px;
            }

        .form-control {
            margin-bottom: 10px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            display: block;
            width: 100%;
            box-sizing: border-box;
        }

        .btn-primary {
            padding: 15px;
            border: none;
            width: 100%;
            background-color: #4FA8A8;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

            .btn-primary:hover {
                background-color: #81D6D6;
            }

        #errorMessage {
            color: red;
            margin-top: 10px;
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
    function authenticate() {
        var username = $('#username').val();
        var password = $('#password').val();

        if (!username || !password) {
            $('#errorMessage').text('Username and Password are required.');
            return;
        }

        $.ajax({
            url: 'http://localhost:8080/customer/getallcustomers',
            type: 'GET',
            success: function(data) {
                var users = JSON.parse(data);
                var isValid = users.some(user => user.username === username && user.password === password);
                if (isValid) {
                    window.location.href = 'profile.jsp'; // Redirect to the profile page
                } else {
                    $('#errorMessage').text('Invalid username or password.');
                }
            },
            error: function() {
                $('#errorMessage').text('Error loading customer data.');
            }
        });
    }
    </script>
</head>
<body>
    <div class="container">
        <div class="login-box">
            <form class="LoginForm" id="loginForm">
                <h2>LOGIN</h2>
                <input type="text" id="username" class="form-control" placeholder="Email">
                <input type="password" id="password" class="form-control" placeholder="Password">
                <button type="button" class="btn-primary" onclick="authenticate()">Login</button>
                <div id="errorMessage"></div>
            </form>
        </div>
    </div>
</body>
</html>
