package com.revature;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.models.Employee;
import com.revature.service.LoginService;
import com.revature.service.LoginServiceImpl;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private final LoginService service = new LoginServiceImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String username = req.getParameter("username");
		final String password = req.getParameter("password");
		
		Employee attempting = service.attemptAuthentication(username, password);
		
		if (attempting != null) {
			if (attempting.getIsManager() == 0) {
				req.getSession().setAttribute("firstname", attempting.getFirstname());
				req.getRequestDispatcher("erslogin.html").forward(req, resp);
			}
			else if (attempting.getIsManager() == 1) {
				req.getSession().setAttribute("firstname", attempting.getFirstname());
				req.getRequestDispatcher("erslogin.html").forward(req, resp);
			}
		}
	}
}