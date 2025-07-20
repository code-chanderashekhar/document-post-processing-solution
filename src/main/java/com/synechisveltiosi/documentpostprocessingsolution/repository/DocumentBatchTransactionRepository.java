package com.synechisveltiosi.documentpostprocessingsolution.repository;

import com.synechisveltiosi.documentpostprocessingsolution.model.entity.DocumentBatchTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentBatchTransactionRepository extends JpaRepository<DocumentBatchTransaction, UUID> {
}