<%-- 
    Document   : menu
    Created on : 29 sep 2024, 7:46:58 p.m.
    Author     : Wilber's-Laptop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
    body {
        font-family: 'Poppins', sans-serif;
        line-height: 1.6;
        color: #333;
    }
    .container {
        max-width: 1200px;
        margin: 0 auto;
        padding: 0 20px;
    }
    header {
        background-color: #222;
        color: #fff;
        padding: 20px 0;
    }
    nav {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .logo {
        font-size: 24px;
        font-weight: 600;
    }
    .nav-links {
        display: flex;
        list-style: none;
    }
    .nav-links li {
        margin-left: 20px;
    }
    .nav-links a {
        color: #fff;
        text-decoration: none;
        position: relative;
        padding-bottom: 5px;
    }
    .nav-links a::after {
        content: '';
        position: absolute;
        width: 100%;
        height: 2px;
        bottom: 0;
        left: 0;
        background-color: #fff;
        transform: scaleX(0);
        transition: transform 0.3s ease-in-out;
    }
    .nav-links a:hover::after,
    .nav-links a.active::after {
        transform: scaleX(1);
    }
    .nav-links a.active::after {
        transform: scaleX(1);
    }
</style>
<header>
    <nav class="container">
        <div class="logo">FRAMEWORKS</div>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/index.jsp">Inicio</a></li>
            <li><a href="${pageContext.request.contextPath}/estudiantes">Estudiantes</a></li>
            <li><a href="${pageContext.request.contextPath}/evaluaciones">Evaluaciones</a></li>
        </ul>
    </nav>
</header>