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
import store.business.Cart;
import store.business.Invoice;
import store.business.LineItem;
import store.business.Product;
import store.business.User;
import store.data.ProductDB;
import store.data.UserDB;
import store.util.CookieUtil;

/**
 *
 * @author curti
 */
public class OrderController extends HttpServlet {
    private static final String defaultURL = "/cart/cart.jsp";
    
    @Override
    public void doPost(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String url = "";
        if (requestURI.endsWith("/addItem")) {
            url = addItem(request, response);
        } else if (requestURI.endsWith("/updateItem")) {
            url = updateItem(request, response);
        } else if (requestURI.endsWith("/removeItem")) {
            url = removeItem(request, response);
        } else if (requestURI.endsWith("/checkUser")) {
            url = checkUser(request, response);
        } else if (requestURI.endsWith("/processUser")) {
            url = processUser(request, response);
        } else if (requestURI.endsWith("/displayInvoice")) {
            url = displayInvoice(request, response);
        } else if (requestURI.endsWith("/displayUser")) {
            url = "/cart/user.jsp";
        } else if (requestURI.endsWith("/displayCreditCard")) {
            url = "/cart/credit_card.jsp";
        } else if (requestURI.endsWith("/completeOrder")) {
            url = completeOrder(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String url = defaultURL;
        if (requestURI.endsWith("/showCart")) {
            showCart(request, response);
        } else if (requestURI.endsWith("/checkUser")) {
            url = checkUser(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    private String showCart(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getCount() == 0) {
            request.setAttribute("emptyCart", "Your cart is empty");
        } else {
            request.getSession().setAttribute("cart", cart);
        }
        return defaultURL;
    }
    
    private String addItem(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null)
            cart = new Cart();
        String productCode = request.getParameter("productCode");
        Product product = ProductDB.selectProduct(productCode);
        if (product != null) {
            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            cart.addItem(lineItem);
        }
        session.setAttribute("cart", cart);
        return defaultURL;
    }
    
    private String updateItem(HttpServletRequest request,
            HttpServletResponse response) {
        String quantityString = request.getParameter("quantity");
        String productCode = request.getParameter("productCode");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        int quantity;
        try {
            quantity = Integer.parseInt(quantityString);
            if (quantity < 0)
                quantity = 1;
        } catch (NumberFormatException ex) {
            quantity = 1;
        }
        Product product = ProductDB.selectProduct(productCode);
        if (product != null && cart != null) {
            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            lineItem.setQuantity(quantity);
            if (quantity > 0)
                cart.addItem(lineItem);
            else
                cart.removeItem(lineItem);
        }
        return defaultURL;
    }
    
    private String removeItem(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        String productCode = request.getParameter("productCode");
        Product product = ProductDB.selectProduct(productCode);
        if (product != null && cart != null) {
            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            cart.removeItem(lineItem);
        }
        return defaultURL;
    }
    
    private String checkUser(HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // if the User object exists with address1, skip User page
        String url = "/cart/user.jsp";
        if (user != null && !user.getAddress().equals("")) {
            url = "/order/displayInvoice";
        } else {  // otherwise, check the email cookie
            Cookie[] cookies = request.getCookies();
            String email
                    = CookieUtil.getCookieValue(cookies, "emailCookie");
            if (email.equals("")) {
                user = new User();
                url = "/cart/user.jsp";
            } else {
                user = UserDB.selectUser(email);
                if (user != null && !user.getAddress().equals("")) {
                    url = "/order/displayInvoice";
                }
            }
        }
        session.setAttribute("user", user);
        return url;
    }

    private String processUser(HttpServletRequest request,
            HttpServletResponse response) {
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String st = request.getParameter("st");
        String zip = request.getParameter("zip");
        String country = request.getParameter("country");
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
        }

        if (UserDB.emailExists(email)) {
            user = UserDB.selectUser(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setAddress(address);
            user.setCity(city);
            user.setSt(st);
            user.setZip(zip);
            user.setCountry(country);            
            UserDB.update(user);
        } else {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setAddress(address);
            user.setCity(city);
            user.setSt(st);
            user.setZip(zip);
            user.setCountry(country);
            UserDB.insert(user);
        }

        session.setAttribute("user", user);

        return "/order/displayInvoice";
    }

    private String displayInvoice(HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession();        
        User user = (User) session.getAttribute("user");       
        Cart cart = (Cart) session.getAttribute("cart");

        java.util.Date today = new java.util.Date();

        Invoice invoice = new Invoice();
        invoice.setUser(user);
        invoice.setInvoiceDate(today);
        invoice.setLineItems(cart.getItems());
        
        session.setAttribute("invoice", invoice);
        
        return "/cart/invoice.jsp";
    }
    
    private String completeOrder(HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession();
        Invoice invoice = (Invoice) session.getAttribute("invoice");

        String creditCardType = 
                request.getParameter("creditCardType");
        String creditCardNumber = 
                request.getParameter("creditCardNumber");
        String creditCardExpMonth = 
                request.getParameter("creditCardExpirationMonth");
        String creditCardExpYear = 
                request.getParameter("creditCardExpirationYear");

        User user = invoice.getUser();
        user.setCreditCardType(creditCardType);
        user.setCreditCardNumber(creditCardNumber);
        user.setCreditCardExpiration(creditCardExpMonth
                + "/" + creditCardExpYear);
        
        // if a record for the User object exists, update it
        if (UserDB.emailExists(user.getEmail())) {
            UserDB.update(user);
        } else { // otherwise, write a new record for the user            
            UserDB.insert(user);
        }        
        invoice.setUser(user);
        
        // write a new invoice record
        //InvoiceDB.insert(invoice);
        
        // set the emailCookie in the user's browser.
        Cookie emailCookie = new Cookie("emailCookie",
                user.getEmail());
        emailCookie.setMaxAge(60*24*365*2*60);
        emailCookie.setPath("/");
        response.addCookie(emailCookie);

        // remove all items from the user's cart
        session.setAttribute("cart", null);
        
        // send an email to the user to confirm the order.
        
        
        return "/cart/complete.jsp";
    }    
}
