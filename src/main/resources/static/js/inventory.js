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

document.getElementById('keyword-search-btn').addEventListener('click', function() {
  searchInventory();
});

function searchInventory() {
  // 검색 키워드와 검색 타입을 가져옵니다.
  const keyword = document.getElementById('keyword-search').value;
  const searchType = document.getElementById('keyword-search-type').value;

  // 검색 요청을 서버로 보냅니다.
  fetch('/inventory/search', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: `keyword=${keyword}&searchType=${searchType}`,
  })
      .then(response => response.json())
      .then(data => {
        // 검색 결과를 테이블에 표시합니다.
        const tableBody = document.getElementById('inventory-calculation-list');
        tableBody.innerHTML = ''; // 기존 테이블 내용을 지웁니다.

        data.forEach(item => {
          const row = document.createElement('tr');
          row.innerHTML = `
                <td>${item.item.itemCode}</td>
                <td>${item.item.itemName}</td>
                <td>${item.item.width}x${item.item.length}x${item.item.height}</td>
                <td>${item.item.material}</td>
                <td>0</td>
                <td>${item.quantityAvailable}</td>
                <td>0</td>
                <td>0</td>
                <td>0</td>
                <td></td>
            `;
          tableBody.appendChild(row);
        });

        // 기말 재고와 재고 금액을 계산합니다.
        calculateInventoryAmount();
      })
      .catch(error => {
        console.error('검색 요청 중 오류가 발생했습니다:', error);
      });
}


/*

const loadData = () => {
  // 실제로는 여기에서 서버에서 데이터를 가져와야 합니다.
  const dummyData = [
    // 더미 데이터를 여기에 추가합니다.
  ];

  totalPages = Math.ceil(dummyData.length / itemsPerPage);
  renderPagination();
  showItems(dummyData, currentPage);
};

document.querySelector('.prev-btn').addEventListener('click', () => {
  if (currentPage > 1) {
    currentPage--;
    loadData();
  }
});

document.querySelector('.next-btn').addEventListener('click', () => {
  if (currentPage < totalPages) {
    currentPage++;
    loadData();
  }
});*/
