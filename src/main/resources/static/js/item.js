let currentPage = 1;
let totalPages = 1;

function searchItems() {
  const searchOption = document.getElementById('search-option').value;
  const keyword = document.getElementById('searchInput').value;
  fetchItems(currentPage, searchOption, keyword);
}

function fetchItems(page, searchOption = 'itemCode', keyword = '') {
  const url = `/item/search?page=${page}&searchOption=${searchOption}&keyword=${encodeURIComponent(keyword)}`;

  fetch(url)
      .then(response => response.json())
      .then(data => {
        renderItems(data.dtoList); // 아이템 목록 렌더링
        totalPages = data.totalPage;
        document.getElementById('currentPage').textContent = data.page;
        document.getElementById('totalPages').textContent = totalPages;
        togglePaginationButtons(); // 페이지네이션 버튼 상태 갱신
      })
      .catch(error => console.error('Error fetching items:', error));
}

function renderItems(items) {
  const itemTableBody = document.querySelector('#itemTable tbody');
  itemTableBody.innerHTML = ''; // 기존 항목 초기화

  items.forEach(item => {
    const row = document.createElement('tr');
    row.innerHTML = `
            <td>${item.itemCode}</td>
            <td>${item.itemName}</td>
            <td>${item.width} x ${item.length} x ${item.height}</td>
            <td>${item.material}</td>
            <td>${item.blueprintOriginName}</td>
        `;
    itemTableBody.appendChild(row);
  });
}

function previousPage() {
  if (currentPage > 1) {
    currentPage--;
    searchItems();
  }
}

function nextPage() {
  if (currentPage < totalPages) {
    currentPage++;
    searchItems();
  }
}

function togglePaginationButtons() {
  document.getElementById('prevButton').disabled = currentPage <= 1;
  document.getElementById('nextButton').disabled = currentPage >= totalPages;
}

// 초기 로드 시 기본 아이템 목록을 가져옴
document.addEventListener('DOMContentLoaded', function() {
  searchItems();
});


/*
// 검색 요청 보내기
function searchItems(keyword, pageNumber, pageSize, sortField, sortDir) {
  $.ajax({
    type: "GET",
    url: "/item/search",
    data: {
      keyword: keyword,
      pageNumber: pageNumber,
      pageSize: pageSize,
      sortField: sortField,
      sortDir: sortDir
    },
    success: function(data) {
      // 검색 결과를 받아서 동적으로 페이지 업데이트하기
      updatePage(data);
    },
    error: function(error) {
      console.error("Error:", error);
    }
  });
}

// 페이지 업데이트 함수
function updatePage(data) {
  // 검색 결과를 이용하여 HTML 동적으로 생성하여 페이지에 표시하기
  // 예: 검색 결과를 테이블 형태로 만들어서 페이지에 표시하기
}

// 페이지네이션 정보
var currentPage = 1; // 현재 페이지
var totalPages = 1; // 전체 페이지 수, 초기값은 1로 설정

// 페이지 변경 함수
function changePage(page) {
  // 페이지 변경에 따른 서버 요청
  $.ajax({
    type: "GET",
    url: "/item/getItems", // 해당 URL은 서버에서 페이지에 해당하는 데이터를 가져오는 엔드포인트여야 합니다.
    data: {
      pageNumber: page,
      pageSize: pageSize, // 페이지 크기는 필요에 따라 조절 가능합니다.
      sortField: sortField, // 필요에 따라 정렬 필드를 지정할 수 있습니다.
      sortDir: sortDir // 필요에 따라 정렬 방향을 지정할 수 있습니다.
    },
    success: function(data) {
      // 데이터를 받아와서 페이지를 업데이트하고 페이지네이션을 다시 렌더링
      updatePage(data);
    },
    error: function(error) {
      console.error("Error:", error);
    }
  });
}

// 이전 페이지로 이동하는 함수
function previousPage() {
  if (currentPage > 1) {
    changePage(currentPage - 1);
  }
}

// 다음 페이지로 이동하는 함수
function nextPage() {
  if (currentPage < totalPages) {
    changePage(currentPage + 1);
  }
}

// 페이지네이션 초기화 함수
function renderPagination() {
  var currentPageSpan = document.getElementById('currentPage');
  var totalPagesSpan = document.getElementById('totalPages');

  // 현재 페이지 및 전체 페이지 수 표시
  currentPageSpan.textContent = currentPage;
  totalPagesSpan.textContent = totalPages;

  // 현재 페이지가 첫 번째 페이지이면 이전 버튼 비활성화
  var previousButton = document.querySelector('.pagination-button:first-child');
  previousButton.disabled = (currentPage === 1);

  // 현재 페이지가 마지막 페이지이면 다음 버튼 비활성화
  var nextButton = document.querySelector('.pagination-button:last-child');
  nextButton.disabled = (currentPage === totalPages);
}

// 페이지네이션 초기화
renderPagination();
*/

/*
처음에 있던 코드
var currentPage = 1;
var totalPages = 1;

function updatePagination() {
  var pagination = document.querySelector('.pagination');
  var currentPageSpan = document.getElementById('currentPage');
  var totalPagesSpan = document.getElementById('totalPages');

  currentPageSpan.textContent = currentPage;
  totalPagesSpan.textContent = totalPages;
}

function previousPage() {
  if (currentPage > 1) {
    currentPage--;
    fetchData();
  }
}

function nextPage() {
  if (currentPage < totalPages) {
    currentPage++;
    fetchData();
  }
}*/

/*
// 한 페이지에 표시될 아이템 수
int itemsPerPage = 5;
// 페이지 번호
int pageNumber = 1; // 페이지 번호는 필요에 따라 동적으로 설정 가능

// 해당 페이지에 표시될 아이템 수와 페이지 번호를 전달하여 데이터를 가져옴
List<Item> items = itemService.getItemsPerPage(pageNumber, itemsPerPage);
model.addAttribute("items", items);*/
