<div class="panel">
  <div class="resultPanel">
    <p>以下の通り、更新されました。</p>
      <c:forEach items="${updateResult.updateParamList}" var="updateParam" varStatus="status">
        <table>
          <tbody>
            <c:choose>
              <c:when test="${updateParam == 'userName'}">
                <tr>
                  <th colspan="2">ユーザ名</th>
                </tr>
                <tr>
                  <th>更新前</th>
                  <th>更新後</th>
                </tr>
                <tr>
                  <td>
                    <spring:nestedPath path="updateResult.beforeEntity" >
                      <spring:bind path="userName">${status.value}</spring:bind>
                    </spring:nestedPath>
                  </td>
                  <td>
                    <spring:nestedPath path="updateResult.afterEntity" >
                      <spring:bind path="userName">${status.value}</spring:bind>
                    </spring:nestedPath>
                  </td>
                </c:when>
                <c:when test="${updateParam == 'loginId'}">
                  <tr>
                    <th colspan="2">ログインID</th>
                  </tr>
                  <tr>
                    <th>更新前</th>
                    <th>更新後</th>
                  </tr>
                  <tr>
                    <td>
                      <spring:nestedPath path="updateResult.beforeEntity" >
                        <spring:bind path="loginId">${status.value}</spring:bind>
                      </spring:nestedPath>
                    </td>
                    <td>
                      <spring:nestedPath path="updateResult.afterEntity" >
                        <spring:bind path="loginId">${status.value}</spring:bind>
                      </spring:nestedPath>
                    </td>
                  </tr>
                </c:when>
                <c:when test="${updateParam == 'imageFile'}">
                  <tr>
                    <th colspan="2">ピクチャ</th>
                  </tr>
                  <tr>
                    <th>更新前</th>
                    <th>更新後</th>
                  </tr>
                  <tr>
                    <td>
                      <spring:nestedPath path="updateResult.beforeEntity" >
                        <spring:bind path="imageFilePath">
                          <img src="${pageContext.request.contextPath}/${status.value}">
                        </spring:bind>
                      </spring:nestedPath>
                    </td>
                    <td>
                      <spring:nestedPath path="updateResult.afterEntity" >
                        <spring:bind path="imageFilePath">
                          <img src="${pageContext.request.contextPath}/${status.value}">
                        </spring:bind>
                      </spring:nestedPath>
                    </td>
                  </tr>
                </c:when>
                <c:when test="${updateParam == 'address.postCd'}">
                  <tr>
                    <th colspan="2">郵便番号</th>
                  </tr>
                  <tr>
                    <th>更新前</th>
                    <th>更新後</th>
                  </tr>
                  <tr>
                    <td>
                      <spring:nestedPath path="updateResult.beforeEntity" >
                        <spring:bind path="address.postCd">${status.value}</spring:bind>
                      </spring:nestedPath>
                    </td>
                    <td>
                      <spring:nestedPath path="updateResult.afterEntity" >
                        <spring:bind path="address.postCd">${status.value}</spring:bind>
                      </spring:nestedPath>
                    </td>
                  </tr>
                </c:when>
                 <c:when test="${updateParam == 'credentials#PASSWORD'}">
                  <tr>
                    <th colspan="2">パスワード※セキュリティのため、表示されません。</th>
                  </tr>
                  <tr>
                    <th>更新前</th>
                    <th>更新後</th>
                  </tr>
                  <tr>
                    <td>
                      ******
                    </td>
                    <td>
                      ******
                    </td>
                  </tr>
                </c:when>
                <c:when test="${updateParam == 'address.address'}">
                  <tr>
                    <th colspan="2">住所</th>
                  </tr>
                  <tr>
                    <th>更新前</th>
                    <th>更新後</th>
                  </tr>
                  <tr>
                    <td>
                      <spring:nestedPath path="updateResult.beforeEntity" >
                        <spring:bind path="address.address">${status.value}</spring:bind>
                      </spring:nestedPath>
                    </td>
                    <td>
                      <spring:nestedPath path="updateResult.afterEntity" >
                        <spring:bind path="address.address">${status.value}</spring:bind>
                      </spring:nestedPath>
                    </td>
                  </tr>
                </c:when>
                <c:when test="${fn:startsWith(updateParam, 'emails')}">
                  <tr>
                    <th colspan="2">Email # ${fn:substringAfter(updateParam, '#')}</th>
                  </tr>
                  <tr>
                    <th>更新前</th>
                    <th>更新後</th>
                  </tr>
                  <tr>
                    <td>
                      <c:forEach var="email" items="${updateResult.beforeEntity.emails}">
                        <c:if test="${email.id.emailId == fn:substringAfter(updateParam, '#')}">
                          <c:out value="${email.email}" />
                        </c:if>
                      </c:forEach>
                    </td>
                    <td>
                      <c:forEach var="email" items="${updateResult.afterEntity.emails}">
                        <c:if test="${email.id.emailId == fn:substringAfter(updateParam, '#')}">
                          <c:out value="${email.email}" />
                        </c:if>
                      </c:forEach>
                    </td>
                  </tr>
                </c:when>
              </c:choose>
           </tbody>
        </table>
      </c:forEach>
    </div>
</div>