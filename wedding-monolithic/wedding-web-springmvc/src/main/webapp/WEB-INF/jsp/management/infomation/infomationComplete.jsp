 <div class="panel">
   <div class="resultPanel">
     <c:forEach items="${updateResult.updateParamList}" var="updateParam" varStatus="status">
       <c:choose>
         <c:when test="${updateParam == 'infomation.title'}">
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
                      <spring:bind path="infomation.title">${status.value}</spring:bind>
                    </spring:nestedPath>
                  </td>
                  <td>
                    <spring:nestedPath path="updateResult.afterEntity">
                      <spring:bind path="infomation.title">${status.value}</spring:bind>
                    </spring:nestedPath>
                  </td>
                </tr>
              </tbody>
            </table>
          </c:when>
          <c:when test="${updateParam == 'infomation.releaseDate'}">
           <table>
             <tbody>
              <tr>
                  <th colspan="2">公開日時</th>
                </tr>
                <tr>
                  <th>更新前</th>
                  <th>更新後</th>
                </tr>
                <tr>
                  <td>
                    <fmt:formatDate value="${updateResult.beforeEntity.infomation.releaseDate}" pattern="yyyy/MM/dd HH:mm:ss" />
                  </td>
                  <td>
                    <fmt:formatDate value="${updateResult.afterEntity.infomation.releaseDate}" pattern="yyyy/MM/dd HH:mm:ss" />
                  </td>
                </tr>
              </tbody>
            </table>
          </c:when>
        </c:choose>
      </c:forEach>
      <c:forEach items="${updateResult.updateParamList}" var="updateParam" varStatus="status">
        <c:choose>
          <c:when test="${updateParam == 'infomation.messageBody'}">
            <h3>メッセージ本文</h3>
              <div id="messageBody" class="infomation-panel">
                <c:import url="/WEB-INF/jsp/${updateResult.afterEntity.infomation.infoPagePath}"></c:import>
              </div>
          </c:when>
        </c:choose>
      </c:forEach>
     <c:set var="userCount" value="0"/>
      <c:forEach items="${updateResult.updateParamList}" var="updateParam" varStatus="status">
        <c:choose>
          <c:when test="${fn:startsWith(updateParam, 'viewUser-')}">
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
              <c:forEach items="${updateResult.afterEntity.accessedUsers}" var="user" varStatus="status1">
                <c:if test="${user.userId == fn:substringAfter(updateParam, '-')}">
                  <tr>
                    <td>${userCount+1}</td>
                    <td>${user.userId}</td>
                    <td>${user.userName}</td>
                    <td>追加</td>
                  </tr>
                </c:if>
              </c:forEach>
              <c:forEach items="${updateResult.afterEntity.noAccessedUsers}" var="user" varStatus="status1">
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
              <c:forEach items="${updateResult.beforeEntity.accessedUsers}" var="user" varStatus="status1">
                <c:if test="${user.userId == fn:substringAfter(updateParam, '-')}">
                  <tr>
                    <td>${userCount+1}</td>
                    <td>${user.userId}</td>
                    <td>${user.userName}</td>
                    <td>削除</td>
                  </tr>
                </c:if>
              </c:forEach>
              <c:forEach items="${updateResult.beforeEntity.noAccessedUsers}" var="user" varStatus="status1">
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