function searchItem() {
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById("searchInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("itemTable");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[1]; // 두 번째 열은 품목명
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }
  }
}

// 페이지네이션 정보
var currentPage = 1; // 현재 페이지
var totalPages = 1; // 전체 페이지 수, 초기값은 1로 설정

// 페이지 변경 함수
function changePage(page) {
  currentPage = page;
  // 페이지 변경에 따른 서버 요청 등의 동작을 수행
  // 이후 데이터를 다시 렌더링하거나 화면 갱신 등의 동작을 수행
  console.log('Current Page:', currentPage);
  // 여기에 서버로부터 데이터를 요청하고, 받은 데이터로 화면을 갱신하는 로직을 추가할 수 있습니다.
  renderPagination(); // 페이지 이동 후 페이지네이션 다시 렌더링
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

/*
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
