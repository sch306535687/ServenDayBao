package sun.ch.servendaybao.domain;

import java.util.ArrayList;

/**
 * 新闻列表数据
 * 
 * @author Kevin
 * @date 2015-8-12
 */
public class RankListData {

	public RankData Data;

	public class RankData {

		public ArrayList<DataList> list;
		public MyData myTincome;
	}
	public class MyData {
		public String uid;
		public String TINCOME;
		public String realname;
		public String ranking;
		public String c_name;

		public MyData(String uid, String TINCOME, String realname, String ranking, String c_name) {
			this.uid = uid;
			this.TINCOME = TINCOME;
			this.realname = realname;
			this.ranking = ranking;
			this.c_name = c_name;
		}

		public String getUid() {
			return uid;
		}

		public String getTINCOME() {
			return TINCOME;
		}

		public String getRealname() {
			return realname;
		}

		public String getRanking() {
			return ranking;
		}

		public String getC_name() {
			return c_name;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public void setTINCOME(String TINCOME) {
			this.TINCOME = TINCOME;
		}

		public void setRealname(String realname) {
			this.realname = realname;
		}

		public void setRanking(String ranking) {
			this.ranking = ranking;
		}

		public void setC_name(String c_name) {
			this.c_name = c_name;
		}

		@Override
		public String toString() {
			return "MyData [c_name=" + c_name + ", realname=" + realname
					+ ", TINCOME=" + TINCOME + "]";
		}
	}

	public class DataList {

		public String uid;
		public String TINCOME;
		public String realname;
		public String ranking;
		public String c_name;

		public DataList(String uid, String TINCOME, String realname, String ranking, String c_name) {
			this.uid = uid;
			this.TINCOME = TINCOME;
			this.realname = realname;
			this.ranking = ranking;
			this.c_name = c_name;
		}

		public String getUid() {
			return uid;
		}

		public String getTINCOME() {
			return TINCOME;
		}

		public String getRealname() {
			return realname;
		}

		public String getRanking() {
			return ranking;
		}

		public String getName() {
			return c_name;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public void setTINCOME(String TINCOME) {
			this.TINCOME = TINCOME;
		}

		public void setRealname(String realname) {
			this.realname = realname;
		}

		public void setRanking(String ranking) {
			this.ranking = ranking;
		}

		public void setName(String c_name) {
			this.c_name = c_name;
		}

		@Override
		public String toString() {
			return "DataList [c_name=" + c_name + ", realname=" + realname
					+ ", TINCOME=" + TINCOME + "]";
		}

	}

}
