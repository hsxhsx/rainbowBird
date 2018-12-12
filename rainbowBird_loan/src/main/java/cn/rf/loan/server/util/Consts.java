package cn.rf.loan.server.util;

public class Consts {
	/**
	 * redis的ip地址
	 */
	public static final String	REDIS_SERVER							= "localhost";
	/**
	 * redis的ip地址端口
	 */
	public static final String	REDIS_SERVER_PORT						= "6379";


	public enum ResultCode {
		SUCCESS					("success", 				   200);                     //成功

		// 成员变量
		private String	name;
		private int		index;

		// 构造方法
		private ResultCode(String name, int index) {
			this.name = name;
			this.index = index;
		}

		// 覆盖方法
		@Override
		public String toString() {
			return this.name;
		}

		public int getIndex() {
			return this.index;
		}
	}
}
