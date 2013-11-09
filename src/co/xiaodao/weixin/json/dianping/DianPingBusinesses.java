package co.xiaodao.weixin.json.dianping;

import java.util.List;

/**
 * ���ڵ����̼ҵ�ʵ����
 * 
 * С��IT΢�Ź��ں���:��xiaodaoit��
 * 
 * @UpdateDate: 2013-4-21
 * @author: ��С��
 * @Email: xiaodaoit@gmail.com
 * @Copyright 2012-2015(C)xiaodao.co. All Rights Reserved.
 * 
 */
public class DianPingBusinesses {

	// �̻�ID
	private int business_id;
	// �̻���
	private String name;
	// �ֵ���
	private String branch_name;
	// ��ַ
	private String address;
	// �����ŵĵ绰
	private String telephone;
	// ���ڳ���
	private String city;
	// ����������Ϣ�б���[���������һ�]
	private String[] regions;
	// ����������Ϣ�б���[�����ˣ�����Ƶ�]
	private String[] categories;
	// γ������
	private float latitude;
	// ��������
	private float longitude;
	// �Ǽ����֣�5.0�������ǣ�4.5�������ǰ룬��������
	private float avg_rating;
	// �Ǽ�ͼƬ����
	private String rating_img_url;
	// С�ߴ��Ǽ�ͼƬ����
	private String rating_s_img_url;
	// ��Ʒ/ʳƷ��ζ���ۣ�1:һ�㣬2:�пɣ�3:�ã�4:�ܺã�5:�ǳ���
	private int product_grade;
	// �������ۣ�1:һ�㣬2:�пɣ�3:�ã�4:�ܺã�5:�ǳ���
	private int decoration_grade;;
	// �������ۣ�1:һ�㣬2:�пɣ�3:�ã�4:�ܺã�5:�ǳ���
	private int service_grade;
	// ��������
	private int review_count;
	// �̻����������ľ��룬��λΪ�ף��粻���뾭γ�����꣬���Ϊ-1
	private int distance;
	// �̻�ҳ������
	private String business_url;
	// ��Ƭ���ӣ���Ƭ���ߴ�700��700
	private String photo_url;
	// С�ߴ���Ƭ���ӣ���Ƭ���ߴ�278��200
	private String s_photo_url;
	// �Ƿ����Ż�ȯ��0:û�У�1:��
	private int has_coupon;
	// �Ż�ȯID
	private int coupon_id;
	// �Ż�ȯ����
	private String coupon_description;
	// �Ż�ȯҳ������
	private String coupon_url;
	// �Ƿ����Ź���0:û�У�1:��
	private int has_deal;
	// �̻���ǰ�����Ź�����
	private int deal_count;
	// �Ź��б�
	private List<DianPingDeal> deals;

	public int getBusiness_id() {
		return business_id;
	}

	public void setBusiness_id(int business_id) {
		this.business_id = business_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String[] getRegions() {
		return regions;
	}

	public void setRegions(String[] regions) {
		this.regions = regions;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getAvg_rating() {
		return avg_rating;
	}

	public void setAvg_rating(float avg_rating) {
		this.avg_rating = avg_rating;
	}

	public String getRating_img_url() {
		return rating_img_url;
	}

	public void setRating_img_url(String rating_img_url) {
		this.rating_img_url = rating_img_url;
	}

	public String getRating_s_img_url() {
		return rating_s_img_url;
	}

	public void setRating_s_img_url(String rating_s_img_url) {
		this.rating_s_img_url = rating_s_img_url;
	}

	public int getProduct_grade() {
		return product_grade;
	}

	public void setProduct_grade(int product_grade) {
		this.product_grade = product_grade;
	}

	public int getDecoration_grade() {
		return decoration_grade;
	}

	public void setDecoration_grade(int decoration_grade) {
		this.decoration_grade = decoration_grade;
	}

	public int getService_grade() {
		return service_grade;
	}

	public void setService_grade(int service_grade) {
		this.service_grade = service_grade;
	}

	public int getReview_count() {
		return review_count;
	}

	public void setReview_count(int review_count) {
		this.review_count = review_count;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getBusiness_url() {
		return business_url;
	}

	public void setBusiness_url(String business_url) {
		this.business_url = business_url;
	}

	public String getPhoto_url() {
		return photo_url;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

	public String getS_photo_url() {
		return s_photo_url;
	}

	public void setS_photo_url(String s_photo_url) {
		this.s_photo_url = s_photo_url;
	}

	public int getHas_coupon() {
		return has_coupon;
	}

	public void setHas_coupon(int has_coupon) {
		this.has_coupon = has_coupon;
	}

	public int getCoupon_id() {
		return coupon_id;
	}

	public void setCoupon_id(int coupon_id) {
		this.coupon_id = coupon_id;
	}

	public String getCoupon_description() {
		return coupon_description;
	}

	public void setCoupon_description(String coupon_description) {
		this.coupon_description = coupon_description;
	}

	public String getCoupon_url() {
		return coupon_url;
	}

	public void setCoupon_url(String coupon_url) {
		this.coupon_url = coupon_url;
	}

	public int getHas_deal() {
		return has_deal;
	}

	public void setHas_deal(int has_deal) {
		this.has_deal = has_deal;
	}

	public int getDeal_count() {
		return deal_count;
	}

	public void setDeal_count(int deal_count) {
		this.deal_count = deal_count;
	}

	public List<DianPingDeal> getDeals() {
		return deals;
	}

	public void setDeals(List<DianPingDeal> deals) {
		this.deals = deals;
	}

}
