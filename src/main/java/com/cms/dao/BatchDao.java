package com.cms.dao;

import java.util.List;

import com.cms.Course_Monitoring_System.Batch;
import com.cms.exception.BatchException;

public interface BatchDao {

	// checking course id present or not
	public boolean isCourseIdPresent(int courseId) throws BatchException;

	// checking faculty id present or not
	public boolean isFacultyIdPresent(int facultyId) throws BatchException;

	// checking batch name previously present or not
	public boolean isBatchNameUnique(String name) throws BatchException;

	// create batch
	public String createBatch(Batch batch) throws BatchException;

	// update batch by name
	public String upadteBatchByName(String old_name, Batch batch) throws BatchException;

	// view all batch details
	public List<Batch> viewAllBatchDetails() throws BatchException;

	// delete batch by name
	public String batchDeleteByName(String name) throws BatchException;

	//all batches
	public List<Batch> availableBatch() throws BatchException;
	
	// Get batch by ID
    Batch getBatchById(int batchId) throws BatchException;

}
