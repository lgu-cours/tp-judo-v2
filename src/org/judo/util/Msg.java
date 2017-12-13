package org.judo.util;

import javax.servlet.http.HttpServletRequest;

public class Msg {

	public static void setMessage( HttpServletRequest request, String msg )
	{
		request.setAttribute("msg", msg );
	}
}
