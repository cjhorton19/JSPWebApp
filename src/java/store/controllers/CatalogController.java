/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.business.Product;
import store.business.User;
import store.data.ProductDB;
import store.data.UserDB;
import store.util.CookieUtil;

/**
 *
 * @author curti
 */
public class CatalogController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String url;
        if (requestURI.endsWith("/listen")) {
            url = listen(request, response);
        } else {
            url = showProduct(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String url = "/catalog";
        if (requestURI.endsWith("/register")) {
            url = registerUser(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String showProduct(HttpServletRequest request,
            HttpServletResponse response) {
        String productCode = request.getPathInfo();
        String path = null;
        // This should never be null. But just to be safe.
        if (productCode != null) {

            productCode = productCode.substring(1);
            Product product = ProductDB.selectProduct(productCode);
            HttpSession session = request.getSession();
            session.setAttribute("product", product);
        }
        if(removeFirstChar(productCode) == "w"){
        path = "/catalog/women/" + productCode + "/index.jsp";
        }
        return path;
    }

    private String listen(HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // if the User object doesn't exist, check for the email cookie
        if (user == null) {
            Cookie[] cookies = request.getCookies();
            String emailAddress
                    = CookieUtil.getCookieValue(cookies, "emailCookie");
            // if the email cookie doesn't exist, go to the registration page
            if (emailAddress == null || emailAddress.equals("")) {
                return "/catalog/register.jsp";
            } else {
                user = UserDB.selectUser(emailAddress);
                // if a user for that email isn't in the database, 
                // go to the registration page
                if (user == null) {
                    return "/catalog/register.jsp";
                }
                session.setAttribute("user", user);
            }
        }

        Product product = (Product) session.getAttribute("product");

        return "/catalog/" + product.getCode() + "/sound.jsp";
    }

    public String removeFirstChar(String s) {
        return s.substring(1);
    }

    private String registerUser(HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession();
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        User user;
        if (UserDB.emailExists(email)) {
            user = UserDB.selectUser(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            UserDB.update(user);
        } else {
            user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            UserDB.insert(user);
        }

        session.setAttribute("user", user);

        Cookie emailCookie = new Cookie("emailCookie", email);
        emailCookie.setMaxAge(60 * 60 * 24 * 365 * 2);
        emailCookie.setPath("/");
        response.addCookie(emailCookie);

        Product product = (Product) session.getAttribute("product");
        String url = "/catalog/" + product.getCode() + "/sound.jsp";
        return url;
    }
}
