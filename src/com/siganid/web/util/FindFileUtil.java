package com.siganid.web.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class FindFileUtil {
	static List<File> allFile = new ArrayList<File>();

	public static void getAllFile(String projectPath, String endString) {
		File file = new File(projectPath);
		File[] files = file.listFiles(new FilenameFilter() {
			public boolean accept(File arg0, String arg1) {
				if (arg0.isDirectory()) {
					return true;
				}
				return false;
			}
		});
		File result = null;
		if (files == null) {
			return;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				getAllFile(files[i].getAbsolutePath(), endString);
			} else if (files[i].getAbsolutePath().endsWith(endString)) {
				allFile.add(files[i]);
			}
		}
	}

	public static List<File> getAllFile() {
		return allFile;
	}

	public static void setAllFile(List<File> allFile) {
		FindFileUtil.allFile = allFile;
	}

}
