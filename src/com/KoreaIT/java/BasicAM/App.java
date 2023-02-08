package com.KoreaIT.java.BasicAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.BasicAM.controller.ArticleController;
import com.KoreaIT.java.BasicAM.controller.MemberController;
import com.KoreaIT.java.BasicAM.dto.Article;
import com.KoreaIT.java.BasicAM.dto.Member;

public class App {
	
	public static List<Article> articles;
	public static List<Member> members;

	static {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void run() {
		System.out.println("==프로그램 시작==");
		Scanner sc = new Scanner(System.in);
		MemberController memberController = new MemberController(members, sc);
		ArticleController articleController = new ArticleController(articles, sc);
		articleController.makeTestData();
		while (true) {

			System.out.printf("명령어 ) ");
			String command = sc.nextLine().trim();

			if (command.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}

			if (command.equals("system exit")) {
				break;
			}

			if (command.equals("member join")) {

				memberController.doJoin();

			} else if (command.equals("article list")) {
				articleController.showList();
				
			} else if (command.equals("article write")) {
				articleController.articleWrite();
				
			} else if (command.startsWith("article detail ")) {
				articleController.articleDetail(command);
				  
				

			} else if (command.startsWith("article modify ")) {
				articleController.aricleModify(command);
				

			} else if (command.startsWith("article delete ")) {
				articleController.articleDelete(command);
				

			}

			else {
				System.out.println("존재하지 않는 명령어입니다");
			}

		}

		System.out.println("==프로그램 끝==");

		sc.close();

	}

}
