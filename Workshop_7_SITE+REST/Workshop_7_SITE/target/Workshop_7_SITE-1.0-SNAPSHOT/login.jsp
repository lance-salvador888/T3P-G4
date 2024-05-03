<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
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
            background-color: white;
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

        .register-link {
            margin-top: 10px;
            text-align: center;
        }

        .register-link a {
            color: #4FA8A8;
            text-decoration: none;
        }

        .register-link a:hover {
            text-decoration: underline;
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
                url: 'http://localhost:8080/Workshop_7_REST-1.0-SNAPSHOT/api/customer/getallcustomers',
                type: 'GET',
                dataType: 'json',
                success: function(data) {
                    var users = data;
                    var isValid = users.some(user => user.custEmail === username && user.custPassword === password);
                    if (isValid) {

                        var userData = users.find(user => user.custEmail === username && user.custPassword === password);

                        sessionStorage.setItem('userData', JSON.stringify(userData));
                        // Redirect to profile page
                        window.location.href = 'profile.jsp';
                    } else {
                        $('#errorMessage').text('Invalid username or password.');
                    }
                },
                error: function(xhr, status, error) {
                    console.log('Error:', xhr.responseText);
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
            <div class="register-link">
                New here? <a href="register.jsp">Register now</a>
            </div>
            <div id="errorMessage"></div>

        </form>
    </div>
</div>
</body>
</html>
