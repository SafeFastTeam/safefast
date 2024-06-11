// 날짜 검색 기능
const startDateInputs = document.querySelectorAll('#start-date');
const endDateInputs = document.querySelectorAll('#end-date');

startDateInputs.forEach(input => {
  input.addEventListener('click', () => showCalendar(input));
});

endDateInputs.forEach(input => {
  input.addEventListener('click', () => showCalendar(input));
});

function showCalendar(input) {
  const calendar = document.createElement('div');
  calendar.classList.add('calendar');

  const header = document.createElement('div');
  header.classList.add('header');

  const prevMonth = document.createElement('button');
  prevMonth.textContent = '<';
  prevMonth.addEventListener('click', () => changeMonth(-1));

  const monthYear = document.createElement('span');
  monthYear.textContent = getCurrentMonthYear();

  const nextMonth = document.createElement('button');
  nextMonth.textContent = '>';
  nextMonth.addEventListener('click', () => changeMonth(1));

  header.appendChild(prevMonth);
  header.appendChild(monthYear);
  header.appendChild(nextMonth);

  const daysContainer = document.createElement('div');
  daysContainer.classList.add('days');

  calendar.appendChild(header);
  calendar.appendChild(daysContainer);

  document.body.appendChild(calendar);

  renderDays();

  calendar.addEventListener('click', (e) => {
    if (e.target.classList.contains('day')) {
      const selectedDate = `${getCurrentMonthYear().split(' ')[1]}-${(parseInt(getCurrentMonthYear().split(' ')[0], 10) + 1).toString().padStart(2, '0')}-${e.target.textContent.padStart(2, '0')}`;
      input.value = selectedDate;
      document.body.removeChild(calendar);
    }
  });

  function changeMonth(offset) {
    const currentDate = new Date(getCurrentMonthYear().split(' ')[1], getCurrentMonthYear().split(' ')[0]);
    currentDate.setMonth(currentDate.getMonth() + offset);
    monthYear.textContent = getCurrentMonthYear(currentDate);
    renderDays(currentDate);
  }

  function renderDays(currentDate = new Date()) {
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();
    const firstDay = new Date(year, month, 1).getDay();
    const lastDate = new Date(year, month + 1, 0).getDate();

    daysContainer.innerHTML = '';

    for (let i = 0; i < firstDay; i++) {
      const day = document.createElement('div');
      day.classList.add('day', 'disabled');
      daysContainer.appendChild(day);
    }

    for (let i = 1; i <= lastDate; i++) {
      const day = document.createElement('div');
      day.classList.add('day');
      day.textContent = i;
      daysContainer.appendChild(day);
    }
  }

  function getCurrentMonthYear(currentDate = new Date()) {
    const month = currentDate.getMonth();
    const year = currentDate.getFullYear();
    return `${month} ${year}`;
  }
}

const keywordSearchTypes = document.querySelectorAll('#keyword-search-type');
const keywordSearchInputs = document.querySelectorAll('#keyword-search');
const keywordSearchBtns = document.querySelectorAll('#keyword-search-btn');

keywordSearchBtns.forEach((btn, index) => {
  btn.addEventListener('click', () => {
    const searchType = keywordSearchTypes[index].value;
    const searchKeyword = keywordSearchInputs[index].value;
    // 선택한 검색 유형과 키워드로 데이터 필터링 및 테이블 업데이트 로직 추가
  });
});

document.addEventListener('DOMContentLoaded', () => {
  const issueOrderBtn = document.getElementById('issue-order-btn');
  const orderModal = document.getElementById('orderModal');
  const closeModal = document.querySelector('.close');

  issueOrderBtn.addEventListener('click', () => {
    const selectedPlan = document.querySelector('.plan-row input[type="checkbox"]:checked');
    if (selectedPlan) {
      const row = selectedPlan.closest('tr');
      const purchOrderNumber= row.children[0].innerText;
      const procDuedate = row.children[1].innerText;
      const companyName = row.children[2].innerText;
      const itemName = row.children[3].innerText;
      const orderQuantity = row.querySelector('input[name="procQuantity"]').value;
      const receiveDueDate = row.querySelector('input[name="procDuedate"]').value;

      document.getElementById('modal-purch-order-number').innerText = purchOrderNumber;
      document.getElementById('modal-proc-duedate').innerText = procDuedate;
      document.getElementById('modal-company-name').innerText = companyName;
      document.getElementById('modal-item-name').innerText = itemName;
      document.getElementById('modal-purchase-order-quantity').innerText = orderQuantity;
      document.getElementById('modal-receive-duedate').innerText = receiveDueDate;

      orderModal.style.display = 'block';
    } else {
      alert('발주할 조달 계획을 선택하세요.');
    }
  });

  closeModal.addEventListener('click', () => {
    orderModal.style.display = 'none';
  });

  window.addEventListener('click', (event) => {
    if (event.target === orderModal) {
      orderModal.style.display = 'none';
    }
  });

  document.getElementById('checkOrder').addEventListener('click', () => {
    orderModal.style.display = 'none';
    window.location.href = '/purchase_order/purchase_order';
  });

  document.getElementById('saveOrder').addEventListener('click', () => {
    const modalContent = document.querySelector('.modal-content');
    const pdf = new jsPDF();
    html2canvas(modalContent).then(canvas => {
      const imgWidth = pdf.internal.pageSize.getWidth();
      const imgHeight = pdf.internal.pageSize.getHeight();
      pdf.addImage(canvas.toDataURL('image/png'), 'PNG', 0, 0, imgWidth, imgHeight);
      pdf.save('order.pdf');
    });
  });

  document.getElementById('sendOrder').addEventListener('click', function () {
    const emailAddress = 'ekzmemforhs3@naver.com';
    const dummyInput = document.createElement('input');
    document.body.appendChild(dummyInput);
    dummyInput.setAttribute('value', emailAddress);
    dummyInput.select();
    document.execCommand('copy');
    document.body.removeChild(dummyInput);
    alert('이메일 주소가 복사되었습니다: ' + emailAddress);
  });
});

$(document).ready(function() {
  $('#delete-order-btn').click(function() {
    const selectedOrders = [];
    $('#purchase-order-list input[type="checkbox"]:checked').each(function() {
      const row = $(this).closest('tr');
      const orderNumber = row.find('td:nth-child(2)').text();
      selectedOrders.push(orderNumber);
    });

    if (selectedOrders.length === 0) {
      alert('삭제할 발주서를 선택하세요.');
      return;
    }

    if (confirm('선택한 발주서를 삭제하시겠습니까?')) {
      $.ajax({
        url: '/purchase_order/delete',
        type: 'DELETE',
        contentType: 'application/json',
        data: JSON.stringify(selectedOrders),
        success: function(response) {
          alert(response);
          window.location.href = "/purchase_order/purchase_order";
        },
        error: function(xhr, status, error) {
          console.error('Failed to delete purchase orders:', error);
          alert('발주서 삭제 중 오류가 발생했습니다: ' + xhr.responseText);
        }
      });
    }
  });
});
