package com.matrix.sxt.e16;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

// -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC
// -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC
// -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC
// -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC
//-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC
public class GcSerialDemo {
	public static void main(String[] args) {
		int i = 0;
		List<String> list = new ArrayList<String>();
		try {
			while(true) {
				list.add(String.valueOf(++ i).intern());
			}
		} catch (Throwable e) { // Exception和Error的上层接口
			e.printStackTrace();
		}
	}
	
	
	public static void aiXin(){
		heart(18, 0.5, "♥", "♥", "宝芬");
		
//		IntStream.range(-15, 15).map(y -> -y).forEach(y -> IntStream.range(-30, 30).forEach(x -> System.out.print(Math.pow(Math.pow((x * 0.05), 2) + Math.pow((y * 0.1), 2) - 1, 3) - Math.pow(x * 0.05, 2) * Math.pow(y * 0.1, 3) <= 0 ? "love".charAt(Math.abs((y - x) % 4)) : " " + (x == 29 ? "\n" : ""))));
	}
	
	/**
     * 使用公式
     *((0.05*x)^2 + (0.1*y)^2-1)^3-(0.05*x)^2 * (0.1*y)^3 < = 0
     * @param r   半径
     * @param size  大小 0-1
     * @param left  左边显示
     * @param right 右边显示
     * @param center 中间显示
     */
    private static void heart(int r,double size,String left,String right,String center){
        size=1/(1.5*r*size);
        StringBuilder sb=new StringBuilder();
        for (int y = r; y > -r; y--,sb.append("\n"))
            for (int x = -2*r; x < 2*r; x++ ) {
                String req=center;
                if(x<0) 
                	req=left;
                else if(x>0) 
                	req=right;
                char msg=(req + req).charAt((x - y) % req.length() + req.length());
                sb.append((Math.pow(Math.pow(x * size, 2) + Math.pow(y * 2*size, 2) - 1, 3) - Math.pow(x * size, 2) * Math.pow(y * 2*size, 3) <= 0 ?msg + " " : "  "));
            }
        
        System.out.println(sb.toString());
    }
}













