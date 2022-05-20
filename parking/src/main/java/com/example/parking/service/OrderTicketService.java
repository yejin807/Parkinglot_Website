package com.example.parking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.parking.model.OrderTicket;
import com.example.parking.model.Ticket;
import com.example.parking.repository.MemberRepository;
import com.example.parking.repository.OrderTicketRepository;
import com.example.parking.repository.TicketRepository;

@Service
@Transactional(readOnly = true)
public class OrderTicketService {
	
	@Autowired
	private MemberRepository mRepository;
	
	@Autowired
	private TicketRepository tRepository;
	
	@Autowired
	private OrderTicketRepository oRepository;
	
	@Transactional
	public void insert(Long parkinglotId, String username, OrderTicket orderticket) {
		Ticket t = tRepository.findByParkinglotId(parkinglotId);
		if(orderticket.getTicketType()==1) {
			if(t.getDayStock()==0) return; //재고가 없음
			t.setDayStock(t.getDayStock()-1);
		} else if(orderticket.getTicketType()==2) {
			if(t.getWeekStock()==0) return; //재고가 없음
			t.setWeekStock(t.getWeekStock()-1);
		} else if(orderticket.getTicketType()==3) {
			if(t.getMonthStock()==0) return; //재고가 없음
			t.setMonthStock(t.getMonthStock()-1);
		}
		
		orderticket.setMember(mRepository.findById(username).get());
		orderticket.setParkLot(t.getParkLot());
		oRepository.save(orderticket);
	}

	//티켓구매리스트(전체)
	public List<OrderTicket> list() {
		return oRepository.findAll();
	}
	
	//티켓구매리스트(일반)
	public List<OrderTicket> list(String username) {
		return oRepository.findByMemberId(username);
	}
	
	//구매티켓 개별조회
	public OrderTicket findById(Long ticketId) {
		return oRepository.findById(ticketId).get();
	}	

	//해당주차장 구매티켓조회
	public Ticket findByParkinglotId(Long id) {
		return tRepository.findByParkinglotId(id);
	}

	@Transactional
	public void update(OrderTicket orderticket) {
		OrderTicket o = oRepository.findById(orderticket.getTicketId()).get();
		o.setTicketType(orderticket.getTicketType());
		o.setBuyDate(orderticket.getBuyDate());
		o.setEndDate(orderticket.getEndDate());
	}
	
	@Transactional
	public void delete(Long ticketId) {
		oRepository.deleteById(ticketId);		
	}

	
}