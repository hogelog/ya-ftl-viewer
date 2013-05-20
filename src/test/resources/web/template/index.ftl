<html>
<head>
<title>Hello Freemarker!</title>
</head>
<body>
Hello<#if name?has_content>${name?html}</#if>!
<img src="test.png" />
</body>
</html>
