<div class="panel">
  <div class="resultPanel">
    <c:forEach items="${updateResult.updateParamList}" var="updateParam" varStatus="status">
      <c:choose>
        <c:when test="${updateParam == 'request.title'}">
          <table>
            <tbody>
              <tr>
                <th colspan="2">タイトル</th>
              </tr>
              <tr>
                <th>更新前</th>
                <th>更新後</th>
              </tr>
              <tr>
                <td>
                  <spring:nestedPath path="updateResult.beforeEntity">
                    <spring:bind path="request.title">${status.value}</spring:bind>
                  </spring:nestedPath>
                </td>
                <td>
                  <spring:nestedPath path="updateResult.afterEntity">
                    <spring:bind path="request.title">${status.value}</spring:bind>
                  </spring:nestedPath>
                </td>
              </tr>              
            </tbody>
          </table>
        </c:when>
      </c:choose>
    </c:forEach>
    <c:forEach items="${updateResult.updateParamList}" var="updateParam" varStatus="status">
      <c:choose>
        <c:when test="${updateParam == 'request.requestContents'}">
          <table>
            <tbody>
              <tr>
                <th colspan="2">メッセージ本文</th>
              </tr>
              <tr>
                <th>更新前</th>
              </tr>
              <tr>
                <td>
                  <spring:nestedPath path="updateResult.beforeEntity">
                    <spring:bind path="request.requestContents">${status.value}</spring:bind>
                  </spring:nestedPath>
                </td>
              </tr>
              <tr>
                <th>更新後</th>
              <tr>
                <td>
                  <spring:nestedPath path="updateResult.afterEntity">
                    <spring:bind path="request.requestContents">${status.value}</spring:bind>
                  </spring:nestedPath>
                </td>
              </tr>              
            </tbody>
          </table>
        </c:when>
      </c:choose>
    </c:forEach>
    <c:set var="userCount" value="0"/>
      <c:forEach items="${updateResult.updateParamList}" var="updateParam" varStatus="status">
        <c:choose>
          <c:when test="${fn:startsWith(updateParam, 'acceptor-')}">
            <c:if test="${userCount == 0}">
              <table>
                <tbody>
                  <tr>
                    <th>No</th>
                    <th>ユーザID</th>
                    <th>ユーザ名</th>
                    <th></th>
                  </tr>
            </c:if>
              <c:forEach items="${updateResult.afterEntity.approvedUsers}" var="user" varStatus="status1">
                <c:if test="${user.userId == fn:substringAfter(updateParam, '-')}">
                  <tr>
                    <td>${userCount+1}</td>
                    <td>${user.userId}</td>
                    <td>${user.userName}</td>
                    <td>追加</td>
                  </tr>
                </c:if>
              </c:forEach>
              <c:forEach items="${updateResult.afterEntity.deniedUsers}" var="user" varStatus="status1">
                <c:if test="${user.userId == fn:substringAfter(updateParam, '-')}">
                  <tr>
                    <td>${userCount+1}</td>
                    <td>${user.userId}</td>
                    <td>${user.userName}</td>
                    <td>追加</td>
                  </tr>
                </c:if>
              </c:forEach>
          <c:set var="userCount" value="${userCount+1}"/>
          </c:when>
          <c:when test="${fn:startsWith(updateParam, 'excludeUser-')}">
            <c:if test="${userCount == 0}">
              <table>
                <tbody>
                  <tr>
                    <th>No</th>
                    <th>ユーザID</th>
                    <th>ユーザ名</th>
                    <th></th>
                  </tr>
              </c:if>
              <c:forEach items="${updateResult.beforeEntity.approvedUsers}" var="user" varStatus="status1">
                <c:if test="${user.userId == fn:substringAfter(updateParam, '-')}">
                  <tr>
                    <td>${userCount+1}</td>
                    <td>${user.userId}</td>
                    <td>${user.userName}</td>
                    <td>削除</td>
                  </tr>
                </c:if>
              </c:forEach>
              <c:forEach items="${updateResult.beforeEntity.deniedUsers}" var="user" varStatus="status1">
                <c:if test="${user.userId == fn:substringAfter(updateParam, '-')}">
                  <tr>
                    <td>${userCount+1}</td>
                    <td>${user.userId}</td>
                    <td>${user.userName}</td>
                    <td>削除</td>
                  </tr>
                </c:if>
              </c:forEach>
          <c:set var="userCount" value="${userCount+1}"/>
          </c:when>
        </c:choose>
      </c:forEach>
      <c:if test="${userCount > 0}">
                </tbody>
              </table>
      </c:if>
  </div>
</div>
 