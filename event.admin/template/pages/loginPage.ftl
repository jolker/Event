<!DOCTYPE html>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <title>Login Tool Setup</title>

  <link rel="stylesheet" type="text/css" href="${root_url}bootstrap/css/bootstrap.css" />
  <link rel="stylesheet" href="${root_url}custom/css/style.css">
</head>

<body>
  <div class="container">
    <div class="wrapper-login">
      <form action="login" method="POST" name="Login_Form" class="form-horizontal form-signin" role="form">
        <h3 class="form-signin-heading">Welcome To</br>Tool Setup</h3>
        <hr class="colorgraph"><br>

        <#if errorMessage??>
          <div class="error-message">
            <div class="alert alert-danger fade in">
              ${errorMessage}
            </div>
          </div>
        </#if>

        <#if messageFromServer??>
          <div class="error-message">
            <div class="alert alert-danger fade in">
              ${messageFromServer}
            </div>
          </div>
        </#if>

        <div style="margin-bottom: 25px" class="input-group">
          <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
          <input id="username" type="text" class="form-control" name="username" placeholder="username" required="" autofocus>
        </div>

        <div style="margin-bottom: 25px" class="input-group">
          <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
          <input id="password" type="password" class="form-control" name="password" placeholder="password" required>
        </div>

        <button class="btn btn-lg btn-primary btn-block" type="Submit">Login</button>
      </form>
    </div>
  </div>
</body>

</html>
