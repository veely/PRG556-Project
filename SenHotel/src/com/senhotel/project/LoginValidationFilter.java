// by Milan K.
package com.senhotel.project;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.senhotel.project.data.GuestDAOFactory;

import ca.on.senecac.prg556.common.StringHelper;

/**
 * Servlet Filter implementation class LoginValidationFilter
 */

public class LoginValidationFilter implements Filter {
	private String loginPage = "/welcome.jspx";
    /**
     * Default constructor. 
     */
    public LoginValidationFilter() {
    	
    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest)req;
		String uriLogin = request.getContextPath() + getLoginPage();
		//  If the session-scoped attribute for the current guest does not exist and the requested page is not the welcome page,
		if (null == request.getSession().getAttribute("userSession") && !uriLogin.equals(request.getRequestURI())) 
			((HttpServletResponse)resp).sendRedirect(uriLogin);
		else
			chain.doFilter(req, resp);
	}

    public void init(FilterConfig fConfig) throws ServletException
	{
		if (StringHelper.isNotNullOrEmpty(fConfig.getInitParameter("login")))
			setLoginPage(StringHelper.stringPrefix(fConfig.getInitParameter("login"), "/"));
	}
	@Override
	public void destroy() {
	// TODO Auto-generated method stub	
	}
	
	public synchronized String getLoginPage() {
		return loginPage;
	}

	private synchronized void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}
}
