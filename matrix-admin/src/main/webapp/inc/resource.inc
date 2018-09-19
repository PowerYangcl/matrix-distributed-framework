	<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		String js = path + "/js";
		String jsp = path + "/jsp";
		String css = path + "/css";
		String img = path + "/images";
		String resources = path + "/resources";
	
		pageContext.setAttribute("js", js);
		pageContext.setAttribute("jsp", jsp);
		pageContext.setAttribute("css", css);
		pageContext.setAttribute("img", img);
		pageContext.setAttribute("basePath", basePath);
		pageContext.setAttribute("resources", resources);
		
		if(session.getAttribute("userInfo") == null){
			String url = basePath + "login.jsp";
			response.sendRedirect(url);
			return;
		}
	%>
	
	








