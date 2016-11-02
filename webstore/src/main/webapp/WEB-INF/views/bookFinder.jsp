<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>find book</title>
</head>
<body>
	<div class="container">
		<h2>find books by given parameters</h2>
		<form class="form-horizontal">
			<div class="form-group">
				<label class="control-label col-sm-2" for="bookId">BookId:</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="bookId"
						placeholder="Enter bookId">
				</div>
				<div class="col-sm-offset-2 col-sm-2">
					<a href="/webstore/findBook/findById" class="btn btn-default"> <span
								class="glyphicon-info-sign glyphicon" /></span> Add book
							</a>
				</div>
			</div>
		</form>
	</div>

	</div>

</body>
</html>