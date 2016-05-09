<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${page != null && page.content != null && fn:length(page.content) != 0}">
    <div class="span6">
        <div class="pagination">
            <ul>
                <c:choose>
                    <c:when test="${page.isFirst}">
                        <li class="disabled"><a href="#"><i class="icon-double-angle-left">首页</i></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="#" onclick="goto(0)"><i class="icon-double-angle-left">首页</i></a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${page.hasPrevious}">
                        <li><a href="#" onclick="goto(${page.number-1})"><i>上一页</i></a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="disabled"><a href="#"><i>上一页</i></a></li>
                    </c:otherwise>
                </c:choose>
                <c:forEach var="i" begin="0" end="${page.totalPages-1}">
                    <c:choose>
                        <c:when test="${page.number==i}">
                            <li class="active"><a href="#">${i+1}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="#" onclick="goto(${i});">${i+1}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:choose>
                    <c:when test="${page.hasNext}">
                        <li><a href="#"  onclick="goto(${page.number+1})"><i>下一页</i></a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="disabled"><a href="#"><i>下一页</i></a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${page.isLast}">
                        <li class="disabled"><a href="#"><i
                                class="icon-double-angle-right">尾页</i></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="#" onclick="goto(${page.totalPages-1})"><i class="icon-double-angle-right">尾页</i></a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
    <div class="span4 pull-right">
        <div class="dataTables_length"><label>
            <span>当前第${page.number+1}页 共${page.totalPages}页  共${page.totalElements}条数据</span>
            每页显示
            <select size="1" id="page_size" name="page_size" aria-controls="table_report"
                    onchange="changePagesize(this.value)">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="10">10</option>
                <option value="25">25</option>
                <option value="50">50</option>
                <option value="100">100</option>
            </select> 条记录</label>
        </div>
    </div>
</c:if>