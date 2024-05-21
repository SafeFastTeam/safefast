$(document).ready(function () {
  // "검수계획 등록" 버튼 클릭 시 모달 열기
  $("td .issue-btn").click(function () {
    // 선택된 행 찾기
    var selectedRow = $("input[type='checkbox']:checked").closest("tr");

    if (selectedRow.length !== 1) {
      alert("항목을 한 개 선택하세요.");
      return;
    }

    // 선택된 행에서 데이터 가져오기
    var purchOrderNumber = selectedRow.find("td:nth-child(2)").text();

    // AJAX 요청으로 발주서 세부 정보 가져오기
    $.ajax({
      url: "/purchase_order/" + purchOrderNumber,
      method: "GET",
      success: function (data) {
        // Set data in the modal
        $("#modal1 #order-no").text(data.purchOrderNumber);
        $("#modal1 [data-field='orderDate']").text(data.purchOrderDate);
        $("#modal1 [data-field='receiveDueDate']").text(data.receiveDuedate);
        $("#modal1 [data-field='itemCode']").text(data.item.itemCode);
        $("#modal1 [data-field='itemName']").text(data.item.itemName);
        $("#modal1 [data-field='companyName']").text(data.coOpCompany.companyName);
        $("#modal1 [data-field='businessNumber']").text(data.coOpCompany.businessNumber);
        $("#modal1 [data-field='orderQuantity']").text(data.purchOrderQuantity);
        // 모달 표시
        $("#modal1").show();
      },
      error: function () {
        alert("데이터를 불러오는 데 실패했습니다. 다시 시도해주세요.");
      }
    });
  });

  var printButton = document.getElementById('printButton');

  printButton.addEventListener('click', function() {
    window.print(); // 페이지 프린트
  });

  document.getElementById('email').addEventListener('click', function() {
    // 이메일 클라이언트가 없는 경우에는 다른 방법으로 이메일을 전송할 수 있도록 안내
    var emailAddress = 'ekzmemforhs3@naver.com';
    // 사용자에게 이메일 주소를 복사하도록 안내
    var dummyInput = document.createElement('input');
    document.body.appendChild(dummyInput);
    dummyInput.setAttribute('value', emailAddress);
    dummyInput.select();
    document.execCommand('copy');
    document.body.removeChild(dummyInput);
    alert('이메일 주소가 복사되었습니다: ' + emailAddress);
  });
});

// 닫기 버튼 클릭 이벤트
$(".close-btn").click(function () {
  $(this).closest(".modal").hide();
});