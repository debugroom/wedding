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
                    <fmt:formatDate value="${updateResult.beforeEntity.infomation.releaseDate}" pattern="yyyy/MM/dd hh:mm:ss" />
                  </td>
                  <td>
                    <fmt:formatDate value="${updateResult.afterEntity.infomation.releaseDate}" pattern="yyyy/MM/dd hh:mm:ss" />
                  </td>
                </tr>
              </tbody>
            </table>
          </c:when>
        </c:choose>
        <c:choose>
          <c:when test="${updateParam == 'infomation.messageBody'}">
            <h3>メッセージ本文</h3>
              <div id="messageBody" class="infomation-panel">
                <c:import url="/WEB-INF/jsp/${updateResult.afterEntity.infomation.infoPagePath}"></c:import>
              </div>
          </c:when>
        </c:choose>
     </c:forEach>
   </div>
 </div>