$(document).ready(function () {
    // 모달 초기화 함수
    function resetModal(modalId) {
        var initialRow = modalId === "modal1" ? `
            <tr>
                <td>1차</td>
                <td><input type="date" class="inspection-date"></td>
                <td>
                    <button class="btn add-btn">추가</button>
                    <button class="btn delete-btn">삭제</button>
                </td>
            </tr>
        ` : `
            <tr>
                <td>1차</td>
                <td><input type="date" class="inspection-date"></td>
                <td><input type="number" class="completedQuantity" placeholder="수량 입력"></td>
                <td><input type="text" class="progCheckResult"></td>
                <td><input type="text" class="supplementation"></td>
<!--                <td>
                    <button class="btn add-btn">추가</button>
                    <button class="btn delete-btn">삭제</button>
                </td>-->
            </tr>
        `;
        $(`#${modalId} .modal-table-3 tbody`).html(initialRow);
        updateDeleteButtons(modalId);
    }

    // 행 인덱스 업데이트 함수
    function updateRowIndices(modalId) {
        $(`#${modalId} .modal-table-3 tbody tr`).each(function (index) {
            $(this).find("td:first-child").text((index + 1) + "차");
        });
    }

    // 삭제 버튼 상태 업데이트 함수
    function updateDeleteButtons(modalId) {
        var rows = $(`#${modalId} .modal-table-3 tbody tr`);
        rows.find(".delete-btn").prop('disabled', rows.length === 1);
    }

    // 모달 데이터 설정 함수
    function setModalData(modalId, data) {
        var orderNoSelector = modalId === "modal1" ? "#order-no" : "#modal2-order-no";
        $(`#${modalId} ${orderNoSelector}`).text(data.purchOrderNumber);
        $(`#${modalId} [data-field='orderDate']`).text(data.purchOrderDate);
        $(`#${modalId} [data-field='receiveDueDate']`).text(data.receiveDuedate);
        $(`#${modalId} [data-field='itemCode']`).text(data.item.itemCode);
        $(`#${modalId} [data-field='itemName']`).text(data.item.itemName);
        $(`#${modalId} [data-field='companyName']`).text(data.coOpCompany.companyName);
        $(`#${modalId} [data-field='businessNumber']`).text(data.coOpCompany.businessNumber);
        $(`#${modalId} [data-field='orderQuantity']`).text(data.purchOrderQuantity);
    }

    // "검수계획 등록" 및 "검수처리 등록" 버튼 클릭 시 모달 열기
    function openModal(buttonClass, modalId) {
        $(`.${buttonClass}`).click(function () {
            var selectedRow = $("input[type='checkbox']:checked").closest("tr");

            if (selectedRow.length !== 1) {
                alert("항목을 한 개 선택하세요.");
                return;
            }

            var purchOrderNumber = selectedRow.find("td:nth-child(2)").text();

            resetModal(modalId);

            // AJAX 요청으로 발주서 세부 정보 가져오기
            $.ajax({
                url: "/purchase_order/" + purchOrderNumber,
                method: "GET",
                success: function (data) {
                    setModalData(modalId, data);

                    if (modalId === "modal2") {
                        // 검수계획 불러오기
                        $.ajax({
                            url: "/progress_check_item/list/" + purchOrderNumber,
                            method: "GET",
                            success: function (planData) {
                                var tbody = $(`#${modalId} .modal-table-3 tbody`);
                                tbody.empty();

                                planData.forEach(function (item, index) {
                                    var newRow = `
                                        <tr>
                                            <td>${index + 1}차</td>
                                            <td><input type="date" class="inspection-date" value="${item.progCheckDate}" readonly></td>
                                            <td><input type="number" class="completedQuantity" placeholder="수량 입력" value="${item.completedQuantity}"></td>
                                            <td><input type="text" class="progCheckResult" value="${1}" readonly></td>
                                            <td><input type="text" class="supplementation" value="없음"></td>
<!--                                            <td>
                                                <button class="btn add-btn">추가</button>
                                                <button class="btn delete-btn">삭제</button>
                                            </td>-->
                                        </tr>
                                    `;
                                    tbody.append(newRow);
                                });

                                updateRowIndices(modalId);
                                updateDeleteButtons(modalId);
                            },
                            error: function () {
                                alert("검수계획 데이터를 불러오는 데 실패했습니다.");
                            }
                        });
                    }

                    $(`#${modalId}`).show();
                },
                error: function () {
                    alert("데이터를 불러오는 데 실패했습니다. 다시 시도해주세요.");
                }
            });
        });
    }

    // 모달 닫기 이벤트
    $(".close-btn").click(function () {
        $(this).closest(".modal").hide();
    });

    // 검수 계획 및 검수 처리 행 추가 이벤트
    $(document).on("click", ".add-btn", function () {
        var modalId = $(this).closest(".modal").attr("id");
        var rowCount = $(`#${modalId} .modal-table-3 tbody tr`).length;
        if (rowCount >= 3) {
            alert("최대 3개의 행만 추가할 수 있습니다.");
            return;
        }

        var newRow = modalId === "modal1" ? `
            <tr>
                <td></td>
                <td><input type="date" class="inspection-date"></td>
                <td>
                    <button class="btn add-btn">추가</button>
                    <button class="btn delete-btn">삭제</button>
                </td>
            </tr>
        ` : `
            <tr>
                <td></td>
                <td><input type="date" class="inspection-date"></td>
                <td><input type="number" class="completedQuantity" placeholder="수량 입력"></td>
                <td><input type="text" class="progCheckResult"></td>
                <td><input type="text" class="supplementation"></td>
<!--                <td>
                    <button class="btn add-btn">추가</button>
                    <button class="btn delete-btn">삭제</button>
                </td>-->
            </tr>
        `;
        $(`#${modalId} .modal-table-3 tbody`).append(newRow);
        updateRowIndices(modalId);
    });

    // 저장 버튼 클릭 이벤트 위임
    $(document).on("click", ".save-btn", function () {
        var modalId = $(this).closest(".modal").attr("id");
        var rows = $(`#${modalId} .modal-table-3 tbody tr`);
        var purchOrderNumber = $(`#${modalId} #order-no`).text() || $(`#${modalId} #modal2-order-no`).text();
        var dataItems = [];

        var valid = true; // 필드 입력 확인용 변수

        rows.each(function (index, row) {
            var date = $(row).find(".inspection-date").val();
            if (!date) { // 검수 일자가 입력되지 않은 경우
                valid = false;
                return false; // 반복문 중지
            }

            var item = {
                progCheckDate: date,
                progCheckOrder: index + 1,
                purchaseOrder: {purchOrderNumber: purchOrderNumber}
            };

            if (modalId === "modal2") {
                var quantity = $(row).find(".completedQuantity").val();
                var result = $(row).find(".progCheckResult").val();
                var supplementation = $(row).find(".supplementation").val();

                if (!quantity || !result || !supplementation) {
                    valid = false;
                    return false; // 반복문 중지
                }

                item.completedQuantity = quantity;
                item.progCheckResult = result;
                item.supplementation = supplementation;
            }

            dataItems.push(item);
        });

        if (!valid) {
            alert("모든 필드를 입력하세요.");
            return;
        }

        var ajaxUrl = modalId === "modal1" ? "/progress_check_item/save?type=plan" : "/progress_check_item/save?type=process";

        $.ajax({
            url: ajaxUrl,
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(dataItems),
            success: function (response) {
                alert(response);
                $(`#${modalId}`).hide();
                window.location.href = "/progress_check_item/progress_check_item";
            },
            error: function () {
                alert("저장에 실패했습니다. 다시 시도해주세요.");
            }
        });
    });

    // 삭제 버튼 클릭 이벤트 위임
    $(document).on("click", ".delete-btn", function () {
        var modalId = $(this).closest(".modal").attr("id");
        if ($(`#${modalId} .modal-table-3 tbody tr`).length > 1) {
            $(this).closest("tr").remove();
            updateRowIndices(modalId);
            updateDeleteButtons(modalId);
        } else {
            alert("최소 하나의 행이 남아있어야 합니다.");
        }
    });

    // "검수계획 등록" 및 "검수처리 등록" 버튼 클릭 시 모달 열기
    openModal("plan-btn", "modal1");
    openModal("process-btn", "modal2");
});
