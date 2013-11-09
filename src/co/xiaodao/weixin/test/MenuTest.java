package co.xiaodao.weixin.test;

import co.xiaodao.weixin.menu.CommonButton;
import co.xiaodao.weixin.menu.ComplexButton;
import co.xiaodao.weixin.menu.Button;
import co.xiaodao.weixin.menu.Menu;
import co.xiaodao.weixin.util.WeixinMenuUtil;

public class MenuTest {

//	public static void main(String[] args) {
//		//�ҵ�openid:
//		//ofY_njtS7-WVmIFehsR4NRwfKYAc
//		int result = WeixinMenuUtil.pushTextMsg("ofY_njtS7-WVmIFehsR4NRwfKYAc", "��������", 
//				WeixinMenuUtil.getAccessToken("wx06aa57dd3b6fb19d","93d25ba4ef404e8a7907bb9ea4124f61").getToken());
//
//		if (1 == result)
//			System.out.println("���ͳɹ���");
//		else
//			System.out.println("����ʧ�ܣ������룺" + result);
//	}
	public static void main(String[] args) {
		Menu menu = getMenu();
		int result = WeixinMenuUtil.createMenu(menu, "p2Y80bNE-XFTJXzfMOhmTxdjjo6Ym_yT7kuyKt1A2bPSRcKmOd8v4qtQQvgoOjss8PJxkzrlGSc0WuFNAN8XsNaPT5z394Y2FSoB7jqHHGaGlJbLS-ldNTcdW3Qaw2r5o9kGabHKPXwGAJbZdtVaXA");
		
		if (0 == result)
			System.out.println("�˵������ɹ���");
		else
			System.out.println("�˵�����ʧ�ܣ������룺" + result);
	}

	/**
	 * �õ��˵�����
	 * 
	 * @return
	 */
	private static Menu getMenu() {

		CommonButton btn11 = new CommonButton();
		btn11.setName("��������");
		btn11.setType("click");
		btn11.setKey("11");
		
		CommonButton btn12 = new CommonButton();
		btn12.setName("��������");
		btn12.setType("click");
		btn12.setKey("12");

		CommonButton btn13 = new CommonButton();
		btn13.setName("������ʳ");
		btn13.setType("click");
		btn13.setKey("13");

		CommonButton btn14 = new CommonButton();
		btn14.setName("�����");
		btn14.setType("click");
		btn14.setKey("14");
		
		CommonButton btn15 = new CommonButton();
		btn15.setName("�������");
		btn15.setType("click");
		btn15.setKey("15");
		
		
		CommonButton btn21 = new CommonButton();
		btn21.setName("�ҿս���");
		btn21.setType("click");
		btn21.setKey("21");
		
		CommonButton btn22 = new CommonButton();
		btn22.setName("���񹫸�");
		btn22.setType("click");
		btn22.setKey("22");
		
		CommonButton btn23 = new CommonButton();
		btn23.setName("�ҵĿα�");
		btn23.setType("click");
		btn23.setKey("23");
		
		CommonButton btn24 = new CommonButton();
		btn24.setName("���޿γ�");
		btn24.setType("click");
		btn24.setKey("24");

		CommonButton btn25 = new CommonButton();
		btn25.setName("���³ɼ�");
		btn25.setType("click");
		btn25.setKey("25");
		
		CommonButton btn30 = new CommonButton();
		btn30.setName("�������");
		btn30.setType("click");
		btn30.setKey("30");
		
		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("������");
		mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14, btn15});
		
		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("���񺣴�");
		mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24, btn25});

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, btn30 });
        
		return menu;
	}

}
