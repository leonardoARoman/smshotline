package com.lroman.demo.controller;

import io.grpc.casinoserviceapi.CasinoNetworkServiceGrpc;
import io.grpc.casinoserviceapi.Employee;
import io.grpc.casinoserviceapi.Employee.ABSENCE;
import io.grpc.casinoserviceapi.Employee.SHIFT;
import javafx.application.Platform;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Component;

import io.grpc.*;
import io.grpc.stub.StreamObserver;

@Component
public class DataBaseHandler implements SmsService{

	@Override
	public Object findWorker(String phoneNumber, String request) {
		// TODO connect to data base to find worker given a phone number
		Employee newEmployee = null;
		if(phoneNumber.equals("17323742773")) {
			if(request.equals("sick")) {
				newEmployee = Employee.newBuilder()
						.setEName("Leonardo")
						.setEId(1)
						.setEShift(SHIFT.DAY)
						.setEStatus(ABSENCE.SICK)
						.build();
			}else if(request.equals("late")){
				newEmployee = Employee.newBuilder()
						.setEName("Leonardo")
						.setEId(1)
						.setEShift(SHIFT.DAY)
						.setEStatus(ABSENCE.LATE)
						.build();
			}else {
				newEmployee = Employee.newBuilder().build();
			}
		}
		return newEmployee;
	}

	@Override
	public void processWorkerRequest(Object worker) {
		System.out.println("processWorkerRequest was called!");
		// TODO Auto-generated method stub
		Employee employee = Employee.newBuilder()
				.setEName("Leonardo")
				.setEId(1)
				.setEShift(SHIFT.DAY)
				.setEStatus(ABSENCE.SICK)
				.build();
		String str = "";
		try {
			str = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		CasinoNetworkServiceGrpc.CasinoNetworkServiceStub newStub = CasinoNetworkServiceGrpc
				.newStub(ManagedChannelBuilder
						.forAddress(str, 8081)
						.usePlaintext()
						.build());

		newStub.processEmployeeRequest(new StreamObserver<Employee>() {

			@Override
			public void onNext(Employee arg0) {
				// TODO Auto-generated method stub
				Platform.runLater(()->{
					System.out.println("a called back!");
				});
			}

			@Override
			public void onError(Throwable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub

			}
		}).onNext(employee);
	}

}
