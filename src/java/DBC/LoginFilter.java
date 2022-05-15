/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBC;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import java.util.List;
public class LoginFilter implements Filter {
/** Configuration member to specify if web app use web fonts */
	public static final boolean APP_USE_WEBFONTS = false;

	/** Configuration member to specify if web app use videos or audios */
	public static final boolean APP_USE_AUDIOS_OR_VIDEOS = false;

	/** Configuration member to specify if filter must add CSP directive used by Mozilla (Firefox) */
	public static final boolean INCLUDE_MOZILLA_CSP_DIRECTIVES = true;

        private SecureRandom prng = null;
        
	/** Filter configuration */
	@SuppressWarnings("unused")
	private FilterConfig filterConfig = null;

	/** List CSP HTTP Headers */
	private List<String> cspHeaders = new ArrayList<String>();

	/** Collection of CSP polcies that will be applied */
	private String policies = null;

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// Get filter configuration
		this.filterConfig = fConfig;

                // Init secure random
		try {
			this.prng = SecureRandom.getInstance("SHA1PRNG");
		}
		catch (NoSuchAlgorithmException e) {
			throw new ServletException(e);
		}
                
		// Define list of CSP HTTP Headers
		this.cspHeaders.add("Content-Security-Policy");
		this.cspHeaders.add("X-Content-Security-Policy");
		this.cspHeaders.add("X-WebKit-CSP");

		// Define CSP policies
		// Loading policies for Frame and Sandboxing will be dynamically defined : We need to know if context use Frame
		List<String> cspPolicies = new ArrayList<String>();
		String originLocationRef = "'self'";
		// --Disable default source in order to avoid browser fallback loading using 'default-src' locations
		cspPolicies.add("default-src 'none'");
		// --Define loading policies for Scripts
		cspPolicies.add("script-src " + originLocationRef + " 'unsafe-inline' 'unsafe-eval'");
		if (INCLUDE_MOZILLA_CSP_DIRECTIVES) {
			cspPolicies.add("options inline-script eval-script");
			cspPolicies.add("xhr-src 'self'");
		}
		// --Define loading policies for Plugins
		cspPolicies.add("object-src " + originLocationRef);
		// --Define loading policies for Styles (CSS)
		cspPolicies.add("style-src " + originLocationRef);
		// --Define loading policies for Images
		cspPolicies.add("img-src " + originLocationRef);
		// --Define loading policies for Form
		cspPolicies.add("form-action " + originLocationRef);
		// --Define loading policies for Audios/Videos
		if (APP_USE_AUDIOS_OR_VIDEOS) {
			cspPolicies.add("media-src " + originLocationRef);
		}
		// --Define loading policies for Fonts
		if (APP_USE_WEBFONTS) {
			cspPolicies.add("font-src " + originLocationRef);
		}
		// --Define loading policies for Connection
		cspPolicies.add("connect-src " + originLocationRef);
		// --Define loading policies for Plugins Types
		cspPolicies.add("plugin-types application/pdf application/x-shockwave-flash");
		// --Define browser XSS filtering feature running mode
		cspPolicies.add("reflected-xss block");

		// Target formating
		this.policies = cspPolicies.toString().replaceAll("(\\[|\\])", "").replaceAll(",", ";").trim();
	}
    @Override
    public void destroy() {
        // ...
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest)) {
            chain.doFilter(servletRequest, response);
            return;
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
      
        // L3- Allow Origin
        String requestOrign=request.getHeader("origin");
        
        System.out.println("requestOrign : " + requestOrign);
        if(requestOrign!=null){
                httpResponse.setHeader("X-XSS-Protection", "1;mode=block");
                httpResponse.setHeader("Content-Security-Policy", "frame-ancestors 'self'"); 
                httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST");
                httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
                httpResponse.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type,data-type, Access-Control-Request-Method, Access-Control-Request-Headers,Set-Cookie");
                httpResponse.setHeader("X-Frame-Options", "SAMEORIGIN");  
                httpResponse.setHeader("Set-Cookie", "key=value; HttpOnly; SameSite=strict");
                
            // end of csp
        }        
        HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(
                httpResponse) {
            @Override
            public String encodeRedirectUrl(String url) {
                return url;
            }
            @Override
            public String encodeRedirectURL(String url) {
                return url;
            }
            @Override
            public String encodeUrl(String url) {
                return url;
            }
            @Override
            public String encodeURL(String url) {
                return url;
            }
           
        };
        System.out.println("settgin headers wheen origine is "+requestOrign);
        httpResponse.setHeader("X-XSS-Protection", "1;mode=block");
        httpResponse.setHeader("Content-Security-Policy", "frame-ancestors 'self'"); 
        httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST");
        httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type,data-type, Access-Control-Request-Method, Access-Control-Request-Headers,Set-Cookie");
        httpResponse.setHeader("X-Frame-Options", "SAMEORIGIN");
        httpResponse.setHeader("Set-Cookie", "key=value; HttpOnly; SameSite=strict");
        chain.doFilter(request, wrappedResponse);
    }   
}


