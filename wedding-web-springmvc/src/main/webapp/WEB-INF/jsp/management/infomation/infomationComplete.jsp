        <table>
          <tbody>
            <tr>
              <td>インフォID</td>
              <td><c:out value="${infomationDetail.infomation.infoId}" /></td>
            </tr>
            <tr>
              <td>タイトル</td>
              <td><c:out value="${infomationDetail.infomation.title}" /></td>
            </tr>
            <tr>
              <td>登録日時</td>
           	  <td><fmt:formatDate value="${infomationDetail.infomation.registratedDate}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
            </tr>
            <tr>
              <td>公開日時</td>
              <td>
                <fmt:formatDate value="${infomationDetail.infomation.releaseDate}" pattern="yyyy-MM-dd hh:mm:ss" />
              </td>
            </tr>
             <tr>
                <td>最終更新日時</td>
                <td><fmt:formatDate value="${infomationDetail.infomation.lastUpdatedDate}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
              </tr>
          </tbody>
        </table>
        <h3>メッセージ本文</h3>
        <div id="messageBody" class="infomation-panel">
          <c:import url="/WEB-INF/jsp/${infomationDetail.infomation.infoPagePath}"></c:import>
        </div>