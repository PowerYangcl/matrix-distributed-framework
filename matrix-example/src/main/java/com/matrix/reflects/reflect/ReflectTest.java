package com.matrix.reflects.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

public class ReflectTest {
	
	//要是是数组就进行拆分,否则就进行可变参数

	public static void main(String[] args) throws Exception{
	}
	
	public static void print(int[] a){
		for(int i=0;i<a.length;i++)
			System.out.println(a[i]);
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unused" })
	public static void example1() throws Exception{
		/**获取String对象指定的构造方法(通过方法的参数类型,传递参数的Class对象)*/
		Constructor constructor = String.class.getConstructor(StringBuffer.class);//得到String对象的一个参数是StringBuffer的构造方法
		String str = (String) constructor.newInstance(new StringBuffer("abc"));//生成对象String，当然要传递一个StringBuffer参数
		System.out.println(str);//打印值
		/**总结:这种方法是要传递参数类型和参数的值，getConstructor(...)方法的参数是一个可变参数，因为构造方法可能有多个参数*/
		
		/**获取String默认的构造方法生成String对象*/
		String str1 = String.class.newInstance();
		
		/**获取String对象的所有构造方法,并将构造方法的参数类型打印*/
		Constructor[] constructors = Class.forName("java.lang.String").getConstructors();
		for(int i=0;i<constructors.length;i++){
			Type[] type = constructors[i].getGenericParameterTypes();
			for(int j=0;j<type.length;j++)
				System.out.print(type[j]+",");
			System.out.println();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void example2() throws Exception{
		String str1 = "abc";//字符串1
		String str2 = "abcd";//字符串2
		//三种方式获取Class类型
		Class cls1 = str1.getClass();
		Class cls2 = str2.getClass();
		Class cls3 = String.class;
		Class cls4 = Class.forName("java.lang.String");
		System.out.println(cls1);//打印字符串1类型
		System.out.println(cls2);//打印字符串2类型
		System.out.println(cls3);
		System.out.println(cls4);
		System.out.println(cls1 == cls2);//字符串1和字符串2的类型是否相等
		System.out.println(cls1 == cls3);
		
		//总之只要在源程序中出现的类型，都有各自的Class实例对象
		System.out.println(cls1.isPrimitive());//是不是原始类型(同样也有类似的方法判断是不是枚举，数组等类型)
		System.out.println(int.class.isPrimitive());
		System.out.println(int.class == Integer.class);//int类型和Integer类型是不一样的，一个是基本类型，一个是对象类型
		System.out.println(int.class == Integer.TYPE);//8中数据基本类型都对应与其对象类型中的TYPE字段
		System.out.println(int[].class.isPrimitive());//数组类型不是原始类型，数组是一个对象类型即int[]是一个Object
	}
	
	public static void example3() throws Exception{
		/**获取ReflectPoint类中的public的y字段(字段用名称来区分,方法用参数类型和个数和顺序来区分)*/
		ReflectPoint point = new ReflectPoint(3,5);
		Field fieldY = point.getClass().getField("y");
		System.out.println("y字段的类型:"+fieldY.getGenericType());//获取字段的类型
		System.out.println("y的值是:"+fieldY.get(point));//获取字段的值
		/**获取ReflectPoint类中的private的x字段*/
		Field fieldX = point.getClass().getDeclaredField("x");//没有用getField方法了，因为getField方法只能获取到public字段，getDeclaredField可以获取到所有的字段
		fieldX.setAccessible(true);//因为x字段是private私有的，所以要暴力访问
		System.out.println("x字段的类型:"+fieldX.getGenericType());
		System.out.println("x的值是:"+fieldX.get(point));
		/**将ReflectPoint对象中的String类型字段中的"j"字符换成"a"*/
		Field[] fields = point.getClass().getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			if(fields[i].getType() == String.class){
				String oldValue = (String) fields[i].get(point);
				String newValue = oldValue.replace("j", "a");
				fields[i].set(point, newValue);
			}
		}
		System.out.println(point);
	}
	
	public static void example4() throws Exception{
		Method methodCharAt = String.class.getMethod("charAt", int.class);
		System.out.println(methodCharAt.invoke("abc", 2));
		
		Method[] methods = String.class.getDeclaredMethods();
		for(int i=0;i<methods.length;i++){
			System.out.println("方法名:"+methods[i].getName());
		}
	}
	
	public static void example5() throws Exception{
		Method method = ReflectTest.class.getMethod("print", int[].class);
		//invoke(Object obj,Object...args);
		//JDK1.5:Object...
		//JDK1.4:Object[]...
		//先走1.4版本的,如果不符合就走1.5的版本,所以传递一个new Integer[]数组,因为Integer是Object,所以走JDK1.4版本的Object[] args
		//这样对应的参数是args:1,2,3 args.length=3,但是方法print只有一个参数,所以会报异常
		//如果将参数改成new int[]{1,2,3}的话,就不会了,因为int[]数组本身就是一个Object类型的,所以JDK1.4版本的走不通,所以走JDK1.5版本的
		//JDK1.5版本中,编译器拿到int[](Object)直接传给args,这时候可变参数还是会转化成Object[]数组,但是这时候args.length=1,所以不会报错
		method.invoke(null, new Integer[]{1,2,3});
	}
	
	public static void example6(){
		int[] intAry = {1,2,3};
		String[] strAry = {"a","b","c"};
		//将数组转化成list然后将内容打印出来
		System.out.println(Arrays.asList(intAry));
		System.out.println(Arrays.asList(strAry));
	}
	
	public static void example7(){
		String str = "jiangwei";
		boolean bool = true;
		byte b = 1;
		char c = 'a';
		short s = 2;
		int i = 1;
		long l = 100l;
		double d = 1.0d;
		float f = 1.2f;
		Object[] array = new Object[]{bool,b,c,s,i,l,d,f};
		printObject(str);
		printObject(array);
	}
	
	@SuppressWarnings("rawtypes")
	private static void printObject(Object obj){
		Class clazz = obj.getClass();
		if(clazz.isArray()){
		    int len = Array.getLength(obj);
		    for(int i=0;i<len;i++){
		    	Object tempObj = Array.get(obj, i);
		    	
		    	if(tempObj instanceof Boolean){
		    		
		    	}else if(tempObj instanceof Character){
		    		
		    	}else if(tempObj instanceof Byte){
		    		
		    	}else if(tempObj instanceof Short){
		    		
		    	}else if(tempObj instanceof Integer){
		    		
		    	}else if(tempObj instanceof Float){
		    		
		    	}else if(tempObj instanceof Double){
		    		
		    	}else if(tempObj instanceof Long){
		    		
		    	}
		    	
		    	Class clazz_temp = tempObj.getClass();
		    	
		    	if(clazz_temp == boolean.class){
		    		
		    	}else if(clazz_temp == char.class){
		    		
		    	}else if(clazz_temp == byte.class){
		    		
		    	}else if(clazz_temp == short.class){
		    		
		    	}else if(clazz_temp == int.class){
		    		
		    	}else if(clazz_temp == long.class){
		    		
		    	}else if(clazz_temp == float.class){
		    		
		    	}else if(clazz_temp == double.class){
		    		
		    	}else if(clazz_temp == long.class){
		    		
		    	}
		    	System.out.println(Array.get(obj, i));
		    }
		}else{
			System.out.println(obj);
		}
	}
	
}
