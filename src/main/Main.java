package main;

import java.util.ArrayList;

import entity.HuffmanNode;
import util.HuffmanEncodeAndDecode;

public class Main {

	public static void main(String[] args) {
		
		
		ArrayList<HuffmanNode> hn_list = HuffmanEncodeAndDecode.read();
		HuffmanNode hn = HuffmanEncodeAndDecode.CreateHuffman(hn_list);
		HuffmanEncodeAndDecode.saveToHfmtree(hn);
		HuffmanEncodeAndDecode.encode();
		StringBuffer en_data = HuffmanEncodeAndDecode.decode();
		HuffmanEncodeAndDecode.formatOutAndSave(en_data);
		HuffmanEncodeAndDecode.treePrintAndWrite(hn);
		
	}
	
}
