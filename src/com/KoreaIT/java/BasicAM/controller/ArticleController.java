package com.KoreaIT.java.BasicAM.controller;

import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.BasicAM.dto.Article;
import com.KoreaIT.java.BasicAM.util.Util;

public class ArticleController extends Controller {

	private List<Article> articles;
	private Scanner sc;
	private String command;
	private String actionMethodName;

	public ArticleController(List<Article> articles, Scanner sc) {
		this.articles = articles;
		this.sc = sc;
	}

	public void doAction(String command, String actionMethodName) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
			case "list" -> showList();
			case "write" -> doWrite();
			case "detail" -> showDetail(command);
			case "modify" -> doModify(command);
			case "delete" -> doDelete(command);
		}
	}



	public void showList() {
		if (articles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}
		System.out.println("번호    /      제목     /     조회    ");
		String tempTitle = null;
		for (int i = articles.size() - 1; i >= 0; i--) {
			Article article = articles.get(i);
			if (article.title.length() > 4) {
				tempTitle = article.title.substring(0, 4);
				System.out.printf("%4d	/    %6s    /   %4d\n", article.id, tempTitle + "...", article.hit);
				continue;
			}

			System.out.printf("%4d	/    %6s    /   %4d\n", article.id, article.title, article.hit);
		}

	}

	public void doWrite() {
		int id = articles.size() + 1;
		String regDate = Util.getNowDateStr();
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		Article article = new Article(id, regDate, regDate, title, body);
		articles.add(article);

		System.out.printf("%d번 글이 생성 되었습니다\n", id);

	}

	public void showDetail(String command) {
		String[] commandBits = command.split(" ");

		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}

		foundArticle.increaseHit();
		System.out.printf("번호 : %d\n", foundArticle.id);
		System.out.printf("작성날짜 : %s\n", foundArticle.regDate);
		System.out.printf("수정날짜 : %s\n", foundArticle.updateDate);
		System.out.printf("제목 : %s\n", foundArticle.title);
		System.out.printf("내용 : %s\n", foundArticle.body);
		System.out.printf("조회 : %d\n", foundArticle.hit);

	}

	public void doModify(String command) {
		String[] commandBits = command.split(" ");

		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}

		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();
		String updateDate = Util.getNowDateStr();

		foundArticle.title = title;
		foundArticle.body = body;
		foundArticle.updateDate = updateDate;

		System.out.printf("%d번 게시물을 수정했습니다\n", id);

	}

	public void doDelete(String command) {
		String[] commandBits = command.split(" ");

		int id = Integer.parseInt(commandBits[2]);

		int foundIndex = getArticleIndexById(id);

		if (foundIndex == -1) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}
		articles.remove(foundIndex);
		System.out.printf("%d번 게시물을 삭제했습니다\n", id);

	}

	public int getArticleIndexById(int id) {
		int i = 0;
		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public Article getArticleById(int id) {

		int index = getArticleIndexById(id);

		if (index != -1) {
			return articles.get(index);
		}

		return null;
	}

}
