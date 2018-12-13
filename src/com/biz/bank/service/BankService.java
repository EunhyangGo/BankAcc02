package com.biz.bank.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.biz.bank.vo.BankVO;

public class BankService {
	List<BankVO> bankList;
	String strFileName;

	public BankService(String strFileName) {
		bankList = new ArrayList();
		this.strFileName = strFileName;
	}
	
	public void bankInput() {
		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("계좌번호를 입력하세요>> ");
		String strId = sc.nextLine();
		BankVO b = this.findId(strId); // findId에서  되돌아온 (return) 값을 b가 받은것이다.
		if(b == null) {
			System.out.println("계좌번호 없음");
			return;
		}
		
		int iB = b.getIntBalance();
		System.out.print("입금액 >>");
		String strB = sc.nextLine();
		int intB = Integer.valueOf(strB);
		
		int lB = iB + intB;
		b.setIntBalance(lB);
		
		System.out.println("입금완료");
	}
	
	public BankVO findId(String strId) {

		BankVO vo = null;
	 for(BankVO bk : bankList) {
		 if(bk.getStrId().equals(strId)) {
			 vo = bk;
			 return bk;			
	 } 
		//계좌번호가 003이면 콘솔에 비추게 하는것 
		// System.out.println(bk.toString());
		// System.out.print(bk.getStrId()+"\t");
		// System.out.print(bk.getIntBalance()+"\t");
		// System.out.println(bk.getStrLastDate()); 		
	 } 
	 
		 return null ; //다 찾고 나서 아무것도 못찾았다고 알려주는 신호
		 
}
	public void readFile() {
		FileReader fr;
		BufferedReader buffer;
		
		try {
			fr = new FileReader(strFileName);
			buffer = new BufferedReader(fr);
			
			while(true) {
				String strLine = buffer.readLine();
				if(strLine == null) break;
				String[] st = strLine.split(":");
				//System.out.println(st[0]+"-"+st[1]+"-"+st[2]);
				System.out.printf("%s-%s-%s\n",st[0],st[1],st[2]);
				BankVO vo = new BankVO();
				vo.setStrId(st[0]);
				vo.setIntBalance(Integer.valueOf(st[1]));
				vo.setStrLastDate(st[2]);
				bankList.add(vo);
				
				
			}
			buffer.close();
			fr.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
