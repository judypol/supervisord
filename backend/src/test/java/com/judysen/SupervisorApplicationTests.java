package com.judysen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupervisorApplicationTests {
	@Test
	public void contextLoads() {
		double year=15000.00;
		double rate=1.04;
		double sum=0;
		sum=getBenjin(year,20);

		System.out.println("sum:"+String.valueOf(sum));
	}
	private double getBenjin(double benjin,int shijin){
		double sum=benjin;
		for(int i=1;i<shijin;i++){
			sum=sum*(1+ Math.pow(0.04,1))+benjin;
		}
		return sum;
	}
}
