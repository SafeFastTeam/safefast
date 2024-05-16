package org.zerock.safefast.service.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.safefast.entity.CoOpCompany;
import org.zerock.safefast.entity.Contract;
import org.zerock.safefast.entity.Item;
import org.zerock.safefast.repository.CoOpCompanyRepository;
import org.zerock.safefast.repository.ContractRepository;
import org.zerock.safefast.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ItemRepository itemRepository;
    private final ContractRepository contractRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Contract> getAllContractsWithDetails() {
        List<Contract> contracts = contractRepository.findAll();
        for (Contract contract : contracts) {
            contract.getCoOpCompany(); // CoOpCompany 정보 로딩
            contract.getItem(); // Item 정보 로딩
        }
        return contracts;
    }

}