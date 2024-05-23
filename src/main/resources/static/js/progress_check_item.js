$(document).ready(function () {
    // "검수계획 등록" 버튼 클릭 시 모달 열기
    $(".plan-btn").click(function () {
        // 선택된 행 찾기
        var selectedRow = $("input[type='checkbox']:checked").closest("tr");

        if (selectedRow.length !== 1) {
            alert("항목을 한 개 선택하세요.");
            return;
        }

        // 선택된 행에서 데이터 가져오기
        var purchOrderNumber = selectedRow.find("td:nth-child(2)").text();

        // 모달 초기화
        resetModal();

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

    // 모달 닫기 이벤트
    $(".close-btn").click(function () {
        $(this).closest(".modal").hide();
    });

    // 검수 계획 행 추가 이벤트
    $(document).on("click", ".add-btn", function () {
        var rowCount = $("#modal1 .modal-table-3 tbody tr").length;
        if (rowCount >= 3) {
            alert("최대 3개의 행만 추가할 수 있습니다.");
            return;
        }

        var newRow = `
            <tr>
                <td></td>
                <td><input type="date" class="inspection-date"></td>
                <td>
                    <button class="btn add-btn">추가</button>
                    <button class="btn delete-btn">삭제</button>
                </td>
            </tr>
        `;
        $("#modal1 .modal-table-3 tbody").append(newRow);
        updateRowIndices();
    });

    // 저장 버튼 클릭 이벤트 위임
    $(document).on("click", "#modal1 .save-btn", function () {
        var rows = $("#modal1 .modal-table-3 tbody tr");
        var purchOrderNumber = $("#modal1 #order-no").text();
        var progressCheckItems = [];

        rows.each(function (index, row) {
            var date = $(row).find(".inspection-date").val();
            if (date) {
                progressCheckItems.push({
                    progCheckDate: date,
                    progCheckOrder: index + 1,
                    purchOrderNumber: purchOrderNumber
                });
            }
        });

        $.ajax({
            url: "/progress_check_item/save",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(progressCheckItems),
            success: function (response) {
                alert(response);
                $("#modal1").hide();
            },
            error: function () {
                alert("저장에 실패했습니다. 다시 시도해주세요.");
            }
        });
    });

    // 삭제 버튼 클릭 이벤트 위임
    $(document).on("click", "#modal1 .delete-btn", function () {
        $(this).closest("tr").remove();
        updateRowIndices();
    });

    // 닫기 버튼 클릭 이벤트
    $(".modal-footer .close-btn").click(function () {
        $(this).closest(".modal").hide();
    });

    // 행 인덱스 업데이트 함수
    function updateRowIndices() {
        $("#modal1 .modal-table-3 tbody tr").each(function (index) {
            $(this).find("td:first-child").text((index + 1) + "차");
        });
    }

    // 모달 초기화 함수
    function resetModal() {
        // 기본 행 정의
        var initialRow = `
            <tr>
                <td>1차</td>
                <td><input type="date" class="inspection-date"></td>
                <td>
                    <button class="btn add-btn">추가</button>
                    <button class="btn delete-btn">삭제</button>
                </td>
            </tr>
        `;
        // tbody 내용 비우고 초기 행 추가
        $("#modal1 .modal-table-3 tbody").html(initialRow);
    }
});
