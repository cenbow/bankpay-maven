package test;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import common.plugins.annotation.ConfirmLongField;
import common.utils.ConfirmLongUtil;
import common.utils.Enum2StringUtils;

public class Dog {

	@ConfirmLongField(length = "4")
	private String color;

	@ConfirmLongField(length = "8")
	private String age;

	@ConfirmLongField(length = "6")
	private Long height;

	@ConfirmLongField(length = "6")
	private BigDecimal wight;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public BigDecimal getWight() {
		return wight;
	}

	public void setWight(BigDecimal wight) {
		this.wight = wight;
	}

	public static <T> T resolve(Class<T> clazz) {
		T t = null;
		try {
			t = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				// 取消字段访问的闲置，是反射访问private的前提
				field.setAccessible(true);
				field.set(t, "a");
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	public static String toString(Object obj) {
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				System.out.println(field.get(obj));
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		// Dog dog = resolve(Dog.class);
		//
		// System.out.println(dog.getAge());
		// System.out.println(dog.getColor());
		Dog dog = new Dog();
		dog.setAge("re");
		dog.setColor("red");
		dog.setHeight(12L);
		dog.setWight(new BigDecimal(12.31));

		try {
			System.out.println(ConfirmLongUtil.format2StringByBytes(dog, "utf-8"));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
