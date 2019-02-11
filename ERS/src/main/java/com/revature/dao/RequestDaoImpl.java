package com.revature.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.data.DataSource;
import com.revature.exception.RequestNotFoundException;
import com.revature.models.Request;

public class RequestDaoImpl implements RequestDao {

	final static Logger log = Logger.getLogger(EmployeeDaoImpl.class);
	private final DataSource dataSource = DataSource.getInstance();
	
	// SELECT * FROM TODOS;
	@Override
	public List<Request> getAllRequests() {
		return dataSource.getRequestTable();
	}
	
	public Request getRequestById(int id) {
		return dataSource.getRequestTable().stream()
				.filter(todo -> todo.getId() == id)
				.findFirst().orElseThrow(() -> new RequestNotFoundException(id));
	}
	
	@Override
	public List<Request> getRequestsPendingById(int id) {
		List<Request> pendingRequests = new ArrayList<Request>();
		for (int i = 0; i < dataSource.getRequestTable().size(); i++) {
			if (dataSource.getRequestTable().get(i).getId() == id) {
				if (dataSource.getRequestTable().get(i).getPending() == 0) {
					pendingRequests.add(getRequestById(i));
				}
			else{
					throw new RequestNotFoundException(id);
				}
			}
		}
	return pendingRequests;
	}

	@Override
	public List<Request> getRequestsCompleteById(int id) {
		List<Request> completedRequests = new ArrayList<Request>();
		for (int i = 0; i < dataSource.getRequestTable().size(); i++) {
			if (dataSource.getRequestTable().get(i).getId() == id) {
				if (dataSource.getRequestTable().get(i).getPending() == 1) {
					completedRequests.add(getRequestById(i));
				}
			else{
					throw new RequestNotFoundException(id);
				}
			}
		}
	return completedRequests;
	}

	@Override
	public List<Request> getAllRequestsPending() {
		List<Request> pendingRequests = new ArrayList<Request>();
		for (int i = 0; i < dataSource.getRequestTable().size(); i++) {
			if (dataSource.getRequestTable().get(i).getPending() == 1) {
					pendingRequests.add(getRequestById(i));
				}
			}
		return pendingRequests;
	}

	@Override
	public List<Request> getAllRequestsComplete() {
		List<Request> completeRequests = new ArrayList<Request>();
		for (int i = 0; i < dataSource.getRequestTable().size(); i++) {
			if (dataSource.getRequestTable().get(i).getPending() == 1) {
					completeRequests.add(getRequestById(i));
				}
			}
		return completeRequests;
	}
	
	@Override
	public Request createRequest(Request request) {
		if (dataSource.getRequestTable().add(request)) {
			return request;
		}
		return null;
	}

	@Override
	public Request updateRequest(Request request) {
		// Get a reference to existing request
		Request updated = getRequestById(request.getId());
		
		// Update the request's state
		updated.setApproved(request.getApproved());
		updated.setApproveBy(request.getApproveBy());
		
		// Store the updated version
		for (int i = 0; i < dataSource.getRequestTable().size(); i++) {
			if (dataSource.getRequestTable().get(i).getId() == request.getId()) {
				dataSource.getRequestTable().set(i, updated);
				return updated;
			}
		}
		return null;
	}
}
