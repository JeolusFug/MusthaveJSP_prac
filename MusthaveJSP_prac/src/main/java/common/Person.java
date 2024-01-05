package common;

public class Person {
	private String name;
	private int age;
	
	public Person() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Person(String name, int age) {	//인수들을 받아 모든 속성을 한번에 초기화 해주는 생성자
		super();
		this.name = name;
		this.age = age;
	}
}
