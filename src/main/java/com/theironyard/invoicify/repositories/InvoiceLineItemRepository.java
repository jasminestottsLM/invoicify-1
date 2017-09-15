package com.theironyard.invoicify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theironyard.invoicify.models.BillingRecord;
import com.theironyard.invoicify.models.InvoiceLineItem;

public interface InvoiceLineItemRepository extends JpaRepository<InvoiceLineItem, Long> {

	InvoiceLineItem findByInvoiceId(Long id);

	

}
