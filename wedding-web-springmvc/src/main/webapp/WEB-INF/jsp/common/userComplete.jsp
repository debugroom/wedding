        <spring:nestedPath path="user">
          <table>
            <tbody>
              <tr>
                <td>ユーザID</td>
                <td><c:out value="${user.userId}" /></td>
              </tr>
              <tr>
                <td>ユーザ名</td>
                <td><c:out value="${user.userName}" /></td>
              </tr>
              <tr>
                <td>ピクチャ</td>
                <td><img src="${pageContext.request.contextPath}/${user.imageFilePath}"></td>
              </tr>
              <tr>
                <td>ログインID</td>
                <td><c:out value="${user.loginId}" /></td>
              </tr>
              <tr>
                <td>権限レベル</td>
                <td><c:out value="${user.authorityLevel}" /></td>
              </tr>
              <tr>
                <td>パスワード(セキュリティのため、非表示としています)</td>
                <td>*****</td>
              </tr>
              <tr>
                <td>郵便番号</td>
                <td><c:out value="${user.address.postCd}" /></td>
              </tr>
              <tr>
                <td>住所</td>
                <td><c:out value="${user.address.address}" /></td>
              </tr>
              <c:forEach items="${user.emails}" var="email" varStatus="status">
                <tr>
                  <td>Email - ${status.index+1}</td>
                  <td><c:out value="${email.email}" /></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </spring:nestedPath>