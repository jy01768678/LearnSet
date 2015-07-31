import java.math.BigDecimal;


public class ScoreTest {

	//SELECT  * FROM jeehe_user_score_detail27 a WHERE a.`user_id`=17203671
	//special-16270381 lizifang 2 12
	//special-17203727 dongtao 4 4
	//special-17203671 pengmingfang 4 22
	//ugrent -19203827 balizhong 4 29
	//normal -19203930 bailizhong 3 23
	//normal -1112140 liugang 1 20
	//normal -19203947 jy01768678  4 27
	//normal -19203755 熊宝 4 11
	//normal -19203759 
	//online -8266070 肉包子 3 22
	//online -499123  4 13
	//online -13989503 jy01768678 4 32
	//dev 20539967--jy01768678 SELECT * FROM wowouserext04.`jeehe_user_score_detail16` a WHERE a.`user_id` = 20539967 ORDER BY a.`time` DESC
	//dev 20540141--dongtao select * from wowouserext02.`jeehe_user_score_detail28` a WHERE a.`user_id`=20540141
	//1.3903103207114E+18
	//select * from wowouserext03.`jeehe_user_score_detail22` a WHERE a.`user_id`=100000598 ORDER BY a.`time` DESC
	//online -1987310 自动测试账户107549618
	public static void main(String[] args) {
		int userId = 44526201;
		System.out.println("分库："+(userId % 4 +1)+ "\t分表：" + (userId %(32 * 4) / 4 + 1));
	}
}
