class Patient
{
	private String name;
	private double age, weight;
	private String bmIndex;
	
	public Patient(String name, double age, double weight, String bmIndex)
	{
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.bmIndex = bmIndex;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setAge(double age)
	{
		this.age = age;
	}
	
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	
	public void setBMIndex(String bmIndex)
	{
		this.bmIndex = bmIndex;
	}
	
	public String getName()
	{
		return this.name;
	}

	public double getAge()
	{
		return this.age;
	}

	public double getWeight()
	{
		return this.weight;
	}
	
	public String getBMIndex()
	{
		return this.bmIndex;
	}
}