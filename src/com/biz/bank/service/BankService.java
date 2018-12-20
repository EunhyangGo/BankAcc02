package com.biz.bank.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.biz.bank.vo.BankVO;

/*
 * BankService 클래스 핵심부분
 * 1. findId() : bankList에서 계좌번호를 조회하는 부분
 * 가. 매개변수로 strId(String)값을 받고
 * 나. bankList를 순회(반복)하면서
 * 다. bankList에 들어있는 vo의 strId값을 추출해서
 *    (bankList.get(i).getStrId 또는 vo.getStrId() 이용)
 * 라. 매개변수로 받은 strId와 일치하는 값이 있는지 검사를 한다.
 *    if(vo.getStrId().equals(strId)), if(strId.equals(vo.getStrid())
 *    
 * 마. 만약 bankList에 찾고자 하는 id가 없을 경우
 *     if(vo.getStrId().equals(strId) ==false)
 *    또는 if(vo.getStrId().equals(strId) !=true)
 *    보편적 코드에서는  if(!vo.getStrId().equals(strId)) 라고 사용한다.
 *    
 * 바. findId()는 null을 return해서 값이 없음을 알리고 
 * 사. 만약 bankList에 찾고자 하는 id가 있으면 
 * 아. findId()는 찾은 vo를 return해준다.
 */
public class BankService {
	List<BankVO> bankList;
	String strFileName;
	String ioFolder ;
	Scanner sc;

	// 맴버변수 영역에 있는 변수, 객체는 생성자에서 초기화를 한다.
	public BankService(String strFileName) {
	    
		this.bankList = new ArrayList();
		this.strFileName = strFileName;
		
		this.ioFolder = "src/com/biz/bank/iolist/";
		
		this.sc = new Scanner(System.in);
	}
	
	// 원정을 update
	public void bankBalanceWrite() {
		PrintWriter pw;
		try {
			pw = new PrintWriter(strFileName);
			for(BankVO vo : bankList) {
				pw.println(
						vo.getStrId() + ":"
						+ vo.getIntBalance() + ":"
						+ vo.getStrLastDate());
			}
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	// 입출금 내역을 저장하는 method
	public void bankIoWrite(String io, int intB, BankVO v) {
		
		FileWriter fw;
		PrintWriter pw;
		String thisId = v.getStrId(); // 계좌번호
		
		try {
			
			// 2번째 매개변수 true : 파일을 Append Mode로 열어라.
			// 기존의 파일이 있으면 추가하는 상태로 열고 없으면 만들어라.
			// append모드로 사용하려면 프린트라이터, 파일라이터 둘다 함께 쓰여야 한다.
			
			fw = new FileWriter(ioFolder + thisId,true);
			pw = new PrintWriter(fw);
			if(io.equals("I")) { 
			pw.printf("%s:%d:%d:%d:%s\n", 
					v.getStrLastDate(),
					"입금", 
					intB,
					0,
					v.getIntBalance(), 
					v.getStrLastDate());
			}else {
				pw.printf("%s:%s:%d:%d:%d:%s\n",
						v.getStrLastDate(),
						"출금",
						0,
						intB,
						v.getIntBalance(),
						v.getStrLastDate());
			}
			pw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void bankMenu() {
		this.readFile();
		while(true) {
			System.out.println("========================================");
			System.out.println("1.입금\t 2.출금\t 3.계좌조희\t 0.종료");
			System.out.println("-----------------------------------------");
			System.out.println("업무선택 >>");
			String strSelect = sc.nextLine();
			int intSelect = Integer.valueOf(strSelect);
			if(intSelect == 0) {
				System.out.println("은행서비스를 종료합니다.");
				break;
			}
			if(intSelect == 1) {
				System.out.println("입금을 시작합니다");
				this.bankInput();
			}
			if(intSelect == 2) {
				System.out.println("출금을 시작합니다.");
				this.bankOutput();
			}
		}
	}
	public void bankInput() {
		
		// 생성자에서 초기화 했으므로 할 필요 없음.
		//Scanner sc = new Scanner(System.in);
		System.out.println("입금 계좌번호를 입력하세요>> ");
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
		this.bankBalanceWrite();
		this.bankIoWrite("I",intB,b);
	}
	
	public void bankOutput() {
		//Scanner sc = new Scanner(System.in);
		System.out.println("출금 계좌번호를 입력하세요 >> ");
		String strId = sc.nextLine();
		BankVO b = this.findId(strId);
		if(b.getStrId().equals(strId)) {
			System.out.println("계좌번호가 있음");
		}
		if(b == null) {
			System.out.println("계좌번호가 없음");
			return;
		}
		int ib = b.getIntBalance();
		System.out.println("출금액 >>");
		String strB = sc.nextLine();
		int intB = Integer.valueOf(strB);
		int lb = ib - intB;
		b.setIntBalance(ib);
		if(ib < intB) {
			System.out.println("잔액 부족 출금 불가");
		}
		System.out.println("출금 완료 ");
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
