# Spring Security Oauth 2.0

本文的重点内容：

* 什么是Oauth 2.0？
* Oauth 2.0的流程？
* Spring security 的简单介绍？
* Spring security Oauth 2.0的如何实现的？

<hr>

## Oauth2.0

### 简介

Oauth2.0
是一个开放授权标准，它允许用户在第三方应用中登录，并获取某一个应用所需要的资源内容，无需提供用户名和密码，也不会暴露用户信息。OAuth允许用户提供一个令牌，而不是用户名和密码来访问他们存放在特定服务提供者的数据。每一个令牌授权一个特定的网站（例如，视频编辑网站)
在特定的时段（例如，接下来的2小时内）内访问特定的资源（例如仅仅是某一相册中的视频）。这样，OAuth让用户可以授权第三方网站访问他们存储在另外服务提供者的某些特定信息，而非所有内容。

OAuth是OpenID的一个补充，但是完全不同的服务。

### 名词定义

以下面的两张图片场景举例，我们可以理解为，我们要登录阿里云，但是我们通过阿里云允许的授权方式登录，如使用支付宝授权。当我们点击支付宝的logo时，则会跳转到支付宝的登录页面。

阿里云登录页面：![aliyun-login.png](security/asset/aliyun-login.png)

支付宝登录页面：（https://auth.alipay.com/login/index.htm?goto=https%3A%2F%2Fpassport.aliyun.com%2Fsns_oauth.htm%3FappName%3Daliyun%26appEntrance%3Daliyun%26type%3Dalipay%26lang%3Dzh_CN%26returnUrl%3Dhttps%25253A%25252F%25252Faccount.aliyun.com%25253A443%25252Flogin%25252Flogin_aliyun.htm%25253Foauth_callback%25253Dhttps%2525253A%2525252F%2525252Fcn.aliyun.com%2525252F%252526ft%25253Dpc_alipay%252526useOneStepVersion%25253Dtrue%252526isQr%25253Dfalse%252526notNeedHardware%25253D0%252526register_method%25253Dmobile_reg%252526log_channel%25253Dindep%252526log_platform%25253Dpc%252526log_biz%25253Daliyun%252526log_entrance%25253Dalipay_bind%26returnUrlEncoded%3Dtrue%26isMobile%3Dfalse%26responseAction%3Dtop_redirect%26loginAction%3DloginResult&accounttraceid=c646ed8cb19548319c7f01111336f4eejaac）
![alipay-login.png](security/asset/alipay-login.png)
细心点会发现，在支付宝登录页面，有一个跳转的链接，这个链接就是Oauth2.0的协议规范。

详细了解Oauth2之前，先了解一些它里面用到的名词定义：

* **资源拥有者（Resource Owner）**：资源拥有者通常是指用户，例如，例子中拥有支付宝、淘宝、新浪等账号的用户。
* **资源服务器（Resource Server）**：资源服务器是指存储用户资源的服务器，例如，例子中支付宝、淘宝、新浪等存储的用户信息。
* **客户端（Client）**：它本身不会存储用户快登录的账户和密码，只是通过资源拥有者的授权去请求资源服务器的资源，例如，例子中用户通过支付宝授权登录阿里云。
* **授权服务器（Authorization Server）**：认证服务器，可以提供身份认证和用户授权的服务器，即给客户端颁发`token`和校验`token`。

### Oauth认证的流程

![oauth2-grant-flow.png](security/asset/oauth2-grant-flow.png)

<hr>
参考文档:

[OpenID 1.0 layer on top of the Oauth 2.0 Protocol](https://openid.net/specs/openid-connect-core-1_0.html)

[Oauth2的协议规范](https://datatracker.ietf.org/doc/html/rfc6749#autoid-29)

[Spring Security Oauth2 官方文档](https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html)