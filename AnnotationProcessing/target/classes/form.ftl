<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Form</title>
</head>
<body>
<form method="${form.method}" action="${form.action}">
    <#list form.inputs as input>
        <input type="${input.type}" name="${input.name}" placeholder="${input.placeholder}"/>
    </#list>
</form>
</body>
</html>