package com.biz.bank.exec;

import java.util.Scanner;

import com.biz.bank.service.BankService;

public class BankExec01 {
	
	public static void main(String[] args) {
		String strFileName = "src/com/biz/bank/bankBalance.txt";
		BankService bs = new BankService(strFileName);
		
		bs.readFile();
	
		Scanner sc = new Scanner(System.in);
		
		
		
	}

}
