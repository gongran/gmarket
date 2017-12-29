package com.gr.market.request;

public class CreateOrderRequest {
  public static interface OrderType {
    /**
     * �޼�����
     */
    static final String BUY_LIMIT = "buy-limit";
    /**
     * �޼�����
     */
    static final String SELL_LIMIT = "sell-limit";
    /**
     * �м�����
     */
    static final String BUY_MARKET = "buy-market";
    /**
     * �м�����
     */
    static final String SELL_MARKET = "sell-market";
  }

  /**
   * ���׶ԣ�������磺"ethcny"��
   */
  public String symbol;

  /**
   * �˻�ID��������磺"12345"
   */
  public String accountId;

  /**
   * ����������Ϊbuy-limit,sell-limitʱ����ʾ���������� ����������Ϊbuy-marketʱ����ʾ�����ܽ� ����������Ϊsell-marketʱ����ʾ����������
   */
  public String amount;

  /**
   * �����۸񣬽�����޼۵���Ч�����磺"1234.56"
   */
  public String price = "0.0";

  /**
   * �������ͣ�ȡֵ��Χ"buy-market,sell-market,buy-limit,sell-limit"
   */
  public String type;

  /**
   * ������Դ�����磺"api"
   */
  public String source = "api";
}
