<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="iso-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">
<s:head parseContent="true" />
<title>webOper 2.0 Grupo Zanardo</title>

<!-- Bootstrap core CSS -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/paginas/bootstrap/css/bootstrap.css" />

<!-- Custom styles for this template -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/paginas/css/signin.css" />

<!-- Just for debugging purposes. Don't actually copy this line! -->
<!--[if lt IE 9]><script src="../../docs-assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<div class="navbar navbar-default navbar-static-top" role="navigation">
      <div class="container">
        <div class="navbar-header">        
          <a class="navbar-brand" href="#">
          	<img alt="Logo Grupo Zanardo" src="<%=request.getContextPath()%>/paginas/img/logo.png">
          	webOper 2.0
          </a>
        </div>
      </div>
    </div>

	<div class="container">
		<div class="row">
			<div class="span4"></div>
			<div class="span4"><s:actionerror theme="ajax" cssClass="alert alert-warning" /></div>
			<div class="span4"></div>
		</div>
		
		<s:form action="login!login.action" method="POST" cssClass="form-signin" validate="true" namespace="/">
			<s:textfield name="codUsu" cssClass="form-control" placeholder="Usuário" />
			<s:password name="senUsu" cssClass="form-control" placeholder="Senha" />
			<s:submit value="Entrar" cssClass="btn btn-lg btn-primary btn-block" />
		</s:form>
	</div>
	<!-- /container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="<%=request.getContextPath()%>/paginas/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
