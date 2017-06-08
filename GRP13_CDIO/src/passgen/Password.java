package passgen;

import java.util.Arrays;

public class Password {
	public String generatePassword(){
		int antalTal = (int) (Math.random() * 2) + 2;
		int antalStoreBog = (int) (Math.random() * 2) + 2;
		int antalSmåBog = 8- antalTal - antalStoreBog;
		
		String[] SB = new String[antalStoreBog];
		
		for(int i = 0; i < SB.length; i++){
			String StoreB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			char a = StoreB.charAt((int) (Math.random() * 27));
			SB[i] ="" + a; 
		}
		String[] SmB = new String[antalSmåBog];
		
		for(int i = 0; i < SmB.length; i++){
			String SmåB = "abcdefghijklmnopqrstuvwxyz";
			char b = SmåB.charAt((int) (Math.random() * 27));
			SmB[i] = "" + b;
		}
		String[] Tal = new String[antalTal];
		
		for(int i = 0; i < Tal.length; i++){
			String tal = "0123456789";
			char c = tal.charAt((int) (Math.random() * 10));
			Tal[i] = "" + c;
		}
		String[] SBSmB = new String[SB.length + SmB.length];
		for(int i =0;i<SBSmB.length;i++){
		    SBSmB[i] = (i<SB.length)?SB[i]:SmB[i-SB.length];
		}
		
		String[] all = new String[SBSmB.length + Tal.length];
		for(int i = 0; i < all.length; i++){
			all[i] = (i < SBSmB.length)?SBSmB[i]:Tal[i-SBSmB.length];
		}
		
		for (int i = 0; i < all.length; i++) {
			float f = (float) Math.random() * all.length;
			int a = (int) f;

			String temp = all[i];
			all[i] = all[a];
			all[a] = temp;
		}
		return Arrays.toString(all);
	}
}
