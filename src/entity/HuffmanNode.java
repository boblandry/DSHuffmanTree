package entity;

import java.util.ArrayList;

/**
 * @author LichKing
 * @description ���������ڵ���
 * @param name �ڵ����� ���÷���
 * @param num �÷��ų��ֹ��Ĵ���
 * @param left,right ���ҽڵ�
 */
public class HuffmanNode {

	public String name;
	public int num;
	public HuffmanNode left;
	public HuffmanNode right;
	
	//���ֹ��췽��
	public HuffmanNode(String name,int num){
		this.name = name;
		this.num = num;
	}
	
	public HuffmanNode(){
		this.name = "";
		this.num = 0;
	}
	
	//Ϊroot�ڵ�������ҽڵ� left right
	private void add(HuffmanNode root,HuffmanNode left,HuffmanNode right){
		root.left = left;
		root.right = right;
		root.num = left.num + right.num;
	}
	
	//�����������
	public HuffmanNode createTree(ArrayList<HuffmanNode> list){
		HuffmanNode root = new HuffmanNode();
		while(list.size()>1){
			sortTwice(list);
			HuffmanNode node = new HuffmanNode();
			add(node,list.get(0),list.get(1));
			list.remove(0);
			list.remove(0);
			list.add(node);
		}
		
		return root;
	}
	
	//�Խڵ�list��������ð������ �ǵ���С�������ڵ������list��ǰ����
	private void sortTwice(ArrayList<HuffmanNode> list){
		int size = list.size();
		for(int i=0;i<2;i++){
			for(int j=size-1;j>i;j--){
				if(list.get(j).num<list.get(j-1).num){
					String name = list.get(j).name;
					int num = list.get(j).num;
					HuffmanNode left = list.get(j).left;
					HuffmanNode right = list.get(j).right;
					list.get(j).name = list.get(j-1).name;
					list.get(j).num = list.get(j-1).num;
					list.get(j).left = list.get(j-1).left;
					list.get(j).right = list.get(j-1).right;
					list.get(j-1).name = name;
					list.get(j-1).num = num;
					list.get(j-1).left = left;
					list.get(j-1).right = right;
				}
			}
		}
	}
	
	//ǰ�����
	public String preOrderTravel(HuffmanNode hn){
		if(hn == null || hn.name == null || hn.equals(null)){
			return "";
		}else if(hn.name == ""){
			return "�սڵ�(" + hn.num + ")   " + preOrderTravel(hn.left) + preOrderTravel(hn.right);
		}else{
			return hn.name + "(" + hn.num + ")	" + preOrderTravel(hn.left) + preOrderTravel(hn.right);
		}
	}
	
}
