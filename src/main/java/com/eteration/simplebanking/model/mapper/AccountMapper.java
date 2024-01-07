package com.eteration.simplebanking.model.mapper;

import com.eteration.simplebanking.model.dto.AccountDTO;
import com.eteration.simplebanking.model.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDTO accountToDto(AccountEntity entity);
}