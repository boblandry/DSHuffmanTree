package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;

import entity.HuffmanCode;
import entity.HuffmanNode;

@SuppressWarnings("unused")
public class HuffmanEncodeAndDecode {

	public static ArrayList<HuffmanNode> read() {
		Scanner scan = new Scanner(System.in);
		int size = scan.nextInt();
		ArrayList<HuffmanNode> hn_list = new ArrayList<HuffmanNode>();
		for (int i = 0; i < size; i++) {
			String s = scan.next();
			int weight = scan.nextInt();
			HuffmanNode hn = new HuffmanNode(s, weight);
			hn_list.add(hn);
		}
		scan.close();
		if (size != hn_list.size()) {
			System.out.println("�������");
			System.exit(0);
		}
		for (int i = 0; i < hn_list.size(); i++) {
			for (int j = hn_list.size() - 1; j > i; j--) {
				String s1 = hn_list.get(i).name;
				String s2 = hn_list.get(j).name;
				if (s1 == s2 || s1.equals(s2)) {
					System.out.println("�������");
					System.exit(0);
				}
			}
		}

		return hn_list;
	}

	public static HuffmanNode CreateHuffman(ArrayList<HuffmanNode> hn_list) {

		HuffmanNode root = new HuffmanNode();
		// �����������
		root.createTree(hn_list);
		root = hn_list.get(0);
		// ���ݹ�����������ÿ���ַ��ı��� ����� code_list��
		getCode(root, "");

		return root;
	}

	
	//����
	public static void encode() {
		
		String data = FileOption.readFile("src/data/tobetrans");
		
		StringBuffer en_sb = new StringBuffer();
		int temp_length = en_sb.length();
		for(int i=0;i<data.length();i++){
			String s = String.valueOf(data.charAt(i));
			for(HuffmanCode hc : code_list){
				if(hc.getName().equals(s)){
					en_sb.append(hc.getCode());
				}
			}
			if(temp_length == en_sb.length()){
				System.out.println("����δƥ����:"+s);
				System.exit(0);
			}
		}
		
		FileOption.writeFile("src/data/codefile", en_sb);
		
		
	}

	//���� ���ز����Ǳ������ַ��� ������һ���Ĳ���
	public static StringBuffer decode(){
		
		String en_data = FileOption.readFile("src/data/codefile");
		StringBuffer sb = new StringBuffer();
		String code = "";
		for(int i=0;i<en_data.length();i++){
			
			code += String.valueOf(en_data.charAt(i));
			if(isMatch(code)){
				String plain = getChar(code);
				if(plain.equals("error")){
					System.out.println("�쳣");
					System.exit(0);
				}else{
					sb.append(plain);
				}
				code = "";
			}else{
				continue;
			}
			
		}
		
		FileOption.writeFile("src/data/textfile", sb);
		return sb;
	}
	
	//���Ĳ�
	public static void formatOutAndSave(StringBuffer sb){
		
		String en_data = sb.toString();
		StringBuffer format = new StringBuffer();
		for(int i=0;i<en_data.length();i++){
			if(i % 50 ==0 && i != 0){
				format.append("\r\n");
			}
			format.append(en_data.charAt(i));
		}
		System.out.println(format.toString());
		FileOption.writeFile("src/data/codeprint", format);
		
	}
	
	
	private static boolean isMatch(String code){
		for(HuffmanCode hc : code_list){
			if(hc.getCode().equals(code))
				return true;
		}
		return false;
	}
	
	private static String getChar(String code){
		for(HuffmanCode hc : code_list){
			if(hc.getCode().equals(code))
				return hc.getName();
		}
		return "error";
	}
	
	
	// ��������ַ�������ͳ�� ����list��
	private static ArrayList<HuffmanNode> getList(String str) {
		ArrayList<HuffmanNode> list = new ArrayList<HuffmanNode>();
		for (int i = 0; i < str.length(); i++) {
			String temp = String.valueOf(str.charAt(i));
			if (list.size() == 0) {
				HuffmanNode node = new HuffmanNode(temp, 1);
				list.add(node);
			} else {
				boolean flag = false;
				for (int j = 0; j < list.size(); j++) {
					if (temp.equals(list.get(j).name)) {
						list.get(j).num += 1;
						flag = true;
					}
				}
				if (!flag) {
					HuffmanNode node = new HuffmanNode(temp, 1);
					list.add(node);
				}

			}

		}

		return list;
	}

	// ���ݹ����������ɱ����
	private static void getCode(HuffmanNode node, String now_code) {
		if (node == null) {
			return;
		} else {
			if (node.name != null && !node.name.equals("")) {
				HuffmanCode code = new HuffmanCode();
				code.setName(node.name);
				code.setCode(now_code);
				code.setNum(node.num);
				code_list.add(code);
			}
			getCode(node.left, now_code + "0");
			getCode(node.right, now_code + "1");
		}
	}

	// ����ƽ���볤
	private static double getAvg_code_length(int str_length) {
		double avg_code_length = 0;

		for (HuffmanCode code : code_list) {
			avg_code_length += code.getNum() * (code.getCode().length());
		}
		avg_code_length /= str_length;

		return avg_code_length;
	}

	// ���� ��Դ��
	private static double getEntropy(int str_length) {
		double entropy = 0;

		for (HuffmanCode code : code_list) {
			double p = code.getNum() / (double) str_length;
			entropy += p * (Math.log(p) / Math.log(2.0));
		}
		entropy = -entropy;

		return entropy;
	}

	public static void saveToHfmtree(HuffmanNode hn) {
		String preot = hn.preOrderTravel(hn);
		
		StringBuffer sb = new StringBuffer();
		sb.append("��������ǰ�������\r\n");
		sb.append(preot);
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("���ձ�\r\n");
		for (int i = 0; i < code_list.size(); i++) {
			sb.append(code_list.get(i).getName() + ":" + code_list.get(i).getCode() + "\r\n");
		}
		
		FileOption.writeFile("src/data/hfmtree", sb);
		
	}

	private static void treePrint(HuffmanNode hn,int space){
		if(hn != null){
			treePrint(hn.right,space+5);
			for(int i=0;i<space;i++){
				System.out.print(" ");
				treeprint.append(" ");
			}
			System.out.println(hn.name+"("+hn.num+")");
			treeprint.append(hn.name+"("+hn.num+")\r\n");
			treePrint(hn.left,space+5);
		}
	}
	
	public static void treePrintAndWrite(HuffmanNode hn){
		treePrint(hn, 0);
		FileOption.writeFile("src/data/treeprint", treeprint);
	}
	
	// ���ű�
	public static ArrayList<HuffmanCode> code_list = new ArrayList<HuffmanCode>();
	public static StringBuffer treeprint = new StringBuffer();
}
