package com.eteration.simplebanking.model.mapper;

import com.eteration.simplebanking.model.dto.TransactionDTO;
import com.eteration.simplebanking.model.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDTO transactionToDto(TransactionEntity entity);
    @Mapping(target = "account", ignore = true)
    TransactionEntity dtoToTransaction(TransactionDTO transactionDetailDTO);
}