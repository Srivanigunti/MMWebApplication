<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Customer details and Account details</title>
    <link rel="stylesheet" href="css/style1.css">
</head>
<body class="center">
    <center>
    <header>
        <img align="left" class="image" src="images/logo.jpg" alt="logo" width="120" height="70"/>
        <h1 align="center" style="color:black;"><i>MONEY MONEY BANK</i></h1>
    </header>
    </center>
    <h1 class="top-left" style="color:black;"><i>SearchAccount</i></h1>
	<form action="search.mm">
	<br><br><br><br><br><br>
		Enter Account Number: <input type="number" name="accountNumber" />
		<br /> <input type="submit" value="Submit">
	</form>
	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
	<footer>
        <p>Copyright © 2018 Money Money Group Inc.</p>
    </footer>
</body>
</html>