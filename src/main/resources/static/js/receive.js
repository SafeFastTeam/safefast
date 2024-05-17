document.addEventListener('DOMContentLoaded', function() {
  // 모달 띄우기
  var modal = document.getElementById("myModal");
  var span = document.getElementsByClassName("close")[0];

  function openModal() {
    modal.style.display = "block";
  }

  span.onclick = function() {
    modal.style.display = "none";
  }

  window.onclick = function(event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  }

  // 취소 버튼 클릭 시 모달 닫기
  document.getElementById("cancelBtn").onclick = function() {
    modal.style.display = "none";
  }

  // 확인 버튼 클릭 시 입고마감 처리 및 데이터 전송
  document.getElementById("confirmBtn").onclick = function() {
    // 서버로 데이터 전송하는 로직 추가
    alert("마감처리가 완료되었습니다.");
    modal.style.display = "none";
  }

  // 테이블에 동적으로 데이터 추가
  var table = document.getElementById("purchase-order-list");
  var modalTable = document.getElementById("modal-purchase-order-list");

  // 예시 데이터 (실제로는 서버에서 데이터를 받아와야 함)
  // var data = [
  //   { orderNumber: "PO20230501", itemCode: "ABC기업", itemName: "너트", orderQuantity: '2024-04-30', inspectionStatus: '2024-05-30', receiveClosing: "마감" },
  //   { orderNumber: "PO20230502", itemCode: "CDE기업", itemName: "안테나", orderQuantity: '2024-05-30', inspectionStatus: '2024-06-30', receiveClosing: "미마감" }
  // ];

  data.forEach(function(item) {
    var row = table.insertRow();
    var modalRow = modalTable.insertRow();

    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);
    var cell6 = row.insertCell(5);

    var modalCell1 = modalRow.insertCell(0);
    var modalCell2 = modalRow.insertCell(1);
    var modalCell3 = modalRow.insertCell(2);
    var modalCell4 = modalRow.insertCell(3);
    var modalCell5 = modalRow.insertCell(4);
    var modalCell6 = modalRow.insertCell(5);

    cell1.innerHTML = item.orderNumber;
    cell2.innerHTML = item.itemCode;
    cell3.innerHTML = item.itemName;
    cell4.innerHTML = item.orderQuantity;
    cell5.innerHTML = item.inspectionStatus;
    cell6.innerHTML = item.receiveClosing;

    modalCell1.innerHTML = `<input type="text" value="${item.orderNumber}">`;
    modalCell2.innerHTML = `<input type="text" value="${item.itemCode}">`;
    modalCell3.innerHTML = `<input type="text" value="${item.itemName}">`;
    modalCell4.innerHTML = `<input type="text" value="${item.orderQuantity}">`;
    modalCell5.innerHTML = `<input type="text" value="${item.inspectionStatus}">`;
    modalCell6.innerHTML = `<input type="text" value="${item.receiveClosing}">`;
  });

  // 입고완료 버튼 클릭 시 모달 열기
  var receiveButtons = document.querySelectorAll("#purchase-order-list button");
  receiveButtons.forEach(function(button) {
    button.addEventListener('click', openModal);
  });
});