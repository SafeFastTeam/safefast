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
