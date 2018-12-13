package com.biz.bank.exec;

import com.biz.bank.service.BankService;

public class BankExec01 {
	
	public static void main(String[] args) {
		String strFileName = "src/com/biz/bank/bankBalance.txt";
		BankService bs = new BankService(strFileName);
		
		bs.readFile();
		bs.findId("03");
		bs.bankInput();
		
		
	}

}
