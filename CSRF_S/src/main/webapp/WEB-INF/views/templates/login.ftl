<#ftl encoding='UTF-8'>
<#import 'layouts/base.ftl' as base>
<@base.main title='Login'>
    <form method="post" action="/signIn">
        <input name="email">
        <input type="hidden" value="${_csrf_token}" name="_csrf_token">
        <input type="password" name="password">
        <input type="submit" value="login">
    </form>
</@base.main>