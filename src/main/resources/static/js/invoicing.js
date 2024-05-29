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

/*  var printButton = document.getElementById('printButton');

  printButton.addEventListener('click', function() {
    window.print(); // 페이지 프린트
  });*/

  var printButton = document.getElementById('printButton');

  printButton.addEventListener('click', function() {
    $("#modal1").show(); // 모달 열기
    printModalContent(); // 모달 창 내용 프린트 함수 호출
  });

  function printModalContent() {
    var modal = document.getElementById('modal1'); // 현재 모달 선택
    var modalContent = modal.querySelector('.modal-content'); // 모달 콘텐츠 요소 선택
    var printableContent = modalContent.innerHTML; // 모달 콘텐츠 내용 저장

    // 모달 외의 내용을 숨김
    var otherContent = document.querySelectorAll('body > *:not(#modal1)');
    otherContent.forEach(function(element) {
      element.style.display = 'none';
    });

    // 프린트용 스타일 생성
    var printStyle = document.createElement('style');
    printStyle.innerHTML = `
    @media print {
      body > *:not(#modal1) {
        display: none !important;
      }
    }
  `;
    document.head.appendChild(printStyle);

    // 임시 HTML을 새 창에 열어 프린트
    var printWindow = window.open('', '_blank');
    printWindow.document.open();
    printWindow.document.write('<html><head><title>모달 창 프린트</title><link rel="stylesheet" type="text/css" href="/css/invoicing.css"></head><body>' + printableContent + '</body></html>');
    printWindow.document.close();

    // 프린트 후에도 원래 상태로 복원
    setTimeout(function() {
      printWindow.print();
      printWindow.close();

      // 다른 내용들을 다시 표시
      otherContent.forEach(function(element) {
        element.style.display = '';
      });

      // 프린트용 스타일 제거
      document.head.removeChild(printStyle);
    }, 1000);
  }

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