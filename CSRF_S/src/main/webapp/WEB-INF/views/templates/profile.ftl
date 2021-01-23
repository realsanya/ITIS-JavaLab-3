<#ftl encoding='UTF-8'>
<#import 'layouts/base.ftl' as base>
<@base.main title='Profile'>
    <h1>Hello ${user.email}!;)</h1>
    <button onclick="location.href = '/logout'">Logout</button>
</@base.main>