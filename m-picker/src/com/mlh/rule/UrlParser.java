package com.mlh.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlParser {

	public static List<String> getMultiUrl(String url) {
		List<String> urlList = new ArrayList<String>();
		if (url.contains("[") && url.contains("]")) {
			fullUrlList(url, urlList);
		} else {
			urlList.add(url);
		}
		return urlList;
	}

	private static void fullUrlList(String url, List<String> urlList) {
		Pattern p = Pattern.compile("\\[\\d*-?\\d*\\]|\\[(\\w,?){1,}\\]");
		Matcher m = p.matcher(url);
		if (m.find()) {
			String group = m.group();
			String newParams = url.replace(group, "");
			int indexOf = url.indexOf(group);
			if (group.contains("-")) {
				String[] minMax = group.replace("[", "").replace("]", "")
						.split("-");
				if (minMax.length == 1) {
					throw new RuntimeException(minMax + "【系统暂不支持无穷大】");
				} else if (minMax.length == 2) {
					int min = Integer.parseInt(minMax[0]);
					int max = Integer.parseInt(minMax[1]);
					for (int i = min; i <= max; i++) {
						StringBuilder builder = new StringBuilder(newParams);
						builder.insert(indexOf, i);
						if (!builder.toString().contains("[")
								&& !builder.toString().contains("]")) {
							urlList.add(builder.toString());
						}
						fullUrlList(builder.toString(), urlList);
					}
				} else {
					throw new RuntimeException(minMax + "【语法错误】");
				}
			} else {
				String[] numbs = group.replace("[", "").replace("]", "")
						.split(",");
				for (int i = 0; i < numbs.length; i++) {
					StringBuilder builder = new StringBuilder(newParams);
					builder.insert(indexOf, numbs[i]);
					if (!builder.toString().contains("[")
							&& !builder.toString().contains("]")) {
						urlList.add(builder.toString());
					}
					fullUrlList(builder.toString(), urlList);
				}
			}
		}
	}

	public static void main(String[] args) {
		List<String> list = UrlParser
				.getMultiUrl("www.baidu.com/[1-8]?params=[1-10]&param2=[a,b,c,5,7,h]");
		for (String url : list) {
			System.out.println(url);
		}
	}

}
