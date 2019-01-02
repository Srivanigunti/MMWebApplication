<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<table>
		<tr>
			<th>Account Number</th>
			<th><a href="sortByName.mm">Holder Name</a></th>
			<th>Account Balance</th>
			<th>Salary</th>
			<th>Over Draft Limit</th>
			<th>Type Of Account</th>
		</tr>
		<jstl:if test="${requestScope.account!=null}">
			<tr>
				<td>${requestScope.account.bankAccount.accountNumber}</td>
				<td>${requestScope.account.bankAccount.accountHolderName }</td>
				<td>${requestScope.account.bankAccount.accountBalance}</td>
				<td>${requestScope.account.salary==true?"Yes":"No"}</td>
				<td>${"N/A"}</td>
				<td>${"Savings"}</td>
			</tr>
		</jstl:if>
		<jstl:if test="${requestScope.accounts!=null}">
			<jstl:forEach var="account" items="${requestScope.accounts}">
				<tr>
					<td>${account.bankAccount.accountNumber}</td>
					<td>${account.bankAccount.accountHolderName }</td>
					<td>${account.bankAccount.accountBalance}</td>
					<td>${account.salary==true?"Yes":"No"}</td>
					<td>${"N/A"}</td>
					<td>${"Savings"}</td>
				</tr>
			</jstl:forEach>
		</jstl:if>
	</table>
	<div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>







