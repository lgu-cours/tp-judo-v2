<%@ taglib uri="/WEB-INF/tld/c.tld"   prefix="c" %>     
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>

<div style="height:100%; ">
<h3>Competitions</h3> 
( competitions.jsp )
<p>
<b><u>Liste des competitions</u></b>
</p>

<table border="1">
<tr> <td> Id </td> <td> Date </td>  <td> Type </td> <td> Ville </td> <td> Lieu </td> <td> Dept </td> </tr>
<c:forEach var="v" items="${requestScope.competitions}" >
<tr> 
 <td> ${v.id} </td> 
 <td> <fmt:formatDate value="${v.date}" pattern="dd/MM/yyyy" /> </td>  
 <td> ${v.nom} </td> 
 <td> ${v.ville} </td> 
 <td> ${v.adresse} </td> 
 <td> ${v.departement} </td> 
</tr>
</c:forEach>
</table>


</div>