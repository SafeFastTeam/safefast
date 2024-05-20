// 발주 리스트 테이블의 각 행에서 첫 번째 열의 체크박스 요소들을 선택합니다.
const checkboxes = document.querySelectorAll('.main-table tbody tr td:first-child input[type="checkbox"]');

// 모달 창1 열기/닫기 기능
const modal = document.querySelector('.modal');
const openModalBtn = document.querySelector('.plan-btn');
const closeModalBtn = document.querySelector('.close-btn');
const orderNoP = document.getElementById('order-no');

// 모달 열기 버튼 클릭 이벤트 핸들러 수정
openModalBtn.addEventListener('click', () => {
    // 체크된 체크박스의 개수를 세어봅니다.
    const checkedCount = Array.from(checkboxes).filter(checkbox => checkbox.checked).length;

    // 만약 체크된 체크박스가 없으면 경고 팝업을 띄웁니다.
    if (checkedCount === 0 || checkedCount !== 1) {
        alert('발주를 한 개씩 선택해주세요.');
    } else {
        // 체크된 체크박스가 있을 경우, 선택된 발주에 대한 데이터를 가져와서 모달에 표시합니다.
        $.ajax({
            url: '/progress_check/purchase_order_details', // 적절한 엔드포인트로 수정 필요
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(data) {
                displayModalContent(data); // 받은 데이터로 모달 내용을 채움
                modal.style.display = 'block'; // 모달 열기
            },
            error: function(xhr, status, error) {
                console.error('Failed to fetch purchase orders:', error);
            }
        });
    }
});

// 선택된 발주의 정보를 가져오는 함수
function getCheckedOrders() {
    const checkedOrders = [];
    checkboxes.forEach((checkbox) => {
        if (checkbox.checked) {
            const row = checkbox.closest('tr'); // 가장 가까운 tr 요소 가져오기
            const orderNoCell = row.querySelector('td:nth-child(2)'); // 발주번호 셀 가져오기
            const orderNo = orderNoCell.textContent; // 발주번호 가져오기
            checkedOrders.push(orderNo);
        }
    });
    return checkedOrders;
}

// 모달 내용을 채우는 함수
function displayModalContent(data) {
    // 모달 내부의 텍스트를 채우는 로직 작성
}

closeModalBtn.addEventListener('click', () => {
    modal.style.display = 'none';
});

window.addEventListener('click', (event) => {
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

// 검수 계획 추가 버튼에 대한 이벤트 리스너 등록 함수
const addBtnEventListener = () => {
    const addBtns = document.querySelectorAll('.modal-table-3 .add-btn');
    addBtns.forEach((btn) => {
        btn.addEventListener('click', () => {
            const newRow = document.createElement('tr');
            const countCell = document.createElement('td');
            const dateCell = document.createElement('td');
            const actionCell = document.createElement('td');
            countCell.textContent = `${inspectionCount + 1}차`;
            // 입력 가능한 날짜 입력란 생성
            const dateInput = document.createElement('input');
            dateInput.type = 'date';
            dateInput.className = 'inspection-date'; // 날짜 입력란에 클래스 추가
            dateCell.appendChild(dateInput);
            actionCell.innerHTML = '<button class="btn add-btn">저장</button><button class="btn delete-btn">삭제</button>';
            newRow.appendChild(countCell);
            newRow.appendChild(dateCell);
            newRow.appendChild(actionCell);
            inspectionList.appendChild(newRow);
            inspectionCount++;
        });
    });
};

// 페이징 기능
// const itemsPerPage = 10;
// let currentPage = 1;
// let totalPages;

// const renderPagination = () => {
//     const prevBtn = document.querySelector('.prev-btn');
//     const nextBtn = document.querySelector('.next-btn');
//     const pageInfo = document.querySelector('.page-info');

//     prevBtn.disabled = currentPage === 1;
//     nextBtn.disabled = currentPage === totalPages;
//     pageInfo.textContent = `${currentPage}/${totalPages}`;
// };

// const showItems = (items, page) => {
//     const tableBody = document.querySelector('tbody');
//     tableBody.innerHTML = '';

//     const startIndex = (page - 1) * itemsPerPage;
//     const endIndex = startIndex + itemsPerPage;
//     const itemsToShow = items.slice(startIndex, endIndex);

//     itemsToShow.forEach((item) => {
//         const row = document.createElement('tr');
//         Object.values(item).forEach((value) => {
//             const cell = document.createElement('td');
//             cell.textContent = value;
//             row.appendChild(cell);
//         });
//         tableBody.appendChild(row);
//     });
// };

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
});

// 검수 계획 삭제 기능
// const addBtn = document.querySelector('.add-btn'); // 삭제
const deleteBtn = document.querySelector('.delete-btn');
const inspectionList = document.querySelector('.modal-table-3 tbody');

let inspectionCount = 1;

addBtnEventListener(); // 이벤트 리스너 등록 함수 호출

inspectionList.addEventListener('click', (event) => {
    if (event.target.classList.contains('delete-btn')) {
        const row = event.target.parentNode.parentNode;
        inspectionList.removeChild(row);
        inspectionCount--;
    }
});


// 모달 창2 열기/닫기 기능
const modal2 = document.querySelector('.modal2');
const openModalBtn2 = document.querySelector('.process-btn');
const closeModalBtn2 = document.querySelector('.close-btn');

openModalBtn2.addEventListener('click', () => {
    modal2.style.display = 'block';
});

closeModalBtn2.addEventListener('click', () => {
    modal2.style.display = 'none';
});

window.addEventListener('click', (event) => {
    if (event.target === modal2) {
        modal2.style.display = 'none';
    }
});

// x 버튼에 대한 이벤트 리스너 추가
const closeModalBtnX = document.querySelector('.modal2 .close-btn');
closeModalBtnX.addEventListener('click', () => {
    modal2.style.display = 'none';
});

// 초기 데이터 로드
loadData();