function calculateInventoryAmount() {
  const tableRows = document.querySelectorAll('#inventory-calculation-list tr');

  tableRows.forEach(row => {
    const cells = row.querySelectorAll('td');
    const initialInventory = parseInt(cells[4].textContent, 10);
    const incomingQuantity = parseInt(cells[5].textContent, 10);
    const outgoingQuantity = parseInt(cells[6].textContent, 10);
    const unitPrice = parseInt(cells[8].textContent, 10);

    const endingInventory = initialInventory + incomingQuantity - outgoingQuantity;
    const inventoryAmount = endingInventory * unitPrice;

    cells[7].textContent = endingInventory;
    cells[9].textContent = inventoryAmount.toLocaleString();
  });
}

document.getElementById('save-excel-btn').addEventListener('click', function () {
  // 테이블 데이터를 추출합니다.
  var table = document.querySelector('table');
  var wb = XLSX.utils.table_to_book(table, {sheet: "재고산출"});

  // 엑셀 파일을 생성하고 다운로드합니다.
  XLSX.writeFile(wb, '재고산출.xlsx');
});