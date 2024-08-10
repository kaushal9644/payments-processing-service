package com.cpt.payments.dao.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.entity.TransactionEntity;
import com.cpt.payments.exception.ProcessingServiceException;

@Component
public class TransactionDaoImpl implements TransactionDao {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public TransactionDTO createTransaction(TransactionDTO txnDTO) {
		// TODO Auto-generated method stub

		System.out.println("*****namedParameterJdbcTemplate:" + namedParameterJdbcTemplate);

		System.out.println("TransactionDaoImpl.createTransaction()"
				+ "| received txnDTO:" + txnDTO);

		TransactionEntity txnEntity = modelMapper.map(
				txnDTO, TransactionEntity.class);

		System.out.println("Converted Entity:" + txnEntity);

		String sql = "INSERT INTO `Transaction` (userId, paymentMethodId, providerId, paymentTypeId, txnStatusId, amount, currency, merchantTransactionReference, txnReference, providerReference, providerCode, providerMessage, retryCount) " +
				"VALUES (:userId, :paymentMethodId, :providerId, :paymentTypeId, :txnStatusId, :amount, :currency, :merchantTransactionReference, :txnReference, :providerReference, :providerCode, :providerMessage, :retryCount)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		try {
			
			int insertedRowCount = namedParameterJdbcTemplate.update(
					sql, 
					new BeanPropertySqlParameterSource(txnEntity),
					keyHolder, 
					new String[] { "id" });

			int transactionId = keyHolder.getKey().intValue();

			txnDTO.setId(transactionId);

			System.out.println("Inserted value in DB count insertedRowCount:" + insertedRowCount + ""
					+ "|transactionId:" + transactionId);

			return txnDTO;

		} catch (DuplicateKeyException ex) {
			System.out.println("Unable to save txn in DB, DUplicate txnReference" + txnDTO.getTxnReference());
			ex.printStackTrace();

			throw new ProcessingServiceException(
					ErrorCodeEnum.DUPLICATE_TXN_REF.getErrorCode(), 
					ErrorCodeEnum.DUPLICATE_TXN_REF.getErrorMessage(),
					HttpStatus.BAD_REQUEST);

		}


	}

}
